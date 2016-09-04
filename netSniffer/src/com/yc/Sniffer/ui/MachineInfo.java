package com.yc.Sniffer.ui;
public class MachineInfo {
	private String ip;
	private int port;
	private String info;
	private String isBusy;
	public String getIsBusy() {
		return isBusy;
	}
	public void setIsBusy(String isBusy) {
		this.isBusy = isBusy;
	}
	public String getInfo() {
		return info;
	}
	/**
	 * 根据 port判断这个端口上运行的程序
	 * @return
	 */
	public String getAppInfo() {
		String info = "未知程序";
		switch (port) {
		case 80:
			info = "IIS服务器";
			break;
		case 8080:
			info = "TOMCAT";
			break;
		case 1433:
			info = "sql server";
			break;
		case 3306:
			info = "mysql";
			break;
		case 1521:
			info = "oracle";
			break;
		case 43:
			info = "ftp服务器";
			break;
		}
		return info;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
