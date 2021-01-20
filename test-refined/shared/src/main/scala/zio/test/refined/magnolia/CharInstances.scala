package zio.test.refined.magnolia

import eu.timepit.refined.api.Refined
import eu.timepit.refined.char.{Digit, Letter, LowerCase, UpperCase, Whitespace}
import zio.test.magnolia.DeriveGen
import zio.test.refined.CharGen

object char extends CharInstances

class CharInstances extends CharGen {
  implicit def digitDeriveGen: DeriveGen[Refined[Char, Digit]] =
    DeriveGen.instance(digitGen)

  implicit def letterDeriveGen: DeriveGen[Refined[Char, Letter]] =
    DeriveGen.instance(letterGen)

  implicit def lowerCaseDeriveGen: DeriveGen[Refined[Char, LowerCase]] =
    DeriveGen.instance(lowerCaseGen)

  implicit def upperCaseDeriveGen: DeriveGen[Refined[Char, UpperCase]] =
    DeriveGen.instance(alphaCharGen)

  implicit def whitespaceDeriveGen: DeriveGen[Refined[Char, Whitespace]] =
    DeriveGen.instance(whitespaceGen)

}
