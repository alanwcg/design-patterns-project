package state;

import game.Mob;

public class Dead extends MobStatus {

	public Dead(Mob mob) {
		super(mob);
		this.name = "Dead";
	}

}
