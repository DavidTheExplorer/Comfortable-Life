package dte.comfortablelife.entity;

import java.util.Set;

import org.bukkit.entity.EntityType;

public interface EntityService
{
	Set<EntityType> getTreatedTypes();
	void despawnAll();
	void preventNextSpawns();
}
