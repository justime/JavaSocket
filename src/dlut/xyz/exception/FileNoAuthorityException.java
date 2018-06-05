package dlut.xyz.exception;

public class FileNoAuthorityException extends RuntimeException {
	private static final long serialVersionUID = 100L;
	public FileNoAuthorityException(String filePath) {
		super("没有操作该文件的权限");
	}
}
