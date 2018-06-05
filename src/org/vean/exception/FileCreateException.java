package org.vean.exception;

public class FileCreateException extends RuntimeException {
	private static final long serialVersionUID = 156L;

	public FileCreateException(String filePath) {
		super("文件创建失败" + filePath);
	}
}
