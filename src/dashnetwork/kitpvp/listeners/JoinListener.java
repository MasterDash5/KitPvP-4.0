package dashnetwork.kitpvp.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.utils.KitUtils;
import dashnetwork.kitpvp.utils.StupidScoreboard;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class JoinListener implements Listener {

    private final BaseComponent[] header;
    private final BaseComponent[] footer;

    public JoinListener() {
        MessageBuilder header = new MessageBuilder();
        header.append("&6&lDashNetwork\n");
        header.append("&7Server: &cPvP\n");

        MessageBuilder footer = new MessageBuilder();
        footer.append("\n&6play.dashnetwork.xyz");

        this.header = header.build();
        this.footer = footer.build();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.teleport(KitPvP.getInstance().getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        KitUtils.refresh(player);
        KitUtils.setSurvival(player);

        player.setPlayerListHeaderFooter(header, footer);

        createScoreboard(player);
    }

    public void createScoreboard(Player player) {
        StupidScoreboard scoreboard = new StupidScoreboard("&6&lDashNetwork");
        scoreboard.add("&7&m-----------------");
        scoreboard.add("&aKills: &f0");
        scoreboard.add("&cDeaths: &f0");
        scoreboard.add("&7&m-----------------");
        scoreboard.build();
        scoreboard.send(player);
    }
}