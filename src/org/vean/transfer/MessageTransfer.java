package org.vean.transfer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.vean.common.SocketWrapper;
import org.vean.common.TransferTypeEnum;
import org.vean.common.Utils;

public class MessageTransfer implements Transferable {
	private String message;// 消息内容
	private byte[] messageBytes;
	private int length = 0;// 消息长度

	public MessageTransfer(String[] tokens) throws UnsupportedEncodingException {
		if (tokens.length >= 2) {
			message = tokens[1];
			messageBytes = message.getBytes("utf-8");
			length = messageBytes.length;
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * 返回传输类型
	 * 
	 * @return
	 */
	@Override
	public TransferTypeEnum getTransferType() {
		return TransferTypeEnum.MESSAGE;
	}

	/**
	 * 进行传输
	 */
	@Override
	public void transfer(SocketWrapper socketWrapper) throws IOException {
		Utils.println("我此时向服务器端发送消息：" + message);
		socketWrapper.write(length);
		socketWrapper.write(messageBytes);
		Utils.println("发送消息完毕。");
	}
}
