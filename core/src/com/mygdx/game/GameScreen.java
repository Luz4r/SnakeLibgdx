package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {

    private final Learning game;
    private OrthographicCamera camera;
    private Texture snakeBody;
    private Texture snakeTail;
    private Texture snakeHead;
    private Array<Rectangle> snakeParts;
    private boolean isMovingUp = false, isMovingRight = true, isMovingDown = false, isMovingLeft = false;
    private float rotation = 0;
    private float rotationShared = 0;
    private long lastDrawedFrameTime = 0;

    private enum SnakePart {SNAKEHEAD, SNAKETAIL, SNAKEBODY};

    public GameScreen(final Learning game){
        this.game = game;

        snakeBody = new Texture(Gdx.files.internal("snakeBody.png"));
        snakeTail = new Texture(Gdx.files.internal("snakeTail.png"));
        snakeHead = new Texture(Gdx.files.internal("snakeHead.png"));

        Rectangle snakeHeadChunk = new Rectangle();
        Rectangle snakeTailChunk = new Rectangle();
        Rectangle snakeBodyChunk = new Rectangle();

        snakeParts = new Array<Rectangle>();

        snakeParts.add(snakeHeadChunk, snakeTailChunk, snakeBodyChunk);

        snakeParts.get(0).x = 800/2F;
        snakeParts.get(0).y = 480/2F;

        for(int i = 2; i < snakeParts.size; i++){
            if(i == 2){
                snakeParts.get(i).x = snakeParts.get(0).x - 75;
                snakeParts.get(i).y = snakeParts.get(0).y;
            }else {
                snakeParts.get(i).x = snakeParts.get(i-1).x - 75;
                snakeParts.get(i).y = snakeParts.get(i-1).y;
            }
        }
        snakeParts.get(1).x = snakeParts.get(snakeParts.size - 1).x - 75;
        snakeParts.get(1).y = snakeParts.get(snakeParts.size - 1).y;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    private void addNewSnakePart(){
        Rectangle newSnakePart = new Rectangle();
        snakeParts.add(newSnakePart);
        snakeParts.get(snakeParts.size - 1).x = snakeParts.get(1).x;
        snakeParts.get(snakeParts.size - 1).y = snakeParts.get(1).y;
        snakeParts.get(1).x = snakeParts.get(snakeParts.size - 1).x - 75;
        snakeParts.get(1).y = snakeParts.get(snakeParts.size - 1).y;
    }

    private void drawEverySnakePart(){
        for(int i = 0; i < snakeParts.size; i++){
            if( i == 0)
                game.batch.draw(new TextureRegion(snakeHead), changeSnakeChunkX(i), changeSnakeChunkY(i),75/2F, 45/2F, 75, 45,1,1, rotation);
            else if(i == 1)
                game.batch.draw(new TextureRegion(snakeTail), changeSnakeChunkX(i), changeSnakeChunkY(i),75/2F, 45/2F, 75, 45,1,1, rotationShared);
            else
                game.batch.draw(new TextureRegion(snakeBody), changeSnakeChunkX(i), changeSnakeChunkY(i), 75/2F, 45/2F, 75, 45, 1, 1, rotationShared); // <-- draw only one thing per method
        }
    }

    private float changeSnakeChunkX(int j){
        if(j == 0){
            if(isMovingRight) {
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).x += 1;
            }else if(isMovingLeft) {
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).x -= 1;
            }else
                return snakeParts.get(j).x;
        }else{
            if((snakeParts.get(j).x < snakeParts.get(0).x) && (snakeParts.get(j).y == snakeParts.get(0).y)) {
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).x += 1;
            }else if((snakeParts.get(j).x < snakeParts.get(0).x) && (snakeParts.get(j).y != snakeParts.get(0).y)){
                return snakeParts.get(j).x += 1;
            }else if((snakeParts.get(j).x > snakeParts.get(0).x) && (snakeParts.get(j).y == snakeParts.get(0).y)) {
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).x -= 1;
            }else if((snakeParts.get(j).x > snakeParts.get(0).x) && (snakeParts.get(j).y != snakeParts.get(0).y)) {
                return snakeParts.get(j).x -= 1;
            }else
                return snakeParts.get(j).x;
        }
    }

    private float changeSnakeChunkY(int j){
        if(j == 0) {
            if (isMovingUp) {
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).y += 1;
            } else if (isMovingDown) {
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).y -= 1;
            } else
                return snakeParts.get(j).y;
        }else{
            if((snakeParts.get(j).y < snakeParts.get(0).y) && (snakeParts.get(j).x == snakeParts.get(0).x)) {
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).y += 1;
            }else if((snakeParts.get(j).y > snakeParts.get(0).y) && (snakeParts.get(j).x == snakeParts.get(0).x)){
                //lastDrawedFrameTime = TimeUtils.nanoTime();
                return snakeParts.get(j).y -= 1;
            }else
                return snakeParts.get(j).y;
        }
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

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !isMovingRight) {
            isMovingUp = false;
            isMovingRight = false;
            isMovingDown = false;
            isMovingLeft = true;
            rotationShared = rotation;
            rotation = 180;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !isMovingLeft){
            isMovingUp = false;
            isMovingRight = true;
            isMovingDown = false;
            isMovingLeft = false;
            rotationShared = rotation;
            rotation = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isMovingDown){
            isMovingUp = true;
            isMovingRight = false;
            isMovingDown = false;
            isMovingLeft = false;
            rotationShared = rotation;
            rotation = 90;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !isMovingUp){
            isMovingUp = false;
            isMovingRight = false;
            isMovingDown = true;
            isMovingLeft = false;
            rotationShared = rotation;
            rotation = 270;
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        //addNewSnakePart();
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
