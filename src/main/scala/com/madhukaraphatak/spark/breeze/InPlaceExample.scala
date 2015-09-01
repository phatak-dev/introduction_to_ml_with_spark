package com.madhukaraphatak.spark.breeze

import breeze.linalg.DenseVector

/**
 * InPlace example
 */
object InPlaceExample {

  def main(args: Array[String]) {
    // in place vector
    val vector = new DenseVector(Array(10.0,20.0))
    val constant = 5.0
    vector :*= constant
    println("update vector is " + vector)
  }

}
