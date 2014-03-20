package Control.Menu;

import Model.Game.Physics;
import View.Game;
import View.Tanks;

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
			Physics physics= new Physics();
			game.setScreen(new Game(game, physics));
			menu.dispose();
			break;

		default:
			break;
		}

	}
}
