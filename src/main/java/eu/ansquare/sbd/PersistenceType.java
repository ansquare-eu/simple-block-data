package eu.ansquare.sbd;

public enum PersistenceType {

	NONE,
	FULL,
	NOT_AIR,
	SAME_TYPE;


	public boolean vulnerableTo(ChangeType changeType){
		switch (this){
			case NONE -> {
				return true;
			}
			case FULL -> {
				return false;
			}
			case NOT_AIR -> {
				return changeType == ChangeType.TO_AIR;
			}
			case SAME_TYPE -> {
				return changeType == ChangeType.TO_AIR || changeType == ChangeType.DIFFERENT_TYPE;
			}
		}
		return false;
	}
}
