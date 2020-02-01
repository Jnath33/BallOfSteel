package fr.jnath.monpltest.BallOfSteel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.jnath.monpltest.BallOfSteel.Listener.GplayerListener;

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
 	public Double midelX = getConfig().getDouble("ballOfSteel.coordonee.mid.x");
 	public Double midelZ = getConfig().getDouble("ballOfSteel.coordonee.mid.z");
 	public Double range = getConfig().getDouble("ballOfSteel.range");
 	public String world = getConfig().getString("ballOfSteel.world");
 	public Double hMax = getConfig().getDouble("ballOfSteel.hMax");
 	public Double hMin = getConfig().getDouble("ballOfSteel.hMin");
 	
 	public void setState(Gstate state) {
		this.state = state;
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
		for(Double x =midelX-range; x<midelX+range;x ++) {
			for(Double z =midelZ-range; z<midelZ+range;z ++) {
				for(Double y =hMin; y<hMax;y ++) {
					Block b=new Location(Bukkit.getWorld(world),x,y,z).getBlock();
					Location to=	new Location(Bukkit.getWorld(world), x, y, z);
					to.getBlock().setType(b.getType());					
				}
			}
		}
		
		
	}
	@Override
	public void onEnable() {
		saveDefaultConfig();
		System.out.println("Plugin de ball of steel actif");
		org.bukkit.plugin.PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GplayerListener(this), this);
		playerParTeamDefaut = this.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam");
		restart();
		}
	
	
}
