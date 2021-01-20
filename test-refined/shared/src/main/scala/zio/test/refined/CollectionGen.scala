package zio.test.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.{NonEmpty, Size}
import zio.random.Random
import zio.test.{Gen, Sized}
import zio.test.magnolia.DeriveGen
import zio.{Chunk, NonEmptyChunk}

object collection extends CollectionGen

trait CollectionGen {
  def nonEmptyChunkRefinedGen[C, T](
    genT: Gen[Random with Sized, T]
  ): Gen[Random with Sized, Refined[NonEmptyChunk[T], NonEmpty]] =
    nonEmptyChunkGen(genT).map(Refined.unsafeApply)

  def nonEmptyListRefinedGen[T](
    genT: Gen[Random, T]
  ): Gen[Random with Sized, Refined[List[T], NonEmpty]] =
    nonEmptyChunkGen(genT)
      .map(v => Refined.unsafeApply(v.toList))

  def nonEmptyVectorRefinedGen[T](
    genT: Gen[Random, T]
  ): Gen[Random with Sized with Sized, Refined[Vector[T], NonEmpty]] =
    nonEmptyChunkGen(genT)
      .map(v => Refined.unsafeApply(v.toVector))

  def sizedChunkRefinedGen[T, P](
    genT: Gen[Random, T],
    sizeGen: Gen[Random, Int Refined P]
  ): Gen[Random with Sized, Refined[Chunk[T], Size[P]]] =
    sizedChunkGen(genT, sizeGen)
      .map(r => Refined.unsafeApply(r))

  def listSizeRefinedGen[T, P](
    implicit
    deriveGenT: Gen[Random, T],
    sizeGen: Gen[Random, Int Refined P]
  ): Gen[Random with Sized, Refined[List[T], Size[P]]] =
    sizedChunkGen(deriveGenT, sizeGen)
      .map(r => Refined.unsafeApply(r.toList))

  def vectorSizeRefinedGen[T: DeriveGen, P](
    genT: Gen[Random, T],
    sizeGen: Gen[Random, Int Refined P]
  ): Gen[Random with Sized, Refined[Vector[T], Size[P]]] =
    sizedChunkGen(genT, sizeGen)
      .map(r => Refined.unsafeApply(r.toVector))

  private[refined] def nonEmptyChunkGen[T](
    arbT: Gen[Random with Sized, T]
  ): Gen[Random with Sized, NonEmptyChunk[T]] =
    Gen.chunkOf1(arbT)

  private[refined] def sizedChunkGen[T, P](
    genT: Gen[Random, T],
    sizeGen: Gen[Random, Int Refined P]
  ): Gen[Random with Sized, Chunk[T]] =
    sizeGen.flatMap { n =>
      Gen.chunkOfN(n.value)(genT)
    }
}
