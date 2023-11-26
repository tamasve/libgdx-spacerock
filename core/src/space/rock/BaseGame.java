package space.rock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;    // class to handle discrete user input: a central dispatcher

/**
 * Part of Game FW   implemented during creating Starfish Collector game
 * The BaseGame class is mainly responsible for storing a static reference to the Game object initialized by the
 * Launcher class so that the Screen-derived classes can easily access and switch the currently active screen.
 * Another task is handling discrete user input -
 * for this set the InputMultiplexer as main InputProcessor, to which BaseScreen will add the stages.
 * 07/01/2022 - last: 11/01/2022
 */

public abstract class BaseGame extends Game
{
    private static BaseGame game;       // a reference to itself

    public BaseGame()
    {
        game = this;
    }

    public void create()
    {
        // prepare for multiple classes/stages to receive discrete input
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor( im );
    }

    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);      // create a reference to the currently active screen
    }
}
