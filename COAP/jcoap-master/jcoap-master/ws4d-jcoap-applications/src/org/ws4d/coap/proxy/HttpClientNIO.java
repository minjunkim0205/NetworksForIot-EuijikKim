/*
 * Copyright 2012 University of Rostock, Institute of Applied Microelectronics and Computer Engineering
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This work has been sponsored by Siemens Corporate Technology. 
 *
 */
package org.ws4d.coap.proxy;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Christian Lerche <christian.lerche@uni-rostock.de>
 * @author Andy Seidel <andy.seidel@uni-rostock.de>
 */
public class HttpClientNIO extends Thread {
	private static Logger logger = LogManager.getLogger();
	private DefaultHttpAsyncClient httpClient;

	public HttpClientNIO() {
		try {
			this.httpClient = new DefaultHttpAsyncClient();
			this.httpClient.start();
			logger.info("HTTP client started");
		} catch (IOReactorException e) {
			logger.error(e.getLocalizedMessage());
			System.exit(-1);
		}
	}

	public void sendRequest(ProxyMessageContext context) {
		// future is used to receive response asynchronous, without blocking
		// ProxyHttpFutureCallback allows to associate a ProxyMessageContext
		logger.info("send HTTP request");
		ProxyHttpFutureCallback fc = new ProxyHttpFutureCallback(context);
		this.httpClient.execute(context.getOutHttpRequest(), fc);
	}

	private class ProxyHttpFutureCallback implements FutureCallback<HttpResponse> {
		private ProxyMessageContext context = null;
		private final Logger callbackLogger = LogManager.getLogger();
		private ProxyMapper callbackMapper = ProxyMapper.getInstance();

		public ProxyHttpFutureCallback(ProxyMessageContext messageContext) {
			this.context = messageContext;
		}

		// this is called when response is received
		public void completed(final HttpResponse response) {
			if (this.context != null) {
				this.context.setInHttpResponse(response);
				this.callbackMapper.handleHttpClientResponse(this.context);
			}
		}

		public void failed(final Exception ex) {
			this.callbackLogger.warn("HTTP client request failed");
			if (this.context != null) {
				this.context.setInHttpResponse(
						new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_NOT_FOUND, ex.getMessage()));
				this.callbackMapper.handleHttpClientResponse(this.context);
			}
		}

		public void cancelled() {
			this.callbackLogger.warn("HTTP Client Request cancelled");
			if (this.context != null) {
				/* null indicates no response */
				this.context.setInHttpResponse(new BasicHttpResponse(HttpVersion.HTTP_1_1,
						HttpStatus.SC_INTERNAL_SERVER_ERROR, "http connection canceled"));
				this.callbackMapper.handleHttpClientResponse(this.context);
			}
		}
	}
}
