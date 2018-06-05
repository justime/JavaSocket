package org.vean.server;

import java.io.File;
import java.io.IOException;

import org.vean.common.CharsetEnum;
import org.vean.common.Properties;
import org.vean.common.SocketWrapper;
import org.vean.common.TransferTypeEnum;
import org.vean.common.Utils;
import org.vean.exception.FileNoAuthorityException;
import org.vean.exception.FileNotExistsException;

public class Worker extends Thread {
	private SocketWrapper socketWrapper;
	private String name;// 线程名称

	public Worker(SocketWrapper socketWrapper, String name) {
		this.socketWrapper = socketWrapper;
		this.name = name;
		Utils.println("线程" + name + "接受客户端的请求");
		this.start();
	}

	public void run() {
		while (true) {
			try {
				if (this.isInterrupted())
					break;
				byte type = socketWrapper.readByte();
				if (TransferTypeEnum.HELP.getTransferTypeByte() == type) {
					processDefault();
				} else if (TransferTypeEnum.MESSAGE.getTransferTypeByte() == type) {
					processMessage();
				} else if (TransferTypeEnum.UPLOADFILE.getTransferTypeByte() == type) {
					processUploadFile();
				} else if (TransferTypeEnum.DOWNLOADFILE.getTransferTypeByte() == type) {
					processDownloadFile();
				}
				// this.interrupt();
			} catch (IOException e) {
				Utils.println("线程" + name + "异常：" + e.getMessage());
				this.interrupt();
			}
		}
		try {
			this.socketWrapper.close();
		} catch (IOException e) {
			Utils.println("线程" + name + "的socket关闭失败");
		}
	}

	public void interrupt() {
		if (this.isAlive()) {
			super.interrupt();
			Utils.println("线程" + this.name + "中断");
		}
	}

	/**
	 * 处理默认传送的方法
	 * 
	 * @throws IOException
	 */
	private void processDefault() throws IOException {
		Utils.println("收到来自客户端的help请求");
	}

	/**
	 * 处理消息传送的方法
	 * 
	 * @throws IOException
	 */
	private void processMessage() throws IOException {
		int length = socketWrapper.readInt();
		byte[] message = new byte[length];
		socketWrapper.read(message);
		Utils.println("收到来自客户端的消息" + new String(message, CharsetEnum.UTF8.getCharsetName()));
	}

	/**
	 * 处理文件上传的方法
	 * 
	 * @throws IOException
	 */
	private void processUploadFile() throws IOException {
		// 读取文件名字的长度
		int fileNameLength = socketWrapper.readInt();
		// 读取文件名字
		Utils.println(fileNameLength + "********");
		byte[] fileNameBytes = new byte[fileNameLength];
		socketWrapper.readFull(fileNameBytes);
		String fileName = new String(fileNameBytes, CharsetEnum.UTF8.getCharsetName());

		File destFile = this.createOnlyFile(fileName);
		socketWrapper.write(1);// 表示文件可以上传了
		Utils.println("线程" + name + "开始接收文件" + fileName);

		long fileLength = socketWrapper.readLong();// 读取文件长度
		this.socketWrapper.readToFile(destFile, fileLength);
		socketWrapper.write(1);// 表示上传完毕
		Utils.println("线程" + name + "接收文件" + fileName + "完毕");
	}

	/**
	 * 处理文件下载的方法
	 */
	private void processDownloadFile() throws IOException {
		int fileNameLength = socketWrapper.readInt();// 读取要下载的文件名字的长度
		byte[] fileNameBytes = new byte[fileNameLength];
		socketWrapper.readFull(fileNameBytes);// 读取要下载的文件名字
		String fileName = new String(fileNameBytes, CharsetEnum.UTF8.getCharsetName());
		// 要下载的文件
		File srcFile = new File(Properties.FILE_SAVE_PATH + fileName);
		if (!srcFile.exists()) {
			socketWrapper.write(-1);
			throw new FileNotExistsException(srcFile.getAbsolutePath());
		}
		if (!srcFile.canRead()) {
			socketWrapper.write(-2);
			throw new FileNoAuthorityException(srcFile.getAbsolutePath());
		}
		socketWrapper.write(1);// 表示可以下载
		Utils.println("线程" + name + "开始向客户端传输文件" + fileName);
		this.socketWrapper.write(srcFile.length());
		this.socketWrapper.writeFromFile(srcFile);
		int status = this.socketWrapper.readInt();
		if (status == 1)
			Utils.println("线程" + name + "向客户端传送文件" + fileName + "完毕");
		else {
			Utils.println("传输失败");
		}
	}

	/**
	 * 根据时间戳创建唯一的文件
	 * 
	 * @param fileName
	 * @return
	 */
	private File createOnlyFile(String fileName) {
		File destFile = new File(Properties.FILE_SAVE_PATH + Utils.getTimeStmp() + "_" + fileName);
		if (!destFile.exists())
			return destFile;
		else
			return createOnlyFile(Properties.FILE_SAVE_PATH + fileName);
	}
}
