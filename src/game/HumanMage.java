package game;

import composite.WeaponCraft;
import strategy.WindStrike;

public class HumanMage extends Character {
	
	public HumanMage(String name) {
		super(name);
		setHp(1000);
		setMp(1000);
		attack = new WindStrike();
	}

	@Override
	public void useHealingPotion() {
		if(getHp() <= 700) {
			super.useHealingPotion();
		} else {
			WeaponCraft potion = null;
			for(WeaponCraft item: bag) {
				if(item.getName().contentEquals("Healing Potion")) {
					item.consumePotion();
					setHp(1000);
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
		if(getMp() <= 800) {
			super.useManaPotion();
		} else {
			WeaponCraft potion = null;
			for(WeaponCraft item: bag) {
				if(item.getName().contentEquals("Mana Potion")) {
					item.consumePotion();
					setMp(1000);
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
