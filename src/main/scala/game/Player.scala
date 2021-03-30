package game

import scala.util.Random

import org.deeplearning4j.nn.multilayer._
import org.deeplearning4j.nn.conf._
import org.deeplearning4j.nn.conf.layers._
import org.deeplearning4j.nn.weights._

import org.nd4j.linalg.learning.config._
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions

case class Player(id: String, var amount: Double) {

  def network: MultiLayerNetwork = {
    val rngSeed = 123
    val numRows = 28
    val numColumns = 28
    val outputNum = 2

    val conf = new NeuralNetConfiguration.Builder()
      .seed(rngSeed)
      .updater(new Adam())
      .l2(1e-4)
      .list()
      .layer(new DenseLayer.Builder()
        .nIn(numRows * numColumns)
        .nOut(1000)
        .activation(Activation.RELU)
        .weightInit(WeightInit.XAVIER)
        .build())
      .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
        .nIn(1000)
        .nOut(outputNum)
        .activation(Activation.SOFTMAX)
        .weightInit(WeightInit.XAVIER)
        .build())
      .build()

    val net = new MultiLayerNetwork(conf)
    net.init()
    net
  }
  var state: Int = 10
  var lastPay: Double = 0

  def update(payoff: Double): Unit = {
    state += (payoff - lastPay).toInt
    amount += payoff
  }

  def play: Double = {
    lastPay = Random.nextDouble() * amount
    amount -= lastPay
    lastPay
  }

  override def toString: String = {
    "Player " + id + ":" + state + ":" + amount
  }
}
