package ca.etsmtl.log320.tp3.gameclient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Move;
import ca.etsmtl.log320.tp3.loa.Team;

public abstract class TCPClient {
	Socket m_socket;
	BufferedInputStream m_socketInput;
	
	Team m_clientTeam;
	Team m_otherTeam;
	Team m_playingTeam = Team.White;
	
	Board m_board;
	
	public TCPClient(String host, int port) throws UnknownHostException, IOException {
		m_socket = new Socket(host, port);
		m_socketInput = new BufferedInputStream(m_socket.getInputStream());
	}
	
	public void start() throws IOException {
		int teamId = m_socketInput.read();
		
		if(teamId == '1') {
			m_clientTeam = Team.White;
			m_otherTeam = Team.Black;
		} else if(teamId == '2') {
			m_clientTeam = Team.Black;
			m_otherTeam = Team.White;
		} else {
			throw new RuntimeException(String.format("Invalid team: %c", teamId));
		}
				
		byte[] boardData = new byte[1024];
		m_socketInput.read(boardData);
		String boardString = new String(boardData);
		boardString = boardString.replaceAll("\\s", "");
		
		m_board = Board.unserialize(boardString.getBytes());
		
		while(playTurn());
		
		System.out.println("Game ended");
	}
	
	private boolean playTurn() throws IOException {
		if(m_playingTeam == m_clientTeam) {			
			Move move = doPlay();
			
			if(move != null) {
				m_board.applyMove(move);
				m_socket.getOutputStream().write(move.serialize());
				m_playingTeam = m_otherTeam;
				
			} else {
				System.out.println("Warning: doPlay returned null");
			}
			
		} else {
			byte[] moveBuf = new byte[32];
			m_socketInput.read(moveBuf);
			String moveStr = new String(moveBuf);
			String[] moveData = moveStr.trim().split(" ");
			
			if(moveData[0].equals("3")) {
				m_board.applyMove(new Move(moveData[1], moveData[3]));
				m_playingTeam = m_clientTeam;
				
			} else if(moveData[0].equals("4")) {
				throw new RuntimeException(String.format("Invalid move sent"));				
			} else {
				throw new RuntimeException(String.format("Unkown response code: %c", moveData[0]));
			}
		}
				
		return !(m_board.isTerminal(m_clientTeam) || m_board.isTerminal(m_otherTeam));
	}
	
	protected abstract Move doPlay();
}
