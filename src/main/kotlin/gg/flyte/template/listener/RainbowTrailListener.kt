package gg.flyte.template.listener

import gg.flyte.twilight.event.event
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

class RainbowTrailListener {
    
    companion object {
        val enabledPlayers = mutableSetOf<UUID>()
        private var colorIndex = 0
        
        private val rainbowColors = listOf(
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.LIME,
            Color.AQUA,
            Color.BLUE,
            Color.PURPLE,
            Color.FUCHSIA
        )
        
        fun getNextColor(): Color {
            val color = rainbowColors[colorIndex]
            colorIndex = (colorIndex + 1) % rainbowColors.size
            return color
        }
    }
    
    init {
        event<PlayerMoveEvent> {
            val player = this.player
            
            // チェック: プレイヤーが有効化しているか
            if (!enabledPlayers.contains(player.uniqueId)) return@event
            
            // チェック: 実際に移動したか（向きだけの変更は無視）
            if (from.toVector() == to.toVector()) return@event
            
            // パーティクルを生成
            spawnRainbowParticles(player)
        }
        
        // プレイヤーが退出したら設定をクリア
        event<PlayerQuitEvent> {
            enabledPlayers.remove(player.uniqueId)
        }
    }
    
    private fun spawnRainbowParticles(player: Player) {
        val location = player.location.clone()
        val world = location.world ?: return
        
        // プレイヤーの足元に複数のパーティクルを生成
        for (i in 0..2) {
            val particleLocation = location.clone().add(
                (Math.random() - 0.5) * 0.5,
                Math.random() * 0.3,
                (Math.random() - 0.5) * 0.5
            )
            
            // 花火のパーティクルを虹色で生成
            world.spawnParticle(
                Particle.REDSTONE,
                particleLocation,
                3,
                0.1,
                0.1,
                0.1,
                0.0,
                Particle.DustOptions(getNextColor(), 1.5f)
            )
            
            // キラキラエフェクトも追加
            if (Math.random() < 0.3) {
                world.spawnParticle(
                    Particle.FIREWORKS_SPARK,
                    particleLocation.add(0.0, 0.5, 0.0),
                    1,
                    0.0,
                    0.0,
                    0.0,
                    0.02
                )
            }
        }
        
        // 音を鳴らす（たまに）
        if (Math.random() < 0.1) {
            world.playSound(
                location,
                Sound.BLOCK_AMETHYST_BLOCK_CHIME,
                SoundCategory.PLAYERS,
                0.3f,
                1.5f + (Math.random().toFloat() * 0.5f)
            )
        }
    }
}