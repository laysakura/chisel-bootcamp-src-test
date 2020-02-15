package module_2_2

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class ParameterizedAdderTester extends ChiselFlatSpecBase {
  "ParameterizedAdder(saturate = false)" should "calc 15 + 15 = 14" in {
    test(() => new ParameterizedAdder(saturate = false)) { d =>
      new PeekPokeTester(d) {
        poke(d.io.in_a, 15)
        poke(d.io.in_b, 15)
        expect(d.io.out, 14)
      }
    }
  }

  "ParameterizedAdder(saturate = true)" should "calc 15 + 15 = 15" in {
    test(() => new ParameterizedAdder(saturate = true)) { d =>
      new PeekPokeTester(d) {
        poke(d.io.in_a, 15)
        poke(d.io.in_b, 15)
        expect(d.io.out, 15)
      }
    }
  }
}
