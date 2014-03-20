package View;

import Control.Game.MyInputProcessor;
import Model.Game.Physics;
import Model.Game.Tank;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Game implements Screen{
	Tanks game;
	protected int width=80;
	protected int height=48;
	protected OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	ShapeRenderer shapeRenderer;
	Physics physics;
	Music rainMusic;
	
	public Game(final Tanks game, Physics physics){
		this.physics=physics;
		this.game=game;
		shapeRenderer=new ShapeRenderer();
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		
		
		
		Gdx.input.setInputProcessor(new MyInputProcessor(camera,physics));
		debugRenderer = new Box2DDebugRenderer();
		
		
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);
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
	
		

		debugRenderer.render(physics.world, camera.combined);
		physics.step(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		rainMusic.play();
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
		rainMusic.dispose();
		
	}

}
