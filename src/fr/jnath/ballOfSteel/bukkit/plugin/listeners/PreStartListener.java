package fr.jnath.ballOfSteel.bukkit.plugin.listeners;

import fr.jnath.ballOfSteel.bukkit.plugin.BallOfSteel;
import fr.jnath.ballOfSteel.game.Game;
import fr.jnath.ballOfSteel.game.Gstate;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PreStartListener implements Listener {

    private BallOfSteel main;

    public PreStartListener() {
        this.main = BallOfSteel.INSTANCE;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Game game = main.getGame();
        int playerMax = game.getMaxPlayerPerTeam()*4;
        Player player = event.getPlayer();
        Location spawn = new Location(game.getGameWorld(), game.getHubX(), game.getHubY(), game.getHubZ());

        player.teleport(spawn);
        player.getInventory().clear();

        if(!(game.getPlayersInGame().size()<=20 && !(main.isState(Gstate.WAITING)||main.isState(Gstate.STARTING)))) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("Vous entrer en spec dans la game");
            event.setJoinMessage(null);

            Location mid = new Location(game.getGameWorld(), game.getCenterX(), game.getHubY(), game.getCenterY());

            player.teleport(mid);
            return;
        }

        if(!game.getPlayersInGame().contains(player)) main.getPlayers().add(player);
        event.setJoinMessage("§7[§eBall of steel§7] §l§c"+player.getName()+"§r§6 a rejoin la partie " + main.getPlayers().size() + "/" + playerMax);
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(false);

        if(main.isState(Gstate.WAITING) && game.getPlayersInGame().size() == 3) {
            game.getStartTask().runTaskTimer(main, 0, 20);
            main.setState(Gstate.STARTING);
        }
    }
}
