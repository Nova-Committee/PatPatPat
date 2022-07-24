package committee.nova.examplemod

import committee.nova.examplemod.common.proxy.CommonProxy
import cpw.mods.fml.common.{Mod, SidedProxy}

@Mod(modid = ExampleMod.MODID, useMetadata = true, modLanguage = "scala")
object ExampleMod {
  final val pkgPrefix = "committee.nova.examplemod."
  final val MODID = "modid"

  @SidedProxy(serverSide = pkgPrefix + "common.proxy.CommonProxy", clientSide = pkgPrefix + "client.proxy.ClientProxy")
  var proxy: CommonProxy = _
}