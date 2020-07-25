#!/bin/bash
./recompile.sh
./reobfuscate.sh
rm -f AnnoyanceFix.zip
zip -r -j AnnoyanceFix.zip reobf/minecraft
