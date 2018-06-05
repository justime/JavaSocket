package dlut.xyz.transfer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import dlut.xyz.common.CharsetEnum;
import dlut.xyz.common.SocketWrapper;
import dlut.xyz.common.TransferTypeEnum;
import dlut.xyz.common.Utils;
import dlut.xyz.exception.ParamNotExistsException;

public class MessageTransfer implements Transferable{
	private String message;//消息内容
	private byte[] messageBytes;
	private int length =0 ;//消息长度
	public MessageTransfer(String [] tokens) throws  ParamNotExistsException,UnsupportedEncodingException{
		if(tokens.length>=2){
			message=tokens[1];
			messageBytes=message.getBytes(CharsetEnum.UTF8.getCharsetName());
			length=messageBytes.length;
		}
		else{
			throw new ParamNotExistsException();
		}
	}
	
	/**
	 * 返回传输类型
	 * @return
	 */
	@Override
	public TransferTypeEnum getTransferType(){
		return TransferTypeEnum.MESSAGE;
	}
	/**
	 * 进行传输
	 */
	@Override
	public void transfer(SocketWrapper socketWrapper) throws IOException{
		Utils.println("我此时向服务器端发送消息：" + message);
		socketWrapper.write(length);
		socketWrapper.write(messageBytes);
		Utils.println("发送消息完毕。");
	}
}
