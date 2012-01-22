package com.lespea.facebook.qualifier.q1

import java.io.File
import java.net.URI

import scala.annotation.switch
import scala.collection.mutable.HashMap
import scala.io.{ Source, BufferedSource }

final object Solver {
  def solveProblem(problemSource: String): Seq[SolvedProblem] =
    solveProblem(new File(problemSource).toURI)

  def solveProblem(uri: URI): Seq[SolvedProblem] =
    getProblems(Source.fromURI(uri)).zipWithIndex.map {
      case (p: String, i: Int) ⇒ Problem(i + 1, p)
    }.toList.par.map(Solver.solve).toList

  def getProblems(file: BufferedSource) =
    file.getLines.drop(1)

  def solve(p: Problem): SolvedProblem = {
    val counts = HashMap[Char, Double]()

    p.problem filter {
      case c: Char ⇒ (c: @switch) match {
        case 'H' | 'A' | 'C' | 'K' | 'E' | 'R' | 'U' | 'P' ⇒ true
        case _ ⇒ false
      }
    } foreach {
      case c: Char ⇒ {
        counts(c) = counts.getOrElse(c, 0.0) + ((c: @switch) match {
          case 'H' | 'A' | 'K' | 'E' | 'R' | 'U' | 'P' ⇒ 1.0
          case 'C'                                     ⇒ 0.5
        })
      }
    }

    SolvedProblem(p, if (counts.isEmpty || counts.size != 8) 0 else counts.values.min.toInt)
  }
}