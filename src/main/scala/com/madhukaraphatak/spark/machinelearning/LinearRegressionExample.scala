package com.madhukaraphatak.spark.machinelearning


import breeze.linalg._
import org.apache.spark.SparkContext

/**
 * Machine learning example
 */
object LinearRegressionExample {

  def main(args: Array[String]) {

    val sc = new SparkContext(args(0), "SparkLR")
    val numSlices = if (args.length > 1) args(1).toInt else 2

    val noOfData = 10 // Number of data points
    val D = 2 // Numer of dimensions
    val ITERATIONS = 10

    var w = new DenseVector(Array(1.0, 1.5))
    println("Initial w: " + w)
    val data = Utils.generateData(noOfData)
    val rdd = sc.makeRDD(data)
    val learningRate = 0.00001

    for (i <- 1 to ITERATIONS) {
      println("On iteration " + i)
      val cost = sc.accumulator(0.0)
      val gradient = rdd.map(dataPoint => {
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
