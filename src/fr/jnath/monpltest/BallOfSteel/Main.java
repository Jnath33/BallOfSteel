package fr.jnath.monpltest.BallOfSteel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import fr.jnath.monpltest.BallOfSteel.Listener.GplayerListener;
import fr.jnath.monpltest.BallOfSteel.commands.Start;
import fr.jnath.monpltest.BallOfSteel.commands.help;
import fr.jnath.monpltest.BallOfSteel.commands.noKick;
import fr.jnath.monpltest.BallOfSteel.commands.verif;

public class Main extends JavaPlugin {
	Comparator<Player> comparePlayerbyName = new Comparator<Player>() {
		@Override
		public int compare(Player o1, Player o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	private List<Player> playersOnWating = new ArrayList<>();
	private TreeMap<Player, String> _team = new TreeMap<Player,String>(comparePlayerbyName);
 	private Gstate state;
 	public Integer playerParTeamDefaut;
 	private TreeMap<String, Integer> _playerParTeam = new TreeMap<String, Integer>();
 	private TreeMap<String, Integer> _pointParTeam = new TreeMap<String, Integer>();
 	public HashMap<Player, ScoreboardSign> scoreBoard = new HashMap<Player, ScoreboardSign>();
 	public HashMap<Player, Integer> playerKill = new HashMap<Player, Integer>();
 	public HashMap<Player, ImmutablePair<Player, Long>> lastHit = new HashMap<Player, ImmutablePair<Player, Long>>();
 	public Double midelX = getConfig().getDouble("ballOfSteel.coordonee.mid.x");
 	public Double midelZ = getConfig().getDouble("ballOfSteel.coordonee.mid.z");
 	public Double range = getConfig().getDouble("ballOfSteel.range");
 	public String world = getConfig().getString("ballOfSteel.world");
 	public Double hMax = getConfig().getDouble("ballOfSteel.hMax");
 	public Double hMin = getConfig().getDouble("ballOfSteel.hMin");
 	public fr.jnath.monpltest.BallOfSteel.util.rejen rejenWorld = new fr.jnath.monpltest.BallOfSteel.util.rejen();
 	
 	public void setState(Gstate state) {
		this.state = state;
	}
	
 	public void teleportServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        
      //Envoyer un message au joueur pour le prévenir (FACULTATIF)
        player.sendMessage(ChatColor.GREEN+"Vous etes envoyé sur "+ChatColor.GOLD+server);

        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}
	
	public boolean isState(Gstate state) {
		return state==this.state;
	}
	
	public List<Player> getPlayers(){
		return playersOnWating;
	}
	
	public TreeMap<String, Integer> pointParTeam(){
		return _pointParTeam;
	}
	
	public TreeMap<String, Integer> playerParTeam(){
		return _playerParTeam;
	 	
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
			if (_playerParTeam.get("red")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "red");
				_playerParTeam.put("red", _playerParTeam.get("red")+1);
				rmPlayerOnTeam(player, curentPlayerTeam);
				return;
			}
			else {
				player.sendMessage("ÉquipePleine");
				return;
			}
		}
		else if ("blue"==team) {
			if (_playerParTeam.get("blue")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "blue");
				_playerParTeam.put("blue", _playerParTeam.get("blue")+1);
				rmPlayerOnTeam(player, curentPlayerTeam);
				return;
			}
			else {
				player.sendMessage("ÉquipePleine");
				return;
			}
		}
		else if ("green"==team) {
			if (_playerParTeam.get("green")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "green");
				_playerParTeam.put("green", _playerParTeam.get("green")+1);
				rmPlayerOnTeam(player, curentPlayerTeam);
				return;
			}
			else {
				player.sendMessage("ÉquipePleine");
				return;
			}
		}
		else if ("yellow"==team) {
			if (_playerParTeam.get("yellow")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "yellow");
				_playerParTeam.put("yellow", _playerParTeam.get("yellow")+1);
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
			_playerParTeam.put("red", _playerParTeam.get("red")-1);
		}
		else if ("blue"==team) {
			_playerParTeam.put("blue", _playerParTeam.get("blue")-1);
		}
		else if ("green"==team) {
			_playerParTeam.put("green", _playerParTeam.get("green")-1);
		}
		else if ("yellow"==team) {
			_playerParTeam.put("yellow", _playerParTeam.get("yellow")-1);
		}
		return;
	}
	
	public void restart() {
		setState(Gstate.WAITING);
		getPlayersTeam().clear();
		getPlayers().clear();
		_playerParTeam.clear();
		_playerParTeam.put("red", 0);
		_playerParTeam.put("green", 0);
		_playerParTeam.put("blue", 0);
		_playerParTeam.put("yellow", 0);
		_pointParTeam.put("red", 0);
		_pointParTeam.put("green", 0);
		_pointParTeam.put("blue", 0);
		_pointParTeam.put("yellow", 0);
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
		saveDefaultConfig();
		System.out.println("Plugin de ball of steel actif");
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		org.bukkit.plugin.PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GplayerListener(this), this);
		playerParTeamDefaut = this.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam");
		restart();
		this.getCommand("start").setExecutor(new Start(this));
		this.getCommand("verif").setExecutor(new verif());
		this.getCommand("nokick").setExecutor(new noKick());
		this.getCommand("helpLBoS").setExecutor(new help());
		}
	@Override
	public void onDisable() {
		rejen();
	}
	
	
}
