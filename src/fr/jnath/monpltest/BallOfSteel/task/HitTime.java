package fr.jnath.monpltest.BallOfSteel.task;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.jnath.monpltest.BallOfSteel.Main;

public class HitTime extends BukkitRunnable{
	Main _main;
	ImmutablePair<Player, Long> _pair;
	Player _player;
	public HitTime(Main main, ImmutablePair<Player, Long> pair, Player player){
		_main=main;
		_pair=pair;
		_player=player;
	}
	@Override
	public void run() {
		if(_main.lastHit.get(_player)==_pair) {
			_main.lastHit.put(_player, null);
		}
	}
 
}
