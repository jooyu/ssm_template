package com.dsky.baas.gameinvite.lib;


import com.alibaba.fastjson.JSON;
public class ApiResultPacker {

	public static ApiResultObject packToApiResultObject(Integer code,String msg,Object data,Object ext){
		ApiResultObject resultObj = new ApiResultObject();
		
		if(code==null){
			code = 0;
		}
		resultObj.setCode(code);
		
		if(msg==null){
			msg = "";
		}
		resultObj.setMsg(msg);
		
		if(data!=null){
			resultObj.setData(data);
		}
		
		if(ext!=null){
			resultObj.setExt(ext);
		}
		
		
		return resultObj;
		
	}
	public static ApiResultObject packToApiResultObject(Integer code,String msg,Object data){
		return packToApiResultObject(code,msg,data,null);
	}
	public static ApiResultObject packToApiResultObject(Integer code,String msg){
		return packToApiResultObject(code,msg,null,null);
	}

	public static ApiResultObject packToApiResultObject(Object data){
		
		return packToApiResultObject(null,null,data,null);
	}
	public static ApiResultObject packToApiResultObject(Object data,Object ext){
		
		return packToApiResultObject(null,null,data,ext);
	}



	public static ApiResultErrorObject packToApiResultErrorObject(Integer code,String msg){
		ApiResultErrorObject resultErrObj = new ApiResultErrorObject();
		
		if(code==null){
			code = 0;
		}
		resultErrObj.setCode(code);
		
		if(msg==null){
			msg = "";
		}
		resultErrObj.setMsg(msg);
		
		return resultErrObj;
	}
	
	
	public static String packToJSON(Integer code,String msg,Object data,Object ext){
		
		return JSON.toJSONString(packToApiResultObject(code,msg,data,ext));
	}
	public static String packToJSON(Integer code,String msg,Object data){
		return packToJSON(code,msg,data,null);
	}
	public static String packToJSON(Integer code,String msg){
		return packToJSON(code,msg,null,null);
	}	
	public static String packToJSON(Object data){
		
		return packToJSON(null,null,data,null);
	}
	public static String packToJSON(Object data,Object ext){
		
		return packToJSON(null,null,data,ext);
	}
	
	
}

