package fr.jnath.monpltest.BallOfSteel.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jnath.monpltest.BallOfSteel.Gstate;
import fr.jnath.monpltest.BallOfSteel.Main;
import fr.jnath.monpltest.BallOfSteel.task.GAutoStart;

public class GplayerListener implements Listener {
	
	private Main main;
	
	public GplayerListener(Main main) {
		this.main = main;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		int playerMax = main.getConfig().getInt("ballOfSteel.nomberOfPlayerParTeam")*4;
		Player player = event.getPlayer();
		Location spawn = new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
				main.getConfig().getDouble("ballOfSteel.coordonee.start.x"),
				main.getConfig().getDouble("ballOfSteel.coordonee.start.y"),
				main.getConfig().getDouble("ballOfSteel.coordonee.start.z"));
		player.teleport(spawn);
		player.getInventory().clear();
		if(!main.isState(Gstate.WAITING)) {
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("La partie est deja lancer !");
			event.setJoinMessage(null);
			return;	
		}
		
		if(!main.getPlayers().contains(player)) main.getPlayers().add(player);
		event.setJoinMessage("§7[§eBall of steel§7] §l§c"+player.getName()+"§r§6 a rejoin la partie " + 
		main.getPlayers().size() + "/" + playerMax);
		player.setGameMode(GameMode.ADVENTURE);
		//don des item
		
		//item
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
		player.getInventory().setItem(0, redW);
		player.getInventory().setItem(3, blueW);
		player.getInventory().setItem(5, yelowW);
		player.getInventory().setItem(8, greenW);
		player.updateInventory();
		
		player.setAllowFlight(false);
		player.setDisplayName("§c"+player.getName()+"§7");
		if(main.isState(Gstate.WAITING) && main.getPlayers().size() == playerMax-3) {
			GAutoStart autoStart = new GAutoStart(main);
			autoStart.runTaskTimer(main, 0, 20);
			main.setState(Gstate.STARTING);
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		System.out.println(main.getConfig().getInt("ballOfSteel.coordonee.start.x"));
	}
	
	
	@EventHandler
	public void PlayerDamageEvent​(EntityDamageEvent event){
		
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		event.getCause();
		Player player = (Player) event.getEntity();
		if(event.getCause()==DamageCause.ENTITY_ATTACK) {
			
		}
		if(player.getHealth() < event.getDamage()) {
			player.setHealth(20);
			player.getInventory().clear();
			Bukkit.broadcastMessage("§c Jnath §7 viens de mourir.");
			// cette partie de code servira quand les sélection de team et les getTeam marcheron
			
			player.teleport(new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
					main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(player)+".coordonee.spawn.x"),
					main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(player)+".coordonee.spawn.y"), 
					main.getConfig().getDouble("ballOfSteel.equipe."+main.getPlayersTeam().get(player)+".coordonee.spawn.z")));
			event.setCancelled(true);
		}
		player.setCustomName("§c"+player.getName());
	}
	
	@EventHandler
	public void PlayerDropItemEvent​(PlayerDropItemEvent event) {
		if (main.isState(Gstate.STARTING)||main.isState(Gstate.WAITING)) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Material itM = event.getMaterial();
		ItemStack it= event.getItem();
		if(itM == Material.WOOL) {
			if(it.getItemMeta().getDisplayName()=="§l§4Red") {
				main.addPlayerOnTeam(player, "red");
			}else if(it.getItemMeta().getDisplayName()=="§l§1Blue") {
				main.addPlayerOnTeam(player, "blue");
			}else if(it.getItemMeta().getDisplayName()=="§l§2Green") {
				main.addPlayerOnTeam(player, "green");				
			}else if(it.getItemMeta().getDisplayName()=="§l§eYellow") {
				main.addPlayerOnTeam(player, "yellow");
			}
		}
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		Material block = event.getBlock().getType();
		if (block == Material.GLASS && main.isState(Gstate.PLAYING)) {
			player.getInventory().setItem(8, new ItemStack(Material.GLASS,64));
		}
	}
}
