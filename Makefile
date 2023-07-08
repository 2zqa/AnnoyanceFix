RETRO_MCP_DL=https://github.com/MCPHackers/RetroMCP-Java/releases/latest/download/RetroMCP-Java-CLI.jar
MODLOADER_DL=https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases/download/v1.0.0/ModLoader.Fix.b1.7.3-1.0.0.jar

# Internal variables
tmp_dir=/tmp
wget_flags=-q --show-progress
retro_mcp_bin=RetroMCP-Java-CLI.jar
modloader_bin=Modloader.jar
modloader_extract_dir=ModLoader

download:
	# Download RetroMCP CLI and Modloader
	wget $(wget_flags) -O $(retro_mcp_bin) $(RETRO_MCP_DL)
	wget $(wget_flags) -O $(tmp_dir)/$(modloader_bin) $(MODLOADER_DL)

setup:
	java -jar $(retro_mcp_bin) setup b1.7.3
	unzip -d $(tmp_dir)/$(modloader_extract_dir) $(tmp_dir)/$(modloader_bin)
	zip -uj jars/minecraft.jar $(tmp_dir)/$(modloader_extract_dir)/*
	java -jar $(retro_mcp_bin) decompile
	java -jar $(retro_mcp_bin) updatemd5

clean:
	java -jar $(retro_mcp_bin) cleanup
	# Clean working directory
	rm -rf libraries $(tmp_dir)/$(modloader_extract_dir) $(tmp_dir)/$(modloader_bin)
