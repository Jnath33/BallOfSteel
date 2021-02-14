package fr.jnath.ballOfSteel.task;

import fr.jnath.ballOfSteel.game.Game;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.Utils.ScoreboardSign;
import fr.jnath.ballOfSteel.bukkit.plugin.BallOfSteel;

public class GameTask extends BukkitRunnable{
	Integer timeInMin;
	Integer timeInSec = 0;
	BallOfSteel main;
	Game game = BallOfSteel.getGame();
	public GameTask(BallOfSteel main) {
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
		String timeStr;
		if(String.valueOf(timeInSec).length()==1) {
			timeStr = timeInMin+":0"+timeInSec;
		}else {
			timeStr = timeInMin+":"+timeInSec;
		}
		for(Player pls : main.getPlayers()){
			if(pls.getLocation().getY()<20) {
				pls.setHealth(20);ImmutablePair<Player,Long> lastHit = main.lastHit.get(pls);
				if(lastHit == null)
					Bukkit.broadcastMessage("§c"+pls.getName()+"§7 viens de mourir.");
				else {
					Bukkit.broadcastMessage("§c"+pls.getName()+"§7 à été tué par §c"+lastHit.left.getName()+".");
					game.get.playerKill.put(lastHit.left,main.playerKill.get(lastHit.left)+1);
					ScoreboardSign sc = main.scoreBoard.get(lastHit.left);
					sc.setLine(7, "§1Kill : "+main.playerKill.get(lastHit.left));
					int diamond = 0;
					for(ItemStack it : pls.getInventory().getContents()) {
						if(it.getType()==Material.DIAMOND) {
							lastHit.left.getInventory().addItem(it);
							diamond+=it.getAmount();
						}
					}
					if(diamond!=0) {
						lastHit.left.sendMessage("§5+§b"+diamond+"§3diamonds.");
					}
				}
				GDeath death = new GDeath(pls, main);
				death.runTaskTimer(main, 0, 20);
			}
				ScoreboardSign sc = main.scoreBoard.get(pls);
				sc.setLine(6, "§1Temps restant : §c"+timeStr);
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
