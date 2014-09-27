package org.hashids

class Hashids(
  salt: String = "",
  minHashLength: Int = 10,
  alphabet: String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
) {

  require(alphabet.length == alphabet.distinct.length , "check your alphabet for duplicates")
  require(alphabet.length >= 16, "alphabet must contain at least 16 characters")
  require(alphabet.indexOf(" ") < 0, "alphabet cannot contains spaces")

  private val sepDiv = 3.5
  private val guardDiv = 12

  private val (seps, guards, effectiveAlphabet) = {
    val filteredSeps = "cfhistuCFHISTU".filter(x => alphabet.contains(x))
    val filteredAlphabet = alphabet.filterNot(x => filteredSeps.contains(x))
    val shuffledSeps = consistentShuffle(filteredSeps, salt)

    val (tmpSeps, tmpAlpha) =
      if (shuffledSeps.isEmpty || ((filteredAlphabet.size / shuffledSeps.size) > sepDiv)) {
        val sepsTmpLen = Math.ceil(filteredAlphabet.size / sepDiv).toInt
        val sepsLen = if(sepsTmpLen == 1) 2 else sepsTmpLen

        if(sepsLen > shuffledSeps.size) {
          val diff = sepsLen - shuffledSeps.size
          val seps =  shuffledSeps + filteredAlphabet.substring(0, diff)
          val alpha = filteredAlphabet.substring(diff)
          (seps, alpha)
        } else {
          val seps = shuffledSeps.substring(0, sepsLen)
          val alpha = filteredAlphabet
          (seps, alpha)
        }
      } else (shuffledSeps, filteredAlphabet)

    val guardCount = Math.ceil(tmpAlpha.size.toDouble / guardDiv).toInt
    val shuffledAlpha = consistentShuffle(tmpAlpha, salt)

    if(shuffledAlpha.size < 3) {
      val guards = tmpSeps.substring(0, guardCount)
      val seps = tmpSeps.substring(guardCount)
      (seps, guards, shuffledAlpha)
    } else {
      val guards = shuffledAlpha.substring(0, guardCount)
      val alpha = shuffledAlpha.substring(guardCount)
      (tmpSeps, guards, alpha)
    }
  }

  def encode(x: Long*): String = ???
  def decode(x: String): List[Long] = ???

  def consistentShuffle(alphabet: String, salt: String): String = {
    def doShuffle(i: Int, v: Int, p: Int, result: String): String = {
      if (i <= 0) {
        result
      } else {
        val newV = v % salt.size;
        val ascii = salt.charAt(newV).toInt
        val newP = p + ascii
        val j = (ascii + newV + newP) % i
        val tmp = result.charAt(j)

        val alphaSuff = result.substring(0, j) + result.charAt(i) + result.substring(j + 1)
        val res = alphaSuff.substring(0, i) + tmp + alphaSuff.substring(i + 1)

        doShuffle(i - 1, newV + 1, newP, res)
      }
    }

    if(salt.size <= 0) alphabet
    else doShuffle(alphabet.size - 1, 0, 0, alphabet)
  }

  private def hash(input: Long, alphabet: String): String = {
    val alphaSize = alphabet.size.toLong

    def doHash(in: Long, hash: String): String = {
      if (in <= 0) hash
      else {
        val newIn = in / alphaSize
        val newHash = alphabet.charAt((in % alphaSize).toInt) + hash
        doHash(newIn, newHash)
      }
    }

    doHash(input, "")
  }

  private def unhash(input: String, alphabet: String): Long =
    input.foldLeft[Long](0L){case (acc, in) =>
      acc + alphabet.indexOf(in).toLong *
      Math.pow(alphabet.size, input.size - 1 - input.indexOf(in)).toLong
    }

}
