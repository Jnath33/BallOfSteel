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
			Bukkit.broadcastMessage("partie terminée");
			Bukkit.broadcastMessage("____________________");
			Bukkit.broadcastMessage("|L'équipe rouge à "+main.pointParTeam().get("red"));
			Bukkit.broadcastMessage("|L'équipe bleu à " +main.pointParTeam().get("blue"));
			Bukkit.broadcastMessage("|L'équipe verte à "+main.pointParTeam().get("green"));
			Bukkit.broadcastMessage("|L'équipe jaune à "+main.pointParTeam().get("yellow"));
			Bukkit.broadcastMessage("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		}
		for(Player pls : main.getPlayers()){
			if(pls.getLocation().getY()<20) {
				pls.setHealth(20);
				Bukkit.broadcastMessage("§c"+pls.getName()+"§7 viens de mourir.");
				GDeath death = new GDeath(pls, main);
				death.runTaskTimer(main, 0, 20);
			}
		}
		if(timeInMin==10&timeInSec==0|timeInMin<=5&timeInSec==0) {
			Bukkit.broadcastMessage("il reste "+timeInMin+" min");
		}
		if(timeInMin==-1&&timeInSec==30) {
			for(Player pls : main.getPlayers()) {
				main.teleportServer(pls, main.getConfig().getString("ballOfSteel.lobbyName"));
			}
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
		}
	}
}
