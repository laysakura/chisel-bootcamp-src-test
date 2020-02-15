package module_2_3

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class LastConnectTester extends ChiselFlatSpecBase {
  "LastConnect" should "output lastly connected input" in {
    test(() => new LastConnect) { d =>
      new PeekPokeTester(d) {
        expect(d.io.out, 4)
      }
    }
  }
}
