RETRO_MCP_DL=https://github.com/MCPHackers/RetroMCP-Java/releases/latest/download/RetroMCP-Java-CLI.jar
MODLOADER_DL=https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases/download/v1.0.0/ModLoader.Fix.b1.7.3-1.0.0.jar
MOD_NAME=AnnoyanceFix

tmp_dir=/tmp
wget_flags=-q --show-progress
retro_mcp_bin=RetroMCP-Java-CLI.jar

.PHONY: default
default: download applymodloader decompile applypatch

.PHONY: download
download:
	wget $(wget_flags) -O $(retro_mcp_bin) $(RETRO_MCP_DL)
	wget $(wget_flags) -O $(tmp_dir)/modloader.jar $(MODLOADER_DL)
	java -jar $(retro_mcp_bin) setup b1.7.3

.PHONY: applymodloader
applymodloader:
	mkdir $(tmp_dir)/modloader
	cd $(tmp_dir)/modloader && jar -xvf ../modloader.jar
	mv jars/minecraft.jar $(tmp_dir)/modloader
	cd $(tmp_dir)/modloader && jar -uf minecraft.jar *
	mv $(tmp_dir)/modloader/minecraft.jar jars/

.PHONY: decompile
decompile:
	java -jar $(retro_mcp_bin) decompile
	java -jar $(retro_mcp_bin) updatemd5

.PHONY: clean
clean:
	java -jar $(retro_mcp_bin) cleanup
	# Clean working directory
	rm -rf libraries $(MOD_NAME).zip $(retro_mcp_bin) $(tmp_dir)/modloader $(tmp_dir)/modloader.jar

.PHONY: applypatch
applypatch:
	java -jar $(retro_mcp_bin) applypatch

.PHONY: patch
patch:
	java -jar $(retro_mcp_bin) createpatch

.PHONY: $(MOD_NAME).zip
$(MOD_NAME).zip:
	java -jar $(retro_mcp_bin) recompile
	java -jar $(retro_mcp_bin) reobfuscate
	rm -f $(MOD_NAME).zip
	zip -r -j $(MOD_NAME).zip minecraft/reobf
