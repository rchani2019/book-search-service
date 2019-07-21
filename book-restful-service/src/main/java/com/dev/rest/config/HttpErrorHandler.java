package com.dev.rest.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.dev.rest.exception.E502BadGatewayException;

public class HttpErrorHandler extends DefaultResponseErrorHandler {

	Logger logger = LoggerFactory.getLogger(HttpErrorHandler.class);

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		// @formatter:off
		return (response.getStatusCode().series() == Series.CLIENT_ERROR
				|| response.getStatusCode().series() == Series.SERVER_ERROR);
		// @formatter:on
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		logger.warn("Fail to interact with Http");
		traceResponse(response);

		throw new E502BadGatewayException();
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
		String line = bufferedReader.readLine();
		while (line != null) {
			inputStringBuilder.append(line);
			inputStringBuilder.append('\n');
			line = bufferedReader.readLine();
		}
		logger.error("=============== response begin ===============");
		logger.error("Status code  : {}", response.getStatusCode());
		logger.error("Status text  : {}", response.getStatusText());
		logger.error("Headers      : {}", response.getHeaders());
		logger.error("Response body: {}", inputStringBuilder.toString());
		logger.error("=============== response end ===============");

	}
}
