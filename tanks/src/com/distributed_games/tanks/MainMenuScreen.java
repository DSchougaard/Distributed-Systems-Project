package com.distributed_games.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenuScreen implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton play,play2,play3;
//	private Label heading;
    final Tanks game;
    Sprite sprite;
    OrthographicCamera camera;
    
    TextureAtlas atlas;
	
	public MainMenuScreen(final Tanks game) {
        this.game = game;
        camera = new OrthographicCamera();

        camera.setToOrtho(false);
        Texture.setEnforcePotImages(false);
        Texture background = new Texture("img/tanks.png");
        
        sprite = new Sprite(background);
        sprite.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        stage =new Stage();
        Gdx.input.setInputProcessor(stage);
        
        
        atlas = new TextureAtlas("ui/Button.pack");
        skin=new Skin(atlas);
        table= new Table(skin);
        
        table.setBounds(0, 0, Gdx.graphics.getWidth()/2-30, Gdx.graphics.getHeight()/3);
        
        
        TextButtonStyle textButtonstyle= new TextButtonStyle();
        textButtonstyle.up =skin.getDrawable("button.normal");
        textButtonstyle.over=skin.getDrawable("button.hover");
        textButtonstyle.down=skin.getDrawable("button.pressed");
        textButtonstyle.pressedOffsetX=1;
        textButtonstyle.pressedOffsetY=-1;
        textButtonstyle.font= game.font;
        
        play =new TextButton("Tanks", textButtonstyle);
        play.addListener(new ButtonListner(game, this, "Tanks"));
      
        play2 =new TextButton("Raining", textButtonstyle);  
        play2.addListener(new ButtonListner(game, this, "Raining"));
        
        play3 =new TextButton("Physics", textButtonstyle);
        play3.addListener(new ButtonListner(game, this, "Physics"));
        
        
        table.add(play);;
        table.row();
        table.add(play2);;
        table.add(play3);
        table.debug();
        stage.addActor(table);
        

	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       
        stage.act();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        sprite.draw(game.batch);
        game.font.draw(game.batch, "Welcome to Tanks!!! ", 100, 150);
        
        game.batch.end();
        stage.draw();
//        Table.drawDebug(stage);

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
		sprite.getTexture().dispose();
		
	}

}
