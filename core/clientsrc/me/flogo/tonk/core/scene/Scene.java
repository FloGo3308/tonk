package me.flogo.tonk.core.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import me.flogo.tonk.core.settings.SettingsDevMode;

public class Scene {
    public Environment environment;

    public ModelBatch modelBatch;
    public AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public PerspectiveCamera cam;

    public void init() {
        modelBatch = new ModelBatch();
        environment = new Environment();
        assets = new AssetManager();
    }

    public void render() {
        modelBatch.begin(cam);
        if (SettingsDevMode.wireframe) {
            Gdx.gl.glLineWidth(SettingsDevMode.wireframeSize);
        }
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();
    }
}
