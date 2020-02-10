sonatypeProfileName := "com.github.ancane"

publishMavenStyle := true

publishArtifact in Test := false

pomExtra := {
  <url>https://github.com/ancane/hashids.scala</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com:ancane/hashids.scala</connection>
      <developerConnection>scm:git:git@github.com:ancane/hashids.scala</developerConnection>
      <url>https://github.com/ancane/hashids.scala</url>
    </scm>
    <developers>
      <developer>
        <id>ancane</id>
        <email>igor.shimko@gmail.com</email>
        <name>Igor Shymko</name>
        <url>https://github.com/ancane</url>
      </developer>
    </developers>
}
