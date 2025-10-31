package dte.comfortablelife.annoyancestopper;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import static java.util.stream.Collectors.toList;

public class EntityBlacklistStopper implements AnnoyanceStopper
{
	private final Set<EntityType> blacklist;
	private final ModernJavaPlugin plugin;

	public EntityBlacklistStopper(Set<EntityType> blacklist, ModernJavaPlugin plugin)
	{
		this.blacklist = EnumSet.copyOf(blacklist);
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
