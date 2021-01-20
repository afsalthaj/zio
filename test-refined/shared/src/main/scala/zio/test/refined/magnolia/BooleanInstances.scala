package zio.test.refined.magnolia

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.Or
import zio.test.magnolia.DeriveGen
import zio.test.refined.BooleanGen

object boolean extends BooleanInstances

trait BooleanInstances extends BooleanGen {
  def orDeriveGen[T, A, B](
    implicit
    raGen: DeriveGen[Refined[T, A]],
    rbGen: DeriveGen[Refined[T, B]]
  ): DeriveGen[Refined[T, A Or B]] =
    DeriveGen.instance(orGen(raGen.derive, rbGen.derive))

}
