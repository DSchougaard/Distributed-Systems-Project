package Control.Game;
import Model.Game.Tank;
import Model.Game.Weapon;
import View.Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
public class MyContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Body a=contact.getFixtureA().getBody();
		Body b=contact.getFixtureB().getBody();
		
	
		if(b.getUserData() instanceof Tank && a.getUserData() instanceof Weapon){
//			b.getWorld().destroyBody(b);
			Gdx.app.log( Tanks.LOG, "hit" );
		}else if(a.getUserData() instanceof Tank && b.getUserData() instanceof Weapon){
			Gdx.app.log( Tanks.LOG, "hit2" );
//			a.getWorld().destroyBody(a);
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
