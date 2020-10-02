package dashnetwork.kitpvp.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import dashnetwork.core.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

// not made by me, TODO: fokin redesign this LOL
public class StupidScoreboard {

    private final Scoreboard scoreboard;
    private String title;
    private final Map<String, Integer> scores;
    private final List<Team> teams;

    public StupidScoreboard(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.title = title;
        this.scores = new LinkedHashMap<>();
        this.teams = new ArrayList<>();
    }

    public void blankLine() {
        add(" ");
    }

    public void add(String text) {
        add(text, null);
    }

    public void add(String text, Integer score) {
        Preconditions.checkArgument(text.length() < 48, "text cannot be over 48 characters in length");

        text = ColorUtils.translate(text);
        text = fixDuplicates(text);

        scores.put(text, score);
    }

    private String fixDuplicates(String text) {
        while (scores.containsKey(text))
            text += "§r";

        if (text.length() > 48)
            text = text.substring(0, 47);

        return text;
    }

    private Map.Entry<Team, String> createTeam(String text) {
        String result = "";

        if (text.length() <= 16)
            return new AbstractMap.SimpleEntry<>(null, text);

        Team team = scoreboard.registerNewTeam("text-" + scoreboard.getTeams().size());
        Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();

        team.setPrefix(iterator.next());

        result = iterator.next();

        if (text.length() > 32)
            team.setSuffix(iterator.next());

        teams.add(team);

        return new AbstractMap.SimpleEntry<>(team, result);
    }

    public void build() {
        Objective objective = scoreboard.registerNewObjective((title.length() > 16 ? title.substring(0, 15) : title), "dummy");
        objective.setDisplayName(ColorUtils.translate(title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int index = scores.size();

        for (Map.Entry<String, Integer> text : scores.entrySet()) {
            Map.Entry<Team, String> team = createTeam(text.getKey());
            int score = text.getValue() != null ? text.getValue() : index;
            OfflinePlayer player = Bukkit.getOfflinePlayer(team.getValue());
            Team key = team.getKey();

            if (key != null)
                key.addPlayer(player);

            objective.getScore(player).setScore(score);
            index -= 1;
        }
    }

    public void reset() {
        title = null;

        scores.clear();

        for (Team team : teams)
            team.unregister();

        teams.clear();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void send(Player player) {
        player.setScoreboard(scoreboard);
    }
}