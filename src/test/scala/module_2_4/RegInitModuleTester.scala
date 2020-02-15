package module_2_4

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class RegInitModuleTester extends ChiselFlatSpecBase {
  "RegInitModule" should "initially output 0" in {
    test(() => new RegInitModule) { d =>
      new PeekPokeTester(d) {
        expect(d.io.out, 0)
      }
    }
  }

  "RegInitModule" should "hold hold input and output input + 1 from one clock later" in {
    test(() => new RegInitModule) { d =>
      new PeekPokeTester(d) {
        (0 until 100).foreach { v =>
          poke(d.io.in, v)
          step(1)
          expect(d.io.out, v + 1)
        }
      }
    }
  }
}
