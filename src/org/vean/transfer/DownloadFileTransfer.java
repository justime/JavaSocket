package org.vean.transfer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.ServerException;

import org.vean.common.SocketWrapper;
import org.vean.common.TransferTypeEnum;
import org.vean.common.Utils;

public class DownloadFileTransfer implements Transferable {

	private String remoteFileName;// 要下载的文件名称
	private String fileDir;// 下载的文件保存的路径

	public DownloadFileTransfer(String [] tokens) throws RuntimeException{
			if(tokens.length>=3){
			    //判断给出的文件夹是否存在
				File dir=new File(tokens[2]);
				if(dir.exists()&&dir.isDirectory()){
					fileDir=tokens[2];
					if(!fileDir.endsWith(File.separator))
						fileDir=fileDir+File.separator;
					remoteFileName=tokens[1];
				} else {
					throw new RuntimeException(tokens[2]);
				}
			} else{
				Utils.println("请在后面填写文件名称以及保存路径");
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
		return TransferTypeEnum.DOWNLOADFILE;
	}

	/**
	 * 进行传输
	 */
	@Override
	public void transfer(SocketWrapper socketWrapper)
			throws IOException, UnsupportedEncodingException {
		Utils.println("我此时准备从服务器端下载文件");
		// 通知服务器要下载的文件名称
		byte[] remoteFileNameBytes = this.remoteFileName.getBytes("utf-8");
		socketWrapper.write(remoteFileNameBytes.length);
		socketWrapper.write(remoteFileNameBytes);
		int status = socketWrapper.readInt();
		if (status == 1) {
			// 正常传输文件
			long fileLength = socketWrapper.readLong();
			File file = new File(this.fileDir + this.remoteFileName);
			socketWrapper.readToFile(file, fileLength);
			Utils.println("下载完毕");
			socketWrapper.write(1);
		} else if (status == -1) {
			throw new IOException(this.remoteFileName);
		} else if (status == -2) {
			throw new ServerException(" 没有操作文件的权限");
		} else {
			throw new ServerException("下载功能");
		}
	}
}
