package dlut.xyz.common;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DecimalFormat;

import dlut.xyz.exception.FileNoAuthorityException;
import dlut.xyz.exception.FileNotExistsException;


public class SocketWrapper implements Closeable {
	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	public SocketWrapper(Socket socket) throws IOException {
		this.socket = socket;
		initStreams();
	}
	public SocketWrapper(String host , int port) throws IOException {
		this.socket = new Socket();
		this.socket.connect(new InetSocketAddress(host , port) , 1500);
		this.socket.setKeepAlive(true);
		this.socket.setTcpNoDelay(true);
		initStreams();
	}
	
	private void initStreams() throws IOException {
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	public void write(byte b) throws IOException {
		this.outputStream.write(b);
	}
	
	public void write(short s) throws IOException {
		this.outputStream.writeShort(s);
	}
	
	public void write(int i) throws IOException {
		this.outputStream.writeInt(i);
	}
	
	public void write(long l) throws IOException {
		this.outputStream.writeLong(l);
	}
	
	public void write(byte []bytes) throws IOException {
		this.outputStream.write(bytes);
	}
	
	public void write(byte []bytes , int length) throws IOException {
		this.outputStream.write(bytes , 0 , length);
	}
	/**
	 * 将value按照指定编码后写入到流中
	 * @param value
	 * @param charset
	 * @throws IOException
	 */
	public void write(String value , String charset) throws IOException {
		if(value != null) {
			write(value.getBytes(charset));
		}
	}
	
	public byte readByte() throws IOException {
		return this.inputStream.readByte();
	}
	
	public short readShort() throws IOException {
		return this.inputStream.readShort();
	}
	
	public int readInt() throws IOException {
		return this.inputStream.readInt();
	}
	
	public long readLong() throws IOException {
		return this.inputStream.readLong();
	}
	
	public void readFull(byte []bytes) throws IOException {
		this.inputStream.readFully(bytes);
	}
	
	public int read(byte []bytes) throws IOException {
		return this.inputStream.read(bytes);
	}
	public void read(byte []bytes , int length) throws IOException {
		this.inputStream.read(bytes, 0, length);
	}
	/**
	 * 从流中读取指定长度，并按照指定的编码类型转换为String类型
	 * @param length
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public String read(int length , String charset) throws IOException {
		byte []bytes = new byte[length];
		read(bytes);
		return new String(bytes , charset);
	}
	/**
	 * 把文件写入到socket中
	 * @param path 
	 * @throws IOException
	 */
	public void writeFromFile(File srcFile) throws FileNotExistsException,FileNoAuthorityException,IOException{
		
		FileInputStream fileInputStream = new FileInputStream(srcFile);
		
		int pagesize =Properties.PAGE_SIZE;
	    long fileLength=srcFile.length();
	    long acceptedLength=0;
	    int temp=0;//标记换行
	    byte [] fileBytes=new byte[pagesize]; 
	    int length=fileInputStream.read(fileBytes);
	    while(length>0){
		  this.write(fileBytes,length);
		  acceptedLength=acceptedLength+(long)length;
		  length=fileInputStream.read(fileBytes);
		  //显示传输状态
		  if(temp==0)
				Utils.println("已经传输");
		  if(temp%10==0)
				Utils.println("");
		  this.showUploadStatus(acceptedLength, fileLength);
			temp++;
	   }
		fileInputStream.close();
	}
	/**
	 * 从socket中读取文件到desfFile中
	 * @param destFile
	 * @param destFileLength
	 * @throws FileNoAuthorityException
	 * @throws IOException
	 */
	public void readToFile(File destFile,long destFileLength) throws FileNotExistsException,FileNoAuthorityException,IOException{
		
		FileOutputStream fileOutputStream = new FileOutputStream(destFile);
		int buffersize =Properties.BUFFER_SIZE;
		int temp=0;//标记换行
		byte [] fileBytes=new byte[buffersize];
		long acceptedLength=0;
		
		while(acceptedLength<destFileLength){
			int length=this.read(fileBytes);
			acceptedLength=acceptedLength+length;
			fileOutputStream.write(fileBytes, 0, length);
			if(temp==0)
				Utils.println("已经传输");
			if(temp%10==0)
				Utils.println("");
			this.showUploadStatus(acceptedLength, destFileLength);
			temp++;
			}
		fileOutputStream.close();
	}
	/**
	 * 关闭该类的socket
	 */
	public void close() throws IOException{
			this.socket.close();
	}
	/**
	 * 显示传输进度
	 * @param acceptedLength
	 * @param fileLength
	 */
	private void showUploadStatus(long acceptedLength,long fileLength){
		DecimalFormat decimalFormat=new DecimalFormat("##.00%  ");
		double percent=(double)acceptedLength/fileLength;
		Utils.print(decimalFormat.format(percent));
	}

}
