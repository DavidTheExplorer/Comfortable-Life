package dte.comfortablelife.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import dte.comfortablelife.ComfortableLife;

public abstract class AbstractSpigotConfig 
{
	private final File file;
	protected final YamlConfiguration config;

	static 
	{
		ComfortableLife.getInstance().getDataFolder().mkdirs();
	}

	protected AbstractSpigotConfig(String fileName) 
	{
		this.file = new File(ComfortableLife.getInstance().getDataFolder(), fileName + ".yml");
		
		if(!this.file.exists())
			ComfortableLife.getInstance().saveResource(this.file.getName(), false);
		
		this.config = YamlConfiguration.loadConfiguration(this.file);

	}
	
	public String getString(String path) 
	{
		return this.config.getString(path);
	}
}