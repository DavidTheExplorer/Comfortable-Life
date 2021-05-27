package dte.comfortablelife.storms;

import org.bukkit.World;

public class SimpleStormService implements StormService
{
	private String stormStoppedMessage;
	
	public SimpleStormService(){}
	public SimpleStormService(String stormStoppedMessage) 
	{
		this.stormStoppedMessage = stormStoppedMessage;
	}
	
	@Override
	public void stopStormAt(World world) 
	{
		world.setStorm(false);
		
		if(this.stormStoppedMessage != null)
			world.getPlayers().forEach(player -> player.sendMessage(this.stormStoppedMessage));
	}
}