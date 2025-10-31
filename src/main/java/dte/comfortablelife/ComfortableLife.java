package dte.comfortablelife;

import dte.comfortablelife.annoyancestopper.AnnoyanceStopper;
import dte.comfortablelife.annoyancestopper.EntityBlacklistStopper;
import dte.comfortablelife.annoyancestopper.StormStopper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class ComfortableLife extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		saveDefaultConfig();
        stopAnnoyances();
	}

    private void stopAnnoyances()
    {
        Stream.of(parseEntityBlacklistStopper(), parseStormStopper())
                .filter(Optional::isPresent) //empty optional means the stopper was disabled in the config
                .map(Optional::get)
                .forEach(AnnoyanceStopper::stop);
    }

    private Optional<AnnoyanceStopper> parseEntityBlacklistStopper()
    {
        return getActiveStopperSection("entity-blacklist")
                .map(section ->
                {
                    Set<EntityType> blacklist = section.getStringList("list").stream()
                            .map(typeName ->
                            {
                                try
                                {
                                    return EntityType.valueOf(typeName.toUpperCase().replace(' ', '_'));
                                }
                                catch(IllegalArgumentException exception)
                                {
                                    getLogger().severe(String.format("Couldn't blacklist \"%s\" because no such mob exists, it will just be skipped.", typeName));
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .collect(toSet());

                    return new EntityBlacklistStopper(blacklist, this);
                });
    }

    private Optional<AnnoyanceStopper> parseStormStopper()
    {
        return getActiveStopperSection("storm")
                .map(unused -> new StormStopper(this));
    }

    private Optional<ConfigurationSection> getActiveStopperSection(String name)
    {
        ConfigurationSection section = getConfig().getConfigurationSection("stoppers." + name);

        //return null if the stopper is disabled
        if(!section.getBoolean("active"))
            return Optional.empty();

        return Optional.of(section);
    }
}