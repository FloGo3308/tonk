package me.flogo.tonk.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Logger;
import me.flogo.tonk.scene.Scene;
import me.flogo.tonk.settings.Settings;
import me.flogo.tonk.settings.SettingsDevMode;

import java.nio.IntBuffer;

public class Tonk extends ApplicationAdapter {
	public static Tonk INSTANCE;
	public static GameLoop gameLoop;
	public static boolean loading;
	public static boolean devMode = true;
	public static Logger LOGGER = new Logger("logger", devMode ? Logger.DEBUG : Logger.INFO);

	public CameraInputController camController;
	public DirectionalLight directionalLight;
	public Scene currentScene;


	public Tonk() {
	}

	@Override
	public void create () {
		INSTANCE = this;
		if (GameLoop.shouldMakeNewGameLoop()) {
			gameLoop = new GameLoop();
			gameLoop.start();
		}
		currentScene = new Scene();
		currentScene.init();

		currentScene.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		directionalLight = new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
//		directionalLight = new DirectionalShadowLight(1000, 1000).set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
		DirectionalLight directionalLight1 = new DirectionalLight().set(0.01f, 0.01f, 0.01f, 1f, 0.8f, 0.2f);
		currentScene.environment.add(directionalLight);
		currentScene.environment.add(directionalLight1);

		currentScene.cam = new PerspectiveCamera(Settings.fov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		currentScene.cam.position.set(2f, 2f, 2f);
		currentScene.cam.lookAt(0,0,0);
		currentScene.cam.near = 0.1f;
		currentScene.cam.far = 300f;
		currentScene.cam.update();
		camController = new CameraInputController(currentScene.cam);
		Gdx.input.setInputProcessor(camController);

		currentScene.assets.load("models/KV-2_one_object.g3db", Model.class);
		loading = true;

//		ModelBuilder modelBuilder = new ModelBuilder();
//		Model model = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		IntBuffer buffer = BufferUtils.newIntBuffer(16);
		Gdx.gl.glGetIntegerv(GL20.GL_DEPTH_BITS, buffer);
		System.out.println("Depth bits: " + buffer.get());
	}

	private void doneLoading() {
		int size = 1;
		size--;
		Model ship = currentScene.assets.get("models/KV-2_one_object.g3db", Model.class);
		if (devMode && SettingsDevMode.wireframe) {
			for (MeshPart part : ship.meshParts) {
				part.primitiveType = SettingsDevMode.wireframeMode;
			}

		}
		for (float x = -size; x <= size; x += 2f) {
			for (float y = -size; y <= size; y += 2f) {
				for (float z = -size; z <= size; z += 2f) {
					ModelInstance shipInstance = new ModelInstance(ship);
					shipInstance.transform.setToTranslation(x, y, z);
					currentScene.instances.add(shipInstance);
				}
			}
		}
		loading = false;
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.graphics.setTitle("tonk | FPS: " + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		if (loading && currentScene.assets.update())
			doneLoading();
		directionalLight.setDirection(-1f, -0.8f, -0.2f);
		currentScene.render();
	}

	@Override
	public void dispose () {
		currentScene.dispose();
	}

	@Override
	public void resume () {
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {
	}
}
