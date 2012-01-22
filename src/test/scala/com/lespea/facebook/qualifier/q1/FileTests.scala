package com.lespea.facebook.qualifier.q1

import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec

final class FileTests extends WordSpec with MustMatchers {
  val answerMap = List(
    "WELCOME TO FACEBOOK HACKERCUP" -> 1,
    "CUP WITH LABEL HACKERCUP BELONGS TO HACKER" -> 2,
    "QUICK CUTE BROWN FOX JUMPS OVER THE LAZY DOG" -> 1,
    "MOVE FAST BE BOLD" -> 0,
    "HACK THE HACKERCUP" -> 1)

  val wanted = answerMap.zipWithIndex.map {
    case (e, i) ⇒
      (SolvedProblem(i + 1, e._1, e._2), "Case #" + (i + 1) + ": " + e._2)
  }

  val sampleFile = this.getClass.getClassLoader.getResource("givenProblems.txt")

  "A problem file" should {
    "correctly parse" when {
      "given a file" in {
        val answers = Solver.solveProblem(sampleFile.toURI)

        answers zip wanted foreach {
          case (a, w) ⇒
            a must equal(w._1)
            a.outStr must equal(w._2)
        }
      }
    }
  }
}