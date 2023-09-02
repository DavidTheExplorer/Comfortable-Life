package dte.comfortablelife.annoyingservicie;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.google.common.collect.Sets;

import dte.comfortablelife.ComfortableLife;

public class SimpleEntityService implements AnnoyingService, Listener
{
	private final Set<EntityType> treatedTypes;

	public SimpleEntityService(EntityType... treatedTypes) 
	{
		this.treatedTypes = Sets.newHashSet(treatedTypes);
	}
	
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) 
	{
		if(shouldDespawn(event.getEntityType())) 
			event.setCancelled(true);
	}

	@Override
	public void despawnAll()
	{
		Bukkit.getWorlds().stream()
		.flatMap(world -> world.getEntities().stream())
		.filter(entity -> shouldDespawn(entity.getType()))
		.forEach(Entity::remove);
	}

	@Override
	public void preventNextSpawns() 
	{
		Bukkit.getPluginManager().registerEvents(this, ComfortableLife.getInstance());
	}

	private boolean shouldDespawn(EntityType entityType) 
	{
		return this.treatedTypes.contains(entityType);
	}
}
