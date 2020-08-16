package com.vix.digital.services.online.exception;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class CustomExceptionHandlerTest {

    HttpHeaders headers = new HttpHeaders();

    @Mock
    WebRequest request;

    @InjectMocks
    CustomExceptionHandler errorHandlerAdvice;

    @Test
    void shouldHandleAllExceptionThrown() {

        IllegalArgumentException exception = mock(IllegalArgumentException.class);
        ResponseEntity<Object> re = errorHandlerAdvice.handleAllExceptions(exception);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse ExceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(ExceptionResponse.getMessage()).isNull();
        assertThat(ExceptionResponse.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(ExceptionResponse.getTimestamp()).isNotNull();
    }

    @Test
    void shouldHandleServiceNotFoundExceptionWhenExceptionThrown() {

        ServiceNotFoundException exception = mock(ServiceNotFoundException.class);
        Mockito.when(exception.getMessage()).thenReturn("Service Not Found");

        ResponseEntity<Object> re = errorHandlerAdvice.handleServiceNotFoundException(exception);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse ExceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(ExceptionResponse.getMessage()).contains("Service Not Found");
        assertThat(ExceptionResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(ExceptionResponse.getTimestamp()).isNotNull();
    }

    @Test
    void shouldHandleMethodArgumentNotValidWhenExceptionThrown() {

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        Mockito.when(exception.getMessage()).thenReturn("Validation Failed");
        ResponseEntity<Object> re = errorHandlerAdvice.handleMethodArgumentNotValid(exception, headers, HttpStatus.BAD_REQUEST, request);

        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(re.getBody()).isInstanceOf(ExceptionResponse.class);
        ExceptionResponse ExceptionResponse = (ExceptionResponse) re.getBody();
        assertThat(ExceptionResponse.getMessage()).isNotNull();
        assertThat(ExceptionResponse.getMessage()).isEqualTo("Validation Failed");
        assertThat(ExceptionResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(ExceptionResponse.getTimestamp()).isNotNull();
    }

}
