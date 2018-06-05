package org.vean.common;

import org.vean.transfer.DefaultTransfer;
import org.vean.transfer.DownloadFileTransfer;
import org.vean.transfer.MessageTransfer;
import org.vean.transfer.Transferable;
import org.vean.transfer.UploadFileTransfer;

public enum TransferTypeEnum {
	HELP("help", (byte) 0, DefaultTransfer.class), 
	MESSAGE("message", (byte) 1, MessageTransfer.class), 
	UPLOADFILE("upload", (byte) 2,UploadFileTransfer.class), 
	DOWNLOADFILE("download", (byte) 3, DownloadFileTransfer.class);

	private String transferTypeName;
	private byte transferTypeByte;
	private Class<? extends Transferable> transferClass;

	private TransferTypeEnum(String transferTypeName, byte transferTypeByte,
			Class<? extends Transferable> transferClass) {
		this.transferTypeName = transferTypeName;
		this.transferTypeByte = transferTypeByte;
		this.transferClass = transferClass;
	}

	public String getTransferTypeName() {
		return transferTypeName;
	}

	public void setTransferTypeName(String transferTypeName) {
		this.transferTypeName = transferTypeName;
	}

	public byte getTransferTypeByte() {
		return transferTypeByte;
	}

	public void setTransferTypeByte(byte transferTypeByte) {
		this.transferTypeByte = transferTypeByte;
	}

	public Class<? extends Transferable> getTransferClass() {
		return transferClass;
	}

	public void setTransferClass(Class<? extends Transferable> transferClass) {
		this.transferClass = transferClass;
	}

	/**
	 * 根据传输类型名称返回传输类型
	 * 
	 * @param transferTypeName
	 * @return
	 */
	public static TransferTypeEnum getTransferTypeByName(String transferTypeName) {
		for (TransferTypeEnum transferType : TransferTypeEnum.values()) {
			if (transferType.getTransferTypeName().equals(transferTypeName))
				return transferType;
		}
		return TransferTypeEnum.MESSAGE;
	}

	/**
	 * 判断是否是合法的类型
	 * 
	 * @param transferTypeEnum
	 * @return
	 */
	public static boolean isValidTransferType(TransferTypeEnum transferTypeEnum) {
		for (TransferTypeEnum transferType : TransferTypeEnum.values()) {
			if (transferType.getTransferTypeByte() == transferTypeEnum.getTransferTypeByte())
				return true;
		}
		return false;
	}

}
