package world.cepi.sit

import net.minestom.server.event.player.PlayerBlockInteractEvent
import world.cepi.kstom.util.asPos

object SitRightClickListener {

    fun onClick(event: PlayerBlockInteractEvent) = with(event) {
        if (!block.isSolid) return@with
        
        if (!player.isSneaking) return@with

        if (!player.itemInMainHand.isAir) return@with

        if (!player.isOnGround) return@with

        if (player.vehicle != null) return@with

        Sit.sit(player, player.instance!!, blockPosition.add(0.0, 1.0, 0.0).asPos())
    }

}