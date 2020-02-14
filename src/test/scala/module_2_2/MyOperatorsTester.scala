package module_2_2

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class MyOperatorsTester extends ChiselFlatSpecBase {
  "MyOperators" should "always output expected values" in {
    test(() => new MyOperators) { d =>
      new PeekPokeTester(d) {
        expect(d.io.out_add, 5)
        expect(d.io.out_sub, 1)
        expect(d.io.out_mul, 8)
      }
    }
  }
}
