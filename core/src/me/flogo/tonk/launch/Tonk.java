package me.flogo.tonk.launch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;
import me.flogo.tonk.settings.Settings;
import me.flogo.tonk.settings.SettingsDevMode;

public class Tonk extends ApplicationAdapter {
	public boolean loading;
	public boolean devMode = true;

	public ModelBatch modelBatch;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();

	public PerspectiveCamera cam;
	public CameraInputController camController;
	public Environment environment;
	public DirectionalLight directionalLight;


	public Tonk() {
	}

	@Override
	public void create () {
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		directionalLight = new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
//		directionalLight = new DirectionalShadowLight(1000, 1000).set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
		SpotLight spotlight = new SpotLight().set(1F, 1F, 1F, 5F, 5F, 5F, -1F, -1F, -1F, 1F, 1F, 100F);
		environment.add(directionalLight);
		environment.add(spotlight);

		cam = new PerspectiveCamera(Settings.fov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(2f, 2f, 2f);
		cam.lookAt(0,0,0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		assets = new AssetManager();
		assets.load("models/ship.g3db", Model.class);
		loading = true;

//		ModelBuilder modelBuilder = new ModelBuilder();
//		Model model = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
	}

	private void doneLoading() {
		int size = 4;
		Model ship = assets.get("models/ship.g3db", Model.class);
		if (devMode && SettingsDevMode.wireframeMode) {
			ship.meshParts.get(0).primitiveType = GL20.GL_LINES;

		}
		for (float x = -size; x <= size; x += 2f) {
			for (float y = -size; y <= size; y += 2f) {
				for (float z = -size; z <= size; z += 2f) {
					ModelInstance shipInstance = new ModelInstance(ship);
					shipInstance.transform.setToTranslation(x, y, z);
					instances.add(shipInstance);
				}
			}
		}
		for (ModelInstance instance : instances) {
			System.out.println(instance);
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
		if (loading && assets.update())
			doneLoading();
		directionalLight.setDirection(-1f, -0.8f, -0.2f);
		modelBatch.begin(cam);
//		if (SettingsDevMode.wireframeMode) {
//			Gdx.gl.glLineWidth(SettingsDevMode.wireframeSize);
//		}
		modelBatch.render(instances, environment);
		modelBatch.end();
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
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
