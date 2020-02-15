package module_2_3

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class Max3Tester extends ChiselFlatSpecBase {
  "Max3" should "output max input value" in {
    test(() => new Max3) { d =>
      new PeekPokeTester(d) {
        poke(d.io.in1, 6)
        poke(d.io.in2, 4)
        poke(d.io.in3, 2)
        expect(d.io.out, 6) // input 1 should be biggest

        poke(d.io.in2, 7)
        expect(d.io.out, 7) // now input 2 is

        poke(d.io.in3, 11)
        expect(d.io.out, 11) // and now input 3

        poke(d.io.in3, 3)
        expect(d.io.out, 7) // show that decreasing an input works as well

        poke(d.io.in1, 9)
        poke(d.io.in2, 9)
        poke(d.io.in3, 6)
        expect(d.io.out, 9) // still get max with tie
      }
    }
  }
}
