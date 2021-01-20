package zio.test.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.Or
import zio.random
import zio.random.Random
import zio.test.{Gen, Sized}

object boolean extends BooleanGen

trait BooleanGen {
  def orGen[T, A, B](
    raGen: Gen[Random with Sized, Refined[T, A]],
    rbGen: Gen[Random with Sized, Refined[T, B]]
  ): Gen[Random with Sized, Refined[T, A Or B]] = {
    val genA: Gen[random.Random with Sized, T] = raGen.map(_.value)
    val genB: Gen[random.Random with Sized, T] = rbGen.map(_.value)
      Gen.oneOf[Random with Sized, T](genA, genB).map(Refined.unsafeApply)

  }
}
