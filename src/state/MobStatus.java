package state;

import game.Mob;
import state.InvalidStatusException;

public abstract class MobStatus {
	
	protected Mob mob;
	protected String name;
	
	public MobStatus(Mob mob) {
		this.mob = mob;
	}
	
	public void useFrenzy(int hp) throws InvalidStatusException {
		throw new InvalidStatusException("Invalid action to status " + mob.getCurrentStatus().getName());
	}
	
	public void die(int hp) throws InvalidStatusException {
		throw new InvalidStatusException("Invalid action to status " + mob.getCurrentStatus().getName());
	}

	public String getName() {
		return name;
	}

}
