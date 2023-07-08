RETRO_MCP_DL=https://github.com/MCPHackers/RetroMCP-Java/releases/latest/download/RetroMCP-Java-CLI.jar
MODLOADER_DL=https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases/download/v1.0.0/ModLoader.Fix.b1.7.3-1.0.0.jar

# Internal variables
tmp_dir=/tmp
wget_flags=-q --show-progress
retro_mcp_bin=RetroMCP-Java-CLI.jar
modloader=Modloader.jar
modloader_extract_dir=ModLoader

setup: download decompile applypatch

download:
	# Download RetroMCP CLI and Modloader
	wget $(wget_flags) -O $(retro_mcp_bin) $(RETRO_MCP_DL)
	wget $(wget_flags) -O $(tmp_dir)/$(modloader) $(MODLOADER_DL)

decompile:
	java -jar $(retro_mcp_bin) setup b1.7.3
	unzip -d $(tmp_dir)/$(modloader_extract_dir) $(tmp_dir)/$(modloader)
	zip -uj jars/minecraft.jar $(tmp_dir)/$(modloader_extract_dir)/*
	java -jar $(retro_mcp_bin) decompile
	java -jar $(retro_mcp_bin) updatemd5

clean:
	java -jar $(retro_mcp_bin) cleanup
	# Clean working directory
	rm -rf libraries AnnoyanceFix.zip $(retro_mcp_bin) $(tmp_dir)/$(modloader_extract_dir) $(tmp_dir)/$(modloader)

applypatch:
	java -jar $(retro_mcp_bin) applypatch

createpatch:
	java -jar $(retro_mcp_bin) createpatch

compile:
	java -jar $(retro_mcp_bin) recompile
	java -jar $(retro_mcp_bin) reobfuscate
	rm -f AnnoyanceFix.zip
	zip -r -j AnnoyanceFix.zip minecraft/reobf
