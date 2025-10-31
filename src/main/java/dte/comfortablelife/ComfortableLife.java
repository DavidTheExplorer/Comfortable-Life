package dte.comfortablelife;

import dte.comfortablelife.annoyancehandler.AnnoyanceHandler;
import dte.comfortablelife.annoyancehandler.EntityBlacklistHandler;
import dte.comfortablelife.annoyancehandler.StormHandler;
import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.bukkit.ChatColor.RED;

public class ComfortableLife extends ModernJavaPlugin
{
	@Override
	public void onEnable()
	{
		saveDefaultConfig();
        handleAnnoyances();
	}

    private void handleAnnoyances()
    {
        Stream.of(parseEntityBlacklistHandler(), parseStormHandler())
                .filter(Optional::isPresent) //empty optional means the handler was disabled in the config
                .map(Optional::get)
                .forEach(AnnoyanceHandler::stop);
    }

    private Optional<AnnoyanceHandler> parseEntityBlacklistHandler()
    {
        return getActiveHandlerSection("entity")
                .map(section ->
                {
                    Set<EntityType> blacklist = section.getStringList("blacklist").stream()
                            .map(typeName ->
                            {
                                try
                                {
                                    return EntityType.valueOf(typeName.toUpperCase().replace(' ', '_'));
                                }
                                catch(IllegalArgumentException exception)
                                {
                                    logToConsole(RED + String.format("Couldn't blacklist \"%s\" because such mob doesn't exist!", typeName));
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .collect(toSet());

                    return new EntityBlacklistHandler(blacklist, this);
                });
    }

    private Optional<AnnoyanceHandler> parseStormHandler()
    {
        return getActiveHandlerSection("storm")
                .map(unused -> new StormHandler(this));
    }

    private Optional<ConfigurationSection> getActiveHandlerSection(String handlerName)
    {
        ConfigurationSection section = getConfig().getConfigurationSection("handlers." + handlerName);

        //return null if the handler is disabled
        if(!section.getBoolean("active"))
            return Optional.empty();

        return Optional.of(section);
    }
}