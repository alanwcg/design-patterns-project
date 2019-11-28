package state;

import game.Mob;

public class Frenzied extends MobStatus {

	public Frenzied(Mob mob) {
		super(mob);
		this.name = "Frenzied";
	}

	@Override
	public void die(int hp) {
		if(hp <= 0) {
			mob.setCurrentStatus(mob.getDeadStatus());
		}
	}
	
	

}
