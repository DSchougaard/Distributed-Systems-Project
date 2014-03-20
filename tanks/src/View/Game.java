package View;

import Control.Game.MyInputProcessor;
import Model.Game.Tank;
import Model.Game.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Game implements Screen{
	Tanks game;
	protected int width=80;
	protected int height=48;
	protected OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	ShapeRenderer shapeRenderer;
	Physics physics;
	
	public Game(final Tanks game, Physics physics){
		this.physics=physics;
		this.game=game;
		shapeRenderer=new ShapeRenderer();
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		
		
		
		Gdx.input.setInputProcessor(new MyInputProcessor(physics.movementVector));
		debugRenderer = new Box2DDebugRenderer();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		for (Tank tank : physics.tanks) {
			tank.sprite.setPosition(tank.pBody.getPosition().x-physics.tankWidth/2.f, tank.pBody.getPosition().y-physics.tankHeight/2.f);
			tank.sprite.setRotation(tank.pBody.getAngle()* MathUtils.radiansToDegrees);
			tank.sprite.draw(game.batch);
		}
		game.batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl20.glLineWidth(20);
		for (Tank tank : physics.tanks) {

			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(tank.color);
			shapeRenderer.line(tank.getCenter(),tank.getCenter().add(tank.getGunVector()));
			shapeRenderer.end();

		}
	
		Gdx.gl20.glLineWidth(1);
	
		physics.step(delta);
		//user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			for (Tank tank : physics.tanks) {
				physics.createWeapon(tank, touchPos);

			}
		}

		debugRenderer.render(physics.world, camera.combined);
		
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
		// TODO Auto-generated method stub
		
	}

}
