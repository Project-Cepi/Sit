package world.cepi.sit

import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.event.EventFilter
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerPacketEvent
import net.minestom.server.instance.Instance
import net.minestom.server.network.packet.client.play.ClientSteerVehiclePacket
import net.minestom.server.tag.Tag
import world.cepi.kstom.event.listen
import kotlin.experimental.and

object Sit {

    val sitTag = Tag.Byte("sittable")
    val eventNode = EventNode.type("sitNode", EventFilter.ENTITY)

    private val entities = mutableListOf<SitInstance>()

    fun sit(player: Player, instance: Instance, position: Pos): SitInstance {
        val sitInstance = SitInstance(instance, position)
        sitInstance.sit(player)

        eventNode.listen<PlayerPacketEvent> {
            removeWhen {
                player.vehicle?.uuid != sitInstance.arrow.uuid || player.isRemoved
            }

            filters += { packet is ClientSteerVehiclePacket }

            handler {

                if (sitInstance.arrow.aliveTicks < 5) return@handler

                val steerPacket = packet as ClientSteerVehiclePacket

                // 0x02 is the bitflag for dismounting
                if ((steerPacket.flags and 0x02) == 0x02.toByte()) {
                    unsit(player)
                    return@handler
                }
            }
        }

        return sitInstance
    }

    fun unsit(player: Player) {
        val vehicle = player.vehicle

        vehicle?.removePassenger(player)
        player.teleport(player.position.add(.0, .5, .0))

        if (vehicle?.hasTag(sitTag) == true) vehicle.remove()
    }

}