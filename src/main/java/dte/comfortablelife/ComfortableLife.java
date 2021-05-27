package dte.comfortablelife;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.bukkit.entity.EntityType.LLAMA;
import static org.bukkit.entity.EntityType.PHANTOM;
import static org.bukkit.entity.EntityType.WANDERING_TRADER;

import dte.comfortablelife.entity.EntityService;
import dte.comfortablelife.entity.SimpleEntityService;
import dte.comfortablelife.storms.SimpleStormService;
import dte.comfortablelife.storms.StormService;
import me.lucko.helper.Schedulers;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

public class ComfortableLife extends ExtendedJavaPlugin
{
	private StormService stormService;
	private EntityService annoyingMobsService;
	
	@Override
	public void enable()
	{
		saveDefaultConfig();
		setupServices();
		
		int stormStopDelay = getConfig().getInt("Services.Storm.Stop Delay in seconds");
		
		Schedulers.builder()
		.sync()
		.every(stormStopDelay, SECONDS)
		.run(this.stormService::stopAllStorms);
		
		this.annoyingMobsService.despawnAll();
		this.annoyingMobsService.preventNextSpawns();
	}
	
	private void setupServices() 
	{
		//storm
		String stormStoppedMessage = getConfig().getString("Services.Storm.Stopped Message");
		this.stormService = stormStoppedMessage == null ? new SimpleStormService() : new SimpleStormService(colorize(stormStoppedMessage));
		
		//annoying mobs
		this.annoyingMobsService = new SimpleEntityService(WANDERING_TRADER, LLAMA, PHANTOM);
	}
}