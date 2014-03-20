package com.distributed_games.tanks;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class helper {
	static float circleDistanceX; 
	static float circleDistanceY;
	static float cornerDistance_sq;
	public static boolean overlapse(Circle circle, Rectangle rect){
		
		circleDistanceX = Math.abs(circle.x - rect.x);
		circleDistanceY = Math.abs(circle.y - rect.y);

		if (circleDistanceX > (rect.width/2 + circle.radius)) { return false; }
		if (circleDistanceY > (rect.height/2 + circle.radius)) { return false; }

		if (circleDistanceX <= (rect.width/2)) { return true; } 
		if (circleDistanceY <= (rect.height/2)) { return true; }

		cornerDistance_sq = (float) (Math.pow(circleDistanceX - rect.width/2.,2) + Math.pow(circleDistanceY - rect.height/2.,2));

		return (cornerDistance_sq <= (Math.pow(circle.radius,2)));
	}
}
