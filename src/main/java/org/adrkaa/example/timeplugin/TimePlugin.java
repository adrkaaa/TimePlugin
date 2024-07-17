package org.adrkaa.example.timeplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public final class TimePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("time").setExecutor(new TimeCommandExecutor());
    }

    @Override
    public void onDisable() {
    }

    public class TimeCommandExecutor implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
                String formattedDate = dateFormat.format(new Date());


                String actionBarMessage = ChatColor.GRAY + " Время " + formattedDate.split(" ")[1] + " по МСК";

                new BukkitRunnable() {
                    int timeLeft = 10;

                    @Override
                    public void run() {
                        if (timeLeft > 0) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBarMessage));
                            timeLeft--;
                        } else {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(TimePlugin.this, 0L, 20L);

                return true;
            }
            return false;
        }
    }
}
