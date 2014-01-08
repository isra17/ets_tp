package ca.etsmtl.log320.tp3.gameclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import ca.etsmtl.log320.tp3.loa.Move;

public class HumanClient extends TCPClient {

	BufferedReader m_inputReader;
	
	public HumanClient(String host, int port) throws UnknownHostException,
			IOException {
		super(host, port);
		
		m_inputReader = new BufferedReader(new InputStreamReader(System.in)); 
	}

	@Override
	protected Move doPlay() {
		try {
			String moveStr = m_inputReader.readLine();
			String[] moveData = moveStr.trim().split(" ");
			return new Move(moveData[0], moveData[1]);
		} catch(Exception ex) {
			return null;
		}
	}

}
