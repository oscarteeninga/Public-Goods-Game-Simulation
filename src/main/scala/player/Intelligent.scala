package player

import community.Community
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.learning.config.Adam
import org.nd4j.linalg.lossfunctions.LossFunctions
import player.emotion.Personality

case class Intelligent(id: String, community: Community) extends Player {

  override protected def b1: Double = ???

  override protected def b2: Double = ???

  override val personality: Personality = Personality.ordinary

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
}
