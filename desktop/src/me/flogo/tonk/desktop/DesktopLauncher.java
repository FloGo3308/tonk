package me.flogo.tonk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.flogo.tonk.launch.LaunchType;
import me.flogo.tonk.launch.Settings;
import me.flogo.tonk.launch.Tonk;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = Settings.vSync;
		config.foregroundFPS = Settings.fpsLimit;
		config.backgroundFPS = Settings.fpsLimit;
		new LwjglApplication(new Tonk(LaunchType.DESKTOP), config);
	}
}
