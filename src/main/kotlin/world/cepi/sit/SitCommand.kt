package world.cepi.sit

import world.cepi.kstom.command.kommand.Kommand

object SitCommand : Kommand({
    syntax().onlyPlayers {
        if (!player.isOnGround) return@onlyPlayers

        Sit.sit(player, player.instance!!, player.position)
    }
}, "sit")