package dlut.xyz.exception;

public class ParamNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ParamNotExistsException() {
		super("参数不存在");
	}
}
