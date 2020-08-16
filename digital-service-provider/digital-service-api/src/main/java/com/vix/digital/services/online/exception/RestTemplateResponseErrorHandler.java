package com.vix.digital.services.online.exception;

/**
 * Response handler to handle error for scheduler.
 *
 * @Author Subhasis Swain
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ResponseErrorHandler.class);
 
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
      throws IOException {
 
        return (
          httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
          || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR
            log.error("SERVER ERROR ...........");
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
            log.error("CLIENT ERROR ...........");
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Service Not Available ");
            }
        }
    }
}