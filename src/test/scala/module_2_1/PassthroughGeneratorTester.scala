package module_2_1

import chisel3.{iotesters, MultiIOModule}
import chisel3.iotesters.{ChiselFlatSpec, PeekPokeTester}

import scala.math.pow

trait ChiselFlatSpecBase extends ChiselFlatSpec {
  protected val backendNames: Array[String] =
    if (firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version")))
      Array("firrtl", "verilator")
    else
      Array("firrtl")

  protected def test[T <: MultiIOModule](
      cm: () => T,
      testerGen: T => PeekPokeTester[T]): Unit =
    backendNames.foreach { backendName =>
      iotesters.Driver
        .execute(Array("--is-verbose", "--backend-name", backendName), cm)(
          testerGen) should be(true)
    }
}

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
