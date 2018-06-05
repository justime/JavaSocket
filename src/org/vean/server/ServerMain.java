package org.vean.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.vean.common.Properties;
import org.vean.common.SocketWrapper;
import org.vean.common.Utils;

public class ServerMain {
	private static List<Worker> workers = new ArrayList<Worker>();

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8888);
			initPath();
			Utils.println("端口8888已经打开，开启接受请求");
			int index = 1;
			while (true) {
				Socket socket = serverSocket.accept();
				workers.add(new Worker(new SocketWrapper(socket), "thread_" + index));
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
				interruptWorkers(workers);
			} catch (IOException e) {
				// Utils.println("serverSocket关闭失败");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化上传文件路径
	 */
	private static void initPath() throws Exception {
		File dir = new File(Properties.FILE_SAVE_PATH);
		if (!dir.exists()) {
			boolean result = dir.mkdirs();
			if (!result) {
				throw new Exception("服务器无法初始化路径");
			}
		}
	}

	/**
	 * 中断worker线程
	 * 
	 * @param workers
	 */
	private static void interruptWorkers(List<Worker> workers) {
		for (Worker worker : workers) {
			if (worker.isAlive())
				worker.interrupt();
		}
	}
}
