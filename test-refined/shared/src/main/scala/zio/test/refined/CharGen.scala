package zio.test.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.char.{Digit, Letter, LowerCase, UpperCase, Whitespace}
import zio.random.Random
import zio.test.Gen

object char extends CharGen

trait CharGen {
  private[refined] val alphaCharGen: Gen[Random, Char] =
    Gen.weighted(Gen.char(65, 90) -> 26, Gen.char(97, 122) -> 26)

  private[refined] val numericCharGen: Gen[Random, Char] =
    Gen.weighted(Gen.char(48, 57) -> 10)

  private[refined] val whitespaceChars: Seq[Char] =
    (Char.MinValue to Char.MaxValue).filter(_.isWhitespace)

  def digitGen: Gen[Random, Refined[Char, Digit]] =
    numericCharGen.map(value => Refined.unsafeApply(value))

  def letterGen: Gen[Random, Refined[Char, Letter]] =
    alphaCharGen.map(value => Refined.unsafeApply(value))

  def lowerCaseGen: Gen[Random, Refined[Char, LowerCase]] =
    alphaCharGen.map(value => Refined.unsafeApply(value.toLower))

  def upperCaseGen: Gen[Random, Refined[Char, UpperCase]] =
    alphaCharGen.map(value => Refined.unsafeApply(value.toUpper))

  def whitespaceGen: Gen[Random, Refined[Char, Whitespace]] = {
    val whiteSpaceGens: Seq[Gen[Random, Char]] =
      whitespaceChars.map(Gen.const(_))

    Gen
      .oneOf[Random, Char](whiteSpaceGens: _*)
      .map(char => Refined.unsafeApply(char))

  }
}
