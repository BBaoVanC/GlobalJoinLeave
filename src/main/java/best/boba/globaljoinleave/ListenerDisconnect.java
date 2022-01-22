package best.boba.globaljoinleave;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Collection;

public class ListenerDisconnect {
    final Config config;
    public ListenerDisconnect(Config config) {
        this.config = config;
    }

    @Subscribe(order = PostOrder.LATE)
    public void onDisconnect(DisconnectEvent event) {
        Collection<Player> everyone = this.config.server().getAllPlayers();
        String playerUsername = event.getPlayer().getUsername();

        if (event.getLoginStatus() == DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN) {
            everyone.parallelStream().forEach(player -> player.sendMessage(Component.text(
                    String.format("%s disconnected", playerUsername)
            ).color(NamedTextColor.GRAY)));
        } else {
            everyone.parallelStream().forEach(player -> player.sendMessage(Component.text(
                    String.format("%s was disconnected", playerUsername)
            ).color(NamedTextColor.GRAY)));
        }
    }
}
