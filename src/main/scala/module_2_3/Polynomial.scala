package module_2_3

import chisel3.{
  fromIntToLiteral,
  fromIntToWidth,
  when,
  Bundle,
  Input,
  Module,
  Output,
  SInt,
  UInt,
  Wire
}

class Polynomial extends Module {
  val io = IO(new Bundle {
    val select = Input(UInt(2.W))
    val x = Input(SInt(32.W))
    val f_x = Output(SInt(32.W))
  })

  val x2 = Wire(SInt(32.W))
  x2 := io.x * io.x

  when(io.select === 0.U) {
    io.f_x := x2 - 2.S * io.x + 1.S
  }.elsewhen(io.select === 1.U) {
    io.f_x := 2.S * x2 + 6.S * io.x + 3.S
  } otherwise {
    io.f_x := 4.S * x2 - 10.S * io.x - 5.S
  }
}
