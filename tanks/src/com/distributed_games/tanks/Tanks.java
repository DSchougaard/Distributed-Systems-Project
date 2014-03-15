package com.distributed_games.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tanks extends Game {
	protected SpriteBatch batch;
	protected float ScreenWidth=0;
	protected float ScreenHeight=0;

    public BitmapFont font;
	// constant useful for logging
	public static final String LOG = Tanks.class.getSimpleName();

	// a libgdx helper class that logs the current FPS each second
	private FPSLogger fpsLogger;

	@Override
	public void create() {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        this.setScreen(new MainMenuScreen(this));
		fpsLogger = new FPSLogger();

	}


	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log( Tanks.LOG, "Disposing game" );
        batch.dispose();
        font.dispose();
	}

	@Override
	public void render() {		
		super.render();
		// output the current FPS
		fpsLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Gdx.app.log( Tanks.LOG, "Resizing game to: " + width + " x " + height );
	}


	/**
	 * Called when game looses focus
	 */
	@Override
	public void pause() {
		super.pause();
		Gdx.app.log( Tanks.LOG, "Pausing game" );
	}

	/**
	 * Called when game gains focus
	 */
	@Override
	public void resume() {
		super.resume();
		Gdx.app.log( Tanks.LOG, "Resuming game" );
	}


}
