package thelonebarkeeper.mgame.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import thelonebarkeeper.mgame.SkyWarsProxy;

import java.util.Arrays;
import java.util.HashMap;

public class PluginMessageListener implements Listener {

    public static HashMap<String, Boolean> serverAvailabilities = new HashMap<>();


    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equalsIgnoreCase( "SkyWarsConnect") )
        {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput( event.getData() );
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase("Connect")) {
            if (event.getReceiver() instanceof ProxiedPlayer) {
                ProxiedPlayer receiver = (ProxiedPlayer) event.getReceiver();
                ProxyServer proxy = SkyWarsProxy.getInstance().getProxy();
                for (int i = 1; i <= 9; i++) {
                    String serverName = "skywars-" + i;
                    ServerInfo server = proxy.getServerInfo(serverName);
                    SkyWarsProxy.getInstance().getProxy().broadcast(new ComponentBuilder(String.valueOf(serverAvailabilities.get(serverName))).create());
                    if (server.getPlayers().size() == 0 || serverAvailabilities.get(serverName)) {
                        receiver.connect(server);
                        return;
                    }
                }

                receiver.sendMessage(new ComponentBuilder("Все сервера заполнены! :о").create());
                return;
            }
        }

        String[] args = subChannel.split(",");

        if (args[0].equalsIgnoreCase("GameState")) {
            SkyWarsProxy.getInstance().getProxy().broadcast(new ComponentBuilder(Arrays.toString(args)).create());

            String serverName = args[1];
            Boolean isAvailable = Boolean.getBoolean(args[2]);

            serverAvailabilities.put(serverName, isAvailable);
        }

    }

    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        String serverName = event.getServer().getInfo().getName();
        if (serverName.contains("skywars") && !serverName.contains("lobby"))
            serverAvailabilities.put(serverName, true);
    }
}
