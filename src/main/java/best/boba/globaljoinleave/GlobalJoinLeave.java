package best.boba.globaljoinleave;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "globaljoinleave",
        name = "GlobalJoinLeave",
        version = "1.0",
        url = "https://github.com/bobacraft/GlobalJoinLeave",
        authors = {"bbaovanc"},
        description = "Global join/leave messages for the Velocity proxy"
)
public class GlobalJoinLeave {
    private final Config config;

    @Inject
    public GlobalJoinLeave(ProxyServer server, Logger logger) {
        this.config = new Config(server, logger);
    }

    public void initialize() {
        EventManager eventManager = this.config.server().getEventManager();
        eventManager.register(this, new ListenerServerConnected(this.config));
        eventManager.register(this, new ListenerDisconnect(this.config));
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        initialize();
    }
}
