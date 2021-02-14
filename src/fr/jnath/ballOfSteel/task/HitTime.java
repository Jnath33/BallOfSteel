package fr.jnath.ballOfSteel.task;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.ballOfSteel.bukkit.plugin.BallOfSteel;

public class HitTime extends BukkitRunnable{
	BallOfSteel main;
	ImmutablePair<Player, Long> _pair;
	Player _player;
	public HitTime(BallOfSteel main, ImmutablePair<Player, Long> pair, Player player){
		this.main =main;
		_pair=pair;
		_player=player;
	}
	@Override
	public void run() {
		if(main.lastHit.get(_player)==_pair) {
			main.lastHit.put(_player, null);
		}
	}
 
}
