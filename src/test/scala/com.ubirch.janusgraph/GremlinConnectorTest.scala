package com.ubirch.janusgraph

import gremlin.scala.{ Key, KeyValue }

class GremlinConnectorTest extends TestBase {

  val DEFAULT_VERTEX_LABEL = "coucou"
  val DEFAULT_VERTEX_PROP_NAME = "the name"
  val DEFAULT_VERTEX_PROP_VALUE = "the value"
  val DEFAULT_VERTEX_PROP: KeyValue[String] = KeyValue[String](Key[String](DEFAULT_VERTEX_PROP_NAME), DEFAULT_VERTEX_PROP_VALUE)

  feature("connect to gremlin") {
    scenario("simple test") {
      g.addV(DEFAULT_VERTEX_LABEL).property(DEFAULT_VERTEX_PROP).iterate()
      g.V().label().l() shouldBe List(DEFAULT_VERTEX_LABEL)
      val valueMap = g.V().valueMap().l()
      valueMap.size shouldBe 1
      valueMap.head.get(DEFAULT_VERTEX_PROP_NAME).toString shouldBe "[" + DEFAULT_VERTEX_PROP_VALUE + "]"

    }
  }

}
