package space.rock;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Main screen of Space Rocks game
 * 09/01/2022 - last: 11/01/2022
 */

public class LevelScreen extends BaseScreen
{
    private SpaceShip spaceship;        // the main character
    private boolean gameOver;           // game over flag

    @Override
    public void initialize()
    {
        BaseActor space = new BaseActor(0,0, mainStage);    // space background
        space.loadTexture( "space.png" );
        space.setSize(800,600);
        BaseActor.setWorldBounds(space);            // set bounds to space image

        spaceship = new SpaceShip(400,300, mainStage);      // spaceship

        new Rock(600,500, mainStage);       // rocks
        new Rock(600,300, mainStage);
        new Rock(600,100, mainStage);
        new Rock(400,100, mainStage);
        new Rock(200,100, mainStage);
        new Rock(200,300, mainStage);
        new Rock(200,500, mainStage);
        new Rock(400,500, mainStage);

        gameOver = false;
    }

    @Override
    public void update(float dt)
    {
        // any rock overlaps with spaceship?
        for (BaseActor rockActor : BaseActor.getList(mainStage, "Rock"))
        {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {

                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(spaceship);

                    spaceship.remove();             // removed from stage but still can be overlap therefore the minus coords
                    spaceship.setPosition(-1000, -1000);

                    BaseActor messageLose = new BaseActor(0,0, uiStage);    // Lose...
                    messageLose.loadTexture("message-lose.png");
                    messageLose.centerAtPosition(400,300);
                    messageLose.setOpacity(0);
                    messageLose.addAction( Actions.fadeIn(1) );

                    gameOver = true;

                } else {
                    spaceship.shieldPower -= 34;
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    rockActor.remove();
                }
            }
            // any rock is hit by a laser?
            for (BaseActor laserActor : BaseActor.getList(mainStage, "Laser")) {
                if (laserActor.overlaps(rockActor)) {
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    laserActor.remove();
                    rockActor.remove();
                }
            }
        }
        // no game over and no rocks anymore -> win!
        if ( !gameOver && BaseActor.count(mainStage, "Rock") == 0 )     // Win!
        {
            BaseActor messageWin = new BaseActor(0,0, uiStage);
            messageWin.loadTexture("message-win.png");
            messageWin.centerAtPosition(400,300);
            messageWin.setOpacity(0);
            messageWin.addAction( Actions.fadeIn(1) );
            gameOver = true;
        }

    }


    @Override
    public boolean keyDown(int keycode)     // random warp by pressing 'X', shoot laser for hitting space
    {
        if ( keycode == Keys.X )
            spaceship.warp();
        if ( keycode == Keys.SPACE )
            spaceship.shoot();
        return false;
    }
}