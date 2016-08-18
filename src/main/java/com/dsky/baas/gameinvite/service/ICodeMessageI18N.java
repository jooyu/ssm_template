package com.dsky.baas.gameinvite.service;

import org.springframework.context.MessageSource;

public interface ICodeMessageI18N extends MessageSource{
	public String getMessage(String code, Object[] args, String defaultMessage);
	public String getMessage(String code, Object[] args);
	public String getMessage(String code,String defaultMessage);
	public String getMessage(String code);
	
}
