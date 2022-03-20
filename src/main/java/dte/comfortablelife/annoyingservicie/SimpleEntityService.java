package dte.comfortablelife.annoyingservicie;

import static me.lucko.helper.event.filter.EventHandlers.cancel;

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
		Events.subscribe(CreatureSpawnEvent.class)
		.filter(event -> shouldDespawn(event.getEntityType()))
		.handler(cancel());
	}

	@Override
	public boolean shouldDespawn(EntityType entityType) 
	{
		return this.treatedTypes.contains(entityType);
	}
}
