package Model.Game;

import Control.Game.MyContactListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Physics{

	public World world = new World(new Vector2(0, -100), true);  
	Box2DDebugRenderer debugRenderer;  
	static final float BOX_STEP=1/60f;  
	static final int BOX_VELOCITY_ITERATIONS=8;  
	static final int BOX_POSITION_ITERATIONS=3; 
	public Vector2 movementVector= new Vector2();
	protected int width=80;
	protected int height=48;
	public Array<Tank> tanks;
	public final int tankWidth=3;
	public final int tankHeight=3;



	public Physics() {
 
		//Ground body  
		BodyDef groundBodyDef =new BodyDef();  
		groundBodyDef.position.set(new Vector2(0, 1));  
		Body groundBody = world.createBody(groundBodyDef);  
		ChainShape groundBox = new ChainShape();
		groundBox.createChain(new Vector2[]{new Vector2(0,7.5f),new Vector2(width,7.5f)});
		groundBody.createFixture(groundBox, 0.0f);  
		
		//Dynamic Body Circle Test 
		BodyDef bodyDef = new BodyDef();  
		bodyDef.type = BodyType.DynamicBody;  
		bodyDef.position.set(width / 2, height / 2);  
		Body body = world.createBody(bodyDef);  
		CircleShape dynamicCircle = new CircleShape();  
		dynamicCircle.setRadius(1f);  
		FixtureDef fixtureDef = new FixtureDef();  
		fixtureDef.shape = dynamicCircle;  
		fixtureDef.density = 1.0f;  
		fixtureDef.friction = 0.0f;  
		fixtureDef.restitution = 1;  
		body.createFixture(fixtureDef);


		initTanks();

		world.setContactListener(new MyContactListener());
		
	}

	public void initTanks(){


		tanks = new Array<Tank>();
		Tank tank1 = new Tank(0-tankWidth/2.f+5.f);
		tanks.add(tank1);
		Tank tank2 = new Tank(width-tankWidth/2.f-5.f);
		tanks.add(tank2);
		// load the images for the tanks, 30x30 pixels each
		tank1.sprite = new Sprite(new Texture(Gdx.files.internal("img/tank1.png")));
		tank2.sprite = new Sprite(new Texture(Gdx.files.internal("img/tank2.png")));


		Vector3 center=new Vector3(width/2.f,height/2.f,0);
		
		
		
		//Dynamic Tank Body  
		for(Tank tank: tanks){
			//sprite
			tank.sprite.setSize(tankWidth, tankHeight);
			tank.sprite.setOrigin(tank.sprite.getWidth()/2.f, tank.sprite.getHeight()/2.f);
			tank.color=Color.BLACK;

			//physic body
			BodyDef bodyDef2 = new BodyDef();  
			bodyDef2.type = BodyType.DynamicBody;  
			bodyDef2.position.set(tank.startPosX,10);  
			tank.pBody= world.createBody(bodyDef2);
			tank.pBody.setUserData(tank);
			PolygonShape dynamicBox = new PolygonShape();  
			dynamicBox.setAsBox(tankWidth/2.f, tankHeight/2.f);
			FixtureDef fixtureDef = new FixtureDef();  
			fixtureDef.shape = dynamicBox;  
			fixtureDef.density = .30f;  
			fixtureDef.friction = 0.75f;  
			fixtureDef.restitution = 0.1f;  
			tank.pBody.createFixture(fixtureDef);
			
			//gun
			tank.gunLength=4;
			tank.calcGunVector(center);
		}

	}
	
	public void createWeapon(Tank tank, Vector3 touchPos){
		tank.calcGunVector(touchPos);
		Weapon weapon= new Weapon();

		BodyDef bodyDef2 = new BodyDef();  
		bodyDef2.type = BodyType.DynamicBody;  
		bodyDef2.position.set(tank.getCenter().x+tank.getGunVector().x,tank.getCenter().y+tank.getGunVector().y);  
		weapon.pBody= world.createBody(bodyDef2);
		weapon.pBody.setUserData(weapon);

		CircleShape Weapon = new CircleShape();  
		Weapon.setRadius(0.5f);

		FixtureDef fixtureDef = new FixtureDef();  
		fixtureDef.shape = Weapon;  
		fixtureDef.density = .01f;  
		fixtureDef.friction = 0.75f;  
		fixtureDef.restitution = 0.1f;  
		weapon.pBody.createFixture(fixtureDef);
		weapon.pBody.applyForceToCenter(tank.getGunVector().x*20.f,tank.getGunVector().y*20.f, true);
	}

	
	public void step(float delta) {
		

		for(Tank tank: tanks){
			tank.pBody.applyForceToCenter(movementVector, true);
		}
		//physics
		world.step(BOX_STEP/2, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);  
		world.step(BOX_STEP/2, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		
	}


	
	public void dispose() {
		for(Tank tank: tanks){
			tank.dispose();
		}
	}
}
