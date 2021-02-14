package fr.jnath.ballOfSteel.game.waiting;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.ballOfSteel.bukkit.plugin.BallOfSteel;

public class GAutoStart extends BukkitRunnable{
	private int timeInS = 180;
	private final BallOfSteel main;
	public GAutoStart() {
		this.main = BallOfSteel.INSTANCE;
	}
	@Override
	public void run() {
		for (Player pls : main.getPlayers()) {
			pls.setLevel(timeInS+10);
		}
		if(main.getPlayers().size())
		if(main.getPlayers().size()>=6&&timeInS>120) {
			timeInS=120;
		}else if(main.getPlayers().size()>=9&&timeInS>90) {
			timeInS=90;
		}else if(main.getPlayers().size()>=12&&timeInS>60) {
			timeInS=60;
		}else if(main.getPlayers().size()>=15&&timeInS>30) {
			timeInS=30;
		}else if(main.getPlayers().size()>=20&&timeInS>10) {
			timeInS=10;
		}
		if (timeInS==60 || timeInS==30 || timeInS==10 || timeInS<=5) {
			Bukkit.broadcastMessage("La partie commence dans §c" + (timeInS+10)+ "s");
		}
		timeInS --;
		
	}

}
