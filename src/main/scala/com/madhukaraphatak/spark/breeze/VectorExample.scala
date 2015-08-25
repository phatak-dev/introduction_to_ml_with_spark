package com.madhukaraphatak.spark.breeze

/**
 * Representing data in Vector
 */

import breeze.linalg._

object VectorExample {

  def main(args: Array[String]) {

    // column vector with 5 columns
    val columnVector = DenseVector.zeros[Double](5)
    println(columnVector)
    //row vector
    val rowVector = columnVector.t
    println(rowVector)
    //from array
    val vectorFromArray = new DenseVector(Array(10.0,20.0))
    println(vectorFromArray)

    //sparse Vector
    val sparseVector = new SparseVector[Double](Array(1,3,5), Array(10.0,4.0,2.0), 5)
    println(sparseVector)


  }

}
