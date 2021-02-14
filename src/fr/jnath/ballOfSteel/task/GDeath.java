package fr.jnath.ballOfSteel.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.ballOfSteel.bukkit.plugin.BallOfSteel;

public class GDeath extends BukkitRunnable{
	int timeInS = 3;
	Player player;
	BallOfSteel main;
	public GDeath(Player player, BallOfSteel main) {
		this.player=player;
		player.setGameMode(GameMode.SPECTATOR);
		this.main=main;
		player.teleport(new Location(Bukkit.getWorld(main.world), main.midelX, 70, main.midelZ));
	}
	public void run() {
		timeInS --;
		if(timeInS==0){
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
					main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(player)+".coordonee.spawn.x"),
					main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(player)+".coordonee.spawn.y"), 
					main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(player)+".coordonee.spawn.z")));
			main.setDefaltStuff(player);
		}
		if(timeInS <= -3) {
			cancel();
		}
	}
}
