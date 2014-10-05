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
        message = "alphabet must contain at least 16 characters")
    }

    "require unique alphabet chars" in {
      (new Hashids(
        salt = "this is my salt",
        alphabet = "1123467890abcdefghijklmnopqrstuvwxyz"
      )) must throwA[IllegalArgumentException](
        message = "check your alphabet for duplicates")
    }

    "deny spaces in alphabet" in {
      (new Hashids(
        salt = "this is my salt",
        alphabet = "1234567890 abcdefghijklmnopqrstuvwxyz"
      )) must throwA[IllegalArgumentException](
        message = "alphabet cannot contains spaces")
    }

    "encode" in {

      "single number" in {
        val hashid = Hashids("this is my salt")
        hashid.encode(12345L) must_== "NkK9"
      }

      "several number at once" in {
        val hashid = Hashids("this is my salt")
        hashid.encode(683L, 94108L, 123L, 5L) must_== "aBMswoO2UB3Sj"
      }

      "with custom hash length" in {
        val hashid = Hashids("this is my salt", 8)

        hashid.encode(1L) must_== "gB0NV05e"
      }

      "provide randomness" in {
        val hashid = Hashids("this is my salt")

        hashid.encode(5L, 5L, 5L, 5L) must_== "1Wc8cwcE"
      }

      "provide randomness for incrementing numbers" in {
        val hashid = Hashids("this is my salt")
        hashid.encode(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L) must_== "kRHnurhptKcjIDTWC3sx"
      }

      "max long" in {
        val hashid = Hashids("this is my salt")
        hashid.encode(Long.MaxValue) must_== "jvNx4BjM5KYjv"
      }

      "0L" in {
        val hashid = Hashids("this is my salt")
        hashid.encode(0L) must_== "5x"
      }

      "75527867232L" in {
        val hashid = Hashids("this is my salt")
        hashid.encode(75527867232L) must_== "3kK3nNOe"
      }

      "implicitly" in {
        import Hashids._
        implicit val hahids = Hashids("this is my salt")
        75527867232L.hashid must_== "3kK3nNOe"
      }
    }

    "encodeHex" in {

      "encodes hex string" in {
        val hashids = Hashids("this is my salt")

        hashids.encodeHex("FA")         must_== "lzY"
        hashids.encodeHex("26dd")       must_== "MemE"
        hashids.encodeHex("FF1A")       must_== "eBMrb"
        hashids.encodeHex("12abC")      must_== "D9NPE"
        hashids.encodeHex("185b0")      must_== "9OyNW"
        hashids.encodeHex("17b8d")      must_== "MRWNE"
        hashids.encodeHex("1d7f21dd38") must_== "4o6Z7KqxE"
        hashids.encodeHex("20015111d")  must_== "ooweQVNB"
      }

      "throw if non-hex string passed" >> {
        val hashids = Hashids("this is my salt")
        hashids.encodeHex("XYZ123") must throwA[IllegalArgumentException](
          message = "Not a HEX string")
      }

      "implicitly" in {
        import Hashids._
        implicit val hahids = Hashids("this is my salt")
        "FA".hashidHex must_== "lzY"
      }
    }

    "decode" in {

      "single number" in {
        val hashid = Hashids("this is my salt")
        hashid.decode("NkK9") must_== List(12345L)
      }

      "several number at once" in {
        val hashid = Hashids("this is my salt")
        hashid.decode("aBMswoO2UB3Sj") must_== List(683L, 94108L, 123L, 5L)
      }

      "with custom hash length" in {
        val hashid = Hashids("this is my salt", 8)
        hashid.decode("gB0NV05e") must_== List(1L)
      }

      "provide randomness" in {
        val hashid = Hashids("this is my salt")
        hashid.decode("1Wc8cwcE") must_== List(5L, 5L, 5L, 5L)
      }

      "provide randomness for incrementing numbers" in {
        val hashid = Hashids("this is my salt")
        hashid.decode("kRHnurhptKcjIDTWC3sx") must_== List(
          1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)
      }

      "0L" in {
        val hashid = Hashids("this is my salt")
        hashid.decode("5x") must_== List(0L)
      }

      "75527867232L" in {
        val hashid = Hashids("this is my salt")
        hashid.decode("3kK3nNOe") must_== List(75527867232L)
      }

      "Max long" >> {
        val hashids = Hashids("this is my salt")
        hashids.decode("jvNx4BjM5KYjv") must_== List(Long.MaxValue)
      }

      "implicitly" in {
        import Hashids._
        implicit val hahids = Hashids("this is my salt")
        "3kK3nNOe".unhashid must_== List(75527867232L)
      }
    }

    "decodeHex" >> {

      "single string" >> {
        val hashids = Hashids("this is my salt")
        hashids.decodeHex("lzY")   must_== "FA"
        hashids.decodeHex("eBMrb") must_== "FF1A"
        hashids.decodeHex("D9NPE") must_== "12ABC"
      }

      "implicitly" in {
        import Hashids._
        implicit val hahids = Hashids("this is my salt")
        "lzY".unhashidHex must_== "FA"
      }
    }

    "reject decode with different salt" >> {
      val hashid = Hashids("this is my salt")
      val hash = hashid.encode(10L)

      (Hashids("different salt").decode(hash)) must throwA[IllegalStateException]
    }
  }

}
