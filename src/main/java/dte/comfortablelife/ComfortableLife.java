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
	private static ComfortableLife INSTANCE;

	@Override
	public void onEnable()
	{
		INSTANCE = this;

		saveDefaultConfig();

		parseActiveHandlers().forEach(AnnoyanceHandler::stop);
	}

	public static ComfortableLife getInstance() 
	{
		return INSTANCE;
	}

	private List<AnnoyanceHandler> parseActiveHandlers()
	{
		AnnoyanceHandlerFactory handlerFactory = new AnnoyanceHandlerFactory(getConfig());
		
		return Stream.of(handlerFactory.parseEntitiesHandler(), handlerFactory.parseStormsHandler())
				.filter(Objects::nonNull) //null means the handler was disabled in the config
				.collect(toList());
	}
}