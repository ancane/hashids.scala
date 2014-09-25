package org.hashids

class Hashids(
  salt: String,
  minHashLength: Int = 10,
  alphabet: String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
) {

  require(alphabet.length >= 16,
    "alphabet must contain at least 16 unique characters")

  require(alphabet.indexOf(" ") < 0, "alphabet cannot contains spaces")

  def encode(x: Long*): String = ???
  def decode(x: String): List[Long] = ???

}
