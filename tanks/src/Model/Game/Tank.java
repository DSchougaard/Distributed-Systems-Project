package Model.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Tank {
	 public Texture image;
	 public Rectangle body;
	 private Vector3 gunVector;
	 public float gunLength=40;
	 public Body pBody;
	 public Sprite sprite;
	 public Color color = Color.WHITE;
	 
	 float startPosX=0;
	 
	 
	 public Tank(float startPosX) {
		 this.startPosX=startPosX;
		// TODO Auto-generated constructor stub
	}
	 

	 public void calcGunVector(Vector3 coordinates){

		 gunVector=  new Vector3((new Vector3(coordinates)).sub(getCenter()).nor().scl(gunLength));
	 }
	 public Vector3 getGunVector(){
		 return new Vector3(gunVector);
	 }

	 public Vector3 getCenter(){
		 return new Vector3(pBody.getPosition().x,pBody.getPosition().y,0);
	 }
	 
	
	public void dispose() {
		sprite.getTexture().dispose();
	}
}
