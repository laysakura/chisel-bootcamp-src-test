package module_2_4

import chisel3.{
  fromIntToLiteral,
  fromIntToWidth,
  Bundle,
  Input,
  Module,
  Output,
  RegInit,
  UInt
}

class RegInitModule extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(12.W))
    val out = Output(UInt(12.W))
  })

  val register = RegInit(UInt(12.W), 0.U)
  register := io.in + 1.U
  io.out := register
}
