package fr.jnath.monpltest.BallOfSteel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.jnath.monpltest.BallOfSteel.Listener.GplayerListener;

public class Main extends JavaPlugin {
	
	private List<Player> playersOnWating = new ArrayList<>();
	private TreeMap<Player, String> _team = new TreeMap<Player, String>();
 	private Gstate state;
 	private Integer playerParTeamDefaut;
 	private TreeMap<String, Integer> _playerParTeam = new TreeMap<String, Integer>();
 	
 	
 	public void setState(Gstate state) {
		this.state = state;
	}
	
	
	public boolean isState(Gstate state) {
		return state==this.state;
	}
	
	public List<Player> getPlayers(){
		return playersOnWating;
	}
	
//	public TreeMap<String, Integer> _playerParTeam{
//		return _playerParTeam;
//	}
	
	public TreeMap<Player, String> getPlayersTeam(){
		return _team;
	}
	
	public void addPlayerOnTeam(Player player, String team){
		System.out.println("test 43 M");
		String curentPlayerTeam = "non";
		System.out.println(curentPlayerTeam);
		getPlayersTeam().containsKey(player);
		System.out.println(curentPlayerTeam);	
		if (getPlayersTeam().containsKey(player)) {
			System.out.println(curentPlayerTeam+2);
			curentPlayerTeam = getPlayersTeam().get(player);
			System.out.println(curentPlayerTeam);
		}
		System.out.println(curentPlayerTeam);
		if ("red"==team) {
			System.out.println("test red M");
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
			System.out.println("test blue M");
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
			System.out.println("test green M");
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
			System.out.println("test yellow M");
			if (_playerParTeam.get("yellow")<playerParTeamDefaut) {
				getPlayersTeam().put(player, "red");
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
	@Override
	public void onEnable() {
		saveDefaultConfig();
		setState(Gstate.WAITING);
		getPlayersTeam().clear();
		System.out.println("Plugin de ball of steel actif");
		org.bukkit.plugin.PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GplayerListener(this), this);
		playerParTeamDefaut = this.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam");
		_playerParTeam.put("red", 0);
		_playerParTeam.put("green", 0);
		_playerParTeam.put("blue", 0);
		_playerParTeam.put("yellow", 0);
		}
	
	
}
