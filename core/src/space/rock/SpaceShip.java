package space.rock;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;

public class SpaceShip extends BaseActor
{
    private Thrusters thrusters;        // engine fire will be attached
    private Shield shield;              // shield will be attached
    public int shieldPower;             // = opacity value

    public SpaceShip(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture( "spaceship.png" );

        setAcceleration(200);
        setMaxSpeed(100);
        setDeceleration(10);

        setBoundaryPolygon(8);

        thrusters = new Thrusters(0,0, s);      // initialize engine fire then attach it...
        addActor(thrusters);                      // Group works...
        // position: behind the spaceship and center vertically
        thrusters.setPosition(-thrusters.getWidth(), getHeight()/2 - thrusters.getHeight()/2 );

        shield = new Shield(0,0, s);        // initialize shield
        addActor(shield);
        shield.centerAtPosition( getWidth()/2, getHeight()/2 );
        shieldPower = 100;
    }


    public void act(float dt)
    {
        super.act( dt );

        float degreesPerSecond = 120;       // rotation speed
        if (Gdx.input.isKeyPressed(Keys.LEFT))      // direction of rotation
            rotateBy(degreesPerSecond * dt);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            rotateBy(-degreesPerSecond * dt);

        if (Gdx.input.isKeyPressed(Keys.UP))        // accelerate in rotation angle + show engine fire - else: hide fire
        {
            accelerateAtAngle( getRotation() );
            thrusters.setVisible(true);
        }
        else
        {
            thrusters.setVisible(false);
        }

        shield.setOpacity(shieldPower / 100f);      // set opacity of shield acc. to shield power
        if (shieldPower <= 0)
            shield.setVisible(false);

        applyPhysics(dt);       // acceleration -> velocity -> movement

        wrapAroundWorld();      // goes out at one side, comes in at the opposite...
    }


    public void warp()          // random teleportation
    {
        if ( getStage() == null )   return;

        Warp warp1 = new Warp(0,0, this.getStage());    // 1st warp effect at current location
        warp1.centerAtActor(this);

        setPosition(MathUtils.random(800), MathUtils.random(600));      // spaceship to a random pos. + 2nd warp effect there
        Warp warp2 = new Warp(0,0, this.getStage());
        warp2.centerAtActor(this);
    }


    public void shoot()     // shoot laser
    {
        if ( getStage() == null )   return;

        Laser laser = new Laser(0,0, this.getStage());
        laser.centerAtActor(this);
        laser.setRotation( this.getRotation() );
        laser.setMotionAngle( this.getRotation() );
    }

}