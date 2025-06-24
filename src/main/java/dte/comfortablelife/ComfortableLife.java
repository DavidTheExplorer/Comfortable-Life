package dte.comfortablelife;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import dte.comfortablelife.annoyancehandlers.AnnoyanceHandler;
import dte.comfortablelife.annoyancehandlers.factory.AnnoyanceHandlerFactory;
import dte.modernjavaplugin.ModernJavaPlugin;

public class ComfortableLife extends ModernJavaPlugin
{
	@Override
	public void onEnable()
	{
		saveDefaultConfig();

		parseActiveHandlers().forEach(AnnoyanceHandler::stop);
	}

	private List<AnnoyanceHandler> parseActiveHandlers()
	{
		AnnoyanceHandlerFactory handlerFactory = new AnnoyanceHandlerFactory(getConfig(), this);
		
		return Stream.of(handlerFactory.parseEntitiesHandler(), handlerFactory.parseStormsHandler())
				.filter(Objects::nonNull) //null means the handler was disabled in the config
				.collect(toList());
	}
}