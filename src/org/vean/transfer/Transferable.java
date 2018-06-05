package org.vean.transfer;

import java.io.IOException;

import org.vean.common.SocketWrapper;
import org.vean.common.TransferTypeEnum;

public interface Transferable {
	/**
	 * 返回传输类型
	 * 
	 * @return
	 */
	public TransferTypeEnum getTransferType();

	/**
	 * 进行传输
	 */
	public void transfer(SocketWrapper socketWrapper) throws IOException;

}
