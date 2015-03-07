package ca.etsmtl.log430.lab3.predicate;


public class ProgressPredicates {
	private static int extractProgress(String entry) {
		String[] tokens = entry.split(" ");
		String progressToken = tokens[4];
		return Integer.parseInt(progressToken);
	}
	
	public static class Above implements IPredicate<String> {
		int progressLimit;
		public Above(int progressLimit) {
			this.progressLimit = progressLimit;
		}
		
		@Override
		public boolean apply(String entry) {
			return ProgressPredicates.extractProgress(entry) > progressLimit;
		}
		
	}
	
	public static class Equal implements IPredicate<String> {
		int progressLimit;
		public Equal(int progressLimit) {
			this.progressLimit = progressLimit;
		}
		
		@Override
		public boolean apply(String entry) {
			return ProgressPredicates.extractProgress(entry) == progressLimit;
		}
		
	}
	
	public static class Below implements IPredicate<String> {
		int progressLimit;
		public Below(int progressLimit) {
			this.progressLimit = progressLimit;
		}
		
		@Override
		public boolean apply(String entry) {
			return ProgressPredicates.extractProgress(entry) < progressLimit;
		}
		
	}
}
