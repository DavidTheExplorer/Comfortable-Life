package dte.comfortablelife.annoyancehandlers;

import java.util.Arrays;
import java.util.Set;

import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import com.google.common.collect.Sets;

public class EntitiesHandler implements AnnoyanceHandler, Listener
{
	private final Set<EntityType> blacklist;
	private final ModernJavaPlugin plugin;

	public EntitiesHandler(ModernJavaPlugin plugin, EntityType... blacklistedTypes)
	{
		this.plugin = plugin;
		this.blacklist = Sets.newHashSet(blacklistedTypes);
	}
	
	@Override
	public void stop()
	{
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntitySpawn(CreatureSpawnEvent event) 
	{
		if(isBlacklisted(event.getEntity())) 
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChunkLoad(ChunkLoadEvent event) 
	{
		Arrays.stream(event.getChunk().getEntities())
		.filter(this::isBlacklisted)
		.forEach(Entity::remove);
	}

	private boolean isBlacklisted(Entity entity) 
	{
		return this.blacklist.contains(entity.getType());
	}
}
