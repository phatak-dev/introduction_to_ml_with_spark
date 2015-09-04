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
    val constant = 5.0
    val broadcastConstant = sc.broadcast(constant)
    val scaledRDD = vectorRDD.map(row => {
      row :* broadcastConstant.value
    })

    println(scaledRDD.take(10).toList)

    // using glom to partitionwise calculation

    val scaledRDDByPartition = vectorRDD.glom().map((value:Array[DenseVector[Double]]) => {
      val arrayValues = value.map(denseVector => denseVector.data).flatten
      val denseMatrix = new DenseMatrix[Double](value.length,value(0).length,arrayValues)
      denseMatrix :*= broadcastConstant.value
      denseMatrix.toDenseVector
    })

    println(scaledRDDByPartition.take(10).toList)

  }


}
