package strategy;

public interface Attack {
	
	int atack(int chance);

	int atack(boolean hasWeapon, int chance);
	
	int getMP();
}
