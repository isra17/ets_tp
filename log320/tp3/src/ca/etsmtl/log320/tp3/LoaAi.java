package ca.etsmtl.log320.tp3;

import ca.etsmtl.log320.tp3.gameclient.ComputerClient;
import ca.etsmtl.log320.tp3.gameclient.TCPClient;

public class LoaAi {

	public static void main(String[] args) throws Exception {
		TCPClient client;
		client = new ComputerClient("localhost", 8888);
		client.start();
	}

}
