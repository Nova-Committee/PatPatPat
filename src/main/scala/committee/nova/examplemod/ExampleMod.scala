package committee.nova.examplemod

import org.apache.logging.log4j.{LogManager, Logger}
import org.dimdev.riftloader.listener.InitializationListener
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins

object ExampleMod {
  val logger: Logger = LogManager.getLogger
}

class ExampleMod extends InitializationListener {
  override def onInitialization(): Unit = {
    MixinBootstrap.init()
    Mixins.addConfiguration("mixins.examplemod.json")
  }
}
