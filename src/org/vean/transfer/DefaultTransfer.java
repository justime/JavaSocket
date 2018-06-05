package org.vean.transfer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.vean.common.Properties;
import org.vean.common.SocketWrapper;
import org.vean.common.TransferTypeEnum;
import org.vean.common.Utils;
import org.vean.exception.ParamNotExistsException;

public class DefaultTransfer implements Transferable{
	private String message=Properties.HELP;
	public DefaultTransfer(String [] tokens) throws  ParamNotExistsException,UnsupportedEncodingException{
		Utils.println(message);
	}
	
	/**
	 * 返回传输类型
	 * @return
	 */
	@Override
	public TransferTypeEnum getTransferType(){
		return TransferTypeEnum.HELP;
	}
	/**
	 * 进行传输
	 */
	@Override
	public void transfer(SocketWrapper socketWrapper) throws IOException{
	    
	}
}
