package net.soundmining

import net.soundmining.modular.ModularInstrument.ControlInstrument
import net.soundmining.modular.ModularSynth.{lineControl, staticControl}
import net.soundmining.sound.{SoundPlay, SoundPlays}
import net.soundmining.synth.SuperColliderClient.loadDir
import net.soundmining.synth.{Instrument, SuperColliderClient}

object ConcreteMusic7 {

  implicit val client: SuperColliderClient = SuperColliderClient()
  val SOUND_DIR = "/Users/danielstahl/Documents/Music/Pieces/Concrete Music/Concrete Music 7/sounds/Concrete Music 7_sounds"
  val SYNTH_DIR = "/Users/danielstahl/Documents/Projects/soundmining-modular/src/main/sc/synths"

  val CHAIR_DRAG_1 = "chair-drag-1"
  val CHAIR_DRAG_2 = "chair-drag-2"
  val APPLE_BITE = "apple-bite"
  val CHEST_HANDLE = "chest-handle"
  val CHEST_HIT = "chest-hit"
  val CHEST_LID = "chest-lid"
  val CHEST_SCRATCH_1 = "chest-scratch-1"
  val CHEST_SCRATCH_2 = "chest-scratch-2"
  val CLOCK_DOOR = "clock-door"
  val PEN_CLICK = "pen-click"
  val PEN_LID_HIT = "pen-lid-hit"
  val PEN_LID_RATTLE = "pen-lid-rattle"
  val PEN_LID_SCRATCH = "pen-lid-scratch"

  val soundPlays = SoundPlays(
    soundPlays = Map(
      APPLE_BITE -> SoundPlay(s"${SOUND_DIR}/Apple bite.flac", 0.024, 1.156),
      CHAIR_DRAG_1 -> SoundPlay(s"${SOUND_DIR}/Chair drag 1.flac", 0.0, 1.098),
      CHAIR_DRAG_2 -> SoundPlay(s"${SOUND_DIR}/Chair drag 2.flac", 0.196, 1.477),
      CHEST_HANDLE -> SoundPlay(s"${SOUND_DIR}/Chest handle.flac", 0.084, 1.142),
      CHEST_HIT -> SoundPlay(s"${SOUND_DIR}/Chest hit.flac", 0.162, 0.638),
      CHEST_LID -> SoundPlay(s"${SOUND_DIR}/Chest lid.flac", 0.000, 5.145),
      CHEST_SCRATCH_1 -> SoundPlay(s"${SOUND_DIR}/Chest scratch 1.flac", 0.000, 2.159),
      CHEST_SCRATCH_2 -> SoundPlay(s"${SOUND_DIR}/Chest scratch 2.flac", 0.171, 1.695),
      CLOCK_DOOR -> SoundPlay(s"${SOUND_DIR}/Clock door.flac", 0.400, 2.781),
      PEN_CLICK -> SoundPlay(s"${SOUND_DIR}/Pen click.flac", 0.019, 0.317),
      PEN_LID_HIT -> SoundPlay(s"${SOUND_DIR}/Pen lid hit.flac", 0.280, 0.955),
      PEN_LID_RATTLE -> SoundPlay(s"${SOUND_DIR}/Pen lid rattle.flac", 0.122, 2.110),
      PEN_LID_SCRATCH -> SoundPlay(s"${SOUND_DIR}/Pen lid scratch.flac", 0.058, 2.001),
    ),
    numberOfOutputBuses = 64)

  def playChair(): Unit = {
    client.resetClock

    soundPlays.mono(CHAIR_DRAG_1)
      .playMono(1.0, 1.0)
      .splay(0.2, -0.2)
      .play(0, 0)

    soundPlays.mono(CHAIR_DRAG_2)
      .playMono(1.0, 1.0)
      .splay(0.2, 0.2)
      .play(0.5, 0)
  }

  /*
  ("chair-drag-1", 0, 0.6), ("chair-drag-2", 0.5, -0.6)
  ("chest-handle", 4, 0.6), ("chest-lid", 0, -0.6)
  ("chest-scratch-1", 0.6, 0.5), ("chest-hit", 0, -0.5)
  ("pen-lid-hit", 0.2, 0.5), ("chest-hit", 0, -0.5)
  ("pen-lid-rattle", 0, 0.5), ("chest-scratch-2", 0.2, -0.5)
  ("clock-door", 0, 0.5), ("chest-hit", 0.2, -0.5)
  * */

  def playSound(sound: Int): Unit = {
    client.resetClock

    val sounds: Seq[((String, Double, Double), (String, Double, Double))] = Seq(
      (("chair-drag-1", 0, 0.6), ("chair-drag-2", 0.5, -0.6)),
      (("chest-handle", 4, 0.6), ("chest-lid", 0, -0.6)),
      (("chest-scratch-1", 0.6, 0.5), ("chest-hit", 0, -0.5)),
      (("pen-lid-hit", 0.2, 0.5), ("chest-hit", 0, -0.5)),
      (("pen-lid-rattle", 0, 0.5), ("chest-scratch-2", 0.2, -0.5)),
      (("clock-door", 0, 0.5), ("chest-hit", 0.2, -0.5)))

    val (sound1, sound2) = sounds(sound)

    internalPlay(sound1)
    internalPlay(sound2)

    def internalPlay(sound: (String, Double, Double)): Unit = {
      val (audio, start, pan) = sound

      soundPlays.mono(audio)
        .playMono(1.0, 1.0)
        .splay(0.2, pan)
        .play(start, 0)
    }
  }

  def playSound(sound: (String, Double)): Unit = {
    client.resetClock

    val (audio, pan) = sound
    soundPlays.mono(audio)
      .playMono(1.0, 1.0)
      .splay(0.2, pan)
      .play(0, 0)
  }

  def playSounds(sounds: (String, Double, Double)*): Unit = {
    client.resetClock

    sounds.foreach {
      case (audio, start, pan) =>
        soundPlays.mono(audio)
          .playMono(1.0, 1.0)
          .splay(0.2, pan)
          .play(start, 0)
    }
  }

  def theme1(): Unit = {
    client.resetClock

    val chestHitTime = 14.5
    val chestHitStart = 14.5

    val chestHitTimes = Melody.absolute(chestHitStart, Seq(
      chestHitTime, chestHitTime, chestHitTime, chestHitTime,
      chestHitTime, chestHitTime,
    ))

    val leftChestHitPan =
      (() => staticControl(-0.6), () => staticControl(-0.5), () => staticControl(-0.4))
    val rightChestHitPan =
      (() => staticControl(0.6), () => staticControl(0.5), () => staticControl(0.4))

    val leftToRightChestHitPan =
      (() => lineControl(-0.6, 0.6), () => lineControl(-0.5, 0.5), () => lineControl(-0.4, 0.4))

    val rightToLeftChestHitPan =
      (() => lineControl(0.6, -0.6), () => lineControl(0.5, -0.5), () => lineControl(0.4, -0.4))

    val chestHitPans = Seq(
      leftChestHitPan,
      leftToRightChestHitPan,
      rightChestHitPan,
      rightToLeftChestHitPan,

      leftChestHitPan,
      leftToRightChestHitPan,
    )

    println(s"Chest hit times $chestHitTimes")
    chestHitTimes.zipWithIndex.foreach {
      case (time, i) => playChestHit(time, chestHitPans(i))
    }
    def playChestHit(startTime: Double, pan: (() => ControlInstrument, () => ControlInstrument, () => ControlInstrument)): Unit = {
      soundPlays.mono(CHEST_HIT)
        .playMono(1.0, 1.0)
        .lowPass(2000)
        .splay(staticControl(0.4), pan._1())
        .play(startTime, 18)

      soundPlays.mono(CHEST_HIT)
        .playMono(1.01, 1.0)
        .highPass(6000)
        .splay(staticControl(0.4), pan._2())
        .play(startTime, 20)

      soundPlays.mono(CHEST_HIT)
        .playMono(0.99, 3.0)
        .sine(36, 0.8, 0.8)
        .splay(staticControl(0.2), pan._3())
        .play(startTime, 22)
    }

    val times = Melody.absolute(1.3, Seq(
      4.1, 4.1, 4.1, 4.1,
      4.1, 2.085, 4.1, 4.1,
      4.1, 4.1, 2.085, 4.1,
      4.1, 4.1, 4.1, 2.085,
      4.1, 4.1, 4.1, 4.1,
      4.1, 4.1, 4.1))

    introOutro(0)
    introOutro(times.last + 4.1)

    def introOutro(startTime: Double): Unit = {
      soundPlays.mono(CLOCK_DOOR)
        .playMono(0.99, 4.0)
        .bandPass(48, 0.1)
        .splay(staticControl(0.5), staticControl(-0.8))
        .play(startTime, 12)

      soundPlays.mono(CLOCK_DOOR)
        .playMono(1.01, 1.0)
        .highPass(1184)
        .splay(staticControl(0.5), staticControl(0.8))
        .play(startTime, 14)

      soundPlays.mono(CLOCK_DOOR)
        .playMono(1.00, 1.0)
        .bandPass(561, 5)
        .splay(staticControl(0.5), staticControl(0))
        .play(startTime, 16)
    }

    // Time to overlap is 2.085 or 4.1
    val lowFilter = () => staticControl(300)
    val highFilter = () => staticControl(12000)
    val lowToMidLow = () => lineControl(300, 700)
    val lowToHighLow = () => lineControl(300, 2000)
    val highLow = () => staticControl(2000)
    val highLowToLow = () => lineControl(2000, 300)

    val midLowToLow = () => lineControl(700, 300)
    val highToMidHigh = () => lineControl(12000, 8000)
    val midHighToHigh = () => lineControl(8000, 12000)
    val highToLowHigh = () => lineControl(12000, 6000)
    val lowHigh = () => staticControl(6000)
    val lowHighToHigh = () => lineControl(6000, 12000)


    val filters = Seq(
      (lowFilter, highFilter),
      (lowFilter, highFilter),
      (lowFilter, highFilter),
      (lowFilter, highFilter),

      (lowFilter, highFilter),
      (lowFilter, highFilter),
      (lowFilter, highFilter),
      (lowFilter, highFilter),

      (lowToMidLow, highToMidHigh),
      (midLowToLow, midHighToHigh),
      (lowFilter, highFilter),
      (lowFilter, highFilter),

      (lowFilter, highFilter),
      (lowFilter, highFilter),
      (lowFilter, highFilter),
      (lowFilter, highFilter),

      (lowToMidLow, highToMidHigh),
      (midLowToLow, midHighToHigh),
      (lowFilter, highFilter),
      (lowFilter, highFilter),

      (lowToHighLow, highToLowHigh),
      (highLow, lowHigh),
      (highLowToLow, lowHighToHigh))

    val channels = Seq(
      (0, 2),
      (0, 2),
      (0, 2),
      (0, 2),

      (0, 2),
      (0, 2),
      (0, 2),
      (0, 2),

      (4, 6),
      (4, 6),
      (0, 2),
      (0, 2),

      (0, 2),
      (0, 2),
      (0, 2),
      (0, 2),

      (4, 6),
      (4, 6),
      (0, 2),
      (0, 2),

      (8, 10),
      (8, 10),
      (8, 10))

    val midLeft = () => staticControl(-0.6)
    val farLeft = () => staticControl(-0.9)
    val midRight = () => staticControl(0.6)
    val farRight = () => staticControl(0.9)
    val midLeftToRight = () => lineControl(-0.6, 0.6)
    val farLeftToRight = () => lineControl(-0.9, 0.9)
    val midRightToLeft = () => lineControl(0.6, -0.6)
    val farRightToLeft = () => lineControl(0.9, -0.9)
    val midLeftToMid = () => lineControl(-0.6, 0.0)
    val farLefToMid = () => lineControl(-0.9, 0.0)
    val mid = () => staticControl(0)
    val midToRightMid = () => lineControl(0, 0.6)
    val midToFarRight = () => lineControl(0, 0.9)

    val pans = Seq(
      (midLeft, farLeft),
      (midLeftToRight, farLeftToRight),
      (midRight, farRight),
      (midRightToLeft, farRightToLeft),

      (midLeft, farLeft),
      (midLeftToRight, farLeftToRight),
      (midRight, farRight),
      (midRightToLeft, farRightToLeft),

      (midLeft, farLeft),
      (midLeftToRight, farLeftToRight),
      (midRight, farRight),
      (midRightToLeft, farRightToLeft),

      (midLeft, farLeft),
      (midLeftToRight, farLeftToRight),
      (midRight, farRight),
      (midRightToLeft, farRightToLeft),

      (midLeft, farLeft),
      (midLeftToRight, farLeftToRight),
      (midRight, farRight),
      (midRightToLeft, farRightToLeft),

      (midLeftToMid, farLefToMid),
      (mid, mid),
      (midToRightMid, midToFarRight))

    println(s"chest lid times $times")
    times.zipWithIndex.foreach {
      case (time, i) => playChestLid(time, filters(i), pans(i), channels(i))
    }

    def playChestLid(startTime: Double,
                     filter: (() => ControlInstrument, () => ControlInstrument),
                     pan: (() => ControlInstrument, () => ControlInstrument),
                     channels: (Int, Int)): Unit = {
      soundPlays.mono(CHEST_LID)
        .playMono(1.0, 1.0)
        .lowPass(filter._1())
        .splay(staticControl(0.2), pan._1())
        .play(startTime, channels._1)

      soundPlays.mono(CHEST_LID)
        .playMono(0.99, 1.0)
        .highPass(filter._2())
        .splay(staticControl(0.7), pan._2())
        .play(startTime, channels._2)
    }
  }

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
