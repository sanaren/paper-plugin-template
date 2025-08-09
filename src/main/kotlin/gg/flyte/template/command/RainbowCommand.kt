package gg.flyte.template.command

import gg.flyte.template.listener.RainbowTrailListener
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.bukkit.annotation.CommandPermission

class RainbowCommand {
    
    @Command("rainbow")
    @Description("虹色パーティクルトレイルのON/OFF切り替え")
    @CommandPermission("template.rainbow")
    fun rainbow(player: Player) {
        val uuid = player.uniqueId
        
        if (RainbowTrailListener.enabledPlayers.contains(uuid)) {
            // OFF にする
            RainbowTrailListener.enabledPlayers.remove(uuid)
            
            player.sendMessage(
                Component.text("🌈 虹色トレイルが ", NamedTextColor.GRAY)
                    .append(Component.text("OFF", NamedTextColor.RED, TextDecoration.BOLD))
                    .append(Component.text(" になりました！", NamedTextColor.GRAY))
            )
            
            player.playSound(
                player.location,
                Sound.BLOCK_NOTE_BLOCK_BASS,
                SoundCategory.PLAYERS,
                1.0f,
                0.8f
            )
            
        } else {
            // ON にする
            RainbowTrailListener.enabledPlayers.add(uuid)
            
            player.sendMessage(
                Component.text("🌈 虹色トレイルが ", NamedTextColor.WHITE)
                    .append(Component.text("ON", NamedTextColor.GREEN, TextDecoration.BOLD))
                    .append(Component.text(" になりました！歩き回ってみてください！", NamedTextColor.WHITE))
            )
            
            // 成功音を鳴らす
            player.playSound(
                player.location,
                Sound.ENTITY_PLAYER_LEVELUP,
                SoundCategory.PLAYERS,
                1.0f,
                1.2f
            )
        }
    }
    
    @Command("rainbow status")
    @Description("虹色トレイルの状態を確認")
    @CommandPermission("template.rainbow")
    fun rainbowStatus(player: Player) {
        val isEnabled = RainbowTrailListener.enabledPlayers.contains(player.uniqueId)
        
        player.sendMessage(
            Component.text("🌈 虹色トレイル: ", NamedTextColor.WHITE)
                .append(
                    if (isEnabled) {
                        Component.text("ON", NamedTextColor.GREEN, TextDecoration.BOLD)
                    } else {
                        Component.text("OFF", NamedTextColor.RED, TextDecoration.BOLD)
                    }
                )
        )
    }
}