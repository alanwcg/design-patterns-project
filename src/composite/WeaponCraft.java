package composite;

public abstract class WeaponCraft {
	
	private String name;
	private int quantity;
	
	public WeaponCraft(String name) {
		this.name = name;
		quantity = 0;
	}

	public void add(WeaponCraft component) {
		throw new UnsupportedOperationException();
	}
	
	public void remove(WeaponCraft component) {
		throw new UnsupportedOperationException();
	}
	
	public WeaponCraft getChild(int i) {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void updateQuantity() {
		this.quantity++;
	}
	
	public void consumePotion() {
		this.quantity--;
	}
	
	public void consumeComponent() {
		this.quantity -= 2;
	}
	
	public void consumeItem() {
		this.quantity--;
	}
	
	public void consumeMaterial() {
		this.quantity -= 5;
	}

	public void print() {
		throw new UnsupportedOperationException();
	}

}
