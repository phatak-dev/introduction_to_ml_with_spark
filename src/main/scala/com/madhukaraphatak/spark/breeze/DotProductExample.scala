package com.madhukaraphatak.spark.breeze

import breeze.linalg.DenseVector

/**
 * Created by madhu on 4/9/15.
 */
object DotProductExample {

  def main(args: Array[String]) {

    val vectorA = new DenseVector(Array(10.0,20.0))

    val vectorB = new DenseVector(Array(10.0,40.0))

    // if we use dot, we get return type as Nothing
    val dotProduct = vectorA.dot(vectorB)
    println("dot Product is" + dotProduct)

    // using transpose method to to dot

    val vectorDotProduct = (vectorA.t * vectorB)
    println("dot product using vector is " + vectorDotProduct)




  }

}
