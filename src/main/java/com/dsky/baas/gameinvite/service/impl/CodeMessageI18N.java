package com.dsky.baas.gameinvite.service.impl;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.service.ICodeMessageI18N;

@Service("codeMessageI18N")
public class CodeMessageI18N implements ICodeMessageI18N,ApplicationContextAware{
	private final Logger log = Logger.getLogger(CodeMessageI18N.class);
	private ApplicationContext ac;
	private MessageSource msgSrc;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
		msgSrc = (MessageSource)ac.getBean("messageSource");
	}
	
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage,
			Locale locale) {
		return msgSrc.getMessage(code, args, defaultMessage, locale);
		
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale)
			throws NoSuchMessageException {
		return msgSrc.getMessage(code, args, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale)
			throws NoSuchMessageException {
		return msgSrc.getMessage(resolvable, locale);
	}

	@Override
	public String getMessage(String code, Object[] args) {

		return msgSrc.getMessage(code, args, Locale.CHINA);
	}

	@Override
	public String getMessage(String code) {
		return msgSrc.getMessage(code, null, Locale.CHINA);
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage) {
		return msgSrc.getMessage(code, args, defaultMessage, Locale.CHINA);
	}

	@Override
	public String getMessage(String code, String defaultMessage) {
		return msgSrc.getMessage(code, null, defaultMessage, Locale.CHINA);
	}


}
