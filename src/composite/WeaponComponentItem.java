package composite;

public class WeaponComponentItem extends WeaponCraft {

	public WeaponComponentItem(String name) {
		super(name);
	}

	@Override
	public void print() {
		setQuantity(10);
		if(getName().contentEquals("Obsidian")) {
			System.out.println("\t\t" + getName() + "(" + getQuantity() + ")");
		} else {
			System.out.println("\t\t\t" + getName() + "(" + getQuantity() + ")");
		}
		
	}

}
