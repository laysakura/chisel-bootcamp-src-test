package module_2_3

import chisel3.{
  fromIntToLiteral,
  fromIntToWidth,
  Bundle,
  Input,
  Module,
  Output,
  UInt
}

class LastConnect extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })
  io.out := 1.U
  io.out := 2.U
  io.out := 3.U
  io.out := 4.U
}
