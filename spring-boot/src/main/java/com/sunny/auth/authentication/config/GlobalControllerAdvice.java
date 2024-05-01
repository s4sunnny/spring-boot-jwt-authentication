package com.sunny.auth.authentication.config;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sunny.auth.authentication.utils.ResponseUtils;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ SQLException.class })
	public ResponseEntity<Object> handleSQLException(final SQLException ex, final WebRequest request) {
		ex.printStackTrace();
		ResponseUtils responseUtil = new ResponseUtils();
		responseUtil.setMessage(ex.getLocalizedMessage());
		responseUtil.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return handleExceptionInternal(ex, responseUtil, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ ApplicationException.class })
	public ResponseEntity<Object> handleMyRequest(final RuntimeException ex, final WebRequest request) {
		ex.printStackTrace();
		ResponseUtils responseUtil = new ResponseUtils();
		responseUtil.setMessage(ex.getLocalizedMessage());
		responseUtil.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return handleExceptionInternal(ex, responseUtil, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleException(final RuntimeException ex, final WebRequest request) {
		ex.printStackTrace();
		ResponseUtils responseUtil = new ResponseUtils();
		responseUtil.setMessage(ex.getLocalizedMessage());
		responseUtil.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return handleExceptionInternal(ex, responseUtil, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(final RuntimeException ex, final WebRequest request) {
		ex.printStackTrace();
		ResponseUtils responseUtil = new ResponseUtils();
		responseUtil.setMessage(ex.getLocalizedMessage());
		responseUtil.setStatus(HttpStatus.FORBIDDEN.value());
		return handleExceptionInternal(ex, responseUtil, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(final RuntimeException ex, final WebRequest request) {
		ex.printStackTrace();
		ResponseUtils responseUtil = new ResponseUtils();
		responseUtil.setMessage(ex.getLocalizedMessage());
		responseUtil.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return handleExceptionInternal(ex, responseUtil, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ TransactionSystemException.class })
	public ResponseEntity<Object> handleTransactionException(final RuntimeException ex, final WebRequest request) {
		ex.printStackTrace();
		ResponseUtils responseUtil = new ResponseUtils();
		responseUtil.setMessage(ex.getLocalizedMessage());
		responseUtil.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return handleExceptionInternal(ex, responseUtil, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ResponseUtils responseUtil = new ResponseUtils();
		responseUtil.setMessage(ex.getLocalizedMessage());
		responseUtil.setStatus(HttpStatus.BAD_REQUEST.value());
		return handleExceptionInternal(ex, responseUtil, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
