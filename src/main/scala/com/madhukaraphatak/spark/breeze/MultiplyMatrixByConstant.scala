package com.madhukaraphatak.spark.breeze

import breeze.linalg.{DenseMatrix, DenseVector}

/**
 * Multiply from the constant
 */
object MultiplyMatrixByConstant {

  def main(args: Array[String]) {
    val denseMatrix = DenseMatrix.eye[Double](5)
    println("original matrix"+"\n"+denseMatrix)
    // multiply by a constant
    val constant = 5.0
    println( "multiply matrix \n"+ (denseMatrix :* constant) )

    // vector
    val vector = new DenseVector[Double](Array(1.0,2.0,4.0,5.0,1.0))
    println(denseMatrix * vector)
  }

}
