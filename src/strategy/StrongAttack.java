package strategy;

public class StrongAttack implements Attack {

	@Override
	public int atack(int chance) {
		if(chance == 0) {
			System.out.println("CRITICAL HIT: Mob dealt 100 damage!");
			return 100;
		}
		System.out.println("Mob dealt 50 damage");
		return 50;
	}

	@Override
	public int getMP() {
		return 0;
	}

	@Override
	public int atack(boolean hasWeapon, int chance) {
		return 0;
	}

}
