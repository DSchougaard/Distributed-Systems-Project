package game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Tank {
	 public Texture image;
	 public Rectangle body;
	 public Vector3 position;
	 private Vector3 gunVector;
	 private Vector3 center;
	 public float gunLength=40;
	 
	 public Color color = Color.WHITE;
	 
	 
	 public Tank() {
		 
		// TODO Auto-generated constructor stub
	}
	 
	 
	 public Vector3 calcCenter(){
		 center = new Vector3(body.x+body.width/2,body.y+body.height/2,0);
		 return new Vector3(center);
		
	 }
	 
	 public void calcGunVector(Vector3 coordinates){

		 gunVector=  new Vector3((new Vector3(coordinates)).sub(calcCenter()).nor().scl(gunLength));
	 }
	 public Vector3 getGunVector(){
		 return new Vector3(gunVector);
	 }

	 public Vector3 getCenter(){
		 return new Vector3(center);
	 }
}
