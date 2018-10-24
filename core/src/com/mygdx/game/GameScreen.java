package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {

    final Learning game;
    private OrthographicCamera camera;
    private Texture snakeBody;
    private Texture snakeTail;
    private Texture snakeHead;
    private Rectangle snakeChunk;
    private Array<Rectangle> snakeParts;
    private boolean isMovingUp = false, isMovingRight = false, isMovingDown = false, isMovingLeft = false;
    private float rotation = 0;
    private long lastDrawedFrameTime = 0;

    private enum SnakePart {SNAKEHEAD, SNAKETAIL, SNAKEBODY};

    public GameScreen(final Learning game){
        this.game = game;

        snakeBody = new Texture(Gdx.files.internal("snakeBody.png"));
        snakeTail = new Texture(Gdx.files.internal("snakeTail.png"));
        snakeHead = new Texture(Gdx.files.internal("snakeHead.png"));

        snakeChunk = new Rectangle();
        snakeChunk.x = 800/2;
        snakeChunk.y = 480/2;
        snakeChunk.width = 75;
        snakeChunk.height = 45;

        Rectangle snakeHeadChunk = new Rectangle();
        Rectangle snakeTailChunk = new Rectangle();
        Rectangle snakeBodyChunk = new Rectangle();

        snakeParts = new Array<Rectangle>();

        snakeParts.add(snakeHeadChunk, snakeTailChunk, snakeBodyChunk);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    private void addNewSnakePart(){
        Rectangle newSnakePart = new Rectangle();
        snakeParts.add(newSnakePart);
    }

    private void drawEverySnakePart(){
        game.batch.draw(new TextureRegion(snakeHead), modifySnakeChunkX(SnakePart.SNAKEHEAD, 1), modifySnakeChunkY(SnakePart.SNAKEHEAD, 1),75/2, 45/2, 75, 45,1,1, rotation);
        for(int i = 2; i < snakeParts.size; i++){
            game.batch.draw(new TextureRegion(snakeBody), modifySnakeChunkX(SnakePart.SNAKEBODY, i), modifySnakeChunkY(SnakePart.SNAKEBODY, i), 75/2, 45/2, 75, 45, 1, 1, rotation); // <-- draw only one thing per method
        }
        game.batch.draw(new TextureRegion(snakeTail), modifySnakeChunkX(SnakePart.SNAKETAIL, 1), modifySnakeChunkY(SnakePart.SNAKETAIL, 1),75/2, 45/2, 75, 45,1,1,rotation);
    }

    private float modifySnakeChunkX(SnakePart whichPart, int j){
        switch(whichPart) {
            case SNAKEHEAD:
                if(isMovingRight && (TimeUtils.nanoTime() - lastDrawedFrameTime) > 40000000) {
                    lastDrawedFrameTime = TimeUtils.nanoTime();
                    return snakeChunk.x += 100 * Gdx.graphics.getDeltaTime();
                }else if(isMovingLeft && (TimeUtils.nanoTime() - lastDrawedFrameTime) > 40000000) {
                    lastDrawedFrameTime = TimeUtils.nanoTime();
                    return snakeChunk.x -= 100 * Gdx.graphics.getDeltaTime();
                }else
                    return snakeChunk.x;
            case SNAKETAIL:
                if (rotation == 0)
                    return snakeChunk.x - (75 * (snakeParts.size - 1));
                else if(rotation == 180)
                    return snakeChunk.x + (75 * (snakeParts.size - 1));
                else
                    return snakeChunk.x;
            case SNAKEBODY:
            if (rotation == 0)
                return snakeChunk.x - (75 * (j - 1));
            else if(rotation == 180)
                return snakeChunk.x + (75 * (j - 1));
            else
                return snakeChunk.x;
        }
        return 0;
    }

    private float modifySnakeChunkY(SnakePart whichPart, int j){
        switch(whichPart) {
            case SNAKEHEAD:
                if(isMovingUp && (TimeUtils.nanoTime() - lastDrawedFrameTime) > 40000000){
                    lastDrawedFrameTime = TimeUtils.nanoTime();
                    return snakeChunk.y += 100 * Gdx.graphics.getDeltaTime();
                }else if(isMovingDown && (TimeUtils.nanoTime() - lastDrawedFrameTime) > 40000000) {
                    lastDrawedFrameTime = TimeUtils.nanoTime();
                    return snakeChunk.y -= 100 * Gdx.graphics.getDeltaTime();
                }else{
                    return snakeChunk.y;
                }
            case SNAKETAIL:
                if(rotation == 90)
                    return snakeChunk.y - (75 *(snakeParts.size - 1));
                else if(rotation == 270)
                    return snakeChunk.y + (75 *(snakeParts.size - 1));
                else
                    return snakeChunk.y;
            case SNAKEBODY:
            if(rotation == 90)
                return snakeChunk.y - (75 * (j - 1));
            else if(rotation == 270)
                return snakeChunk.y + (75 * (j - 1));
            else
                return snakeChunk.y;
        }
        return 0;
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
        drawEverySnakePart();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        addNewSnakePart();
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
