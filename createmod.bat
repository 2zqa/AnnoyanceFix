@echo off
call "recompile.bat" < nul
call "reobfuscate.bat" < nul
del /F AnnoyanceFix.zip
"C:\Program Files\7-Zip\7z.exe" a "AnnoyanceFix.zip" .\reobf\minecraft\*
pause