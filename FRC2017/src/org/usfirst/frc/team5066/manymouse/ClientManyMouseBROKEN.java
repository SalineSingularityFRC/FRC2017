package org.usfirst.frc.team5066.manymouse;

/**
 * Client program to recieve mouse data from the manymouse server.
 * 
 * @author Michael Wolf, FRC Team 5066 "Singularity" 
 * @version 1.0
 */
import java.io.*;
import java.net.*;

import edu.wpi.first.wpilibj.DriverStation;

public class ClientManyMouseBROKEN {

	public int PORT = 9999;
	public String HOST = "raspberrypi.local";

	private Socket socket;
	private DataOutputStream os;
	private BufferedReader is;

	public ClientManyMouseBROKEN() throws UnknownHostException, IOException{
		socket = null;
		os = null;
		is = null;
		
		this.open();
	}
	
	public ClientManyMouseBROKEN(String host) throws UnknownHostException, IOException{

		this();
		this.HOST = host;
		// Attempt to open socket and initialize input and output streams
		//this.open();
	}
	
	public ClientManyMouseBROKEN(String host, int port) throws UnknownHostException, IOException{
		
		this();
		this.HOST = host;
		this.PORT = port;

		// Attempt to open socket and initialize input and output streams
		this.open();
	}

	public void readMessages() {

		try {
			if (socket != null && os != null && is != null) {
				String responseLine = "";
				if ((responseLine = is.readLine()) != null) {
					// System.out.println("loop");
					DriverStation.reportWarning("Server: " + responseLine, false);
				}

				else {
					DriverStation.reportError("Server terminated mouse data stream", false);
				}
			} else {
				DriverStation.reportError("Socket, output, or input object is null. Perhaps you called close()?",
						false);
			}
		} catch (UnknownHostException e) {
			DriverStation.reportError("Unknown host: " + HOST, false);
		} catch (IOException e) {
			DriverStation.reportError("I/O Exception for connection: " + HOST, false);
		}
	}
	/*
	public void reopen() {
		close();
		open();
	}
	*/

	private void open() throws UnknownHostException, IOException{

		// Attempt to open socket and initialize input and output streams
			socket = new Socket(HOST, PORT);
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Deprecated: is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			DriverStation.reportWarning("Socket and streams created successfully.", false);
	}

	public void close() {
		try {

			os.close();
			is.close();
			socket.close();

		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + HOST);
		} catch (IOException e) {
			System.err.println("I/O Exception for connection: " + HOST);
		}

		os = null;
		is = null;
		socket = null;
	}
	
	public String getHost() {
		return HOST;
	}
	
	public int getPort() {
		return PORT;
	}
	
}
