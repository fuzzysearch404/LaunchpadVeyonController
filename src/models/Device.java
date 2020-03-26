package models;

public class Device {
	
	private String ipAdreess;
	private int row;
	private int collumn;
	
	public Device(String ipAdress) {
		this.ipAdreess = ipAdress;
	}

	public Device(String ipAdreess, int row, int collumn) {
		setIpAdreess(ipAdreess);
		setRow(row);
		setCollumn(collumn);
	}

	public String getIpAdreess() {
		return ipAdreess;
	}

	public void setIpAdreess(String ipAdreess) {
		this.ipAdreess = ipAdreess;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCollumn() {
		return collumn;
	}

	public void setCollumn(int collumn) {
		this.collumn = collumn;
	}

}
