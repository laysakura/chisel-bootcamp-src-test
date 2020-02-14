package module_2_2

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

import scala.math.pow

class MACTester extends ChiselFlatSpecBase {
  "MAC" should "correctly calculate MAC" in {
    val width = 4

    test(() => new MAC) { d =>
      new PeekPokeTester(d) {
        (0 until pow(2, width).toInt).foreach { a =>
          (0 until pow(2, width).toInt).foreach { b =>
            (0 until pow(2, width).toInt).foreach { c =>
              poke(d.io.in_a, a)
              poke(d.io.in_b, b)
              poke(d.io.in_c, c)
              expect(d.io.out, a * b + c)
            }
          }
        }
      }
    }
  }
}
