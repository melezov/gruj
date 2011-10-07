#!/bin/bash

echo Opening up the Scala console...
`dirname $0`/sbt.sh "$@" console
