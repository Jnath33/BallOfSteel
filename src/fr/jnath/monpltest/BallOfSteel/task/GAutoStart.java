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
				pls.teleport(new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
						main.getConfig().getDouble("ballOfSteel.equipe."+"red"+".coordonee.spawn.x"),
						main.getConfig().getDouble("ballOfSteel.equipe."+"red"+".coordonee.spawn.y"), 
						main.getConfig().getDouble("ballOfSteel.equipe."+"red"+".coordonee.spawn.x")));
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
