package fr.jnath.ballOfSteel.game;

import fr.jnath.Utils.ScoreboardSign;
import fr.jnath.ballOfSteel.game.waiting.GAutoStart;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    //coordonate
    //game
    private final Double centerX, centerY, range, hMax, hMin;
    private final World world;
    //hub
    private final Double hubX, hubY, hubZ;
    //scoreboard
    private final HashMap<Player, ScoreboardSign> playerScoreboard = new HashMap<Player, ScoreboardSign>();
    //team
    private final HashMap<String, Player[]> playersInTeam = new HashMap<>();
    private final HashMap<Player, String> teamOfPlayer = new HashMap<>();
    //kill par joueur
    private final HashMap<Player, Integer> gameKills = new HashMap<>();
    //état de la partie
    private final Gstate gameState;
    //setings
    private final int maxPlayerPerTeam;
    //Joueurs
    private final ArrayList<Player> playersInGame = new ArrayList<>();
    //AutoStart
    private final GAutoStart startTask = new GAutoStart();

    public Game(Double centerX, Double centerY, Double range, Double hMax, Double hMin, World world,
                int maxPlayerPerTeam, Double hubX, Double hubY, Double hubZ) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.range = range;
        this.hMax = hMax;
        this.hMin = hMin;
        this.world = world;
        this.gameState = Gstate.WAITING;
        this.maxPlayerPerTeam = maxPlayerPerTeam;
        this.hubX = hubX;
        this.hubY = hubY;
        this.hubZ = hubZ;
    }

    public Double getCenterX() {
        return centerX;
    }

    public Double getCenterY() {
        return centerY;
    }

    public Double getRange() {
        return range;
    }

    public Double gethMax() {
        return hMax;
    }

    public Double gethMin() {
        return hMin;
    }

    public World getGameWorld() {
        return world;
    }

    public Double getHubX() {
        return hubX;
    }

    public Double getHubY() {
        return hubY;
    }

    public Double getHubZ() {
        return hubZ;
    }

    public Gstate getGameState() {
        return gameState;
    }

    public int getMaxPlayerPerTeam() {
        return maxPlayerPerTeam;
    }

    public ArrayList<Player> getPlayersInGame() {
        return playersInGame;
    }

    public GAutoStart getStartTask() {
        return startTask;
    }

    public HashMap<Player, Integer> getGameKills() {
        return gameKills;
    }


}
