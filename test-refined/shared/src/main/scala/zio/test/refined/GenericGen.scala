package zio.test.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.generic.Equal
import shapeless.Witness
import zio.random.Random
import zio.test.Gen

object generic extends GenericGen

trait GenericGen {
  def equalArbitrary[T, U <: T](
    implicit
    wu: Witness.Aux[U]
  ): Gen[Random, Refined[T, Equal[U]]] =
    Gen.const(wu.value).map(Refined.unsafeApply)
}
