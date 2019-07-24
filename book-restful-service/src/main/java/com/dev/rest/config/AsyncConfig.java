package com.dev.rest.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync(proxyTargetClass=true)
public class AsyncConfig implements AsyncConfigurer{
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ThreadPoolTaskExecutor searchRankCalThreadPoolTaskExecutor;

	@Override
	public Executor getAsyncExecutor() {
		return searchRankCalThreadPoolTaskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncExceptionHandler();
	}
	
	public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	    /**
	     * AsyncTask 에서 오류 발생 시 실행
	     */
	    @Override
	    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
	        log.error("==============>>>>>>>>>>>> THREAD ERROR");
	        log.error("Exception Message :: " + throwable.getMessage());
	        log.error("Method Name :: " + method.getName());
	        for (Object param : obj) {
	        		log.error("Parameter Value :: " + param);
	        }
	        log.error("Trace Log :: ", throwable);
	        
	        // JOB_LOG : 종료 입력
	        // ...
	        log.error("==============>>>>>>>>>>>> THREAD ERROR END");
	    }
	 
	}
}


