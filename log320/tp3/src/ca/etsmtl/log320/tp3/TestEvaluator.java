package ca.etsmtl.log320.tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.etsmtl.log320.tp3.ai.AdversarialSearch;
import ca.etsmtl.log320.tp3.ai.AlphaBetaSearch;
import ca.etsmtl.log320.tp3.ai.IterativeAlphaBetaSearch;
import ca.etsmtl.log320.tp3.ai.evaluator.CenterEvaluator;
import ca.etsmtl.log320.tp3.ai.evaluator.ConcentrationEvaluator;
import ca.etsmtl.log320.tp3.ai.evaluator.OpposingEvaluator;
import ca.etsmtl.log320.tp3.ai.evaluator.WeightedEvaluators;
import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Move;
import ca.etsmtl.log320.tp3.loa.Team;

public class TestEvaluator {

	public static void main(String[] args) throws IOException {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		Board board = new Board();
		for(int y=0;y<8;y++) {
			String line = buffer.readLine();
			for(int x=0; x<line.length(); x++){
				char c = line.charAt(x);
				if(c == 'x') {
					board.setCase(x, y, 2);
				} else if(c == 'o') {
					board.setCase(x, y, 4);
				}
			}
		}
				
		WeightedEvaluators evaluator = new WeightedEvaluators();
		evaluator.add(new OpposingEvaluator(new ConcentrationEvaluator(), 2.f), 2.f);
		evaluator.add(new OpposingEvaluator(new CenterEvaluator(), 2.f), 1.f);
		AdversarialSearch alphaBeta = new IterativeAlphaBetaSearch(board, Team.White, evaluator, 5, 4500);
		//AdversarialSearch alphaBeta = new AlphaBetaSearch(board, Team.White, evaluator, 5);
		Move move = alphaBeta.getBestMove();
		
		board.print();
		System.out.println("Score[x]: " + evaluator.evaluate(board, Team.Black));
		System.out.println("Score[o]: " + evaluator.evaluate(board, Team.White));
		System.out.println("isTerminal[x]: " + board.isTerminal(Team.Black));
		System.out.println("isTerminal[o]: " + board.isTerminal(Team.White));
		System.out.println("bestMove[o]: " + move);
		alphaBeta.printStats();
		board.applyMove(move);
		board.print();
		System.out.println("Score[x]: " + evaluator.evaluate(board, Team.Black));
		System.out.println("Score[o]: " + evaluator.evaluate(board, Team.White));
		System.out.println("isTerminal[x]: " + board.isTerminal(Team.Black));
		System.out.println("isTerminal[o]: " + board.isTerminal(Team.White));
	}

}
