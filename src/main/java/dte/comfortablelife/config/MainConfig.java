package dte.comfortablelife.config;

public class MainConfig extends AbstractSpigotConfig
{
	public MainConfig()
	{
		super("config");
	}
	
	public String getMessagesPrefix() 
	{
		return this.config.getString("Messages Prefix");
	}
	
	public String getStormStoppedMessage() 
	{
		return getMessage("Services.Storm.Stopped Message");
	}
	
	public String getMessage(String path) 
	{
		String messagesPrefix = getMessagesPrefix();
		String message = this.config.getString(path);
		
		return String.format("%s %s", messagesPrefix, message);
	}
}
