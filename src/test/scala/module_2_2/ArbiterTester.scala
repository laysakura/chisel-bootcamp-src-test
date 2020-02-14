package module_2_2

import chisel3.iotesters.PeekPokeTester
import testbase.ChiselFlatSpecBase

class ArbiterTester extends ChiselFlatSpecBase {
  val data = 42

  "Arbiter" should "make PE0 valid when FIFO & PE0 are ready and FIFO is valid" in {
    test(() => new Arbiter) { d =>
      new PeekPokeTester(d) {
        poke(d.io.fifo_data, data)
        poke(d.io.fifo_valid, true)

        poke(d.io.pe0_ready, true)
        poke(d.io.pe1_ready, false)

        expect(d.io.fifo_ready, true)

        expect(d.io.pe0_valid, true)
        expect(d.io.pe1_valid, false)

        expect(d.io.pe0_data, data)
      }
    }
  }

  "Arbiter" should "make PE0 (prioritized to PE1) valid when FIFO & PE0 & PE1 are ready and FIFO is valid" in {
    test(() => new Arbiter) { d =>
      new PeekPokeTester(d) {
        poke(d.io.fifo_data, data)
        poke(d.io.fifo_valid, true)

        poke(d.io.pe0_ready, true)
        poke(d.io.pe1_ready, true)

        expect(d.io.fifo_ready, true)

        expect(d.io.pe0_valid, true)
        expect(d.io.pe1_valid, false)

        expect(d.io.pe0_data, data)
      }
    }
  }

  "Arbiter" should "make PE1 valid when FIFO & PE1 are ready and FIFO is valid" in {
    test(() => new Arbiter) { d =>
      new PeekPokeTester(d) {
        poke(d.io.fifo_data, data)
        poke(d.io.fifo_valid, true)

        poke(d.io.pe0_ready, false)
        poke(d.io.pe1_ready, true)

        expect(d.io.fifo_ready, true)

        expect(d.io.pe0_valid, false)
        expect(d.io.pe1_valid, true)

        expect(d.io.pe1_data, data)
      }
    }
  }

  "Arbiter" should "make both PE0 & PE1 invalid when FIFO is invalid" in {
    test(() => new Arbiter) { d =>
      new PeekPokeTester(d) {
        poke(d.io.fifo_data, data)
        poke(d.io.fifo_valid, false)

        poke(d.io.pe0_ready, true)
        poke(d.io.pe1_ready, true)

        expect(d.io.fifo_ready, true)

        expect(d.io.pe0_valid, false)
        expect(d.io.pe1_valid, false)
      }
    }
  }

  "Arbiter" should "make FIFO not ready when both PE0 & PE1 are not ready" in {
    test(() => new Arbiter) { d =>
      new PeekPokeTester(d) {
        poke(d.io.fifo_data, data)
        poke(d.io.fifo_valid, true)

        poke(d.io.pe0_ready, false)
        poke(d.io.pe1_ready, false)

        expect(d.io.fifo_ready, false)

        expect(d.io.pe0_valid, false)
        expect(d.io.pe1_valid, false)
      }
    }
  }

  "Arbiter" should "pass integration test" in {
    test(() => new Arbiter) { d =>
      new PeekPokeTester(d) {
        import scala.util.Random

        val data = Random.nextInt(65536)
        poke(d.io.fifo_data, data)

        for (i <- 0 until 8) {
          poke(d.io.fifo_valid, (i >> 0) % 2)
          poke(d.io.pe0_ready, (i >> 1) % 2)
          poke(d.io.pe1_ready, (i >> 2) % 2)

          expect(d.io.fifo_ready, i > 1)
          expect(d.io.pe0_valid, i == 3 || i == 7)
          expect(d.io.pe1_valid, i == 5)

          if (i == 3 || i == 7) {
            expect(d.io.pe0_data, data)
          } else if (i == 5) {
            expect(d.io.pe1_data, data)
          }
        }
      }
    }
  }
}
