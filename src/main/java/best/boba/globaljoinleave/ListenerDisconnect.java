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
        Player eventPlayer = event.getPlayer();
        String playerUsername = eventPlayer.getUsername();

        if (event.getLoginStatus() == DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN) {
            everyone.parallelStream().forEach(player -> player.sendMessage(Component.text(
                    String.format("%s disconnected", playerUsername)
            ).color(NamedTextColor.GRAY)));
        }
        // if the player hadn't successfully connected, then the ServerConnectedEvent is skipped
        // that means there's no need for an else here since the player joining would have never
        // been announced in the first place
        //
        // kick is handled in ListenerKickedFromServer
    }
}
