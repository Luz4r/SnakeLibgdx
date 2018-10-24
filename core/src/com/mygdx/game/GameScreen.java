package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class GameScreen implements Screen {

    final Learning game;
    private OrthographicCamera camera;
    private Texture snakeBody;
    private Texture snakeTail;
    private Texture snakeHead;
    private Rectangle snakeChunk;
    private Array<Rectangle> snakeParts;

    public GameScreen(final Learning game){
        this.game = game;

        snakeBody = new Texture(Gdx.files.internal("snakeBody.png"));
        snakeTail = new Texture(Gdx.files.internal("snakeTail.png"));
        snakeHead = new Texture(Gdx.files.internal("snakeHead.png"));

        snakeChunk = new Rectangle();
        snakeChunk.x = 800/2 - 75/2;
        snakeChunk.y = 480/2 - 45/2;
        snakeChunk.width = 75;
        snakeChunk.height = 45;

        Rectangle snakeHeadChunk = new Rectangle();
        Rectangle snakeTailChunk = new Rectangle();

        snakeParts = new Array<Rectangle>();

        snakeParts.add(snakeHeadChunk, snakeTailChunk);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    private void addNewSnakePart(){
        Rectangle newSnakePart = new Rectangle();
        snakeParts.add(newSnakePart);
    }

    private void drawEverySnakePart(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 150, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin drawing stuff on screen.
        game.batch.begin();
        //game.font.draw(game.batch, ); // <-- draw some text on screen
        game.batch.draw(new TextureRegion(snakeHead), snakeChunk.x, snakeChunk.y, 75/2, 45/2, 75, 45, 1, 1, 0); // <-- draw only one thing per method
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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
        snakeHead.dispose();
        snakeBody.dispose();
        snakeTail.dispose();
    }
}
