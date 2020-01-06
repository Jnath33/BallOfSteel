package fr.jnath.monpltest.BallOfSteel.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.monpltest.BallOfSteel.Gstate;
import fr.jnath.monpltest.BallOfSteel.Main;

public class GAutoStart extends BukkitRunnable{
	int timeInS = 10;
	int nbJoueurParEquipe;
	private Main main;
	public GAutoStart(Main _main) {
		this.main = _main;
		nbJoueurParEquipe = _main.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam");
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
					if (main.playerParTeam().get("red")<main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "red");
					}
					else if (main.playerParTeam().get("green")<main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "green");
					}
					else if (main.playerParTeam().get("blue")<main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "blue");
					}
					else if (main.playerParTeam().get("yellow")<main.playerParTeamDefaut) {
						main.addPlayerOnTeam(pls, "yellow");
					}
				}
				
				System.out.println(main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(pls)+".coordonee.spawn.x"));
				System.out.println(main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(pls)+".coordonee.spawn.y"));
				System.out.println(main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(pls)+".coordonee.spawn.z"));
				//tp au spawn
				pls.teleport(new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
						main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(pls)+".coordonee.spawn.x"),
						main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(pls)+".coordonee.spawn.y"), 
						main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(pls)+".coordonee.spawn.z")));
				
				//update de l'inventaire
				pls.getInventory().clear();
				pls.getInventory().setItem(8, new ItemStack(Material.GLASS, 64));
				pls.updateInventory();
			}
			main.setState(Gstate.PLAYING);
			cancel();
		}
		if (timeInS==10 || timeInS<=5) {
			Bukkit.broadcastMessage("La partie commence dans §c" + timeInS+ "s");
		}
		timeInS --;
		
	}

}
