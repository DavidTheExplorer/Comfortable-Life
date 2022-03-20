package dte.comfortablelife.annoyingservicie;

import org.bukkit.entity.EntityType;

public interface EntityService extends AnnoyingService
{
	boolean shouldDespawn(EntityType entity);
}
