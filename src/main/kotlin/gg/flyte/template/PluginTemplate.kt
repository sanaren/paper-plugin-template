package gg.flyte.template

import gg.flyte.template.command.PingCommand
import gg.flyte.template.command.RainbowCommand
import gg.flyte.template.listener.JoinListener
import gg.flyte.template.listener.RainbowTrailListener
import gg.flyte.twilight.twilight
import io.papermc.lib.PaperLib
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler

class PluginTemplate : JavaPlugin() {

    companion object {
        lateinit var instance: PluginTemplate
    }

    override fun onEnable() {
        instance = this
        twilight(this) { }

        BukkitCommandHandler.create(this).apply {
            enableAdventure()
            register(PingCommand())
            register(RainbowCommand())
            registerBrigadier()
        }

        JoinListener()
        RainbowTrailListener()

        PaperLib.suggestPaper(this)
    }
}