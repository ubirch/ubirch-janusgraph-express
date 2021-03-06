package com.ubirch.janusgraph

import com.typesafe.config.{ Config, ConfigFactory }
import com.ubirch.janusgraph.ConnectorType.ConnectorType
import org.apache.commons.configuration.PropertiesConfiguration

import scala.collection.JavaConverters._

object ConfigBase {
  lazy val conf: Config = ConfigFactory.load()
}

object GremlinConnectorFactory {

  val gremlinConf: Config = ConfigBase.conf

  private lazy val instanceTest = new GremlinConnectorForTests
  private lazy val instanceJanusGraph = new JanusGraphConnector(gremlinConf)

  def getInstance(connectorType: ConnectorType): GremlinConnector = {
    connectorType match {
      case ConnectorType.JanusGraph => instanceJanusGraph
      case ConnectorType.Test => instanceTest
    }
  }

  def buildProperties(config: Config): PropertiesConfiguration = {
    val conf = new PropertiesConfiguration()

    val hosts: List[String] = config.getString("core.connector.hosts")
      .split(",")
      .toList
      .map(_.trim)
      .filter(_.nonEmpty)

    println("hosts: " + hosts)

    conf.addProperty("hosts", hosts.asJava)
    conf.addProperty("port", config.getString("core.connector.port"))
    conf.addProperty("serializer.className", config.getString("core.connector.serializer.className"))
    conf.addProperty("connectionPool.maxWaitForConnection", config.getString("core.connector.connectionPool.maxWaitForConnection"))
    conf.addProperty("connectionPool.reconnectInterval", config.getString("core.connector.connectionPool.reconnectInterval"))
    // no idea why the following line needs to be duplicated. Doesn't work without
    // cf https://stackoverflow.com/questions/45673861/how-can-i-remotely-connect-to-a-janusgraph-server first answer, second comment ¯\_ツ_/¯
    conf.addProperty("serializer.config.ioRegistries", config.getAnyRef("core.connector.serializer.config.ioRegistries").asInstanceOf[java.util.ArrayList[String]])
    conf.addProperty("serializer.config.ioRegistries", config.getStringList("core.connector.serializer.config.ioRegistries"))
    conf
  }
}

private class GremlinConnectorFactory(val conf: Config) {

}
