package state;

import game.Mob;

public class Alive extends MobStatus {

	public Alive(Mob mob) {
		super(mob);
		this.name = "Alive";
	}

	@Override
	public void useFrenzy(int hp) {
		if(hp <= 200) {
			mob.setCurrentStatus(mob.getFrenziedStatus());
		}
	}

}
