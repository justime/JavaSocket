package dlut.xyz.exception;

public class InputNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 19L;
	public InputNotExistsException() {
		super("输入为空");
	}
}
