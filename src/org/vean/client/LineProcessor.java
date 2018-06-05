package org.vean.client;

import java.io.IOException;

import org.vean.common.SocketWrapper;
import org.vean.common.TransferTypeEnum;
import org.vean.common.Utils;
import org.vean.exception.InputNotExistsException;
import org.vean.exception.ServerException;
import org.vean.transfer.Transferable;

public class LineProcessor {
	private String[] tokens;
	private Transferable transferable = null;

	public LineProcessor(String line) throws InputNotExistsException {
		line = preLine(line).trim();
		String transferTypeName = null;
		if (line.trim().length() == 0) {
			tokens = new String[] { "", "" };
			transferTypeName = "help";
		} else {
			tokens = line.trim().split("\\s+");
			transferTypeName = tokens[0];
		}
		try {
			Class<? extends Transferable> clazz = TransferTypeEnum.getTransferTypeByName(transferTypeName)
					.getTransferClass();
			transferable = clazz.getConstructor(String[].class).newInstance(new Object[] { tokens });
		} catch (Exception e) {
			Utils.println(e.getMessage());
		}
	}

	public void transfer(SocketWrapper socketWrapper) throws IOException, ServerException {
		if (transferable != null) {
			socketWrapper.write(transferable.getTransferType().getTransferTypeByte());// 发送消息类型
			transferable.transfer(socketWrapper);// 传输消息
		}
	}

	/**
	 * 对客户端的命令进行预处理
	 * 
	 * @param line
	 * @return
	 */
	private String preLine(String line) {
		if (line == null)
			return "";
		if (line.startsWith(">"))
			return line.substring(1);
		return line;
	}
}
