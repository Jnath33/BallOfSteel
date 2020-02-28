package fr.jnath.monpltest.BallOfSteel.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.jnath.monpltest.BallOfSteel.Main;
import fr.jnath.monpltest.BallOfSteel.Listener.GplayerListener;
import fr.jnath.monpltest.BallOfSteel.task.GAutoStart2;

public class Start implements CommandExecutor {
	Main main;
	GplayerListener listener;
	public Start(Main main) {
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
