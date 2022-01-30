package world.cepi.sit

import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.Instance

class SitInstance(val instance: Instance, val position: Pos) {

    val arrow = kotlin.run {
        val arrow = Entity(EntityType.ARROW)
        arrow.isInvisible = true
        arrow.setTag(Sit.sitTag, 1)
        arrow.setNoGravity(true)
        arrow.setInstance(instance, position.sub(.0, .5, .0).add(.5, .0, .5))
        arrow
    }

    fun unsit(entity: Entity) {
        arrow.removePassenger(entity)
    }

    fun sit(entity: Entity) {
        if (arrow.hasPassenger()) unsit(arrow.passengers.first())
        arrow.addPassenger(entity)
    }

    fun remove() = arrow.remove()

}