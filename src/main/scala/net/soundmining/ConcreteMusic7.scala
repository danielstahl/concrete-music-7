package net.soundmining

import net.soundmining.sound.{SoundPlay, SoundPlays}
import net.soundmining.synth.SuperColliderClient.loadDir
import net.soundmining.synth.{Instrument, SuperColliderClient}

object ConcreteMusic7 {

  implicit val client: SuperColliderClient = SuperColliderClient()
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"
  val SOUND_DIR = "/Users/danielstahl/Documents/Music/sounds/Concrete Music 7_sounds/"

  val soundPlays = SoundPlays(
    soundPlays = Map(),
    numberOfOutputBuses = 2)

  def init(): Unit = {
    println("Starting up SuperCollider client")
    client.start
    Instrument.setupNodes(client)
    client.send(loadDir(SYNTH_DIR))
    soundPlays.init
  }

  def stop(): Unit = {
    println("Stopping SuperCollider client")
    client.stop
  }
}
