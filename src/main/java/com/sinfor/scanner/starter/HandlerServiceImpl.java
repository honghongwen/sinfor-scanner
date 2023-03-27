package com.sinfor.scanner.starter;

import com.sinfor.scanner.annotation.XfModule;
import com.sinfor.scanner.exception.CommandNotFoundException;
import com.sinfor.scanner.exception.ModuleNotFoundException;
import com.sinfor.scanner.result.Result;
import com.sinfor.scanner.result.ResultDefaultCode;
import com.sinfor.scanner.starter.scanner.Invoke;
import com.sinfor.scanner.starter.scanner.ScannerContext;
import com.sinfor.scanner.starter.scanner.param.ParamSyntax;
import com.sinfor.scanner.starter.scanner.param.ParamValidate;
import com.sinfor.scanner.starter.scanner.util.InvokeKeyGen;
import com.sinfor.stater.net.domain.Request;
import com.sinfor.stater.net.domain.Response;
import com.sinfor.stater.net.exports.ChannelContextHolder;
import com.sinfor.stater.net.exports.RequestHolder;
import com.sinfor.stater.net.exports.service.HandlerService;
import com.sinfor.stater.net.session.SessionManager;
import com.sinfor.stater.net.session.XfSocketSession;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author fengwen
 * @date 2022/4/28
 * @description 入口service。
 * 依赖scanner，读取scanner内存中的invoke，反射调用对应cmd version方法。
 **/
@Slf4j
@Service
public class HandlerServiceImpl implements HandlerService {

    @Override
    public void before() {
        Request request = RequestHolder.getReq();
        int module = request.getModule();
        int cmd = request.getCmd();
        String version = request.getVersion();
        String sign = request.getSign();
        String data = request.getData();
        log.info("[request data]: module:{},cmd:{},version:{},签名:{}\n参数:{}", module, cmd, version, sign, data);
    }

    @Override
    public Object execute() {
        Request request = RequestHolder.getReq();
        String data = request.getData();
        // 获取到Scanner内的module和cmd
        int module = request.getModule();
        int cmd = request.getCmd();
        String version = request.getVersion();
        XfModule xfModule = ScannerContext.moduleMap.get(module);
        if (xfModule == null) {
            throw new ModuleNotFoundException("未找到模块" + module);
        }

        Map<String, Invoke> commandMaps = ScannerContext.commandMap.get(module);
        Invoke invoke = commandMaps.get(InvokeKeyGen.gen(cmd, version));
        if (invoke == null) {
            throw new CommandNotFoundException("模块" + module + "下未找到命令cmd:" + cmd + "version:" + version);
        }

        ChannelHandlerContext ctx = ChannelContextHolder.getChannelCtx();
        XfSocketSession session = SessionManager.getAttachment(ctx.channel());

        // 通过反射执行方法
        Object[] params = ParamSyntax.getInstance().buildMethodParam(invoke.getMethod(), data, session);

        // 参数注解解析
        ParamValidate validate = ParamValidate.getInstance();
        Result<String> checkMsg = validate.valid(invoke, params);
        if (checkMsg != null) {
            return checkMsg;
        }

        try {
            return invoke.invoke(params);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("[反射执行方法错误]:", e);
            ctx.writeAndFlush(Response.getInstance(request).fail("服务器内部错误，解析方法错误。").encrypt());
        }
        return null;
    }


    @Override
    public void after(Object obj) {
        Request request = RequestHolder.getReq();
        ChannelHandlerContext ctx = ChannelContextHolder.getChannelCtx();
        log.info("[请求Result返回结果]:{}", obj);
        if (obj instanceof Response) {
            Response response = (Response) obj;
            ctx.writeAndFlush(response);
        }
        if (obj instanceof Result) {
            Result<?> result = (Result<?>) obj;
            Object data = result.getData();
            Integer code = result.getCode();
            if (code.equals(ResultDefaultCode.SUCCESS().getCode())) {
                ctx.writeAndFlush(Response.getInstance(request).ok(data).code(code).encrypt());
            } else {
                ctx.writeAndFlush(Response.getInstance(request).fail(data).code(result.getCode()).encrypt());
            }
        } else {
            // 可能数据已经在内部用ctx写回。或者在execute方法中执行错误已经返回错误数据包。
            log.debug("[方法无数据包返回...]");
        }
    }
}
