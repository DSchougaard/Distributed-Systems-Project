package Model.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Weapon {
	float initX;
	float initY;
	float time=0;
	public Circle body;
	public Body pBody;
	float damage;
	Vector3 force;
	public Color color= Color.MAGENTA;
	
	public void init(Vector3 startPos, float radius, Vector3 force){
		this.force=force;
		initX=startPos.x;
		initY=startPos.y;
		body=new Circle(initX, initY, radius);
		
	}
	
	public void calcPosition(float delta){
		time+=delta;
		body.x=force.x*time+initX;
		body.y=(float) (-0.5*10.*Math.pow(time, 2.)+force.y*time+initY);
		
	}
}
