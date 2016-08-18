package com.dsky.baas.gameinvite.service.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;

import com.alibaba.fastjson.JSON;

public class TestRemoteInvocationFactory implements RemoteInvocationFactory {

	@Override
	public RemoteInvocation createRemoteInvocation(
			MethodInvocation methodInvocation) {
		System.out.println(JSON.toJSON(methodInvocation)+"~~~~~~~~~~");
		return null;
	}

}
