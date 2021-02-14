package fr.jnath.ballOfSteel.bukkit.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.jnath.ballOfSteel.bukkit.plugin.BallOfSteel;
import fr.jnath.ballOfSteel.bukkit.plugin.listeners.GameListener;
import fr.jnath.ballOfSteel.task.GAutoStart2;

public class Start implements CommandExecutor {
	BallOfSteel main;
	GameListener listener;
	public Start(BallOfSteel main) {
		this.main=main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command name, String arg0, String[] arg1) {
		if(sender instanceof Player) {
			GAutoStart2 ChooseTeam = new GAutoStart2(main);
			ChooseTeam.runTaskTimer(main, 0, 20);
			return true;
		}
		return false;
	}
}
