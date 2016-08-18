package com.dsky.baas.gameinvite.lib;

public final class ApiResultCode {
	
	public static final int NORMAL_ERROR = 100; //一般错误
	
	public static final int AUTH_FAIL = 1000; //鉴权失败
	
	public static final int MISS_REQUIRE_PARAM_DEVICE_ID = 2010;
	public static final int MISS_REQUIRE_PARAM_GAME_ID = 2020;
	public static final int MISS_REQUIRE_PARAM_PLAYER_ID = 2030;
	public static final int MISS_REQUIRE_PARAM_LEVEL = 2040;
	public static final int MISS_REQUIRE_PARAM_INVITE_CODE = 2050;
	
	public static final int NO_ACT_IS_AVAILABLE = 3010; //没有可用活动ID
	public static final int ACT_NO_AVAILABLE = 3011; //活动不可用
	public static final int LEVEL_LOW = 3020; //等级太低
	public static final int CODE_GENERATE_DEVICE_UPPER_LIMIT = 3030; //设备上的code生成到了上限
	public static final int CODE_NOT_EXISTS = 3040;//邀请码不存在
	public static final int PLAYER_HAVE_BEEN_INVITED = 3050; //玩家已经被邀请
	public static final int CODE_APPLY_DEVICE_UPPER_LIMIT = 3060; //设备上的code使用到了上限
	

	private ApiResultCode(){}
	
	
}
