package com.distributed_games.tanks;

import game.Physics;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ButtonListner extends ClickListener{
	Tanks game;
	MainMenuScreen menu;
	String screen;
	public ButtonListner(Tanks game, MainMenuScreen menu, String screen) {
		super();
		this.screen=screen;
		this.game=game;
		this.menu=menu;
	}
	
	@Override
	public void clicked (InputEvent event, float x, float y) {
		
		switch (screen) {
		case "Tanks":
			
			game.setScreen(new TankGame(game));
			menu.dispose();
			break;
			
		case "Physics":
			
			game.setScreen(new Physics(game));
			menu.dispose();
			break;
		case "Raining":
			
			game.setScreen(new Raining(game));
			menu.dispose();
			break;
		default:
			break;
		}

	}
}
