package fr.jnath.monpltest.BallOfSteel.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.Utils.ScoreboardSign;
import fr.jnath.monpltest.BallOfSteel.Gstate;
import fr.jnath.monpltest.BallOfSteel.Main;

public class GAutoStart2 extends BukkitRunnable{
	int timeInS = 10;
	int nbJoueurParEquipe;
	private Main _main;
	public GAutoStart2(Main _main) {
		this._main = _main;
		nbJoueurParEquipe = _main.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam");
		for(Player pls : _main.getPlayers()) {
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
		_main.setState(Gstate.CHOOSETEAM);
		if(_main.getPlayers().size()<=4&&_main.getPlayers().size()>0) _main.playerParTeamDefaut=1;
		if(_main.getPlayers().size()<=8&&_main.getPlayers().size()>4) _main.playerParTeamDefaut=2;
		if(_main.getPlayers().size()<=12&&_main.getPlayers().size()>8) _main.playerParTeamDefaut=3;
		if(_main.getPlayers().size()<=16&&_main.getPlayers().size()>12) _main.playerParTeamDefaut=4;
		if(_main.getPlayers().size()<=20&&_main.getPlayers().size()>16) _main.playerParTeamDefaut=5;
	}
	@Override
	public void run() {
		for (Player pls : _main.getPlayers()) {
			pls.setLevel(timeInS);
		}
		// TODO Auto-generated method stub
		if(timeInS==0) {
			//boucle prenant en conte tous les joueur de la partie
			for (Player pls : _main.getPlayers()) {
				//passage des joueur en survie
				pls.setGameMode(GameMode.SURVIVAL);
				
				//ajout des joueur qui n'ont pas choisis leur équipe dans des équipe random
				if (!_main.getPlayersTeam().containsKey(pls)) {
					if (_main.playerParTeam().get("red")<_main.playerParTeamDefaut) {
						_main.addPlayerOnTeam(pls, "red");
					}
					else if (_main.playerParTeam().get("green")<_main.playerParTeamDefaut) {
						_main.addPlayerOnTeam(pls, "green");
					}
					else if (_main.playerParTeam().get("blue")<_main.playerParTeamDefaut) {
						_main.addPlayerOnTeam(pls, "blue");
					}
					else if (_main.playerParTeam().get("yellow")<_main.playerParTeamDefaut) {
						_main.addPlayerOnTeam(pls, "yellow");
					}
				}
				ScoreboardSign sc = new ScoreboardSign(pls, "§7Game");
				_main.scoreBoard.put(pls, sc);
				sc.create();
				if(_main.getPlayersTeam().get(pls)=="red")sc.setLine(1, "§4Rouge : 0 §7§lYOU");
				else sc.setLine(1, "§4Rouge : 0");
				if(_main.getPlayersTeam().get(pls)=="blue")sc.setLine(2, "§1Bleu  : 0 §7§lYOU");
				else sc.setLine(2, "§1Bleu  : 0");
				if(_main.getPlayersTeam().get(pls)=="green")sc.setLine(3, "§2Vert  : 0 §7§lYOU");
				else sc.setLine(3, "§2Vert  : 0");
				if(_main.getPlayersTeam().get(pls)=="yellow")sc.setLine(4, "§eJaune : 0 §7§lYOU");
				else sc.setLine(4, "§eJaune : 0");
				sc.setLine(5, "Temps restant :");
				sc.setLine(6, "Kill :");
				
				//tp au spawn
				pls.setBedSpawnLocation(new Location(Bukkit.getWorld(_main.getConfig().getString("ballOfSteel.world")),
						_main.getConfig().getDouble("ballOfSteel.equipe."+_main.getPlayersTeam().get(pls)+".coordonee.spawn.x"),
						_main.getConfig().getDouble("ballOfSteel.equipe."+_main.getPlayersTeam().get(pls)+".coordonee.spawn.y"), 
						_main.getConfig().getDouble("ballOfSteel.equipe."+_main.getPlayersTeam().get(pls)+".coordonee.spawn.z")));
				pls.teleport(new Location(Bukkit.getWorld(_main.getConfig().getString("ballOfSteel.world")),
						_main.getConfig().getDouble("ballOfSteel.equipe."+_main.getPlayersTeam().get(pls)+".coordonee.spawn.x"),
						_main.getConfig().getDouble("ballOfSteel.equipe."+_main.getPlayersTeam().get(pls)+".coordonee.spawn.y"), 
						_main.getConfig().getDouble("ballOfSteel.equipe."+_main.getPlayersTeam().get(pls)+".coordonee.spawn.z")));
				
				//update de l'inventaire
				_main.setDefaltStuff(pls);
			}
			_main.setState(Gstate.PLAYING);
			Game game = new Game(_main);
			game.runTaskTimer(_main, 0, 20);
			
			cancel();
		}
		if (timeInS==10 || timeInS<=5) {
			Bukkit.broadcastMessage("La partie commence dans §c" + timeInS+ "s");
		}
		timeInS --;
		
	}

}
