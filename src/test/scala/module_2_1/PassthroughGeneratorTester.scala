package module_2_1

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

import scala.math.pow

class PassthroughGeneratorTester extends ChiselFlatSpecBase {
  "PassthroughGenerator" should "pass-through input" in {
    val width = 4

    test(
      () => new PassthroughGenerator(width),
      (c: PassthroughGenerator) => {
        new PeekPokeTester(c) {
          (0 until pow(2, width).toInt).foreach { v =>
            poke(c.io.in, v)
            expect(c.io.out, v)
          }
        }
      }
    )
  }
}
