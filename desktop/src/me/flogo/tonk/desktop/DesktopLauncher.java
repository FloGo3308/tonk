package me.flogo.tonk.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.flogo.tonk.main.Tonk;
import me.flogo.tonk.settings.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//		config.setBackBufferConfig(8,8,8,8,24,0,0);
		config.setWindowedMode(1280, 720);
//		config.width = 800;
//		config.height = 450;
		config.useVsync(Settings.vSync);
		config.setForegroundFPS(Settings.fpsLimit);
		config.setIdleFPS(60);
		config.setWindowIcon("tonk_logo_new.png");
		new Lwjgl3Application(new Tonk(), config);
	}
}
