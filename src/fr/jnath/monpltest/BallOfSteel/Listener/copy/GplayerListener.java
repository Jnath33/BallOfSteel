package fr.jnath.monpltest.BallOfSteel.Listener.copy;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.jnath.monpltest.BallOfSteel.Gstate;
import fr.jnath.monpltest.BallOfSteel.Main;

public class GplayerListener implements Listener {
	
	private Main main;
	
	
	public GplayerListener(Main main) {
		this.main = main;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Location spawn = new Location(Bukkit.getWorld("world"),5,100,4);
		player.teleport(spawn);
		if(!main.isState(Gstate.WAITING)) {
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("La partie est deja lancer !");
			return;	
		}
		player.setGameMode(GameMode.ADVENTURE);
		if(!main.getPlayers().contains(player))main.getPlayers().add(player);
		if(main.isState(Gstate.WAITING) && main.getPlayers().size() == 2) {
			main.setState(Gstate.STARTING);
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		System.out.println(main.getConfig().getInt("ballOfSteel.coordonee.start.x"));
	}
}
