package com.dsky.baas.gameinvite.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpUtil {

	public static HttpUtilResultBean sendGet(String url) {
		HttpUtilResultBean resultBean = new HttpUtilResultBean();
		BufferedReader in = null;
		Map<String, List<String>> headerMap = null;
		StringBuffer result = new StringBuffer("");

		try {
			URL allUrl = new URL(url);
			URLConnection connection = allUrl.openConnection();
			// 获得相应头
			headerMap = connection.getHeaderFields();
			resultBean.setHeader(headerMap);
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			resultBean.setBody(result.toString());
		} catch (Exception e) {
			resultBean.setException(e);
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					resultBean.setException(e);
				}
			}

		}
		return resultBean;

	}
}

