package thelonebarkeeper.mgame;

import net.md_5.bungee.api.plugin.Plugin;
import thelonebarkeeper.mgame.commands.HubCommand;
import thelonebarkeeper.mgame.listeners.PluginMessageListener;

public final class SkyWarsProxy extends Plugin {


    private static SkyWarsProxy instance;

    public static SkyWarsProxy getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        setupListeners();
        setupServers();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void setupListeners() {

        getProxy().registerChannel("SkyWarsConnect");

        getProxy().getPluginManager().registerListener(this, new PluginMessageListener());

        getProxy().getPluginManager().registerCommand(this, new HubCommand("hub"));
        getProxy().getPluginManager().registerCommand(this, new HubCommand("lobby"));

    }

    public void setupServers() {
        for (int i = 1; i <= 9; i++) {
            PluginMessageListener.serverAvailabilities.put(("skywars-" + i), true);
        }
    }

}
