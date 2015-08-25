package com.madhukaraphatak.spark.mllib

import breeze.linalg._
import com.madhukaraphatak.spark.machinelearning.Utils
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LinearRegressionWithSGD, LabeledPoint}

/**
 * Machine learning example
 */
object LinearRegressionInMLLib {

  def main(args: Array[String]) {

    val sc = new SparkContext(args(0), "SparkLR")
    val numSlices = if (args.length > 1) args(1).toInt else 2
    val noOfData = 10 // Number of data points
    val ITERATIONS = 100
    val data = Utils.generateData(noOfData)
    // convert to Spark MLLib dense matrix
    val labeledPoints = data.map(value=> {
      val data = value.x.data
      val label = value.y
      new LabeledPoint(label, Vectors.dense(data))
    })
    val labeledPointRDD = sc.makeRDD(labeledPoints,numSlices)
    labeledPointRDD.cache()
    // call LinearRegression
    val initialVector = Vectors.dense(Array(1.0, 1.5))
    val model  = LinearRegressionWithSGD.train(labeledPointRDD,ITERATIONS,0.001,1.0,initialVector)
    println(model.intercept+ "  "+model.weights)



  }

}
