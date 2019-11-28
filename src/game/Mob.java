package game;

import java.util.Set;
import java.util.TreeSet;

import composite.WeaponComponentItem;
import observer.Observer;
import observer.Subject;
import state.Alive;
import state.Dead;
import state.Frenzied;
import state.InvalidStatusException;
import state.MobStatus;
import strategy.NormalAttack;

public class Mob extends Character implements Subject{
	
	private MobStatus currentStatus;
	private MobStatus aliveStatus;
	private MobStatus frenziedStatus;
	private MobStatus deadStatus;
	
	Set<Observer> observers = new TreeSet<>();

	public Mob(String name) {
		super(name);
		setHp(600);
		setMp(200);
		
		this.aliveStatus = new Alive(this);
		this.frenziedStatus = new Frenzied(this);
		this.deadStatus = new Dead(this);
		this.currentStatus = this.aliveStatus;
		
		attack = new NormalAttack();
		
		bag.add(new WeaponComponentItem("Steel"));
		bag.add(new WeaponComponentItem("Silver"));
		bag.add(new WeaponComponentItem("Copper"));
		bag.add(new WeaponComponentItem("Gold"));
		bag.add(new WeaponComponentItem("Obsidian"));
		bag.add(new WeaponComponentItem("Skin"));
		bag.add(new WeaponComponentItem("Healing Potion"));
		bag.add(new WeaponComponentItem("Mana Potion"));
		
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
		
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
		
	}

	@Override
	public void notificar() {
		for(Observer observer : observers) {
			observer.update(this);
		}
		
	}

	public MobStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(MobStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public MobStatus getAliveStatus() {
		return aliveStatus;
	}

	public MobStatus getFrenziedStatus() {
		return frenziedStatus;
	}

	public MobStatus getDeadStatus() {
		return deadStatus;
	}
	
	public void useFrenzy(int hp) throws InvalidStatusException {
		currentStatus.useFrenzy(hp);
		System.out.println("\nMob used Frenzy!");
	}
	
	public void die(int hp) throws InvalidStatusException {
		currentStatus.die(hp);
	}
	
	@Override
	public int compareTo(Character o) {
		if(this.equals(o)) {
			return 0;
		}
		return 1;
	}

}
