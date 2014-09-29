hashids.scala
=============

A Scala port of [hashids.org](www.hashids.org) library to generate short hashes from one or many numbers.

* Hashid is initialized with an `alphabet`, `salt` and a `minimum hash length`
* It's possible to hash single and multiple long numbers
* Hashes are unique across the salt value
* Hashes are decryptable to a single or multiple numbers respectively
* Hashes don't contain English curse words
* Supports positive long numbers
* The primary purpose of hashids is to obfuscate ids
* Do **not** use hashids for security purposes or compression

The goal of the port
====================

Besides the goals of the original library, this scala port is written without mutable state,
with tailrec optimization.

## Usage

```scala
import org.hashids._
```

#### Encode(hash)

You should provide your own unique salt to get hashes, different from other hashids.
Do **not** use salf from the examples.

```scala
val hashids = Hashids("this is my salt")
val hash = hashids.encode(12345L)

> "NkK9"
```

#### Decode(unhash)

During decryption, same salt must be used to get original numbers back:

```scala
val hashids = Hashids("this is my salt")
val numbers = hashids.decode("NkK9")

> List(12345L): Seq[Long]
```
## License

MIT License. See the `LICENSE` file.
