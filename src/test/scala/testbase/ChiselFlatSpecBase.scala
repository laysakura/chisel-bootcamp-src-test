package testbase

import chisel3.MultiIOModule
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

trait ChiselFlatSpecBase extends ChiselFlatSpec {
  protected val backendNames: Array[String] =
    if (firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version")))
      Array("firrtl", "verilator")
    else
      Array("firrtl")

  /**
    * Runs chisel3.iotesters.Driver$#execute with firrtl (and verilator if you have the one).
    *
    * @param device The device to be tested
    * @param testerGen A peek-poke tester with test
    * @tparam T Type of device
    */
  protected def test[T <: MultiIOModule](
      device: () => T,
      testerGen: T => PeekPokeTester[T]
  ): Unit =
    backendNames.foreach { backendName =>
      Driver.execute(Array("--is-verbose", "--backend-name", backendName),
                     device)(testerGen) should be(true)
    }
}
