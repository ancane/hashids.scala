#!/bin/bash

#./sbt -Dprops.dir=./dev

java \
-XX:MaxPermSize=384m \
-Xms512m \
-Xmx1536m \
-Xss2m \
-XX:ReservedCodeCacheSize=256m \
-XX:+TieredCompilation \
-XX:+CMSClassUnloadingEnabled \
-XX:+UseConcMarkSweepGC \
-jar ~/.sbt/launchers/0.13.2/sbt-launch.jar \
-Dprops.dir=./dev
