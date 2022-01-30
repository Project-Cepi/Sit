package world.cepi.sit

import net.minestom.server.extensions.Extension;
import world.cepi.kstom.event.listenOnly
import world.cepi.kstom.util.log
import world.cepi.kstom.util.node

class SitExtension : Extension() {

    override fun initialize(): LoadStatus {

        node.addChild(Sit.eventNode)
        node.listenOnly(SitRightClickListener::onClick)

        SitCommand.register()

        log.info("[Sit] has been enabled!")

        return LoadStatus.SUCCESS
    }

    override fun terminate() {

        SitCommand.unregister()

        log.info("[Sit] has been disabled!")
    }

}
