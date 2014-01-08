package ca.etsmtl.log320.tp3.loa;

public enum Team {
	White,
	Black;

	public Team getOpposingTeam() {
		return this == Team.White? Team.Black: Team.White;
	}
}
