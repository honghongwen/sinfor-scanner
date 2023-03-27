package com.sinfor.scanner.eun;

/**
 * 
 * @author 李新周
 *
 */
public interface XfSiteTypeDefine {
	/**
	 * 总部
	 */
	int XF_HQ = 0;

	/**
	 * 财务中心
	 */
	int XF_PAY_CENTER = 1;

	/**
	 * 分拔中心
	 */
	int XF_SCAN_CENTER = 2;

	/**
	 * 财务网点
	 */
	int XF_PAY_SITE = 3;

	/**
	 * 虚拟网点
	 */
	int XF_INVENTED_SITE = 4;

	/**
	 * 虚拟车线1
	 */
	int XF_INVENTED_CAR1 = 5;

	/**
	 * 虚拟车线2
	 */
	int XF_INVENTED_CAR2 = 6;

	/**
	 * 呼叫中心
	 */
	int XF_CALL_CENTER = 7;

	/**
	 * 承包区
	 */
	int XF_CONTRACT_AREA = 8;
	/**
	 * 总部权限
	 */
	int XF_HQ_RIGHTS = 1;
	/**
	 * 财务中心
	 */
	int XF_PAY_CENTER_RIGHTS = XF_HQ_RIGHTS * 2;
	/**
	 * 分拔中心
	 */
	int XF_SCAN_CENTER_RIGHTS = XF_PAY_CENTER_RIGHTS * 2;
	/**
	 * 财务网点
	 */
	int XF_PAY_SITE_RIGHTS = XF_SCAN_CENTER_RIGHTS * 2;
	/**
	 * 虚拟网点
	 */
	int XF_INVENTED_SITE_RIGHTS = XF_PAY_SITE_RIGHTS * 2;
	/**
	 * 虚拟车线1
	 */
	int XF_INVENTED_CAR1_RIGHTS = XF_INVENTED_SITE_RIGHTS * 2;
	/**
	 * 虚拟车线2
	 */
	int XF_INVENTED_CAR2_RIGHTS = XF_INVENTED_CAR1_RIGHTS * 2;
	/**
	 * 呼叫中心
	 */
	int XF_CALL_CENTER_RIGHTS = XF_INVENTED_CAR2_RIGHTS * 2;
	/**
	 * 承包区
	 */
	int XF_CONTRACT_AREA_RIGHTS = XF_CALL_CENTER_RIGHTS * 2;
	/**
	 * 所有用户都有权限
	 */
	int XF_ALL_RIGHTS = 511;
}
