package com.dev.rest.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadPoolExecutor;

import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class EtcConfig {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ObjectMapper mapper() {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setTimeZone(TimeZone.getDefault());
		return objectMapper;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        
		// @formatter:off
		RestTemplate restTemplate = restTemplateBuilder
				.setConnectTimeout(3000)
				.setReadTimeout(3000)
				.errorHandler(new HttpErrorHandler())
				.messageConverters(messageConverters)
				.build();
		return restTemplate;
		// @formatter:on
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor() {
		MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource());
		return messageSourceAccessor;
	}
	
	@Bean(destroyMethod = "destroy")
    public ThreadPoolTaskExecutor searchRankCalThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
        tpte.setAwaitTerminationSeconds(60);
        tpte.setCorePoolSize(16);
        tpte.setMaxPoolSize(32);
        tpte.setQueueCapacity(1024 * 4);
        tpte.setThreadGroupName("searchRankCalTG");
        tpte.setThreadNamePrefix("searchRankCalTG-");
        tpte.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        tpte.setWaitForTasksToCompleteOnShutdown(true);
        tpte.initialize();
        return tpte;
    }
	
	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void threadPoolTaskExecutorStatus() {
		ThreadPoolTaskExecutor tpte = searchRankCalThreadPoolTaskExecutor();
		if (tpte.getThreadPoolExecutor().getQueue().size() > 0) {
			log.info("searchRankTG status : {} Active, {} Pool Size, {} Queue Size, {} Completed",
					tpte.getActiveCount(), tpte.getPoolSize(), tpte.getThreadPoolExecutor().getQueue().size(),
					tpte.getThreadPoolExecutor().getCompletedTaskCount());
		}

	}
}
