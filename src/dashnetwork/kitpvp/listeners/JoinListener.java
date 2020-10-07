package dashnetwork.kitpvp.listeners;

import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.utils.KitUtils;
import dashnetwork.kitpvp.utils.SpawnUtils;
import dashnetwork.kitpvp.utils.StatsUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class JoinListener implements Listener {

    private final KitPvP plugin = KitPvP.getInstance();
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

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeaderFooter(this.header, this.footer);
            setupScoreboard(player);
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers())
                    updateScoreboard(player);
            }

        }.runTaskTimerAsynchronously(plugin, 0L, 1L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        SpawnUtils.teleportToSpawn(player);

        KitUtils.refresh(player);
        KitUtils.setSurvival(player);

        player.setPlayerListHeaderFooter(header, footer);

        setupScoreboard(player);
    }

    public void setupScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("info", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ColorUtils.translate("&6&lKitPvP"));

        objective.getScore(" ").setScore(9);
        objective.getScore(ColorUtils.translate("&2&lKills&2: &e")).setScore(8);
        objective.getScore(ColorUtils.translate("&6&lStreak&6: &e")).setScore(7);
        objective.getScore(ColorUtils.translate("&c&lDeaths&c: &e")).setScore(6);
        objective.getScore(ColorUtils.translate("&b&lKDR&b: &e")).setScore(5);
        objective.getScore("  ").setScore(4);
        objective.getScore(ColorUtils.translate("&d&lCombat&d: &e")).setScore(3);
        objective.getScore("   ").setScore(2);
        objective.getScore(ColorUtils.translate("&6play.dashnetwork.xyz")).setScore(1);

        Team killCount = scoreboard.registerNewTeam("KillCount");
        killCount.addEntry(ColorUtils.translate("&2&lKills&2: &e"));
        killCount.setSuffix("" + StatsUtils.getKills(player));

        Team streakTeam = scoreboard.registerNewTeam("Streak");
        streakTeam.addEntry(ColorUtils.translate("&6&lStreak&6: &e"));
        streakTeam.setSuffix("" + StatsUtils.getKillStreak(player));

        Team deathCount = scoreboard.registerNewTeam("DeathCount");
        deathCount.addEntry(ColorUtils.translate("&c&lDeaths&c: &e"));
        deathCount.setSuffix("" + StatsUtils.getDeaths(player));

        Team kdrTeam = scoreboard.registerNewTeam("KDR");
        kdrTeam.addEntry(ColorUtils.translate("&b&lKDR&b: &e"));
        kdrTeam.setSuffix("" + StatsUtils.getKDR(player));

        Team combatTimer = scoreboard.registerNewTeam("CombatTimer");
        combatTimer.addEntry(ColorUtils.translate("&d&lCombat&d: &e"));
        combatTimer.setSuffix(CombatListener.getCombatTime(player));

        player.setScoreboard(scoreboard);
    }

    public void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Team killCount = scoreboard.getTeam("KillCount");
        int kills = StatsUtils.getKills(player);

        if (kills != Integer.parseInt(killCount.getSuffix()))
            killCount.setSuffix("" + kills);

        Team streakTeam = scoreboard.getTeam("Streak");
        int streak = StatsUtils.getKillStreak(player);

        if (streak != Integer.parseInt(streakTeam.getSuffix()))
            streakTeam.setSuffix("" + streak);

        Team deathCount = scoreboard.getTeam("DeathCount");
        int deaths = StatsUtils.getDeaths(player);

        if (deaths != Integer.parseInt(deathCount.getSuffix()))
            deathCount.setSuffix("" + deaths);

        Team kdrTeam = scoreboard.getTeam("KDR");
        String kdr = StatsUtils.getKDR(player);

        if (!kdr.equals(kdrTeam.getSuffix()))
            kdrTeam.setSuffix("" + kdr);

        Team combatTimer = scoreboard.getTeam("CombatTimer");
        String combatTime = CombatListener.getCombatTime(player);

        if (!combatTime.equals(combatTimer.getSuffix()))
            combatTimer.setSuffix(combatTime);
    }
}