package com.dsky.baas.gameinvite.util;

import java.util.zip.CRC32;

/**
 * 
 * @author eaves.zhu
 * CRC校验，转换设备号成数字
 */
public class DeviceIdUtil {
/**
 * @author eaves.zhu
 * @param deviceId
 * @return int 
 * CRC32
 */
   public static long getCRC32(String deviceId){
	        CRC32 crc32 = new CRC32();  
	        crc32.update(deviceId.getBytes());
	            long tmp = crc32.getValue();
	            return tmp;
   }

}