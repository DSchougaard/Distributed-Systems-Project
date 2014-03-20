package com.distributed_games.tanks;

import View.Tanks;
import Control.Game.MyContactListener;
import Control.Game.MyInputProcessor;
import Model.Game.Tank;
import Model.Game.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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

public class mixPhysics implements Screen {

	World world = new World(new Vector2(0, -100), true);  
	Box2DDebugRenderer debugRenderer;  
	static final float BOX_STEP=1/60f;  
	static final int BOX_VELOCITY_ITERATIONS=8;  
	static final int BOX_POSITION_ITERATIONS=3; 
	Vector2 movementVector= new Vector2();
	protected int width=80;
	protected int height=48;
	protected OrthographicCamera camera;
	protected Array<Tank> tanks;
	final int tankWidth=3;
	final int tankHeight=3;
	ShapeRenderer shapeRenderer;
	Tanks game;


	public mixPhysics(final Tanks game){
		this.game=game;
		shapeRenderer=new ShapeRenderer();
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		//        camera.update();  
		//Ground body  
		BodyDef groundBodyDef =new BodyDef();  
		groundBodyDef.position.set(new Vector2(0, 1));  
		Body groundBody = world.createBody(groundBodyDef);  
		ChainShape groundBox = new ChainShape();
		groundBox.createChain(new Vector2[]{new Vector2(0,8.5f),new Vector2(width,8.5f)});
		groundBody.createFixture(groundBox, 0.0f);  
		
		//Dynamic Body Circle Test 
		BodyDef bodyDef = new BodyDef();  
		bodyDef.type = BodyType.DynamicBody;  
		bodyDef.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2);  
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



		debugRenderer = new Box2DDebugRenderer();  
		Gdx.input.setInputProcessor(new MyInputProcessor(movementVector));
		world.setContactListener(new MyContactListener());
	}

	public void initTanks(){


		tanks = new Array<Tank>();
		Tank tank1 = new Tank(0);
		tanks.add(tank1);
		Tank tank2 = new Tank(0);
		tanks.add(tank2);
		// load the images for the tanks, 30x30 pixels each
		tank1.sprite = new Sprite(new Texture(Gdx.files.internal("img/tank1.png")));
		tank2.sprite = new Sprite(new Texture(Gdx.files.internal("img/tank2.png")));


		// create a Rectangle to logically represent the tank
		tank1.body = new Rectangle();
		tank1.body.width = tankWidth;
		tank1.body.height = tankWidth;
		tank1.body.x = 0-tankWidth/2.f+5.f;
		tank1.body.y = 10;

		tank2.body = new Rectangle();
		tank2.body.width = tankWidth;
		tank2.body.height = tankHeight;
		tank2.body.x = width-tankWidth/2.f-5.f;
		tank2.body.y = 10;

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
			bodyDef2.position.set(tank.getCenter().x,tank.getCenter().y);  
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

	@Override
	public void render(float delta) {
		//user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			for (Tank tank : tanks) {
				createWeapon(tank, touchPos);

			}
		}
		
		//physics
		world.step(BOX_STEP/2, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);  
		world.step(BOX_STEP/2, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		

		//rendering
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		for (Tank tank : tanks) {
			tank.sprite.setPosition(tank.pBody.getPosition().x-tankWidth/2.f, tank.pBody.getPosition().y-tankHeight/2.f);
			tank.sprite.setRotation(tank.pBody.getAngle()* MathUtils.radiansToDegrees);
			tank.sprite.draw(game.batch);
		}
		game.batch.end();

		for(Tank tank: tanks){
			tank.pBody.applyForceToCenter(movementVector, true);
		}
		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl20.glLineWidth(20);
		for (Tank tank : tanks) {

			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(tank.color);
			shapeRenderer.line(tank.getCenter(),tank.getCenter().add(tank.getGunVector()));
			shapeRenderer.end();

		}
		Gdx.gl20.glLineWidth(1);
		debugRenderer.render(world, camera.combined);  
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		for(Tank tank: tanks){
			tank.dispose();
		}
	}
}
