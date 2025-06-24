package dte.comfortablelife;

import dte.comfortablelife.annoyancehandlers.AnnoyanceHandler;
import dte.comfortablelife.annoyancehandlers.AnnoyanceHandlerProvider;
import dte.modernjavaplugin.ModernJavaPlugin;

public class ComfortableLife extends ModernJavaPlugin
{
	@Override
	public void onEnable()
	{
		saveDefaultConfig();

		stopAnnoyances();
	}

	private void stopAnnoyances()
	{
		AnnoyanceHandlerProvider provider = new AnnoyanceHandlerProvider(getConfig(), this);

		provider.getHandlers().forEach(AnnoyanceHandler::stop);
	}
}