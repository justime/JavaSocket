/**
 *文件上传协议，先后发送文件名称长度int，文件名称byte[]，
 *然后读取服务器状态，如果为1，则继续发送文件长度long，文件内容byte[]
 *然后接受服务器状态，如果为1，则表示上传成功 
 */
package dlut.xyz.transfer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import dlut.xyz.common.CharsetEnum;
import dlut.xyz.common.SocketWrapper;
import dlut.xyz.common.TransferTypeEnum;
import dlut.xyz.common.Utils;
import dlut.xyz.exception.FileNotExistsException;
import dlut.xyz.exception.ParamNotExistsException;
import dlut.xyz.exception.ServerException;

public class UploadFileTransfer implements Transferable{
	private String filePath;
	private String fileName;//文件名称
	private byte[] fileNameBytes;
	private int fileNameLength =0 ;//文件名长度
	private long fileLength=0;//文件长度
	/**
	 * 
	 * @param tokens
	 * @throws FileNotExistsException
	 * @throws UnsupportedEncodingException
	 * @throws ParamNotExistsException
	 */
	public UploadFileTransfer(String [] tokens) throws FileNotExistsException,UnsupportedEncodingException,ParamNotExistsException{
		if(tokens.length>=2){
			File file=new File(tokens[1]);
			if(!file.exists()){
				throw new FileNotExistsException("要上传的文件不存在");
			}
			else{
				filePath=tokens[1];
				fileName=Utils.getFileName(tokens[1]);
				fileNameBytes=fileName.getBytes(CharsetEnum.UTF8.getCharsetName());
				fileNameLength=fileNameBytes.length;
				fileLength=(new File(tokens[1])).length();
			}
		}
		else{
			Utils.println("请在后面填写文件绝对路径");
			throw new ParamNotExistsException();
		}
	}
	/**
	 * 返回传输类型
	 * @return
	 */
	@Override
	public TransferTypeEnum getTransferType(){
		return TransferTypeEnum.UPLOADFILE;
	}
	/**
	 * 文件传输
	 */
	@Override
	public void transfer(SocketWrapper socketWrapper) throws IOException,ServerException{
	    Utils.println("我此时向服务器端上传文件：" +fileName);
		socketWrapper.write(fileNameLength);//发送文件名字长度
		socketWrapper.write(fileNameBytes);//发送文件名字内容
		int status = socketWrapper.readInt();
		if(status!=1)
			throw new ServerException("上传功能");
		socketWrapper.write(fileLength);//发送文件长度
		socketWrapper.writeFromFile(new File(filePath));//发送文件内容
		status=socketWrapper.readInt();
		if(status==1)
			Utils.println("文件上传成功");
		else
			Utils.println("文件上传失败");
	}
}
