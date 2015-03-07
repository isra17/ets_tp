package ca.etsmtl.log430.lab3.predicate;

import java.util.List;


public class StatePredicate implements IPredicate<String> {

	List<String> stateFilters;
	
	private static String extractState(String entry) {
		String[] tokens = entry.split(" ");
		return tokens[5];
	}
	
	public StatePredicate(String stateFilters) {
		this(java.util.Arrays.asList(new String[]{stateFilters}));
	}
	
	public StatePredicate(List<String> stateFilters) {
		this.stateFilters = stateFilters;
	}
	
	@Override
	public boolean apply(String entry) {
		String state = extractState(entry);
		return stateFilters.contains(state);
	}
}
