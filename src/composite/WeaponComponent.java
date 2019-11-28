package composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeaponComponent extends WeaponCraft {
	
	private List<WeaponCraft> components = new ArrayList<>();

	public WeaponComponent(String name) {
		super(name);
	}

	@Override
	public void add(WeaponCraft component) {
		components.add(component);
	}

	@Override
	public void remove(WeaponCraft component) {
		components.remove(component);
	}

	@Override
	public WeaponCraft getChild(int i) {
		return components.get(i);
	}

	@Override
	public void print() {
		setQuantity(2);
		if(getName().contentEquals("Weapon:")) {
			System.out.println(getName());
		}
		else if(getName().contentEquals("Weapon Fist") || getName().contentEquals("Special Slab")) {
			System.out.println("\t" + getName());
		} else {
			System.out.println("\t\t" + getName() + "(" + getQuantity() + ")");
		}
		
		Iterator<WeaponCraft> iterator = components.iterator();
		while(iterator.hasNext()) {
			WeaponCraft component = iterator.next();
			component.print();
		}
	}

}
