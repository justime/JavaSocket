package dlut.xyz.exception;

public class FileNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 156L;
	public FileNotExistsException(String filePath) {
		super("文件"+filePath+"不存在");
	}
}
