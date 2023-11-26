package space.rock;

public class SpaceGame extends BaseGame
{
    public void create()
    {
        super.create();         // to create InputMultiplexer and InputProcessor
        setActiveScreen( new LevelScreen() );
    }
}