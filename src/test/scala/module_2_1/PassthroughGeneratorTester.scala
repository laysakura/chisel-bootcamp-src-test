package module_2_1

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

import scala.math.pow

class PassthroughGeneratorTester extends ChiselFlatSpec {
  private val backendNames =
    if (firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
      Array("firrtl", "verilator")
    } else {
      Array("firrtl")
    }

  for (backendName <- backendNames) {
    "PassthroughGenerator" should s"pass-through input (with $backendName)" in {
      val width = 4
      Driver(() => new PassthroughGenerator(width), backendName) { c =>
        new PeekPokeTester(c) {
          (0 until pow(2, width).toInt).foreach { v =>
            poke(c.io.in, v)
            expect(c.io.out, v)
          }
        }
      } should be(true)
    }
  }
}
