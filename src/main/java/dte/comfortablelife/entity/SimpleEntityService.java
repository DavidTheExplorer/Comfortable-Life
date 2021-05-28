package dte.comfortablelife.entity;

import static me.lucko.helper.event.filter.EventHandlers.cancel;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.google.common.collect.Sets;

import me.lucko.helper.Events;

public class SimpleEntityService implements EntityService
{
	private final Set<EntityType> treatedTypes;
	
	public SimpleEntityService(EntityType... treatedTypes) 
	{
		this.treatedTypes = Sets.newHashSet(treatedTypes);
	}
	
	@Override
	public void preventNextSpawns() 
	{
		Bukkit.getWorlds().stream()
		.flatMap(world -> world.getEntities().stream())
		.filter(this::shouldTreat)
		.forEach(Entity::remove);
	}
	
	@Override
	public void despawnAll()
	{
		Events.subscribe(CreatureSpawnEvent.class)
		.filter(this::shouldTreat)
		.handler(cancel());
	}
	
	@Override
	public boolean shouldTreat(EntityType entityType) 
	{
		return this.treatedTypes.contains(entityType);
	}
	
	@Override
	public Set<EntityType> getTreatedTypes() 
	{
		return new HashSet<>(this.treatedTypes);
	}
}
