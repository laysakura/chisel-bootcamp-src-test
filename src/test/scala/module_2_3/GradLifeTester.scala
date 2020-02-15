package module_2_3

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class GradLifeTester extends ChiselFlatSpecBase {
  sealed trait GradLifeStates { val v: Int }
  case object Idle extends GradLifeStates {
    override val v: Int = 0
  }
  case object Coding extends GradLifeStates {
    override val v: Int = 1
  }
  case object Writing extends GradLifeStates {
    override val v: Int = 2
  }
  case object Grad extends GradLifeStates {
    override val v: Int = 3
  }

  private def gradLifeSimulator(state: GradLifeStates,
                                coffee: Boolean,
                                idea: Boolean,
                                pressure: Boolean): GradLifeStates =
    (state, coffee, idea, pressure) match {
      case (Idle, true, false, false)   => Coding
      case (Idle, false, false, true)   => Writing
      case (Coding, true, false, false) => Coding
      case (Coding, false, true, false) | (Coding, false, false, true) =>
        Writing
      case (Writing, true, false, false) | (Writing, false, true, false) =>
        Writing
      case (Writing, false, false, true) => Grad
      case _                             => Idle
    }

  "GradLife" should "transit as defined" in {
    test(() => new GradLife) { d =>
      new PeekPokeTester(d) {
        for {
          state <- Seq(Idle, Coding, Writing, Grad)
          coffee <- Seq(true, false)
          idea <- Seq(true, false)
          pressure <- Seq(true, false)
        } yield {
          poke(d.io.state, state.v)
          poke(d.io.coffee, coffee)
          poke(d.io.idea, idea)
          poke(d.io.pressure, pressure)
          expect(d.io.nextState,
                 gradLifeSimulator(state, coffee, idea, pressure).v)
        }
      }
    }
  }
}
