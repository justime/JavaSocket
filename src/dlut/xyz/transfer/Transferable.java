package dlut.xyz.transfer;

import java.io.IOException;

import dlut.xyz.common.SocketWrapper;
import dlut.xyz.common.TransferTypeEnum;
import dlut.xyz.exception.ServerException;

public interface Transferable {
	/**
	 * 返回传输类型
	 * @return
	 */
	public TransferTypeEnum getTransferType();
	/**
	 * 进行传输
	 */
	public void transfer(SocketWrapper socketWrapper) throws IOException,ServerException ;

}
