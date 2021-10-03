package thelonebarkeeper.mgame.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {

    public HubCommand(String cmd) {
        super(cmd);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.RED).create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (player.getServer().getInfo().getName().equalsIgnoreCase("skywars-lobby")) {
            player.sendMessage(new ComponentBuilder("Вы уже находитесь в хабе.").color(ChatColor.RED).create());
            return;
        }

        ServerInfo target = ProxyServer.getInstance().getServerInfo("skywars-lobby");
        player.connect(target);

    }
}
