package zio.test.refined.magnolia

import zio.test.refined.CollectionGen

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.{NonEmpty, Size}
import zio.test.magnolia.DeriveGen
import zio.{Chunk, NonEmptyChunk}

class CollectionInstances extends CollectionGen {
  implicit def nonEmptyChunkRefinedDeriveGen[C, T](implicit
    deriveGenT: DeriveGen[T]
  ): DeriveGen[Refined[NonEmptyChunk[T], NonEmpty]] =
    DeriveGen.instance(
      nonEmptyChunkRefinedGen(deriveGenT.derive)
    )

  implicit def nonEmptyListRefinedDeriveGen[T](implicit
    deriveGenT: DeriveGen[T]
  ): DeriveGen[Refined[List[T], NonEmpty]] =
    DeriveGen.instance(non)

  implicit def nonEmptyVectorRefinedDeriveGen[T](implicit
    deriveGenT: DeriveGen[T]
  ): DeriveGen[Refined[Vector[T], NonEmpty]] =
    DeriveGen.instance(
      nonEmptyChunkGen(deriveGenT).derive
                                  .map(v => Refined.unsafeApply(v.toVector))
    )

  implicit def sizedChunkRefinedDeriveGen[T, P](implicit
    deriveGenT: DeriveGen[T],
    deriveGenSize: DeriveGen[Int Refined P]
  ): DeriveGen[Refined[Chunk[T], Size[P]]] =
    DeriveGen.instance(
      sizedChunkGen(deriveGenT, deriveGenSize).derive
                                              .map(r => Refined.unsafeApply(r))
    )

  implicit def listSizeRefinedDeriveGen[T, P](implicit
    deriveGenT: DeriveGen[T],
    deriveGenSize: DeriveGen[Int Refined P]
  ): DeriveGen[Refined[List[T], Size[P]]] =
    DeriveGen.instance(
      sizedChunkGen(deriveGenT, deriveGenSize).derive
                                              .map(r => Refined.unsafeApply(r.toList))
    )

  implicit def vectorSizeRefinedDeriveGen[T: DeriveGen, P](implicit
    deriveGenT: DeriveGen[T],
    deriveGenSize: DeriveGen[Int Refined P]
  ): DeriveGen[Refined[Vector[T], Size[P]]] =
    DeriveGen.instance(
      sizedChunkGen(deriveGenT, deriveGenSize).derive
                                              .map(r => Refined.unsafeApply(r.toVector))
    )


}
