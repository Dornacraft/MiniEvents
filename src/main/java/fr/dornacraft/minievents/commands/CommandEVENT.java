package fr.dornacraft.minievents.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.commands.args.ArgFORCERUN;
import fr.dornacraft.minievents.commands.args.ArgHELP;
import fr.dornacraft.minievents.commands.args.ArgJOIN;
import fr.dornacraft.minievents.commands.args.ArgLEAVE;
import fr.dornacraft.minievents.commands.args.ArgNAME;
import fr.dornacraft.minievents.commands.args.ArgPARTICIPANTS;
import fr.dornacraft.minievents.commands.args.ArgRUN;
import fr.dornacraft.minievents.commands.args.ArgSTART;
import fr.dornacraft.minievents.commands.args.ArgSTARTLIST;
import fr.dornacraft.minievents.commands.args.ArgSTATE;
import fr.dornacraft.minievents.commands.args.ArgSTOP;
import fr.dornacraft.minievents.commands.args.ArgTELEPORT;

public class CommandEVENT implements CommandExecutor {

	private Main main;
	private String incompleteCommand = "§cCommande incomplète, utilise la commande §b/event help§c, pour plus d'aide.";
	private String badCommand = "§cCommande éronnée, utilise la commande §b/event help§c, pour plus d'aide.";
	private String badStartList = "§cCommande éronnée, utilise la commande §b/event startlist§c, pour plus d'aide.";
	private String incompleteStartList = "§cCommande incomplète, utilise la commande §b/event start <§7event§b>§c, pour plus d'aide.";

	public CommandEVENT(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				player.sendMessage(main.prefix + incompleteCommand);
				return true;
			}
			if (args.length >= 1) {
				switch (args[0].toLowerCase()) {
				case "forcerun":
					ArgFORCERUN.ArgForceRun(player, main);
					return true;
				case "help":
					ArgHELP.ArgHelp(player);
					return true;
				case "join":
					ArgJOIN.ArgJoin(player, main);
					return true;
				case "leave":
					ArgLEAVE.ArgLeave(player, main);
					return true;
				case "name":
					ArgNAME.ArgName(player, main.prefix, main.getGameName());
					return true;
				case "participants":
					ArgPARTICIPANTS.ArgParticipants(main, player);
					return true;
				case "run":
					ArgRUN.ArgRun(player, main);
					return true;
				case "start":
					if (args.length >= 2) {
						List<GameName> eventNames = Arrays.asList(GameName.values());
						for (int i = 0; i < eventNames.size(); i++) {
							if (eventNames.get(i).getRealName().equals("NONE"))
								i++;
							if (eventNames.get(i).getRealName().equalsIgnoreCase(args[1])) {
								ArgSTART.ArgStart(player, main, eventNames.get(i));
								return true;
							}
						}
						player.sendMessage(main.prefix + badStartList);
						return true;
					} else {
						player.sendMessage(main.prefix + incompleteStartList);
						return true;
					}
				case "startlist":
					ArgSTARTLIST.ArgStartList(main.prefix, player);
					return true;
				case "state":
					ArgSTATE.ArgState(player, main.prefix, main.getGameState());
					return true;
				case "stop":
					ArgSTOP.ArgStop(player, main);
					return true;
				case "teleport":
					ArgTELEPORT.ArgTeleport(player);
					return true;
				default:
					player.sendMessage(main.prefix + badCommand);
					return true;
				}
			}
		} else {
			sender.sendMessage(main.prefix + "§cTu dois être sur le serveur pour effectuer sur cette commande !");
		}
		return false;
	}
}
