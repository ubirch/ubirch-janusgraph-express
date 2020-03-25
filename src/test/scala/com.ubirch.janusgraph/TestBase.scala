package com.ubirch.janusgraph

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{ FeatureSpec, Matchers }

class TestBase extends FeatureSpec with LazyLogging with Matchers {

  val gc: GremlinConnector = GremlinConnectorFactory.getInstance(ConnectorType.Test)
  val g = gc.g

  def cleanJanus(gc: GremlinConnector) = {
    gc.g.V().drop()
  }

}
