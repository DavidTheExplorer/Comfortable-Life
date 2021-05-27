package dte.comfortablelife.entity;

import static me.lucko.helper.event.filter.EventHandlers.cancel;

import java.util.Collections;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.google.common.collect.Sets;

import me.lucko.helper.Events;

public class SimpleEntityService implements EntityService
{
	private final Set<EntityType> entityTypes;
	
	public SimpleEntityService(EntityType... entityTypes) 
	{
		this.entityTypes = Sets.newHashSet(entityTypes);
	}
	
	@Override
	public Set<EntityType> getTreatedTypes() 
	{
		return Collections.unmodifiableSet(this.entityTypes);
	}
	
	@Override
	public void preventNextSpawns() 
	{
		Bukkit.getWorlds().stream()
		.flatMap(world -> world.getEntities().stream())
		.filter(entity -> this.entityTypes.contains(entity.getType()))
		.forEach(Entity::remove);
	}
	
	@Override
	public void despawnAll() 
	{
		Events.subscribe(CreatureSpawnEvent.class)
		.filter(event -> this.entityTypes.contains(event.getEntityType()))
		.handler(cancel());
	}
}
