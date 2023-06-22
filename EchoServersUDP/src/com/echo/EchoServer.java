package com.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class EchoServer extends Thread{

	private DatagramSocket socket;
	private boolean running;
	private byte[] buf=new byte[256];
	public EchoServer() {
		try {
			socket=new DatagramSocket(4445);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

	}
	public void run() {
		running=true;
		while(running) {
			DatagramPacket packet=new DatagramPacket(buf, buf.length);
			InetAddress addr=packet.getAddress();
			System.out.println("The client address is:-"+addr);
			String received=new String(packet.getData(),0,packet.getLength());
			System.out.println("Client message is:-"+received);
			if(received.equals("end")) {
				running=false;
				continue;
			}
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		socket.close();
	}
	

}
