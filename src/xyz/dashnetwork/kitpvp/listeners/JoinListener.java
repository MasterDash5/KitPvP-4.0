package xyz.dashnetwork.kitpvp.listeners;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.kitpvp.KitPvP;
import xyz.dashnetwork.kitpvp.utils.KitUtils;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;
import xyz.dashnetwork.kitpvp.utils.StatsUtils;

public class JoinListener implements Listener {

    private final KitPvP plugin = KitPvP.getInstance();
    private final BaseComponent[] header;
    private final BaseComponent[] footer;

    public JoinListener() {
        MessageBuilder header = new MessageBuilder();
        header.append("&6&lDashNetwork\n");
        header.append("&7Connected to &6&lPvP\n");

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

        Objective sidebarObjective = scoreboard.registerNewObjective("sidebar", "dummy");
        Objective tabListObjective = scoreboard.registerNewObjective("tablist", "dummy");

        sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        sidebarObjective.setDisplayName(ColorUtils.translate("&6&lKitPvP"));

        tabListObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        for (Player online : Bukkit.getOnlinePlayers())
            tabListObjective.getScore(online.getName()).setScore(StatsUtils.getKillStreak(online));

        tabListObjective.getScore(player.getName()).setScore(StatsUtils.getKillStreak(player));

        sidebarObjective.getScore(" ").setScore(9);
        sidebarObjective.getScore(ColorUtils.translate("&2&lKills&2: ")).setScore(8);
        sidebarObjective.getScore(ColorUtils.translate("&6&lStreak&6: ")).setScore(7);
        sidebarObjective.getScore(ColorUtils.translate("&c&lDeaths&c: ")).setScore(6);
        sidebarObjective.getScore(ColorUtils.translate("&b&lKDR&b: ")).setScore(5);
        sidebarObjective.getScore("  ").setScore(4);
        sidebarObjective.getScore(ColorUtils.translate("&d&lCombat&d: ")).setScore(3);
        sidebarObjective.getScore("   ").setScore(2);
        sidebarObjective.getScore(ColorUtils.translate("&6play.dashnetwork.xyz")).setScore(1);

        Team killCount = scoreboard.registerNewTeam("KillCount");
        killCount.addEntry(ColorUtils.translate("&2&lKills&2: "));
        killCount.setSuffix(ColorUtils.translate("&e" + StatsUtils.getKills(player)));

        Team streakTeam = scoreboard.registerNewTeam("Streak");
        streakTeam.addEntry(ColorUtils.translate("&6&lStreak&6: "));
        streakTeam.setSuffix(ColorUtils.translate("&e" + StatsUtils.getKillStreak(player)));

        Team deathCount = scoreboard.registerNewTeam("DeathCount");
        deathCount.addEntry(ColorUtils.translate("&c&lDeaths&c: "));
        deathCount.setSuffix(ColorUtils.translate("&e" + StatsUtils.getDeaths(player)));

        Team kdrTeam = scoreboard.registerNewTeam("KDR");
        kdrTeam.addEntry(ColorUtils.translate("&b&lKDR&b: "));
        kdrTeam.setSuffix(ColorUtils.translate("&e" + StatsUtils.getKDR(player)));

        Team combatTimer = scoreboard.registerNewTeam("CombatTimer");
        combatTimer.addEntry(ColorUtils.translate("&d&lCombat&d: "));
        combatTimer.setSuffix(ColorUtils.translate("&e" + DamageListener.getCombatTime(player)));

        player.setScoreboard(scoreboard);
    }

    public void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective tabListObjective = scoreboard.getObjective("tablist");

        for (Player online : Bukkit.getOnlinePlayers()) {
            int streak = StatsUtils.getKillStreak(online);
            Score score = tabListObjective.getScore(online.getName());

            if (score.getScore() != streak)
                score.setScore(streak);
        }

        Team killCount = scoreboard.getTeam("KillCount");
        int kills = StatsUtils.getKills(player);

        if (kills != Integer.parseInt(ColorUtils.strip(killCount.getSuffix())))
            killCount.setSuffix(ColorUtils.translate("&e" + kills));

        Team streakTeam = scoreboard.getTeam("Streak");
        int streak = StatsUtils.getKillStreak(player);

        if (streak != Integer.parseInt(ColorUtils.strip(streakTeam.getSuffix())))
            streakTeam.setSuffix(ColorUtils.translate("&e" + streak));

        Team deathCount = scoreboard.getTeam("DeathCount");
        int deaths = StatsUtils.getDeaths(player);

        if (deaths != Integer.parseInt(ColorUtils.strip(deathCount.getSuffix())))
            deathCount.setSuffix(ColorUtils.translate("&e" + deaths));

        Team kdrTeam = scoreboard.getTeam("KDR");
        String kdr = StatsUtils.getKDR(player);

        if (!kdr.equals(ColorUtils.strip(kdrTeam.getSuffix())))
            kdrTeam.setSuffix(ColorUtils.translate("&e" + kdr));

        Team combatTimer = scoreboard.getTeam("CombatTimer");
        String combatTime = DamageListener.getCombatTime(player);

        if (!combatTime.equals(ColorUtils.strip(combatTimer.getSuffix())))
            combatTimer.setSuffix(ColorUtils.translate("&e" + combatTime));
    }
}