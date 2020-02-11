package module_2_1

import chisel3.{fromIntToWidth, Bundle, Input, Module, Output, UInt}

class PassthroughGenerator(width: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(width.W))
    val out = Output(UInt(width.W))
  })
  io.out := io.in
}
