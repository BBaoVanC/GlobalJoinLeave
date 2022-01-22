package best.boba.globaljoinleave;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Collection;
import java.util.Optional;

public class ListenerServerConnected {
    private final Config config;
    public ListenerServerConnected(Config config) {
        this.config = config;
    }

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        Collection<Player> everyone = this.config.server().getAllPlayers();
        Optional<RegisteredServer> previousServerOptional = event.getPreviousServer();
        String newServerName = event.getServer().getServerInfo().getName();
        String playerUsername = event.getPlayer().getUsername();

        if (previousServerOptional.isPresent()) {
            String previousServerName = previousServerOptional.get().getServerInfo().getName();
            everyone.parallelStream().forEach(player -> player.sendMessage(Component.text(
                    String.format("%s moved from %s → %s",
                            playerUsername,
                            previousServerName,
                            newServerName)
            ).color(NamedTextColor.GRAY)));
        } else {
            everyone.parallelStream().forEach(player -> player.sendMessage(Component.text(
                    String.format("%s connected → %s",
                            playerUsername,
                            newServerName)
            ).color(NamedTextColor.GRAY)));
        }
    }
}
