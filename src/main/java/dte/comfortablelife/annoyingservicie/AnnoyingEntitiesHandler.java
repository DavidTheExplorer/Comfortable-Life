package dte.comfortablelife.annoyingservicie;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.google.common.collect.Sets;

import dte.comfortablelife.ComfortableLife;

public class AnnoyingEntitiesHandler implements AnnoyanceHandler, Listener
{
	private final Set<EntityType> blacklist;

	public AnnoyingEntitiesHandler(EntityType... blacklistedTypes) 
	{
		this.blacklist = Sets.newHashSet(blacklistedTypes);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntitySpawn(CreatureSpawnEvent event) 
	{
		if(shouldDespawn(event.getEntityType())) 
			event.setCancelled(true);
	}

	@Override
	public void stopAnnoyance()
	{
		//despawn current annoying entities
		Bukkit.getWorlds().stream()
		.flatMap(world -> world.getEntities().stream())
		.filter(entity -> shouldDespawn(entity.getType()))
		.forEach(Entity::remove);

		//prevent next entities from spawning
		Bukkit.getPluginManager().registerEvents(this, ComfortableLife.getInstance());
	}

	private boolean shouldDespawn(EntityType entityType) 
	{
		return this.blacklist.contains(entityType);
	}
}
