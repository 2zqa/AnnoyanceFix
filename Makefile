tmpdir=/tmp
wgetFlags=-q --show-progress

download:
	# Download RetroMCP CLI and Modloader
	wget $(wgetFlags) -O $(tmpdir)/RetroMCP-Java-CLI.jar https://github.com/MCPHackers/RetroMCP-Java/releases/latest/download/RetroMCP-Java-CLI.jar
	wget $(wgetFlags) -O $(tmpdir)/ModLoader.jar https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases/download/v1.0.0/ModLoader.Fix.b1.7.3-1.0.0.jar
	
setup:
	unzip -d $(tmpdir)/ModLoader $(tmpdir)/ModLoader.jar
	mkdir $(tmpdir)/RetroMCP
	cd $(tmpdir)/RetroMCP && java -jar $(tmpdir)/RetroMCP-Java-CLI.jar setup b1.7.3
	zip -uj $(tmpdir)/RetroMCP/jars/minecraft.jar $(tmpdir)/ModLoader/*
	cd $(tmpdir)/RetroMCP && java -jar $(tmpdir)/RetroMCP-Java-CLI.jar decompile
	cd $(tmpdir)/RetroMCP && java -jar $(tmpdir)/RetroMCP-Java-CLI.jar updatemd5 # Good so far
	cp -nr $(tmpdir)/RetroMCP/minecraft/src .

clean:
	cd $(tmpdir) && rm -rf RetroMCP ModLoader RetroMCP-Java-CLI.jar ModLoader.jar
