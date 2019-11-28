package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import composite.WeaponComponent;
import composite.WeaponCraft;
import observer.Observer;
import observer.Subject;
import strategy.Attack;

public abstract class Character implements Observer, Comparable<Character> {
	
	private String name;
	private int hp;
	private int mp;
	private boolean weaponEquipped = false;
	
	protected Attack attack;
	List<WeaponCraft> bag = new ArrayList<>();
	
	public Character(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public boolean isWeaponEquipped() {
		return weaponEquipped;
	}

	public void equipeWeapon() {
		this.weaponEquipped = true;
	}
	
	public int atack(int chance) {
		return attack.atack(chance);
	}

	public int atack(boolean hasWeapon, int chance) {
		return attack.atack(hasWeapon, chance);
	}
	
	public Attack getAttack() {
		return attack;
	}

	public void setAttack(Attack attack) {
		this.attack = attack;
	}
	
	public List<WeaponCraft> getBag() {
		return bag;
	}

	public void showBag() {
		for(int i = 0; i < bag.size(); i ++) {
			System.out.println(bag.get(i).getName() + "(" + bag.get(i).getQuantity() + ")");
		}
	}

	public void updateHP(int damage) {
		this.hp -= damage;
	}
	
	public void updateMP(int cost) {
		this.mp -= cost;
	}
	
	public void useHealingPotion() {
		WeaponCraft potion = null;
		for(WeaponCraft item: bag) {
			if(item.getName().contentEquals("Healing Potion")) {
				item.consumePotion();
				this.hp += 300;
				potion = item;
			}
		}
		
		if(potion.getQuantity() <= 0 && potion != null) {
			bag.remove(potion);
		}
	}
	
	public void useManaPotion() {
		WeaponCraft potion = null;
		for(WeaponCraft item: bag) {
			if(item.getName().contentEquals("Mana Potion")) {
				item.consumePotion();
				this.mp += 250;
				potion = item;
			}
		}
		if(potion.getQuantity() <= 0 && potion != null) {
			bag.remove(potion);
		}
	}
	
	public WeaponCraft craftItem(WeaponCraft component1, WeaponCraft component2) {
		String item1Name = component1.getName();
		String item2Name;
		
		String componentName = null;
		WeaponCraft component = null;
		
		if(component1 != null & component2 != null) {
			item2Name = component2.getName();
			
			if(item1Name.contentEquals("Special Slab") && item2Name.contentEquals("Weapon Fist")) {
				componentName = "Weapon";
			} else if(item1Name.contentEquals("Silver Ingot" ) && item2Name.contentEquals("Golden Ingot")) {
				componentName = "Special Slab";
			} else if(item1Name.contentEquals("Obsidian" ) && item2Name.contentEquals("Leather")) {
				componentName = "Weapon Fist";
			} else if(item1Name.contentEquals("Steel" ) && item2Name.contentEquals("Silver")) {
				componentName = "Silver Ingot";
			} else if(item1Name.contentEquals("Copper" ) && item2Name.contentEquals("Gold")) {
				componentName = "Golden Ingot";
			}
		} else if(component1 != null) {
			if(item1Name.contentEquals("Skin")) {
				componentName = "Leather";
			}
		}
		
		for(WeaponCraft item: bag) {
			if(item.getName().contentEquals(componentName)) {
				component = item;
			}
		}
		
		double value = 1;
		if(componentName.contentEquals("Weapon")) {
			Random chance = new Random();
			value = chance.nextDouble();
			if(value <= 0.66) {
				if(component == null && weaponEquipped) {
					component = new WeaponComponent(componentName);
					component.updateQuantity();
					bag.add(component);
				} else if(component != null && weaponEquipped) {
					component.updateQuantity();
				} else {
					component = new WeaponComponent(componentName);
				}
				
				component1.consumeItem();
				if(component1.getQuantity() <= 0) {
					bag.remove(component1);
				}
				component2.consumeItem();
				if(component2.getQuantity() <= 0) {
					bag.remove(component2);
				}
				
				if(component.getQuantity() == 0) {
					equipeWeapon();
				}
				
				return component;
			}
		} else {
			if(component == null) {
				component = new WeaponComponent(componentName);
				bag.add(component);
			}
		}
		
		if(value <= 0.66 || !componentName.contentEquals("Weapon")) {
			component.updateQuantity();
		}
		
		if(componentName.contentEquals("Weapon")) {
			component1.consumeItem();
			if(component1.getQuantity() <= 0) {
				bag.remove(component1);
			}
			component2.consumeItem();
			if(component2.getQuantity() <= 0) {
				bag.remove(component2);
			}
			
			return null;
			
		} else if(componentName.contentEquals("Special Slab")) {
			component1.consumeComponent();
			if(component1.getQuantity() <= 0) {
				bag.remove(component1);
			}
			component2.consumeComponent();
			if(component2.getQuantity() <= 0) {
				bag.remove(component2);
			}
		} else if (componentName.contentEquals("Weapon Fist")) {
			component1.consumeMaterial();
			component1.consumeMaterial();
			if(component1.getQuantity() <= 0) {
				bag.remove(component1);
			}
			component2.consumeComponent();
			if(component2.getQuantity() <= 0) {
				bag.remove(component2);
			}
		} else if(componentName.contentEquals("Silver Ingot") || componentName.contentEquals("Golden Ingot")) {
			component1.consumeMaterial();
			if(component1.getQuantity() <= 0) {
				bag.remove(component1);
			}
			component2.consumeMaterial();
			if(component2.getQuantity() <= 0) {
				bag.remove(component2);
			}
		} else if(componentName.contentEquals("Leather")) {
			component1.consumeMaterial();
			if(component1.getQuantity() <= 0) {
				bag.remove(component1);
			}
		}
		
		return component;
		
	}
	
	@Override
	public void update(Subject subject) {
		if(subject instanceof Mob) {
			Random chance = new Random();
			int value = -1;
			Mob mob = (Mob) subject;
			if(mob.getHp() <= 0) {
				System.out.println("\nItems added to bag:");
				for(int i = 0; i < 8; i++) {
					value = chance.nextInt(2);
					if(value == 0) {
						WeaponCraft drop = mob.bag.get(i);
						boolean newItem = true;
						for(WeaponCraft item: bag) {
							if(item.getName().contentEquals(drop.getName())) {
								item.updateQuantity();
								newItem = false;
							}
						}
						if(newItem) {
							drop.updateQuantity();
							bag.add(drop);
						}
						System.out.println(mob.getBag().get(i).getName());
					}
				}
			}
		}
	}

}
