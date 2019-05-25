package de.mcgregordev.kiara.nick.user;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import de.mcgregordev.kiara.nick.NickModule;
import de.mcgregordev.kiara.nick.util.NickUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class NickUser {

    @Setter
    private static NickModule module;

    @Getter
    private static Map<UUID, NickUser> cache = new HashMap<>();

    public static NickUser getNickUser(Player player) {
        return cache.getOrDefault(player.getUniqueId(), new NickUser(player));
    }

    private Player player;
    private UUID uuid;
    private String realName;
    private boolean isNicked;

    public NickUser(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.realName = player.getName();
        this.isNicked = false;
        cache.put(player.getUniqueId(), this);
    }

    public void nick(String name) {
        if (isNicked) name = realName;
        isNicked = !isNicked;
        Futures.addCallback(NickUtil.nickPlayer(player, name), new FutureCallback<String>() {
            @Override
            public void onSuccess(String name) {
                player.sendMessage(module.getMessageStorage().getMessage("command.nick.success", name));
            }

            @Override
            public void onFailure(Throwable ignored) {
                player.sendMessage(module.getMessageStorage().getMessage("command.nick.error"));
            }
        });
    }

    public String getSeeName(Player player) {
        if (player.hasPermission("command.nick.bypass")) {
            return realName;
        }
        return player.getName();
    }

    //TODO: with rank module
    public String getRankName(Player player) {
        return null;
    }

}
