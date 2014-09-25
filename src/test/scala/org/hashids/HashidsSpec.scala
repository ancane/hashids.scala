package org.hashids

import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit

class HashidsSpec extends SpecificationWithJUnit with Mockito {

  "Hashids" should {

    "require alphabet with at least 16 chars" in {
      (new Hashids(
        salt = "this is my salt",
        alphabet = "1"
      )) must throwA[IllegalArgumentException](
        message = "alphabet must contain at least 16 unique characters")
    }

    "deny spaces in alphabet" in {
      (new Hashids(
        salt = "this is my salt",
        alphabet = "1234567890 1234567890"
      )) must throwA[IllegalArgumentException](
        message = "alphabet cannot contains spaces")
    }

    "encode/decode" in {

      "single number" in {
        val hashid = new Hashids("this is my salt")
        hashid.encode(12345L) must_== "NkK9"
        hashid.decode("NkK9") must_== List(12345L)
      }

      "several number at once" in {
        val hashid = new Hashids("this is my salt")
        hashid.encode(683L, 94108L, 123L, 5L) must_== "aBMswoO2UB3Sj"
        hashid.decode("aBMswoO2UB3Sj") must_== List(683L, 94108L, 123L, 5L)
      }

      "with custom hash length" in {
        val hashid = new Hashids("this is my salt", 8)

        hashid.encode(1L) must_== "gB0NV05e"
        hashid.decode("gB0NV05e") must_== List(1L)
      }

      "provide randomness" in {
        val hashid = new Hashids("this is my salt")

        hashid.encode(5L, 5L, 5L, 5L) must_== "1Wc8cwcE"
        hashid.decode("1Wc8cwcE") must_== List(5L, 5L, 5L, 5L)
      }

      "provide randomness for incrementing numbers" in {
        val hashid = new Hashids("this is my salt")
        hashid.encode(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L) must_== "kRHnurhptKcjIDTWC3sx"
        hashid.decode("kRHnurhptKcjIDTWC3sx") must_== List(
          1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)
      }

      "max long" in {
        val hashid = new Hashids("this is my salt")
        hashid.encode(Long.MaxValue) must_== "kRHnurhptKcjIDTWC3sx"
        hashid.decode("kRHnurhptKcjIDTWC3sx") must_== List(Long.MaxValue)
      }

      "75527867232L" in {
        val hashid = new Hashids("this is my salt")
        hashid.encode(75527867232L) must_== "3kK3nNOe"
        hashid.decode("3kK3nNOe") must_== List(75527867232L)
      }
    }
  }

}
