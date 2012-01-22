package com.lespea.facebook.qualifier.q1

import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec

trait TestProblem extends MustMatchers { this: WordSpec ⇒
  def testProblem(prob: String, answer: Int) {
    Solver.solve(Problem(0, prob)) must have('answer(answer))
  }
}

final class Tests extends WordSpec with TestProblem {
  private val BigTestCount = 1000000
  val word = "HACKERCUP"

  "A solver" should {
    "handle blank strings" in {
      testProblem("", 0)
    }

    "return the correct answer" when {
      "the problem has no correct characters" in testProblem("zqxt", 0)

      "the problem has some correct characters" in testProblem("HACKER", 0)

      "the problem has only one of a multiple character" in testProblem("HACKERUP", 0)

      "the problem has all the correct characters, but in the wrong case" in testProblem(word.toLowerCase, 0)

      "the problem has the right word in the right sequence" in testProblem(word, 1)

      "the problem has the right word in the wrong sequence" in testProblem(word.reverse, 1)

      "the problem has the right word several times" in testProblem(word + word, 2)

      "the sample problems are run" in {
        val answers = Map(
          "WELCOME TO FACEBOOK HACKERCUP" -> 1,
          "CUP WITH LABEL HACKERCUP BELONGS TO HACKER" -> 2,
          "QUICK CUTE BROWN FOX JUMPS OVER THE LAZY DOG" -> 1,
          "MOVE FAST BE BOLD" -> 0,
          "HACK THE HACKERCUP" -> 1)

        answers foreach { case (p, a) ⇒ testProblem(p, a) }
      }
    }

    "run fast" when {
      "a large string has no wanted chars" in testProblem("a" * BigTestCount, 0)
      "many copies of the word exists" in testProblem(word * BigTestCount, BigTestCount)
    }
  }
}