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

    // multiply parititon at time

   /* val scaledRDDByMatrix = vectorRDD.glom().map(value => {
      val rows = value.length
      val columns = value(0).length
      val data = value.map(value => value.data)
      //covert to matrix
      val combineData = data.fold(Array[Double]())((acc,value) => acc ++ value)
      val denseMatrix = new DenseMatrix[Double](rows,columns, combineData)
      //repmat
      val repeatedArray = ( 1 to denseMatrix.) map (value => broadcastVector.value.data)
      val combinedArray = repeatedArray.fold(Array[Double]())((acc,value) => acc ++ value)
      val multiplierMatrix = new DenseMatrix[Double](rows,columns,combinedArray)

      denseMatrix :* multiplierMatrix
    })


    println(scaledRDDByMatrix.collect().toList)*/




  }


}
