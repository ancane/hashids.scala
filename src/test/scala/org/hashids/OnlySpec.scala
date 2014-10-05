package org.hashids

import org.specs2.mutable._
import org.scalacheck._

class OnlySpec extends Specification {
  "One number should encode then decode" >> {
    val hashids = Hashids("this is my salt")
    hashids.encode(8602212806674807105L) must_== "rPNn4ggOWv6lD"
    hashids.decode("rPNn4ggOWv6lD") must_== List(8602212806674807105L)
  }
}
