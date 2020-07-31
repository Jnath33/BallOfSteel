package fr.jnath.monpltest.BallOfSteel.Listener;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fr.jnath.Utils.ScoreboardSign;
import fr.jnath.monpltest.BallOfSteel.Gstate;
import fr.jnath.monpltest.BallOfSteel.Main;
import fr.jnath.monpltest.BallOfSteel.task.GAutoStart;
import fr.jnath.monpltest.BallOfSteel.task.GDeath;
import fr.jnath.monpltest.BallOfSteel.task.HitTime;

public class GplayerListener implements Listener {
	public GAutoStart autoStart;
	private Main main;
	
	public GplayerListener(Main main) {
		this.main = main;
		autoStart = new GAutoStart(main);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		int playerMax = 20;
		Player player = event.getPlayer();
		Location spawn = new Location(Bukkit.getWorld(main.getConfig().getString("ballOfSteel.world")),
				main.getConfig().getDouble("ballOfSteel.coordonee.start.x"),
				main.getConfig().getDouble("ballOfSteel.coordonee.start.y"),
				main.getConfig().getDouble("ballOfSteel.coordonee.start.z"));
		
		player.teleport(spawn);
		player.getInventory().clear();
		if(!(main.getPlayers().size()<=20 && (main.isState(Gstate.WAITING)||main.isState(Gstate.STARTING)))) {
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("La partie est deja lancer !");
			event.setJoinMessage(null);
			return;	
		}
		
		if(!main.getPlayers().contains(player)) main.getPlayers().add(player);
		event.setJoinMessage("§7[§eBall of steel§7] §l§c"+player.getName()+"§r§6 a rejoin la partie " + main.getPlayers().size() + "/" + playerMax);
		player.setGameMode(GameMode.ADVENTURE);
		
		player.setAllowFlight(false);
		player.setDisplayName("§c"+player.getName()+"§7");
		if(main.isState(Gstate.WAITING) && main.getPlayers().size() == 3) {
			autoStart.runTaskTimer(main, 0, 20);
			main.setState(Gstate.STARTING);
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if(main.isState(Gstate.WAITING)) {
			if(main.getPlayers().contains(event.getPlayer())) {
				main.getPlayers().remove(event.getPlayer());
			}
		}
	}
	
	
	@EventHandler
	public void PlayerDamageEvent​(EntityDamageEvent event){
		if(!main.isState(Gstate.PLAYING))event.setCancelled(true);
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		event.getCause();
		Player player = (Player) event.getEntity();
		if(event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent) event;
			if(event2.getDamager() instanceof Player) {
				ImmutablePair<Player, Long> pair = new ImmutablePair<Player, Long>((Player) event2.getDamager(), System.currentTimeMillis());
				main.lastHit.put(player, pair);
				HitTime hit = new HitTime(main, pair, player);
				hit.runTaskLater(main, 111);
			}
		}
		if(player.getHealth() < event.getDamage()) {
			player.getInventory().clear();
			player.setHealth(20);
			ImmutablePair<Player,Long> lastHit = main.lastHit.get(player);
			if(lastHit == null)
				Bukkit.broadcastMessage("§c"+player.getName()+"§7 viens de mourir.");
			else {
				Bukkit.broadcastMessage("§c"+player.getName()+"§7 à été tué par §c"+lastHit.left.getName()+".");
				main.playerKill.put(lastHit.left,main.playerKill.get(lastHit.left)+1);
				ScoreboardSign sc = main.scoreBoard.get(lastHit.left);
				sc.setLine(7, "§1Kill : "+main.playerKill.get(lastHit.left));
			}
			GDeath death = new GDeath(player, main);
			death.runTaskTimer(main, 0, 20);
			// cette partie de code servira quand les sélection de team et les getTeam marcheron
			
			
			event.setCancelled(true);
		}
		player.setCustomName("§c"+player.getName());
	}
	@EventHandler
	public void PlayerDropItemEvent​(PlayerDropItemEvent event) {
		if (main.isState(Gstate.STARTING)||main.isState(Gstate.WAITING)) {
			event.setCancelled(true);
		}
		if (main.isState(Gstate.PLAYING)&& event.getItemDrop().getItemStack().getItemMeta().getDisplayName()=="§7casque") event.setCancelled(true);
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Material itM = event.getMaterial();
		ItemStack it= event.getItem();
		Integer inventorySlot=player.getInventory().getHeldItemSlot();
		Block itemClickedBlock = event.getClickedBlock();
		String team = main.getPlayersTeam().get(player);
		if(it==null)return;
		if(itM == Material.WOOL && (main.isState(Gstate.CHOOSETEAM))) {
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
		if (itemClickedBlock==null) {
			return;
		}
		if(itemClickedBlock.getType() == Material.BEDROCK && main.isState(Gstate.PLAYING)) {
			if(itM==Material.DIAMOND) {
				player.getInventory().clear(inventorySlot);
				main.pointParTeam().put(team, main.pointParTeam().get(team)+it.getAmount());
				Bukkit.broadcastMessage("l'équipe "+team+" viens d'ajouter "+it.getAmount()+" diamand il en ont maintenant "+main.pointParTeam().get(team));
				for(Player pls : main.getPlayers()) {
					ScoreboardSign sc = main.scoreBoard.get(pls);
					if(main.getPlayersTeam().get(pls)=="red")sc.setLine(1, "§4Rouge : "+main.pointParTeam().get("red")+" §7§lYOU");
					else sc.setLine(1, "§4Rouge : "+main.pointParTeam().get("red"));
					if(main.getPlayersTeam().get(pls)=="blue")sc.setLine(2, "§1Bleu  : "+main.pointParTeam().get("blue")+" §7§lYOU");
					else sc.setLine(2, "§1Bleu  : "+main.pointParTeam().get("blue"));
					if(main.getPlayersTeam().get(pls)=="green")sc.setLine(3, "§2Vert  : "+main.pointParTeam().get("green")+" §7§lYOU");
					else sc.setLine(3, "§2Vert  : "+main.pointParTeam().get("green"));
					if(main.getPlayersTeam().get(pls)=="yellow")sc.setLine(4, "§eJaune : "+main.pointParTeam().get("yellow")+" §7§lYOU");
					else sc.setLine(4, "§eJaune : "+main.pointParTeam().get("yellow"));
				}
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
