package com.madhukaraphatak.spark.mllib



import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.{StandardScaler, Normalizer}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LinearRegressionWithSGD, LabeledPoint}
import org.apache.spark.sql.SQLContext

/**
 * Linear regression for housing data
 */
object LRForHousingData {
  def main(args: Array[String]) {
    val sc = new SparkContext(args(0), "SparkLR")
    val ITERATIONS = 100
     val data = sc.textFile(args(1))
    val vectorRDD = data.map(value => {
      val columns = value.split(",").map(value => value.toDouble)
      Vectors.dense(columns)
    })
    print(vectorRDD.take(10).toList)
    // normalize data
    val scaledRDD = new StandardScaler().fit(vectorRDD).transform(vectorRDD)
    println(scaledRDD.take(10).toList)

    // labeledPoints
    val labeledPointsRDD = scaledRDD.map(value=> {
      val arrayData = value.toArray
      val label = arrayData(1)
      val features = Vectors.dense(Array(arrayData(0)))
      new LabeledPoint(label, features)
    })

    labeledPointsRDD.cache()

    val model  = LinearRegressionWithSGD.train(labeledPointsRDD,ITERATIONS,0.001,1.0)
    println(model.intercept+ "  "+model.weights)

  }

}
