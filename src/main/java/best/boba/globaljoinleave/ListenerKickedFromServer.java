package best.boba.globaljoinleave;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ListenerKickedFromServer {
    final Config config;
    public ListenerKickedFromServer(Config config) {
        this.config = config;
    }

    @Subscribe(order = PostOrder.LATE)
    public void onKickedFromServer(KickedFromServerEvent event) {
        if (event.getPlayer().getCurrentServer().isEmpty()) {
            // the player is connecting to their first server
            return;
        }

        if (event.kickedDuringServerConnect()) {
            // if the player tries to connect to a different server but is kicked in the process
            return;
        }

        this.config.server().getAllPlayers().parallelStream().forEach(player -> player.sendMessage(Component.text(
                String.format("%s was kicked from %s",
                        event.getPlayer().getUsername(),
                        event.getServer().getServerInfo().getName())
        ).color(NamedTextColor.GRAY)));
    }
}
