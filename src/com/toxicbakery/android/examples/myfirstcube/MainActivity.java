package com.toxicbakery.android.examples.myfirstcube;

import rajawali.RajawaliActivity;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

@SuppressLint("InlinedApi")
public class MainActivity extends RajawaliActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Enable Immersive mode
		if (Build.VERSION.SDK_INT >= 18)
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		super.onCreate(savedInstanceState);

		final MyRenderer renderer = new MyRenderer(getApplicationContext());
		renderer.setSurfaceView(mSurfaceView);
		setRenderer(renderer);
	}

	private static final class MyRenderer extends RajawaliRenderer {

		public MyRenderer(Context context) {
			super(context);
		}

		@Override
		protected void initScene() {
			super.initScene();

			/*
			 * Lights provide depth and realism to a scene. Several lights exist in Rajawali for you
			 * to use; Directional, Point, and Spot.
			 */
			final DirectionalLight light = new DirectionalLight(0, 0, -1);
			light.setPower(1f);

			/*
			 * Materials define GLES shaders to be used on one or more objects. Shaders are composed
			 * of two parts, a Vertex Shader and a Fragment Shader. Vertex shaders are applied to a
			 * model before fragment shaders. Vertex shaders can modify the shape of a model or
			 * define broad model appearances such as a solid color. Fragment shaders, commonly
			 * known as pixel shaders, allow modification of individual pixels that make up a model.
			 * Fragment shaders are necessary for properly lighting objects, blur effects, phong,
			 * cartoon, and many more effects.
			 * 
			 * Rajawali provides many simple yet useful shader functions out of the box. For
			 * advanced usage, custom shaders can be written and loaded from code or by Android
			 * resources.
			 */
			final Material material = new Material();
			material.enableLighting(true);
			material.setColor(new float[] { 0.8f, 0.2f, 0.2f, 1f });
			material.setDiffuseMethod(new DiffuseMethod.Lambert());

			/*
			 * Cube is a primitive shape provided by the engine defining the dimensions of the model
			 * from a single parameter defining a size of the cube.
			 */
			final Cube cube = new Cube(1f);
			cube.setMaterial(material);

			/*
			 * Rajawali provides several base animation classes that provide per frame updates of
			 * models. Animation classes require a transformable to manipulate and also to be
			 * registered with the scene which owns the transformable object. This provides fine
			 * grained control backed by automated control of only progressing animations for active
			 * scenes.
			 */
			final RotateAnimation3D rotateAnim = new RotateAnimation3D(
					new Vector3(0.8d, 0.5d, 0.4d), 360d);
			rotateAnim.setDuration(6000);
			rotateAnim.setRepeatMode(RepeatMode.INFINITE);
			rotateAnim.setTransformable3D(cube);
			rotateAnim.play();

			/*
			 * Boiler plate code for adding the object to the desired scene, adding the light, and
			 * registering the animation as previously mentioned.
			 */
			getCurrentScene().addLight(light);
			registerAnimation(rotateAnim);
			addChild(cube);
		}

	}

}
