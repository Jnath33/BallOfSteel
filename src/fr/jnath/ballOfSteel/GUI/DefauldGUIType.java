package fr.jnath.ballOfSteel.GUI;

import fr.jnath.Utils.Utils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public enum DefauldGUIType {

	SMOOTH(new int[] {
			2,2,2,1,1,1,2,2,2,
			2,1,1,3,0,3,1,1,2,
			1,0,3,0,3,0,3,0,1,
			2,1,1,3,0,3,1,1,2,
			2,2,2,1,1,1,2,2,2
	}, 5),
	ACCEPT(new int[] {
			0,0,0,3,0,3,0,0,0
	},1),
	CORNER(new int[] {
			5,2,0,0,0,0,0,2,5,
			2,3,0,3,0,3,0,3,2,
			0,6,0,6,0,6,0,6,0,
			2,3,0,3,0,3,0,3,2,
			5,2,0,0,0,0,0,2,5
	}, 5),
	VOID(new int[] {
			2,2,2,2,5,2,2,2,5,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0
	},6);

	private List<Integer> grayGlassPos = new ArrayList<Integer>();
	private List<Integer> blackGlassPos = new ArrayList<Integer>();
	private TreeMap<Integer, Integer> itemIdAndPos = new TreeMap<Integer, Integer>();
	private TreeMap<Integer, Integer> displayerIdAndPos = new TreeMap<Integer, Integer>();
	private int nomberOfLine;
	
	private DefauldGUIType(int[] items, int line) {
		nomberOfLine=line;
		int i = 0;
		int buttonN = 0;
		int displayN = 0;
		for(int itemInt : items) {
			switch (itemInt) {
				case 1:
					grayGlassPos.add(i);
					break;
				case 2:
					blackGlassPos.add(i);
					break;
				case 3:
					itemIdAndPos.put(buttonN, i);
					buttonN++;
					break;
				case 4:
					grayGlassPos.add(i);
					itemIdAndPos.put(buttonN, i);
					buttonN++;
					break;
				case 5:
					blackGlassPos.add(i);
					itemIdAndPos.put(buttonN, i);
					buttonN++;
					break;
				case 6:
					displayerIdAndPos.put(displayN, i);
					displayN++;
					break;
				case 7:
					grayGlassPos.add(i);
					displayerIdAndPos.put(displayN, i);
					displayN++;
					break;
				case 8:
					blackGlassPos.add(i);
					displayerIdAndPos.put(displayN, i);
					displayN++;
					break;

			default:
				break;
			}
			i++;
		}
	}
	
	public List<Integer> getGrayGlassPos() {
		return grayGlassPos;
	}

	public List<Integer> getBlackGlassPos() {
		return blackGlassPos;
	}

	public int getItemPos(int id) {
		return itemIdAndPos.get(id);
	}

	public int getDisplayPos(int id) {
		return displayerIdAndPos.get(id);
	}
	
	public int getNomberOfLine() {
		return nomberOfLine;
	}

	public Inventory generateAcceptGUI(GUI currentGUI, Consumer<Player> accept, String name, String displayName){
		GUI gui = new GUI(name, Type.MENU, Bukkit.createInventory(null,getNomberOfLine()*9,Type.MENU.getCode()+displayName), displayName);
		gui.addButton(new Button(accept,accept,accept,accept,"button"), getItemPos(0));
		gui.getInventory().setItem(getItemPos(0),Utils.createItem("§aAccept", Material.WOOL,1,(short) 5	));
		gui.addButton(new Button(player -> {
			GUI.openGUI(currentGUI.getName(),player);
		},player -> {
			GUI.openGUI(currentGUI.getName(),player);
		},player -> {
			GUI.openGUI(currentGUI.getName(),player);
		},player -> {
			GUI.openGUI(currentGUI.getName(),player);
		},"button"), getItemPos(1));
		gui.getInventory().setItem(getItemPos(1),Utils.createItem("§cDeny", Material.WOOL,1,(short) 14));
		return gui.getInventory();
	}

	public Inventory generateGUI (Type type, String name, Map<Integer, ImmutablePair<ItemStack, Button>> items, Map<Integer, Displayer> displayers, String displayName) {
		Inventory inv = Bukkit.createInventory(null, getNomberOfLine()*9, type.getCode()+displayName);
		ItemStack item1=Utils.createItem("§1", Material.STAINED_GLASS_PANE, 1, (short) 8);
		ItemStack item2=Utils.createItem("§1", Material.STAINED_GLASS_PANE, 1, (short) 15);
		for(int i : getGrayGlassPos()) {
			inv.setItem(i, item1);
		}
		for(int i : getBlackGlassPos()) {
			inv.setItem(i, item2);
		}
		for(Integer itemId : items.keySet()) {
			if(itemId != null) {
				inv.setItem(getItemPos(itemId), items.get(itemId).left);
			}
		}
		GUI gui = new GUI(name, type, inv, displayName);
		for(Integer itemId : items.keySet()) {
			if(itemId != null) {
				gui.addButton(items.get(itemId).right, getItemPos(itemId));
			}
		}
		for(Integer displayerId : displayers.keySet()) {
			if(displayerId != null) gui.addDisplayer(getDisplayPos(displayerId),
					displayers.get(displayerId));
		}
		return inv;
	}

	public Inventory generateGUI (Type type, String name, Map<Integer, ImmutablePair<ItemStack, Button>> items, String displayName) {
		Inventory inv = Bukkit.createInventory(null, getNomberOfLine()*9, type.getCode()+displayName);
		ItemStack item1=Utils.createItem("§1", Material.STAINED_GLASS_PANE, 1, (short) 8);
		ItemStack item2=Utils.createItem("§1", Material.STAINED_GLASS_PANE, 1, (short) 15);
		for(int i : getGrayGlassPos()) {
			inv.setItem(i, item1);
		}
		for(int i : getBlackGlassPos()) {
			inv.setItem(i, item2);
		}
		for(Integer itemId : items.keySet()) {
			if(itemId != null) {
				inv.setItem(getItemPos(itemId), items.get(itemId).left);
			}
		}
		GUI gui = new GUI(name, type, inv, displayName);
		for(Integer itemId : items.keySet()) {
			if(itemId != null) {
				gui.addButton(items.get(itemId).right, getItemPos(itemId));
			}

		}
		return inv;
	}
}
