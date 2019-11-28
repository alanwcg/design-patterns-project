package strategy;

public class IceBolt implements Attack {
	
	private final int MP = 25;

	@Override
	public int atack(boolean hasWeapon, int chance) {
		if(chance == 0 && hasWeapon) {
			System.out.println("MAGIC CRITICAL: You dealt 300 damage!");
			return 300;
		} else if(hasWeapon) {
			System.out.println("You dealt 150");
			return 150;
		} else if(chance == 0) {
			System.out.println("MAGIC CRITICAL: You dealt 200 damage!");
			return 200;
		} else {
			System.out.println("You dealt 100 damage");
			return 100;
		}
	}

	@Override
	public int getMP() {
		return MP;
	}

	@Override
	public int atack(int chance) {
		return 0;
	}

}
