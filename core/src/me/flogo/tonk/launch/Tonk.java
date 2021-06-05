package me.flogo.tonk.launch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import javafx.util.Pair;

import java.util.ArrayList;

public class Tonk extends ApplicationAdapter {
	public static LaunchType launchType;
	public ModelBatch modelBatch;
	public PerspectiveCamera cam;
	public CameraInputController camController;
	public Environment environment;
	public DirectionalLight light;
	public ArrayList<Pair<Model, ModelInstance>> models = new ArrayList<>();

	public Tonk(LaunchType launchType) {
		this.launchType = launchType;
	}

	@Override
	public void create () {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		light = new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
		SpotLight spotlight = new SpotLight().set(1F, 1F, 1F, 5F, 5F, 5F, -1F, -1F, -1F, 1F, 1F, 100F);
		environment.add(light);
		environment.add(spotlight);
		modelBatch = new ModelBatch();

		cam = new PerspectiveCamera(Settings.fov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		ModelBuilder modelBuilder = new ModelBuilder();
		Model model1 = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		Model model2 = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		models.add(new Pair(model1, new ModelInstance(model1)));
		models.add(new Pair(model2, new ModelInstance(model2)));
	}

	@Override
	public void render () {
		Gdx.graphics.setTitle("tonk | FPS: " + Gdx.graphics.getFramesPerSecond());
		float x = (float)Math.sin(Gdx.graphics.getFrameId()/50F);
		float z = (float)Math.cos(Gdx.graphics.getFrameId()/50F);
//		System.out.println(x);
//		System.out.println(z);
		light.setDirection(-1f, -0.8f, -0.2f);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		for (Pair<Model, ModelInstance> model : models) {
			modelBatch.render(model.getValue(), environment);
		}
		modelBatch.end();
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		for (Pair<Model, ModelInstance> model : models) {
			model.getKey().dispose();
		}
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
