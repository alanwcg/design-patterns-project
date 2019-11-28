package strategy;

public class WindStrike implements Attack {
	
	private final int MP = 50;

	@Override
	public int atack(boolean hasWeapon, int chance) {
		if(hasWeapon && chance == 0) {
			System.out.println("MAGIC CRITICAL: You dealt 600 damage!");
			return 600;
		} else if(hasWeapon) {
			System.out.println("You dealt 300 damage");
			return 300;
		} else if(chance == 0) {
			System.out.println("MAGIC CRITICAL: You dealt 400 damage!");
			return 400;
		} else {
			System.out.println("You dealt 200 damage");
			return 200;
		}
	}
	
	public int getMP() {
		return MP;
	}

	@Override
	public int atack(int chance) {
		return 0;
	}

}
