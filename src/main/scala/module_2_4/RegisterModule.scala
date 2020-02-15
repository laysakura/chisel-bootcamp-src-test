package module_2_4

import chisel3.{
  fromIntToWidth,
  fromIntToLiteral,
  Bundle,
  Input,
  Module,
  Output,
  Reg,
  UInt
}

class RegisterModule extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(12.W))
    val out = Output(UInt(12.W))
  })

  val register = Reg(UInt(12.W))
  register := io.in + 1.U
  io.out := register
}
