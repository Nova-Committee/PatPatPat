package committee.nova.examplemod

import committee.nova.examplemod.common.proxy.CommonProxy
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{Mod, SidedProxy}

@Mod(modid = ExampleMod.MODID, useMetadata = true, modLanguage = "scala")
object ExampleMod {
  final val pkgPrefix = "committee.nova.examplemod."
  final val MODID = "modid"

  @SidedProxy(serverSide = pkgPrefix + "common.proxy.CommonProxy", clientSide = pkgPrefix + "client.proxy.ClientProxy")
  var proxy: CommonProxy = _

  @EventHandler
  def preInit(e: FMLPreInitializationEvent): Unit = proxy.preInit(e)

  @EventHandler
  def init(e: FMLInitializationEvent): Unit = proxy.init(e)

  @EventHandler
  def postInit(e: FMLPostInitializationEvent): Unit = proxy.postInit(e)
}