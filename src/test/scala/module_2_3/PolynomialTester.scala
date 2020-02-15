package module_2_3

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class PolynomialTester extends ChiselFlatSpecBase {
  private def f0(x: Int): Int = x * x - 2 * x + 1
  private def f1(x: Int): Int = 2 * x * x + 6 * x + 3
  private def f2(x: Int): Int = 4 * x * x - 10 * x - 5

  "Polynomial" should "calculate f0(x), f1(x), f2(x) when select == 0, 1, 2" in {
    test(() => new Polynomial) { d =>
      new PeekPokeTester(d) {
        (0 to 20).foreach { x =>
          poke(d.io.x, x)

          poke(d.io.select, 0)
          expect(d.io.f_x, f0(x))

          poke(d.io.select, 1)
          expect(d.io.f_x, f1(x))

          poke(d.io.select, 2)
          expect(d.io.f_x, f2(x))
        }
      }
    }
  }
}
