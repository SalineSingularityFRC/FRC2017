package org.usfirst.frc.team5066.manymouse;

import java.io.IOException;
import java.net.UnknownHostException;

public class MouseOdometrySubsystem {
	
	Mouse[] mice;
	ClientManyMouse client;
	
	public MouseOdometrySubsystem(Mouse[] miceArr, String host, int port) throws UnknownHostException, IOException{
		this.mice = miceArr;
		
		client = new ClientManyMouse(host, port);
		
	}
	
	public MouseOdometrySubsystem(Mouse[] mice, String host) throws UnknownHostException, IOException{
		
		this.mice = mice;
		client = new ClientManyMouse(host);
	}
	
	public MouseOdometrySubsystem(Mouse[] mice) throws UnknownHostException, IOException{
		
		this.mice = mice;
		client = new ClientManyMouse();
	}
	
	//TODO
	public String getMessages() {return "";}
	
	public String getHost() {
		return client.getHost();
	}
	
	public int getPort() {
		return client.getPort();
	}
	
}
