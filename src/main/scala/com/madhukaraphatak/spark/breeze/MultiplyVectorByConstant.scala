package com.madhukaraphatak.spark.breeze

import breeze.linalg.DenseVector

/**
 * Multiply from the constant
 */
object MultiplyVectorByConstant {

  def main(args: Array[String]) {
    val vector = new DenseVector(Array(10.0,20.0))
    val constant = 5.0
    // constant multiply
    val constantMultiply = vector :* constant


    println(constantMultiply)
    // vector multiply
    val constantVector = DenseVector.fill(vector.length,constant)
    println(vector :* constantVector)
  }

}
