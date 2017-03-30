package org.sprouts.digitalmusic.backend.service;

import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractService {
	
	@ExceptionHandler(Throwable.class)
	protected int handleException(Throwable throwable) {
		return 0;
	}
}
