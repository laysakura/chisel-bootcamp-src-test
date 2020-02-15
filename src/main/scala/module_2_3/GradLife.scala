package module_2_3

import chisel3.{fromIntToWidth, when, Bool, Bundle, Input, Module, Output, UInt}
import chisel3.util.Enum

class GradLife extends Module {
  val io = IO(new Bundle {
    val state = Input(UInt(2.W))
    val coffee = Input(Bool())
    val idea = Input(Bool())
    val pressure = Input(Bool())
    val nextState = Output(UInt(2.W))
  })

  val idle :: coding :: writing :: grad :: Nil = Enum(4)

  when(io.state === idle && io.coffee && !io.idea && !io.pressure) {
    io.nextState := coding
  }.elsewhen(io.state === idle && !io.coffee && !io.idea && io.pressure) {
      io.nextState := writing
    }
    .elsewhen(io.state === coding && io.coffee && !io.idea && !io.pressure) {
      io.nextState := coding
    }
    .elsewhen(io.state === coding && !io.coffee && io.idea && !io.pressure) {
      io.nextState := writing
    }
    .elsewhen(io.state === coding && !io.coffee && !io.idea && io.pressure) {
      io.nextState := writing
    }
    .elsewhen(io.state === writing && io.coffee && !io.idea && !io.pressure) {
      io.nextState := writing
    }
    .elsewhen(io.state === writing && !io.coffee && io.idea && !io.pressure) {
      io.nextState := writing
    }
    .elsewhen(io.state === writing && !io.coffee && !io.idea && io.pressure) {
      io.nextState := grad
    }
    .otherwise {
      io.nextState := idle
    }
}
