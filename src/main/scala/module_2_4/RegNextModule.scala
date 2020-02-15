package module_2_4

import chisel3.{
  fromIntToLiteral,
  fromIntToWidth,
  Bundle,
  Input,
  Module,
  Output,
  RegNext,
  UInt
}

class RegNextModule extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(12.W))
    val out = Output(UInt(12.W))
  })

  io.out := RegNext(io.in + 1.U)
}
