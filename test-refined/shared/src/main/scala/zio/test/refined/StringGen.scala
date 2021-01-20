package zio.test.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.{EndsWith, StartsWith, Uuid}
import shapeless.Witness
import zio.random.Random
import zio.test.{Gen, Sized}

object string extends StringGen

trait StringGen {
  def endsWithStringDeriveGen[S <: String](
    implicit
    ws: Witness.Aux[S]
  ): Gen[Random with Sized, Refined[String, EndsWith[S]]] =
    Gen.anyString.map(value => Refined.unsafeApply(value + ws.value))

  def startsWithStringDeriveGen[S <: String](
    implicit
    ws: Witness.Aux[S]
  ): Gen[Random with Sized, Refined[String, StartsWith[S]]] =
    Gen.anyString.map(value => Refined.unsafeApply(ws.value + value))

  implicit val uuidStringDeriveGen: Gen[Random, Refined[String, Uuid]] =
    Gen.anyUUID.map(value => Refined.unsafeApply(value.toString))

}
