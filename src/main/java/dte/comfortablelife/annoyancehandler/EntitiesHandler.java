package dte.comfortablelife.annoyancehandler;

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

public class EntitiesHandler implements AnnoyanceHandler
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
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler(priority = EventPriority.HIGHEST)
			public void preventSpawn(CreatureSpawnEvent event)
			{
				if(isBlacklisted(event.getEntity()))
					event.setCancelled(true);
			}

			@EventHandler(priority = EventPriority.HIGHEST)
			public void despawnOffChunk(ChunkLoadEvent event)
			{
				Arrays.stream(event.getChunk().getEntities())
						.filter(entity -> isBlacklisted(entity))
						.forEach(Entity::remove);
			}
		}, this.plugin);
	}

	private boolean isBlacklisted(Entity entity) 
	{
		return this.blacklist.contains(entity.getType());
	}
}
