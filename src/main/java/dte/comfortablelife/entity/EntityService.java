package dte.comfortablelife.entity;

import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityEvent;

public interface EntityService
{
	void despawnAll();
	void preventNextSpawns();
	boolean shouldTreat(EntityType entity);
	Set<EntityType> getTreatedTypes();
	
	default boolean shouldTreat(EntityEvent event) 
	{
		return shouldTreat(event.getEntityType());
	}
	default boolean shouldTreat(Entity entity) 
	{
		return shouldTreat(entity.getType());
	}
}
