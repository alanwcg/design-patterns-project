import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import composite.WeaponComponent;
import composite.WeaponComponentItem;
import composite.WeaponCraft;
import game.Character;
import game.HumanFighter;
import game.HumanMage;
import game.Mob;
import state.InvalidStatusException;
import strategy.Attack;
import strategy.IceBolt;
import strategy.PowerStrike;
import strategy.StrongAttack;
import strategy.SwordAttack;
import strategy.WindStrike;

public class Main {
	
	public static int option; //User input choice for any menu
	public static Random chance = new Random(); // Random number generator
	public static Scanner input = new Scanner(System.in); //Scanner to request user input
	
	public static Character character; //Player Character
	public static List<Mob> mobs = new ArrayList<>(); //List containing all mobs of the game
	
	public static int i = 0; //Control variable to iterate in the mob list
	public static final int MOBS_TOTAL = 100; //Total number of mobs in the game
	
	public static int mobCrit; //Used in conjunction with random number generator to represent the mob critical hit chance
	public static int characterCrit; //Used in conjunction with random number generator to represent the char critical hit chance
	public static int value;  //Used in conjunction with random number generator to represent the character escape chance
	
	//Main and only thread of the game
	public static void main(String[] args) {
		
		//For loop to populate the mob list before game starts
		for(int i = 0; i < MOBS_TOTAL; i++) {
			Mob mob = new Mob("Orc Warrior");
			mobs.add(mob);
		}
		
		//Starts game
		gameMenu();
	}
	
	//Method to display game main menu and perform an action based on user's choice
	public static void gameMenu() {
		do {
			System.out.println("\n----- GAME MENU -----");
			System.out.println("1 - Create Character");
			System.out.println("2 - Start Game");
			System.out.println("0 - Exit");
			
			chooseOption();
			
			switch(option) {
			case 1:
				if(character == null) {
					createCharacter();
				} else {
					System.out.println("Character already created.");
				}
				break;
			case 2:
				if(character == null) {
					System.out.println("You need to create a character first.\n");
					break;
				}
				characterMenu();
				break;
			case 0:
				System.exit(0); //Finishes application
				break;
			default:
				System.out.println("Invalid Option!\n");
				break;
			}
		} while(option != 0);
	}
	
	//Method to allow user create own character
	public static void createCharacter() {
		System.out.print("Character Name: ");
		String name = input.next();
		int profession;
		
		do {
			System.out.println("Choose Character Class:");
			System.out.println("1 - Human Mage");
			System.out.println("2 - Human Fighter");
			profession = input.nextInt();
			
			switch(profession) {
			case 1:
				character = new HumanMage(name);
				break;
			case 2:
				character = new HumanFighter(name);
				break;
			default:
				System.out.println("Invalid Class! Try Again.\n");
				break;
			}
		} while(profession < 1 || profession > 2);
		
	}
	
	//Method to display character menu and perform an action based on user's choice
	public static void characterMenu() {
		do {
			System.out.println("\n----- CHARACTER ACTIONS -----");
			System.out.println("1 - Battle Mob");
			System.out.println("2 - Open Bag");
			System.out.println("3 - Crafting");
			System.out.println("0 - Exit Game");
			
			chooseOption();
			
			switch(option) {
			case 1:
				if(i >= 100) {
					i = 0;
				}
				battle();
				break;
			case 2:
				openBag();
				break;
			case 3:
				craftMenu();
				break;
			case 0:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Option!\n");
				break;
			}
			
		} while(option != 0);
	}
	
	//Method to start a battle with alive mob
	public static void battle() {
		if(i == MOBS_TOTAL) {
			i = 0;
		}
		
		Mob mob = mobs.get(i);
		if(mob.getCurrentStatus().getName().contentEquals("Alive")) {
			Attack attack = null;
			mob.addObserver(character);
			
			characterSkills(mob, attack);
		} else {
			System.out.println("You've killed all mobs in the world!\n");
		}
			
	}
	
	//Method to display character skills during battle and perform an attack based on user's choice
	public static void characterSkills(Mob mob, Attack attack) {
		System.out.println(mob.getName() + ": " + mob.getHp() + " HP");
		System.out.println(character.getName() + ": " + character.getHp() + " HP / " + character.getMp() + "MP");
		String skill1;
		String skill2;
		if(character instanceof HumanMage) {
			skill1 = "Wind Strike";
			skill2 = "Ice Bolt";
		} else {
			skill1 = "Sword Attack";
			skill2 = "Power Strike";
		}
		
		do {
			System.out.println("\n----- FIGHT! -----");
			System.out.println("1 - " + skill1);
			System.out.println("2 - " + skill2);
			System.out.println("3 - Try Escape");
			
			if(mob.getHp() <= 200 && !mob.getCurrentStatus().getName().equals("Frenzied")) {
				try {
					mob.useFrenzy(mob.getHp());
				} catch(InvalidStatusException e) {
					System.out.println(e.getMessage());
				}
				
			}
			
			chooseOption();
			
			switch(option) {
			case 1:
				if(character instanceof  HumanMage) {
					attack = new WindStrike();
				} else {
					attack = new SwordAttack();
				}
				useSkill(mob, attack);
				break;
			case 2:
				if(character instanceof  HumanMage) {
					attack = new IceBolt();
				} else {
					attack = new PowerStrike();
				}
				useSkill(mob, attack);
				break;
			case 3:
				value = chance.nextInt(3);
				if(value == 0) {
					System.out.println("Escaped successfully!");
					characterMenu();
				} else {
					mobCrit = chance.nextInt(5);
					System.out.println("Failed to escape!");
					character.updateHP(mob.atack(mobCrit));
					charactersStatus(mob);
				}
				break;
			default:
				System.out.println("Invalid Option!\n");
				break;
			}			
		} while(mob.getHp() > 0);
	}
	
	//Method to use character skill based on user's attack choice
	public static void useSkill(Mob mob, Attack attack) {
		character.setAttack(attack);
		int cost = character.getAttack().getMP();
		characterCrit = chance.nextInt(5);
		mobCrit = chance.nextInt(5);
		
		if(mob.getCurrentStatus().getName().equals("Frenzied")) {
			mob.setAttack(new StrongAttack());
			character.updateHP(mob.atack(mobCrit));
			if(character.getHp() <= 0) {
				System.out.println("Game Over!");
				System.exit(0);
			}
			
			if(character.getMp() >= cost) {
				mob.updateHP(character.atack(character.isWeaponEquipped(), characterCrit));
				character.updateMP(cost);
			} else {
				System.out.println("Insufficient mana!");
			}
			
			if(mob.getHp() <= 0) {
				i++;
				try {
					mob.die(mob.getHp());
				} catch(InvalidStatusException e) {
					System.out.println(e.getMessage());
				}
				mob.notificar();
				System.out.println();
			}
			
		} else {
			if(character.getMp() >= cost) {
				mob.updateHP(character.atack(character.isWeaponEquipped(), characterCrit));
				character.updateMP(cost);
			} else {
				System.out.println("Insufficient mana!");
			}
			
			if(mob.getHp() <= 0) {
				i++;
				mob.notificar();
				System.out.println();
			} else {
				character.updateHP(mob.atack(mobCrit));
				if(character.getHp() <= 0) {
					System.out.println("Game Over!");
					System.exit(0);
				}
			}
		}
		charactersStatus(mob);
	}
	
	//Method to show character's bag and allow player to use potions
	public static void openBag() {
		character.showBag();
		System.out.println();
		System.out.println(character.getName() + ": " + character.getHp() + " HP / " + character.getMp() + "MP\n");
		do {
			System.out.println("1 - Use Healing Potion");
			System.out.println("2 - Use Mana Potion");
			System.out.println("0 - Close Bag");
			
			chooseOption();
			
			int potionQuantity = 0;
			
			switch(option) {
			case 1:
				for(WeaponCraft item: character.getBag()) {
					if(item.getName().contentEquals("Healing Potion")) {
						potionQuantity = item.getQuantity();
					}
				}
				if(potionQuantity > 0) {
					character.useHealingPotion();
					openBag();
				} else {
					System.out.println("You don't have healing potions!\n");
					openBag();
				}
				break;
			case 2:
				for(WeaponCraft item: character.getBag()) {
					if(item.getName().contentEquals("Mana Potion")) {
						potionQuantity = item.getQuantity();
					}
				}
				if(potionQuantity > 0) {
					character.useManaPotion();
					openBag();
				} else {
					System.out.println("You don't have mana potions!\n");
					openBag();
				}
				break;
			case 0:
				characterMenu();
				break;
			default:
				System.out.println("Invalid Option!\n");
				break;
			}
		} while(option != 0);
	}
	
	//Method to display craft menu and perform an action based on user's choice
	public static void craftMenu() {
		do {
			System.out.println("\n----- CRAFT MENU -----");
			System.out.println("1 - List of required items to craft weapon");
			System.out.println("2 - Craft Weapon");
			System.out.println("3 - Craft Special Slab");
			System.out.println("4 - Craft Weapon Fist");
			System.out.println("5 - Craft Silver Ingot");
			System.out.println("6 - Craft Golden Ingot");
			System.out.println("7 - Craft Leather");
			System.out.println("0 - Close");
			
			chooseOption();
			
			String item1Name;
			int item1Cost;
			String item2Name;
			int item2Cost;
			
			switch(option) {
			case 1:
				printList();
				break;
			case 2:
				item1Name = "Special Slab";
				item1Cost = 1;
				item2Name = "Weapon Fist";
				item2Cost = 1;
				craftItem(item1Name, item1Cost, item2Name, item2Cost);
				break;
			case 3:
				item1Name = "Silver Ingot";
				item1Cost = 2;
				item2Name = "Golden Ingot";
				item2Cost = 2;
				craftItem(item1Name, item1Cost, item2Name, item2Cost);
				break;
			case 4:
				item1Name = "Obsidian";
				item1Cost = 10;
				item2Name = "Leather";
				item2Cost = 2;
				craftItem(item1Name, item1Cost, item2Name, item2Cost);
				break;
			case 5:
				item1Name = "Steel";
				item1Cost = 5;
				item2Name = "Silver";
				item2Cost = 5;
				craftItem(item1Name, item1Cost, item2Name, item2Cost);
				break;
			case 6:
				item1Name = "Copper";
				item1Cost = 5;
				item2Name = "Gold";
				item2Cost = 5;
				craftItem(item1Name, item1Cost, item2Name, item2Cost);
				break;
			case 7:
				item1Name = "Skin";
				item1Cost = 5;
				item2Name = null;
				item2Cost = 0;
				craftItem(item1Name, item1Cost, item2Name, item2Cost);
				break;
			case 0:
				characterMenu();
				break;
			default:
				System.out.println("Invalid Option!\n");
				break;
			}
		} while(option != 0);
	}
	
	//Method to craft an item based on user's choice
	public static void craftItem(String item1Name, int item1Cost, String item2Name, int item2Cost) {
		WeaponCraft component1 = null;
		WeaponCraft component2 = null;
		for(WeaponCraft item: character.getBag()) {
			if(item.getName().contentEquals(item1Name)) {
				if(item.getQuantity() >= item1Cost) {
					component1 = item;
				}
			}
		}
		
		if(item2Name != null) {
			for(WeaponCraft item: character.getBag()) {
				if(item.getName().contentEquals(item2Name)) {
					if(item.getQuantity() >= item2Cost) {
						component2 = item;
					}
				}
			}
		}
		
		if(component1 != null && component2 != null) {
			if(item1Name.contentEquals("Special Slab" ) && item2Name.contentEquals("Weapon Fist")) {
				WeaponCraft newItem = character.craftItem(component1, component2);
				if(newItem != null && newItem.getQuantity() > 0) {
					System.out.println("Item added to bag: " + newItem.getName() + "\n");
				} else if(newItem == null) {
					System.out.println("Weapon Craft Failed!\n");
				} else {
					System.out.println("Weapon Equiped, Attack Increased!\n");
				}
			} else if(item1Name.contentEquals("Silver Ingot" ) && item2Name.contentEquals("Golden Ingot")) {
				WeaponCraft newItem = character.craftItem(component1, component2);
				System.out.println("Item added to bag: " + newItem.getName() + "\n");
			} else if(item1Name.contentEquals("Obsidian" ) && item2Name.contentEquals("Leather")) {
				WeaponCraft newItem = character.craftItem(component1, component2);
				System.out.println("Item added to bag: " + newItem.getName() + "\n");
			} else if(item1Name.contentEquals("Steel" ) && item2Name.contentEquals("Silver")) {
				WeaponCraft newItem = character.craftItem(component1, component2);
				System.out.println("Item added to bag: " + newItem.getName() + "\n");
			} else if(item1Name.contentEquals("Copper" ) && item2Name.contentEquals("Gold")) {
				WeaponCraft newItem = character.craftItem(component1, component2);
				System.out.println("Item added to bag: " + newItem.getName() + "\n");
			}
		} else if(item1Name.contentEquals("Skin") && component2 == null ) {
			if(component1 != null) {
				WeaponCraft newItem = character.craftItem(component1, null);
				System.out.println("Item added to bag: " + newItem.getName() + "\n");
			} else {
				System.out.println("Insufficient materials to craft this item!\n");
			}
		} else {
			System.out.println("Insufficient materials to craft this item!\n");
		}
	}
	
	//Method to show the list of needed items and materials to craft a weapon
	public static void printList() {
		WeaponCraft weapon = new WeaponComponent("Weapon:");
		
		WeaponCraft item1 = new WeaponComponent("Special Slab");
		weapon.add(item1);
		
		WeaponCraft item1_1 = new WeaponComponent("Silver Ingot");
		item1.add(item1_1);
		
		WeaponCraft item1_1_1 = new WeaponComponentItem("Steel");
		item1_1.add(item1_1_1);
		
		WeaponCraft item1_1_2 = new WeaponComponentItem("Silver");
		item1_1.add(item1_1_2);
		
		WeaponCraft item1_2 = new WeaponComponent("Golden Ingot");
		item1.add(item1_2);
		
		WeaponCraft item1_2_1 = new WeaponComponentItem("Copper");
		item1_2.add(item1_2_1);
		
		WeaponCraft item1_2_2 = new WeaponComponentItem("Gold");
		item1_2.add(item1_2_2);
		
		WeaponCraft item2 = new WeaponComponent("Weapon Fist");
		weapon.add(item2);
		
		WeaponCraft item2_1 = new WeaponComponentItem("Obsidian");
		item2.add(item2_1);
		
		WeaponCraft item2_2 = new WeaponComponent("Leather");
		item2.add(item2_2);
		
		WeaponCraft item2_2_1 = new WeaponComponentItem("Skin");
		item2_2.add(item2_2_1);
		
		weapon.print();
		
	}
	
	//Method to catch user's input
	public static void chooseOption() {
        System.out.print("\nOption: ");
        option = input.nextInt();
        System.out.println();
    }
	
	//Method to show player's character information
	public static void charactersStatus(Mob mob) {
		System.out.println();
		if(mob.getHp() > 0) {
			System.out.println(mob.getName() + ": " + mob.getHp() + " HP left");
		}
		System.out.println(character.getName() + ": " + character.getHp() + " HP / " + character.getMp() + "MP");
	}

}
