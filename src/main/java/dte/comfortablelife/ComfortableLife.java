package dte.comfortablelife;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;

import org.bukkit.entity.EntityType;

import com.google.common.collect.Lists;

import dte.comfortablelife.annoyingservicie.AnnoyingService;
import dte.comfortablelife.annoyingservicie.EntityService;
import dte.comfortablelife.annoyingservicie.SimpleEntityService;
import dte.comfortablelife.annoyingservicie.SimpleStormService;
import dte.comfortablelife.annoyingservicie.StormService;
import dte.comfortablelife.config.MainConfig;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

public class ComfortableLife extends ExtendedJavaPlugin
{
	private static ComfortableLife INSTANCE;

	@Override
	public void enable()
	{
		INSTANCE = this;

		for(AnnoyingService service : Lists.newArrayList(createEntityService(), newStormService())) 
		{
			service.despawnAll();
			service.preventNextSpawns();
		}
	}

	public static ComfortableLife getInstance() 
	{
		return INSTANCE;
	}
	
	private EntityService createEntityService() 
	{
		return new SimpleEntityService(EntityType.WANDERING_TRADER, EntityType.LLAMA, EntityType.PHANTOM);
	}
	
	private StormService newStormService() 
	{
		MainConfig mainConfig = new MainConfig();
		
		String stormStoppedMessage = colorize(mainConfig.getStormStoppedMessage());
		int stormStopDelay = getConfig().getInt("Services.Storm.Stop Delay in seconds");

		return new SimpleStormService(stormStoppedMessage, stormStopDelay);
	}
}