package module_2_3

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class Sort4Tester extends ChiselFlatSpecBase {
  "Sort4" should "sort 4 integers in asc order" in {
    test(() => new Sort4) { d =>
      new PeekPokeTester(d) {
        List(1, 2, 3, 4).permutations.foreach {
          case i0 :: i1 :: i2 :: i3 :: Nil =>
            println(s"Sorting $i0 $i1 $i2 $i3")
            poke(d.io.in0, i0)
            poke(d.io.in1, i1)
            poke(d.io.in2, i2)
            poke(d.io.in3, i3)
            expect(d.io.out0, 1)
            expect(d.io.out1, 2)
            expect(d.io.out2, 3)
            expect(d.io.out3, 4)
        }
      }
    }
  }
}
