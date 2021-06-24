package me.flogo.tonk.server;

import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class ServerLauncher {
	public static void main (String[] arg) {
		HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
//		config.setBackBufferConfig(8,8,8,8,24,0,0);
//		config.setWindowedMode(1280, 720);
		System.out.println(config.maxNetThreads);
		System.out.println(config.preferencesDirectory);
		System.out.println(config.updatesPerSecond);
//		new Lwjgl3Application(new Tonk(), config);
	}
}
