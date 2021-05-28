package dte.comfortablelife;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;
import static org.bukkit.entity.EntityType.LLAMA;
import static org.bukkit.entity.EntityType.PHANTOM;
import static org.bukkit.entity.EntityType.WANDERING_TRADER;

import java.util.concurrent.TimeUnit;

import dte.comfortablelife.config.MainConfig;
import dte.comfortablelife.entity.EntityService;
import dte.comfortablelife.entity.SimpleEntityService;
import dte.comfortablelife.storms.SimpleStormService;
import dte.comfortablelife.storms.StormService;
import me.lucko.helper.Schedulers;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

public class ComfortableLife extends ExtendedJavaPlugin
{
	private EntityService annoyingMobsService;
	private StormService stormService;
	
	private MainConfig mainConfig;
	
	private static ComfortableLife INSTANCE;
	
	@Override
	public void enable()
	{
		INSTANCE = this;
		
		this.mainConfig = new MainConfig();
		
		//setup annoying mobs service
		this.annoyingMobsService = new SimpleEntityService(WANDERING_TRADER, LLAMA, PHANTOM);
		this.annoyingMobsService.despawnAll();
		this.annoyingMobsService.preventNextSpawns();
		
		
		//setup storm service
		this.stormService = newStormService();
		
		int stormStopDelay = getConfig().getInt("Services.Storm.Stop Delay in seconds");
		
		Schedulers.builder()
		.sync()
		.every(stormStopDelay, TimeUnit.SECONDS)
		.run(this.stormService::stopAllStorms);
	}
	
	public static ComfortableLife getInstance() 
	{
		return INSTANCE;
	}
	private StormService newStormService() 
	{
		String stormStoppedMessage = this.mainConfig.getStormStoppedMessage();
		
		return stormStoppedMessage == null ? new SimpleStormService() : new SimpleStormService(colorize(stormStoppedMessage));
	}
}