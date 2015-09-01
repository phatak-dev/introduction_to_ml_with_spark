package com.madhukaraphatak.spark.machinelearning

import breeze.linalg.DenseVector
import org.apache.spark.SparkContext

/**
 * Created by madhu on 25/8/15.
 */
object LRWithSGD {

  def main(args: Array[String]) {

    val sc = new SparkContext(args(0), "SparkLR")

    val noOfData = 10 // Number of data points
    val D = 2 // Numer of dimensions
    val ITERATIONS = 10
    val batchSize = 0.5

    var w = new DenseVector(Array(1.0, 1.5))
    println("Initial w: " + w)
    val data = Utils.generateData(noOfData)
    val rdd = sc.makeRDD(data)
    val learningRate = 0.00001

    for (i <- 1 to ITERATIONS) {
      println("On iteration " + i)
      val cost = sc.accumulator(0.0)
      val gradient = rdd.sample(false, batchSize, 42 + i).map(dataPoint => {
        val diff = dataPoint.x.dot(w).asInstanceOf[Double] - dataPoint.y
        val costForRow = diff * diff
        //fromBreeze(gradient)
        cost += costForRow
        val diffVector = new DenseVector(Array.fill[Double](D)(diff))
        val gradient = dataPoint.x :* diffVector
        gradient
      }).reduce(
          (v1, v2) => v1 :+ v2)

      val finalCost = cost.value / (noOfData * 2)
      println(finalCost)
      println("gradient is " + gradient)
      val learningRateVector = new DenseVector(Array.fill(D)(learningRate))
      w = w :- (gradient :* learningRateVector)
      println("next w is " + w)
    }

    println("Final w: " + w)


  }


}
