package org.vean.client;

import java.io.IOException;
import java.util.Scanner;

import org.vean.common.Properties;
import org.vean.common.SocketWrapper;
import org.vean.common.Utils;

public class ClientMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			SocketWrapper socketWrapper = new SocketWrapper("localhost", 8888);
			Utils.println("已经连接上服务器端，现在可以输入数据开始通信了.....");
			Utils.println(Properties.HELP);
			Utils.print(">");
			String line = scanner.nextLine();
			while (!"bye".equals(line)) {
				if (line != null) {
					LineProcessor processor = new LineProcessor(line);
					processor.transfer(socketWrapper);
					Utils.print(">");
					line = scanner.nextLine();
				}
			}
		} catch (IOException e) {
			Utils.println(e.getMessage());
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
