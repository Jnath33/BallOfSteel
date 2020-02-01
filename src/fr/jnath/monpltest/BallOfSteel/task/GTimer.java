package fr.jnath.monpltest.BallOfSteel.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.monpltest.BallOfSteel.Main;

public class GTimer extends BukkitRunnable{
	Integer timeInMin;
	Integer timeInSec = 0;
	Main main;
	public GTimer(Main main) {
		this.main=main;
		timeInMin=main.getConfig().getInt("ballOfSteel.time");
	}
	public void run() {
		if (timeInSec==0){
			timeInMin=timeInMin-1;
			timeInSec = 59;
		} else timeInSec=timeInSec-1;
		if(timeInMin==0&&timeInSec==0) {
			Bukkit.broadcastMessage("partie terminer");
			main.restart();
		}
		for(Player pls : main.getPlayers()){
			if(pls.getLocation().getY()<20) {
				pls.setHealth(20);
				Bukkit.broadcastMessage("§c"+pls.getName()+"§7 viens de mourir.");
				GDeath death = new GDeath(pls, main);
				death.runTaskTimer(main, 0, 20);
			}
		}
	}
}
