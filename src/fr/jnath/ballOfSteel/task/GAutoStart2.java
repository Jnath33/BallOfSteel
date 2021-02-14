package fr.jnath.ballOfSteel.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.Utils.ScoreboardSign;
import fr.jnath.ballOfSteel.game.Gstate;
import fr.jnath.ballOfSteel.bukkit.plugin.BallOfSteel;

public class GAutoStart2 extends BukkitRunnable{
	int timeInS = 10;
	int nbJoueurParEquipe;
	private BallOfSteel main;
	public GAutoStart2(BallOfSteel main) {
		this.main = main;
		nbJoueurParEquipe = main.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam");
		for(Player pls : main.getPlayers()) {
				ItemStack redW = new ItemStack(Material.WOOL,1, (byte)14);
				ItemStack greenW = new ItemStack(Material.WOOL,1, (byte)5);
				ItemStack blueW = new ItemStack(Material.WOOL,1, (byte)11);
				ItemStack yelowW = new ItemStack(Material.WOOL,1, (byte)4);
				//meta
				ItemMeta redWM = redW.getItemMeta();
				ItemMeta greenWM = greenW.getItemMeta();
				ItemMeta blueWM = blueW.getItemMeta();
				ItemMeta yelowWM = yelowW.getItemMeta();
				

				//name
				redWM.setDisplayName("§l§4Red");
				greenWM.setDisplayName("§l§2Green");
				blueWM.setDisplayName("§l§1Blue");
				yelowWM.setDisplayName("§l§eYellow");
				
				//replace meta
				redW.setItemMeta(redWM);
				greenW.setItemMeta(greenWM);
				blueW.setItemMeta(blueWM);
				yelowW.setItemMeta(yelowWM);
				
				//update inventory
				pls.getInventory().setItem(0, redW);
				pls.getInventory().setItem(3, blueW);
				pls.getInventory().setItem(5, yelowW);
				pls.getInventory().setItem(8, greenW);
				pls.updateInventory();
		}
		main.setState(Gstate.CHOOSETEAM);
		if(main.getPlayers().size()<=4&& main.getPlayers().size()>0) main.playerParTeamDefaut=1;
		if(main.getPlayers().size()<=8&& main.getPlayers().size()>4) main.playerParTeamDefaut=2;
		if(main.getPlayers().size()<=12&& main.getPlayers().size()>8) main.playerParTeamDefaut=3;
		if(main.getPlayers().size()<=16&& main.getPlayers().size()>12) main.playerParTeamDefaut=4;
		if(main.getPlayers().size()<=20&& main.getPlayers().size()>16) main.playerParTeamDefaut=5;
	}
	@Override
	public void run() {
		for (Player pls : main.getPlayers()) {
			pls.setLevel(timeInS);
		}
		// TODO Auto-generated method stub
		if(timeInS==0) {
			//boucle prenant en conte tous les joueur de la partie
			for (Player pls : main.getPlayers()) {
				//passage des joueur en survie
				pls.setGameMode(GameMode.SURVIVAL);
				
				//ajout des joueur qui n'ont pas choisis leur équipe dans des équipe random
				if (!main.getPlayersTeam().containsKey(pls)) {
					if (main.playerParTeam().get("red")< main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "red");
					}
					else if (main.playerParTeam().get("green")< main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "green");
					}
					else if (main.playerParTeam().get("blue")< main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "blue");
					}
					else if (main.playerParTeam().get("yellow")< main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "yellow");
					}
				}
				ScoreboardSign sc = new ScoreboardSign(pls, "§7Game");
				main.scoreBoard.put(pls, sc);
				sc.create();
				if(main.getPlayersTeam().get(pls)=="red")sc.setLine(1, "§4Rouge : 0 §7§lYOU");
				else sc.setLine(1, "§4Rouge : 0");
				if(main.getPlayersTeam().get(pls)=="blue")sc.setLine(2, "§1Bleu  : 0 §7§lYOU");
				else sc.setLine(2, "§1Bleu  : 0");
				if(main.getPlayersTeam().get(pls)=="green")sc.setLine(3, "§2Vert  : 0 §7§lYOU");
				else sc.setLine(3, "§2Vert  : 0");
				if(main.getPlayersTeam().get(pls)=="yellow")sc.setLine(4, "§eJaune : 0 §7§lYOU");
				else sc.setLine(4, "§eJaune : 0");
				sc.setLine(5, "");
				sc.setLine(6, "§1Temps restant : §c0:0");
				sc.setLine(7, "§1Kill : 0");
				main.playerKill.put(pls, 0);
				
				//tp au spawn
				pls.setBedSpawnLocation(new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
						main.getConfig().getDouble("ballOfSteel.equipe."+ main.getPlayersTeam().get(pls)+".coordonee.spawn.x"),
						main.getConfig().getDouble("ballOfSteel.equipe."+ main.getPlayersTeam().get(pls)+".coordonee.spawn.y"),
						main.getConfig().getDouble("ballOfSteel.equipe."+ main.getPlayersTeam().get(pls)+".coordonee.spawn.z")));
				pls.teleport(new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
						main.getConfig().getDouble("ballOfSteel.equipe."+ main.getPlayersTeam().get(pls)+".coordonee.spawn.x"),
						main.getConfig().getDouble("ballOfSteel.equipe."+ main.getPlayersTeam().get(pls)+".coordonee.spawn.y"),
						main.getConfig().getDouble("ballOfSteel.equipe."+ main.getPlayersTeam().get(pls)+".coordonee.spawn.z")));
				
				//update de l'inventaire
				main.setDefaltStuff(pls);
			}
			main.setState(Gstate.PLAYING);
			GameTask gameTask = new GameTask(main);
			gameTask.runTaskTimer(main, 0, 20);
			
			cancel();
		}
		if (timeInS==10 || timeInS<=5) {
			Bukkit.broadcastMessage("La partie commence dans §c" + timeInS+ "s");
		}
		timeInS --;
		
	}

}
