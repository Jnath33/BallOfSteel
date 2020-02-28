package fr.jnath.monpltest.BallOfSteel.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class noKick implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command name, String arg0, String[] arg1) {
		if(sender instanceof Player) {
			return true;
		}
		return false;
	}
}
