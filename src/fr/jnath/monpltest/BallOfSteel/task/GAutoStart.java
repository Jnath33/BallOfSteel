package fr.jnath.monpltest.BallOfSteel.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.monpltest.BallOfSteel.Main;

public class GAutoStart extends BukkitRunnable{
	int timeInS = 180;
	private Main main;
	public GAutoStart(Main _main) {
		this.main = _main;
	}
	@Override
	public void run() {
		for (Player pls : main.getPlayers()) {
			pls.setLevel(timeInS+10);
		}
		if(main.getPlayers().size()==6&&timeInS>120) {
			timeInS=120;
		}else if(main.getPlayers().size()==9&&timeInS>90) {
			timeInS=90;
		}else if(main.getPlayers().size()==12&&timeInS>60) {
			timeInS=60;
		}else if(main.getPlayers().size()==15&&timeInS>30) {
			timeInS=30;
		}else if(main.getPlayers().size()==20&&timeInS>10) {
			timeInS=10;
		}
		
		// TODO Auto-generated method stub
		if(timeInS==0) {
			//boucle prenant en conte tous les joueur de la partie
			GAutoStart2 ChooseTeam = new GAutoStart2(main);
			ChooseTeam.runTaskTimer(main, 0, 20);
			
			cancel();
		}
		if (timeInS==10 || timeInS==5) {
			Bukkit.broadcastMessage("La partie commence dans §c" + (timeInS+10)+ "s");
		}
		timeInS --;
		
	}

}
