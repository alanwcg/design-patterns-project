package strategy;

public class SwordAttack implements Attack {

	@Override
	public int atack(boolean hasWeapon, int chance) {
		if(hasWeapon && chance == 0) {
			System.out.println("CRITICAL HIT: You dealt 300 damage!");
			return 300;
		} else if(hasWeapon) {
			System.out.println("You dealt 150 damage");
			return 150;
		} else if(chance == 0) {
			System.out.println("CRITICAL HIT: You dealt 200 damage!");
			return 200;
		} else {
			System.out.println("You dealt 100 damage");
			return 100;
		}
	}

	@Override
	public int getMP() {
		return 0;
	}

	@Override
	public int atack(int chance) {
		return 0;
	}

}
