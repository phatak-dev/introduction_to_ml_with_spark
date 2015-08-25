package com.madhukaraphatak.spark.machinelearning

import breeze.linalg.DenseVector

/**
 * Created by madhu on 25/8/15.
 */
object Utils {

  def generateData(noOfData: Int) = {
    (1 to noOfData).map(value => {
      val x1 = value * 1.0
      val x2 = value * 10 * 1.0
      val y = 0 + 2.5 * x1 + 1.5 * x2
      val valueVector = new DenseVector(Array(x1, x2))
      DataPoint(valueVector, y)
    })

  }

}
