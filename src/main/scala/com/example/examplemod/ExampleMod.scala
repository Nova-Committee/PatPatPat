package com.example.examplemod

import cpw.mods.fml.common.Mod

object ExampleMod {
  final val VERSION = "1.0"
  final val MODID = "modid"
  @Mod.Instance(ExampleMod.MODID)
  var instance: ExampleMod = _
}

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
class ExampleMod {
  ExampleMod.instance = this
}