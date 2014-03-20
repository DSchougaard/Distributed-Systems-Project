package Control.Game;
import Model.Game.Tank;
import Model.Game.Physics;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MyInputProcessor implements InputProcessor {

	Physics physics;
	OrthographicCamera camera;
	public MyInputProcessor(OrthographicCamera camera, Physics physics){
		this.camera=camera;
		this.physics=physics;
	}
		
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		Vector3 touchPos = new Vector3();
		touchPos.set(screenX, screenY, 0);
		camera.unproject(touchPos);
		for (Tank tank : physics.tanks) {
			physics.createWeapon(tank, touchPos);

		}
		System.out.println(screenX+"."+screenY);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		physics.movementVector.x=0;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch (keycode) {
		case Keys.D:

			physics.movementVector.x=500;
			break;
		case Keys.A:

			physics.movementVector.x=-500;
			break;

		default:
			break;
		}
		return true;

	}

}
