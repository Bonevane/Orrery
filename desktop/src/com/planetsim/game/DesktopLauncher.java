package com.planetsim.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.planetsim.game.PlanetSim;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Planet Simulator");
		config.setWindowedMode(1800, 900);
		config.useVsync(true);
		config.setBackBufferConfig(1,1,1,1,1,1,2);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new PlanetSim(), config);
	}
}
