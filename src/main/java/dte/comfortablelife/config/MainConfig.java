package dte.comfortablelife.config;

public class MainConfig extends AbstractSpigotConfig
{
	public MainConfig()
	{
		super("config");
	}
	
	public String getStormStoppedMessage() 
	{
		return getString("Services.Storm.Stopped Message");
	}
}
