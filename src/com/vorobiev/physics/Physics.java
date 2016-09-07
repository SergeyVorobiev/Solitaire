package com.vorobiev.physics;

public class Physics {
	public static float[] getDxDy(float fromX, float fromY, float toX, float toY, float speed) {
		float[] dxy = new float[2];
		float dx = toX - fromX;
		float dy = toY - fromY;
		if(dy == 0) {
			dxy[0] = speed;
			dxy[1] = 0;
		} else {
			float ratio = Math.abs(dx) / Math.abs(dy);
			dxy[1] = speed / (float)Math.sqrt(ratio * ratio + 1);
			dxy[0] = dxy[1] * ratio;
		}
		
		if(dx < 0)dxy[0] *= -1;
		if(dy < 0)dxy[1] *= -1;
		return dxy;
	}
}
