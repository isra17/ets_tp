package ca.etsmtl.log320.tp3.ai.evaluator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Position;
import ca.etsmtl.log320.tp3.loa.Team;

public class GroupsEvaluator implements BoardEvaluator {

	@Override
	public float evaluate(Board board, Team team) {
		List<List<Position>> groups = board.getPieceGroup(team);
		List<Float> distances = new ArrayList<Float>();
		
		for(Iterator<List<Position>> groupIt = groups.iterator(); groupIt.hasNext();) {			 
			List<Position> group = groupIt.next();
			groupIt.remove();

			for(List<Position> otherGroup : groups) {	
				float minDistance = 100.f;
				for(Position p : group) {
					for(Position other : otherGroup) {
						if(p.isAdjacent(other)) {
							minDistance = 1.f;
							break;
						} else {
							float d = Math.abs(p.x - other.x) + Math.abs(p.y - other.y);
							if(d < minDistance) {
								minDistance = d;
							}
						}
					}
				}
				distances.add(minDistance);
			}
		}
		
		int n = distances.size();
		if(n > 0) {
			float sum = 0.f;
			for(float d : distances) {
				sum += d;
			}
			
			return sum/n;
		}
		
		return 1.f;
	}

}
