package module_2_1

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

import scala.math.pow

class PassthroughGeneratorTester extends ChiselFlatSpecBase {
  "PassthroughGenerator" should "pass-through input" in {
    val width = 4

    test(() => new PassthroughGenerator(width)) { d =>
      new PeekPokeTester(d) {
        (0 until pow(2, width).toInt).foreach { v =>
          poke(d.io.in, v)
          expect(d.io.out, v)
        }
      }
    }
  }
}
