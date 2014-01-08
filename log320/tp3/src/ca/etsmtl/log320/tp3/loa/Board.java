package ca.etsmtl.log320.tp3.loa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Board {
	public static final int DIM = 8;
			
	int[] cases = new int[DIM * DIM];

	private Stack<Move> m_lastMove = new Stack<Move>();
	private Stack<Integer> m_lastMoveValue = new Stack<Integer>();
	
	public static Board unserialize(byte[] data) {
		Board board = new Board();
		
		for(int i=0; i<Board.DIM * Board.DIM; i++) {
			board.cases[i] = data[i] - '0';
		}
		
		return board;
	}
	
	public void print() {		
		for(int y=0; y<Board.DIM; y++) {
			for(int x=0; x<Board.DIM; x++) {
				int caseVal = cases[x + y * Board.DIM];
				char printableChar = caseVal == 0?'.':caseVal==2?'x':'o';
				System.out.print(printableChar);
			}
			System.out.println();
		}
	}

	public void setCase(int x, int y, int value) {
		cases[x + y * Board.DIM] = value;
	}
	
	public int getCase(int x, int y) {
		return cases[x + y * Board.DIM];
	}
	
	public void applyMove(Move move) {
		m_lastMove.push(move);
		m_lastMoveValue.push(getCase(move.xDst, move.yDst));
		
		setCase(move.xDst, move.yDst, getCase(move.xSrc, move.ySrc));
		setCase(move.xSrc, move.ySrc, 0);
	}
	
	public void unapplyLastMove() {
		Move lastMove = m_lastMove.pop();
		int lastMoveValue = m_lastMoveValue.pop();
		
		setCase(lastMove.xSrc, lastMove.ySrc, getCase(lastMove.xDst, lastMove.yDst));
		setCase(lastMove.xDst, lastMove.yDst, lastMoveValue);
	}
	
	public List<Move> findMoves(Team team) {
		int teamValue = team == Team.White? 4: 2;
		List<Move> moves = new ArrayList<Move>();
		
		final int[][] vecs = new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {-1,-1}};
		
		for(Position p : getPieces(team)) {
			for(int[] vec : vecs) {
				int stepX = vec[0];
				int stepY = vec[1];
				int count = getPieceCount(p.x, p.y, stepX, stepY);
				boolean testMove = isMoveOk(p.x, p.y, stepX, stepY, count, teamValue);
				if(testMove) {
					int dx = stepX * count;
					int dy = stepY * count;
					moves.add(new Move(p.x, p.y, p.x + dx, p.y + dy));
				}
			}
		}
		
		return moves;
	}
	
	public List<Position> getPieces(Team team) {
		int teamValue = team == Team.White? 4: 2;
		List<Position> pieces = new ArrayList<Position>();
		
		for(int x=0; x < Board.DIM; x++) {
			for(int y=0; y < Board.DIM; y++) {
				if(getCase(x, y) == teamValue) {
					pieces.add(new Position(x, y));
				}
			}
		}
		
		return pieces;
	}

	public boolean isMoveOk(int x, int y, int stepX, int stepY, int moveLength, int teamValue) {
		boolean isOk = true;
		int otherTeam = 6 - teamValue;
		
		int dx = x + stepX * moveLength;
		int dy = y + stepY * moveLength;
		
		if(dx < 0 || dx >= Board.DIM || dy < 0 || dy >= Board.DIM) {
			return false;
		}
		
		for(int i=1; i < moveLength && isOk; i++) {
			isOk = getCase(x + stepX * i, y + stepY * i) != otherTeam;
		}
		
		return isOk && getCase(x + stepX * moveLength, y + stepY * moveLength) != teamValue;
	}

	public int getPieceCount(int x, int y, int stepX, int stepY) {
		stepX *= stepX;
		stepY *= stepY;
		int count = 1;
		for(int dx = x + stepX, dy = y + stepY; dx < Board.DIM && dy < Board.DIM; dx += stepX, dy += stepY) {
			count += getCase(dx, dy) > 0? 1: 0;
		}
		
		for(int dx = x - stepX, dy = y - stepY; dx >= 0 && dy >= 0; dx -= stepX, dy -= stepY) {
			count += getCase(dx, dy) > 0? 1: 0;
		}
		
		return count;
	}
	
	public List<List<Position>> getPieceGroup(Team team) {
		List<List<Position>> groups = new ArrayList<List<Position>>();
		List<Position> pieces = getPieces(team);
		
		EACH_PIECE: 
		for(Iterator<Position> pIt = pieces.iterator(); pIt.hasNext();) {
			Position p = pIt.next();
			pIt.remove();
			for(List<Position> group : groups) {
				for(Position other : group) {
					if(p.isAdjacent(other)) {
						group.add(p);
						continue EACH_PIECE;
					}
				}
			}
			
			List<Position> newGroup = new ArrayList<Position>();
			newGroup.add(p);
			groups.add(newGroup);
		}
		
		return groups;
	}
	
	public boolean isTerminal(Team team) {		
		List<Position> pieces = getPieces(team);
		Stack<Position> visitedPieces = new Stack<Position>();
		
		visitedPieces.push(pieces.get(0));
		pieces.remove(0);
		
		while(visitedPieces.size() > 0) {
			Iterator<Position> pieceIt = pieces.iterator();
			Position cur = visitedPieces.pop();
			while(pieceIt.hasNext()) {
				Position piece = pieceIt.next();
				if(cur.isAdjacent(piece)) {
					visitedPieces.push(piece);
					pieceIt.remove();
				}
			}
		}
		
		return pieces.size() == 0;
	}
}
