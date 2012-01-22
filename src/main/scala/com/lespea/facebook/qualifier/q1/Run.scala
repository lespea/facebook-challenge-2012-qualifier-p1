package com.lespea.facebook.qualifier.q1

import java.io.FileWriter

final object Run extends App {
  if (args.length < 1)
    throw new RuntimeException("You must provide at least 1 argument as a problem source")

  val parser = """^(.+)(\.[^.]+)$""".r
  args.par foreach { f ⇒
    val out = try {
      val filename = parser.findFirstMatchIn(f) match {
        case Some(m) ⇒ m.group(1) + "_SOLVED" + m.group(2)
        case _       ⇒ f + "_SOLVED"
      }

      new FileWriter(filename)
    } catch { case e: Exception ⇒ throw e }

    try {
      out.write(Solver.solveProblem(f) map { _.outStr } mkString "\n")
      out.write("\n")
    } finally {
      try { out.close() } catch { case _: Exception ⇒ }
    }
  }
}