package player.citizen

import community.Community
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.learning.config.Adam
import org.nd4j.linalg.lossfunctions.LossFunctions
import player.Player
import player.emotion.Sympathize
import player.personality.Personality
import player.personality.Personality.{CooperatorPersonality, ImpostorPersonality, OrdinaryPersonality}

import scala.util.Random

trait Citizen extends Player {

  val candidatesSympathize: List[Sympathize] = community.candidates.map(candidate => Sympathize(candidate.id))

  def vote: Option[Int] = {
    if (candidatesSympathize.isEmpty) None
    else Some(candidatesSympathize.maxBy(_.getFactor).candidateId)
  }

  def updateSympathize(): Unit = {
    community.president match {
      case Some(president) =>
        candidatesSympathize(president.id).updateLevel(lastPayIn, lastPayoff)
        candidatesSympathize.filterNot(_.candidateId == president.id).foreach(_.randomize())
      case None =>
        candidatesSympathize.foreach(_.randomize())
    }
  }
}

case class Cooperator(id: Int, community: Community) extends Citizen {
  override val personality: Personality = CooperatorPersonality
}

case class Impostor(id: Int, community: Community) extends Citizen {

  override val personality: Personality = ImpostorPersonality
}

case class Ordinary(id: Int, community: Community) extends Citizen {
  override val personality: Personality = OrdinaryPersonality

  override def vote: Option[Int] = {
    if (Random.nextDouble() > 0.25 && candidatesSympathize.nonEmpty)
      Some(community.candidates(Math.abs(Random.nextInt()) % candidatesSympathize.size)).map(_.id)
    else None
  }
}

case class Intelligent(id: Int, community: Community) extends Citizen {

  override val personality: Personality = OrdinaryPersonality

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

