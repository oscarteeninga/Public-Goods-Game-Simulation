package game

import org.deeplearning4j.nn.multilayer._
import org.deeplearning4j.nn.conf._
import org.deeplearning4j.nn.conf.layers._
import org.deeplearning4j.nn.weights._
import org.nd4j.linalg.learning.config._
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions

case class Player(id: String, var amount: Double, players: Int) {

  lazy val network: MultiLayerNetwork = {
    val conf = new NeuralNetConfiguration.Builder()
      .seed(123)
      .updater(new Adam())
      .l2(1e-4)
      .list()
      .layer(new DenseLayer.Builder()
        .nIn(3)
        .nOut(3)
        .activation(Activation.RELU)
        .weightInit(WeightInit.XAVIER)
        .build())
      .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
        .nIn(1)
        .nOut(1)
        .activation(Activation.SOFTMAX)
        .weightInit(WeightInit.XAVIER)
        .build())
      .build()

    val net = new MultiLayerNetwork(conf)
    net.init()
    net
  }

  var lastPay: Double = amount / players
  var lastPayoff: Double = amount / players

  def update(payoff: Double): Unit = {
    lastPayoff = payoff
    amount += payoff
  }

  def contribution(b1: Double, b2: Double): Double = {
    b1 * lastPayoff + b2 * (lastPay * players - lastPayoff) / (players-1)
  }

  def play: Double = {
    lastPay = contribution(0.25, 0.75)
    lastPay
  }

  override def toString: String = {
    "Player " + id + ":" + amount
  }
}
