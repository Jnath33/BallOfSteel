package fr.jnath.ballOfSteel.bukkit.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import fr.jnath.ballOfSteel.game.Game;
import fr.jnath.ballOfSteel.game.Gstate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.jnath.Utils.ScoreboardSign;
import fr.jnath.ballOfSteel.bukkit.plugin.listeners.GameListener;
import fr.jnath.ballOfSteel.bukkit.plugin.commands.Start;
import fr.jnath.ballOfSteel.bukkit.plugin.commands.help;
import fr.jnath.ballOfSteel.bukkit.plugin.commands.noKick;
import fr.jnath.ballOfSteel.bukkit.plugin.commands.verif;

public class BallOfSteel extends JavaPlugin {

	public static BallOfSteel INSTANCE;
	private static Game game;
	private List<Player> playersOnWating = new ArrayList<>();
 	public HashMap<Player, ImmutablePair<Player, Long>> lastHit = new HashMap<Player, ImmutablePair<Player, Long>>();

 	
 	public void setState(Gstate state) {
		this.state = state;
	}
	
 	public void teleportServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}
	
	public boolean isState(Gstate state) {
		return state==this.state;
	}
	
	public List<Player> getPlayers(){
		return playersOnWating;
	}
	
	public TreeMap<String, Integer> pointParTeam(){
		return pointParTeam;
	}
	
	public TreeMap<String, Integer> playerParTeam(){
		return playerParTeam;
	 	
	}
	
	public TreeMap<Player, String> getPlayersTeam(){
		return _team;
	}
	public void setDefaltStuff(Player pls){
		pls.getInventory().clear();
		ItemStack helmet=new ItemStack(Material.LEATHER_HELMET,1);
		LeatherArmorMeta helmetM=(LeatherArmorMeta) helmet.getItemMeta();
		if(getPlayersTeam().get(pls)=="red") {
			helmetM.setColor(Color.RED);
		} else if (getPlayersTeam().get(pls)=="green") {
			helmetM.setColor(Color.GREEN);
		}else if (getPlayersTeam().get(pls)=="blue") {
			helmetM.setColor(Color.BLUE);
		}else if (getPlayersTeam().get(pls)=="yellow") {
			helmetM.setColor(Color.YELLOW);
		}
		helmetM.setDisplayName("§7casque");
		helmet.setItemMeta(helmetM);
		pls.getInventory().setHelmet(helmet);
		pls.getInventory().setItem(8, new ItemStack(Material.GLASS, 64));
		pls.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE, 1));
		pls.getInventory().addItem(new ItemStack(Material.WOOD_SWORD, 1));
		pls.getInventory().addItem(new ItemStack(Material.IRON_AXE, 1));
		pls.updateInventory();
		pls.setHealth(20);
		pls.setFoodLevel(20);
	}
	public void addPlayerOnTeam(Player player, String team){
		String curentPlayerTeam = "non";
		if (getPlayersTeam().containsKey(player)) {
			curentPlayerTeam = getPlayersTeam().get(player);
		}
		if ("red"==team) {
			if (playerParTeam.get("red")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "red");
				playerParTeam.put("red", playerParTeam.get("red")+1);
				rmPlayerOnTeam(player, curentPlayerTeam);
				return;
			}
			else {
				player.sendMessage("ÉquipePleine");
				return;
			}
		}
		else if ("blue"==team) {
			if (playerParTeam.get("blue")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "blue");
				playerParTeam.put("blue", playerParTeam.get("blue")+1);
				rmPlayerOnTeam(player, curentPlayerTeam);
				return;
			}
			else {
				player.sendMessage("ÉquipePleine");
				return;
			}
		}
		else if ("green"==team) {
			if (playerParTeam.get("green")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "green");
				playerParTeam.put("green", playerParTeam.get("green")+1);
				rmPlayerOnTeam(player, curentPlayerTeam);
				return;
			}
			else {
				player.sendMessage("ÉquipePleine");
				return;
			}
		}
		else if ("yellow"==team) {
			if (playerParTeam.get("yellow")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "yellow");
				playerParTeam.put("yellow", playerParTeam.get("yellow")+1);
				rmPlayerOnTeam(player, curentPlayerTeam);
				return;
			}
			else {
				player.sendMessage("ÉquipePleine");
				return;
			}
		}
		return;
	}
	
	public void rmPlayerOnTeam(Player player, String team) {
		if ("red"==team) {
			playerParTeam.put("red", playerParTeam.get("red")-1);
		}
		else if ("blue"==team) {
			playerParTeam.put("blue", playerParTeam.get("blue")-1);
		}
		else if ("green"==team) {
			playerParTeam.put("green", playerParTeam.get("green")-1);
		}
		else if ("yellow"==team) {
			playerParTeam.put("yellow", playerParTeam.get("yellow")-1);
		}
		return;
	}
	
	public void init() {
		setState(Gstate.WAITING);
		getPlayersTeam().clear();
		getPlayers().clear();
		playerParTeam.clear();
		playerParTeam.put("red", 0);
		playerParTeam.put("green", 0);
		playerParTeam.put("blue", 0);
		playerParTeam.put("yellow", 0);
		pointParTeam.put("red", 0);
		pointParTeam.put("green", 0);
		pointParTeam.put("blue", 0);
		pointParTeam.put("yellow", 0);
	}

	public void rejen() {
		World world = Bukkit.getWorld(this.world);
		Bukkit.unloadWorld(world, false);
		File worldFile = new File(world.getName());
		File worldCopyFile = new File(this.world+"-copy");
		rejenWorld.deleateWorld(worldFile);
		try {
			rejenWorld.copyWorld(worldCopyFile, worldFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onEnable() {
 		//save config
		saveDefaultConfig();

		//send message plugin actif
		System.out.println("Plugin de ball of steel actif");

		//get BungeeCord plugin message channel
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		//get spigot plugin manager
		org.bukkit.plugin.PluginManager pm = getServer().getPluginManager();
		//init listeners
		pm.registerEvents(new GameListener(this), this);

		//init variable
		playerParTeamDefaut = this.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam");
		init();
		this.getCommand("start").setExecutor(new Start(this));
		this.getCommand("verif").setExecutor(new verif());
		this.getCommand("nokick").setExecutor(new noKick());
		this.getCommand("helpLBoS").setExecutor(new help());
		}
	@Override
	public void onDisable() {
		rejen();
	}

	public static Game getGame() {
		return game;
	}
}
