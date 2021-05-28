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
	public String getMessage(String path) 
	{
		String messagesPrefix = getMessagesPrefix();
		
		if(messagesPrefix == null)
			return null;
		
		String message = this.config.getString(path);
		
		if(message == null)
			return null;
		
		return String.format("%s%s", messagesPrefix, message);
	}
	public String getStormStoppedMessage() 
	{
		return getMessage("Services.Storm.Stopped Message");
	}
}
