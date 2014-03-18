package com.distributed_games.tanks;

import game.Tank;
import game.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.distributed_games.helper.helper;

public class TankGame implements Screen {
	protected final Tanks game;
    float physicStepSize = 0.1f;
    protected Array<Tank> tanks;
    protected Array<Weapon> weapons;
    Texture tank1img, tank2img;
    Rectangle tank1,tank2;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Sound explosion;
    Music rainMusic;
   protected OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    protected int width=800;
    protected int height=480;
    final int tankWidth=30;
    final int tankHeight=30;
    long deltaTime;
    ShapeRenderer shapeRenderer;

    public TankGame(final Tanks game) {
    	shapeRenderer = new ShapeRenderer();
    	deltaTime=0;
        this.game = game;
        game.font.setColor(Color.WHITE);
       
        weapons=new Array<Weapon>(); 
        
        tanks = new Array<Tank>();
        Tank tank1 = new Tank();
        tanks.add(tank1);
        Tank tank2 = new Tank();
        tanks.add(tank2);
        // load the images for the tanks, 30x30 pixels each
        tank1.image = new Texture(Gdx.files.internal("img/tank1.png"));
        tank2.image= new Texture(Gdx.files.internal("img/tank2.png"));
        tank1.color=Color.BLACK;
        tank2.color=Color.BLACK;
        
     // create a Rectangle to logically represent the bucket
        tank1.body = new Rectangle();
        tank1.body.width = tankWidth;
        tank1.body.height = tankWidth;
        tank1.body.x = 0-tankWidth/2+50; // center the bucket horizontally
        tank1.body.y = 20; // bottom left corner of the bucket is 20 pixels above
                        // the bottom screen edge
        
        tank2.body = new Rectangle();
        tank2.body.width = tankWidth;
        tank2.body.height = tankHeight;
        tank2.body.x = width-tankWidth/2-50; // center the bucket horizontally
        tank2.body.y = 20; // bottom left corner of the bucket is 20 pixels above
                        // the bottom screen edge
        
        Vector3 center=new Vector3(800/2,480/2,0);
        tank1.calcGunVector(center);
        tank2.calcGunVector(center);
        
        
        // load the drop sound effect and the rain background "music"
        explosion = Gdx.audio.newSound(Gdx.files.internal("firework.wav"));        
        
//        // load the images for the droplet and the bucket, 64x64 pixels each
//        dropImage = new Texture(Gdx.files.internal("droplet.png"));
//        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

//        // load the drop sound effect and the rain background "music"
//        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
//        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
//        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

//        // create a Rectangle to logically represent the bucket
//        bucket = new Rectangle();
//        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
//        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
//                        // the bottom screen edge
//        bucket.width = 64;
//        bucket.height = 44;
//
//        // create the raindrops array and spawn the first raindrop
//        raindrops = new Array<Rectangle>();
//        spawnRaindrop();

    }

//    private void spawnRaindrop() {
//        Rectangle raindrop = new Rectangle();
//        raindrop.x = MathUtils.random(0, 800 - 64);
//        raindrop.y = 480;
//        raindrop.width = 64;
//        raindrop.height = 64;
//        raindrops.add(raindrop);
//        lastDropTime = TimeUtils.nanoTime();
//    }

    @Override
    public void render(float delta) {
    	physics(delta);
    	
    	userInput(delta);
    	
        draw(delta);
        
        
        


    }

    
    public void physics(float delta){
    	
    	
    	
    	for(Weapon weapon: weapons){
    		weapon.calcPosition(physicStepSize);
    		for(Tank tank: tanks){
    			
    			if(helper.overlapse(weapon.body,tank.body)){
    				tanks.removeValue(tank,false);
    				explosion.play();
    			}
    		}
    	}
    	
    	
    	
//      // check if we need to create a new raindrop
//      if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
//          spawnRaindrop();
//
//      // move the raindrops, remove any that are beneath the bottom edge of
//      // the screen or that hit the bucket. In the later case we increase the 
//      // value our drops counter and add a sound effect.
//      Iterator<Rectangle> iter = raindrops.iterator();
//      while (iter.hasNext()) {
//          Rectangle raindrop = iter.next();
//          raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
//          if (raindrop.y + 64 < 0)
//              iter.remove();
//          if (raindrop.overlaps(bucket)) {
//              dropsGathered++;
//              dropSound.play();
//              iter.remove();
//          }
//      }
    	
    }
    
    public void userInput(float delta){
        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

//            Gdx.app.log( Tanks.LOG, "touch "+ touchPos);
            for (Tank tank : tanks) {
            	tank.calcGunVector(touchPos);
            	Weapon w= new Weapon();
            	w.init(tank.getCenter().add(tank.getGunVector()) , 5, tank.getGunVector().scl(2));
            	weapons.add(w);
//            	Gdx.app.log( Tanks.LOG, "center "+ tank.calcCenter());
//     		    Gdx.app.log( Tanks.LOG, "gunpoint "+ tank.center.add(tank.gunVector));
//     		   Gdx.app.log( Tanks.LOG, "gunpoint "+ tank.calcGunVector(touchPos));
            }
            
      
        }

    	
    	
    }
    
    public void draw(float delta){
    	
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
//        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
//        game.batch.draw(bucketImage, bucket.x, bucket.y);
//        for (Rectangle raindrop : raindrops) {
//            game.batch.draw(dropImage, raindrop.x, raindrop.y);
//        }
        for (Tank tank : tanks) {
            game.batch.draw(tank.image, tank.body.x, tank.body.y);
        }
        game.batch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
	    Gdx.gl20.glLineWidth(20);
        for (Tank tank : tanks) {

		    shapeRenderer.begin(ShapeType.Line);
		    shapeRenderer.setColor(tank.color);
		    shapeRenderer.line(tank.calcCenter(),tank.getCenter().add(tank.getGunVector()));
		    shapeRenderer.end();
		    
      }
        
    	for(Weapon weapon: weapons){
    		shapeRenderer.begin(ShapeType.Filled);
		    shapeRenderer.setColor(weapon.color);
		    shapeRenderer.circle(weapon.body.x, weapon.body.y, weapon.body.radius);
		    shapeRenderer.end();
    	}

    }
    
    
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
//        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}
