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
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
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

			final DirectionalLight light = new DirectionalLight(0, 0, -1);
			light.setPower(1f);

			final Material material = new Material();
			material.enableLighting(true);
			material.setColor(new float[] { 0.8f, 0.2f, 0.2f, 1f });
			material.setDiffuseMethod(new DiffuseMethod.Lambert());

			final Cube cube = new Cube(1f);
			cube.setMaterial(material);

			final RotateAnimation3D rotateAnim = new RotateAnimation3D(new Vector3(0.8d, 0.5d, 0.4d), 360d);
			rotateAnim.setDuration(6000);
			rotateAnim.setRepeatMode(RepeatMode.INFINITE);
			rotateAnim.setTransformable3D(cube);
			rotateAnim.play();

			getCurrentScene().addLight(light);
			registerAnimation(rotateAnim);
			addChild(cube);
		}

	}

}
