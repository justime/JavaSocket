package org.vean.exception;

public class ServerException extends RuntimeException {
	private static final long serialVersionUID = 186L;

	public ServerException(String msg) {
		super("服务器内部错误:" + msg);
	}
}
