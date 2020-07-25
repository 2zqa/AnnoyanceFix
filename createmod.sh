#!/bin/bash
./recompile.sh
./reobfuscate.sh
rm AnnoyanceFix.zip
zip -r -j AnnoyanceFix.zip reobf/minecraft
