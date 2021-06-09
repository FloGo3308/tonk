package me.flogo.tonk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.flogo.tonk.settings.Settings;
import me.flogo.tonk.main.Tonk;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = Settings.vSync;
		config.foregroundFPS = Settings.fpsLimit;
		config.backgroundFPS = Settings.fpsLimit;
//		config.addIcon("tonk_logo_new.png", Files.FileType.Internal);
		new LwjglApplication(new Tonk(), config);
	}
}
