RETRO_MCP_DL=https://github.com/MCPHackers/RetroMCP-Java/releases/latest/download/RetroMCP-Java-CLI.jar
MODLOADER_DL=https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases/download/v1.0.0/ModLoader.Fix.b1.7.3-1.0.0.jar
MOD_NAME=AnnoyanceFix

tmp_dir=/tmp
wget_flags=-q --show-progress
retro_mcp_bin=RetroMCP-Java-CLI.jar
modloader=Modloader.jar
modloader_extract_dir=ModLoader

.PHONY: default
default: download setup decompile applypatch

.PHONY: download
download:
	test -f $(retro_mcp_bin) || wget $(wget_flags) -O $(retro_mcp_bin) $(RETRO_MCP_DL)
	test -f $(tmp_dir)/$(modloader) || wget $(wget_flags) -O $(tmp_dir)/$(modloader) $(MODLOADER_DL)
	java -jar $(retro_mcp_bin) setup b1.7.3

.PHONY: setup
setup:
	unzip -d $(tmp_dir)/$(modloader_extract_dir) $(tmp_dir)/$(modloader)
	zip -uj jars/minecraft.jar $(tmp_dir)/$(modloader_extract_dir)/*

.PHONY: decompile
decompile:
	java -jar $(retro_mcp_bin) decompile
	java -jar $(retro_mcp_bin) updatemd5

.PHONY: clean
clean:
	java -jar $(retro_mcp_bin) cleanup
	# Clean working directory
	rm -rf libraries $(MOD_NAME).zip $(retro_mcp_bin) $(tmp_dir)/$(modloader_extract_dir) $(tmp_dir)/$(modloader)

.PHONY: applypatch
applypatch:
	java -jar $(retro_mcp_bin) applypatch

.PHONY: createpatch
createpatch:
	java -jar $(retro_mcp_bin) createpatch

$(MOD_NAME).zip:
	java -jar $(retro_mcp_bin) recompile
	java -jar $(retro_mcp_bin) reobfuscate
	rm -f $(MOD_NAME).zip
	zip -r -j $(MOD_NAME).zip minecraft/reobf
