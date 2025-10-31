package dte.comfortablelife.annoyancehandler;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import static java.util.stream.Collectors.toList;

public class EntitiesHandler implements AnnoyanceHandler
{
	private final Set<EntityType> blacklist;
	private final ModernJavaPlugin plugin;

	public EntitiesHandler(Set<EntityType> blacklist, ModernJavaPlugin plugin)
	{
		this.blacklist = blacklist;
		this.plugin = plugin;
	}

	@Override
	public void stop()
	{
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () ->
        {
            for(World world : Bukkit.getWorlds())
            {
                if(world.getPlayers().isEmpty())
                    return;

                getBlacklistedEntitiesAt(world).forEach(Entity::remove);
            }
        }, 0, 5);
	}

    private List<Entity> getBlacklistedEntitiesAt(World world)
    {
        return Arrays.stream(world.getLoadedChunks())
                .flatMap(chunk -> Arrays.stream(chunk.getEntities()))
                .filter(entity -> this.blacklist.contains(entity.getType()))
                .collect(toList());
    }
}
