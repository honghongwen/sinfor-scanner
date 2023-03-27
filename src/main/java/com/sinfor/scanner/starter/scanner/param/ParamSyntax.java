package com.sinfor.scanner.starter.scanner.param;

import cn.hutool.json.JSONUtil;
import com.sinfor.stater.net.domain.Request;
import com.sinfor.stater.net.session.Session;
import com.sinfor.stater.net.session.SessionManager;
import com.sinfor.stater.net.session.SessionUser;
import com.sinfor.stater.net.session.XfSocketSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description 参数解析
 **/
@Slf4j
public class ParamSyntax {


    private ParamSyntax() {

    }

    public static ParamSyntax getInstance() {
        return new ParamSyntax();
    }

    public Object[] buildMethodParam(Method method, String data, XfSocketSession session) {
        int paramSize = method.getGenericParameterTypes().length;
        Object[] params = new Object[paramSize];
        for (int i = 0; i < paramSize; i++) {
            Type parameterType = method.getGenericParameterTypes()[i];
            Class<?> entityClass = getRealClass(parameterType);
            data = data == null ? null : data.trim();
            try {
                if (XfSocketSession.class.equals(entityClass)) {
                    params[i] = session;
                } else if (Session.class.equals(entityClass)) {
                    params[i] = session;
                } else if (SessionUser.class.equals(entityClass)) {
                    params[i] = session.getSessionUser();
                } else if (Request.class.equals(entityClass)) {
                    params[i] = session.getRequest();
                } else {
                    params[i] = buildMethodParamValue(parameterType, entityClass, data);
                }
            } catch (ParseException e) {
                log.error("参数解析错误", e);
            }
        }
        return params;

    }

    public Class<?> getRealClass(Type parameterType) {
        // 如int.class T.class
        if (!(parameterType instanceof ParameterizedType)) {
            return (Class<?>) parameterType;
        }
        // 如List<?> Map<>...
        ParameterizedType type = (ParameterizedType) parameterType;
        for (Type t : type.getActualTypeArguments()) {
            if (t instanceof ParameterizedType) {
                // this the case when parameterized type looks like
                // this:
                // Collection<List<String>>
                // we care only for raw type List
                return (Class<?>) ((ParameterizedType) t).getRawType();
            } else {
                return (Class<?>) t;
            }
        }
        return null;
    }

    public boolean isListParam(Type parameterType) {
        return parameterType instanceof ParameterizedType;
    }


    public Object buildMethodParamValue(Type parameterType, Class<?> clazz, String value) throws ParseException {
        if (!StringUtils.hasLength(value)) {
            return null;
        }
        if (isListParam(parameterType)) {
            return JSONUtil.toBean(value, clazz);
        }
        if (String.class.equals(clazz)) {
            return value;
        }
        if (int.class.equals(clazz)) {
            return Integer.parseInt(value);
        }
        if (Integer.class.equals(clazz)) {
            return Integer.valueOf(value);
        }
        if (BigDecimal.class.equals(clazz)) {
            return new BigDecimal(value);
        }
        if (Double.class.equals(clazz)) {
            return Double.valueOf(value);
        }
        if (double.class.equals(clazz)) {
            return Double.parseDouble(value);
        }
        if (Long.class.equals(clazz)) {
            return Long.valueOf(value);
        }
        if (long.class.equals(clazz)) {
            return Long.parseLong(value);
        }
        if (Boolean.class.equals(clazz)) {
            return Boolean.valueOf(value);
        }
        if (boolean.class.equals(clazz)) {
            return Boolean.parseBoolean(value);
        }
        if (Short.class.equals(clazz)) {
            return Short.valueOf(value);
        }
        if (short.class.equals(clazz)) {
            return Short.parseShort(value);
        }
        if (Float.class.equals(clazz)) {
            return Float.valueOf(value);
        }
        if (float.class.equals(clazz)) {
            return Float.parseFloat(value);
        }
        if (Date.class.equals(clazz)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(value);
        }
        return JSONUtil.toBean(value, clazz);
    }


}
