package me.flogo.tonk.client.launch;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.flogo.tonk.core.main.Tonk;
import me.flogo.tonk.core.settings.Settings;
import org.lwjgl.glfw.GLFW;

public class ClientLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setBackBufferConfig(8,8,8,8,24,0,0);
//		config.setWindowedMode(1280, 720);
		config.setWindowedMode(128*4, 72*4);
//		config.width = 800;
//		config.height = 450;
		config.useVsync(Settings.vSync);
		config.setForegroundFPS(GLFW.GLFW_DONT_CARE);
		config.setIdleFPS(60);
		config.setWindowIcon("tonk_logo_new.png");
		new Lwjgl3Application(new Tonk(), config);
	}
}
