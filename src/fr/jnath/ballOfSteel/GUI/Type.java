package fr.jnath.ballOfSteel.GUI;

import org.bukkit.inventory.Inventory;

public enum Type {
	MENU("§1§1§1", false),
	SETSTUFF("§1§1§2", true);
	private final String code;
	private final boolean pickableItem;
	private Type(String str, boolean canPickup) {
		code=str;
		pickableItem=canPickup;
	}
	public String getCode() {
		return code;
	}
	public boolean isPickableItem() {
		return pickableItem;
	}

	public static Type inventoryType(Inventory inv) {
		try {
			for (Type code : Type.values()) {
				if (inv.getName().startsWith(code.getCode())) {
					return code;
				}
			}
		}catch (NullPointerException e){}
		return null;
	}
}
