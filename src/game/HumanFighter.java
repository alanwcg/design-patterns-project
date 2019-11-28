package game;

import composite.WeaponCraft;
import strategy.SwordAttack;

public class HumanFighter extends Character {

	public HumanFighter(String name) {
		super(name);
		setHp(1500);
		setMp(500);
		attack = new SwordAttack();
	}
	
	@Override
	public void useHealingPotion() {
		if(getHp() <= 1200) {
			super.useHealingPotion();
		} else {
			WeaponCraft potion = null;
			for(WeaponCraft item: bag) {
				if(item.getName().contentEquals("Healing Potion")) {
					item.consumePotion();
					setHp(1500);
					potion = item;
				}
			}
			
			if(potion.getQuantity() <= 0 && potion != null) {
				bag.remove(potion);
			}
		}
	}

	@Override
	public void useManaPotion() {
		if(getMp() <= 300) {
			super.useManaPotion();
		} else {
			WeaponCraft potion = null;
			for(WeaponCraft item: bag) {
				if(item.getName().contentEquals("Mana Potion")) {
					item.consumePotion();
					setMp(500);
					potion = item;
				}
			}
			
			if(potion.getQuantity() <= 0 && potion != null) {
				bag.remove(potion);
			}
		}	
	}

	@Override
	public int compareTo(Character o) {
		if(this.equals(o)) {
			return 0;
		}
		return 1;
	}

}
