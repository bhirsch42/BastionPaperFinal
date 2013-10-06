#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR
ls
java -cp .:stdlib.jar:slick:slick/lib:slick/lib/*:WordConverter -Djava.library.path=slick BastionPaper