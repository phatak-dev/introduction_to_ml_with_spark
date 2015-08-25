package com.madhukaraphatak.spark.breeze

import org.apache.spark.SparkContext
import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix


/**
 * Representing data as RDD[Vector]
 */
object RDDVectorExample {

  def main(args: Array[String]) {

    val sc = new SparkContext(args(0),"rdd vector")

    val data = sc.textFile(args(1))

    println(data.take(10).toList)

    val vectorRDD = data.map(value => {
      val columns = value.split(",").map(value => value.toDouble)
      new DenseVector(columns)
    })

    // multiply each row by a constant vector
    val vector = new DenseVector[Double](Array(1.0,2.0))
    val broadcastVector = sc.broadcast(vector)
    val scaledRDD = vectorRDD.map(row => {
      row :* broadcastVector.value
    })

    println(scaledRDD.take(10).toList)

  }


}
