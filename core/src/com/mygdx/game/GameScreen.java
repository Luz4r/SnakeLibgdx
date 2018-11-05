package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {

    private final Snake SNAKE;
    private final Learning GAME;
    private OrthographicCamera camera;
    private boolean isMovingUp = false,
            isMovingRight = true,
            isMovingDown = false,
            isMovingLeft = false;
    private float rotation = 0; // 0 >, 90 /\, 180 <, 270 \/
    private long timeSinceLastRender = 0;
    private int speedOfSnake = 64; // how fast will SNAKE move on screen

    GameScreen(final Learning game){
        this.GAME = game;

        SNAKE = new Snake(GAME.screenWidth, GAME.screenHeight);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME.screenWidth, GAME.screenHeight);
    }

    private void drawEverySnakePart(){
            if((TimeUtils.nanoTime() - timeSinceLastRender) > 1000000000) {
                for(int i = SNAKE.snakeParts.size - 1; i > 0; i--){
                    SNAKE.snakeParts.get(i).x = SNAKE.snakeParts.get(i - 1).x;
                    SNAKE.snakeParts.get(i).y = SNAKE.snakeParts.get(i - 1).y;
                }
                updateSnakeHead();
                timeSinceLastRender = TimeUtils.nanoTime();
            }
        for(int i = 0; i < SNAKE.snakeParts.size; i++){
            if(i == 0)
                GAME.batch.draw(new TextureRegion(SNAKE.snakeHead), SNAKE.snakeParts.get(i).x, SNAKE.snakeParts.get(i).y,64/2F, 64/2F, 64, 64,1,1, rotation);
            else if(i == SNAKE.snakeParts.size - 1)
                GAME.batch.draw(new TextureRegion(SNAKE.snakeTail), SNAKE.snakeParts.get(i).x, SNAKE.snakeParts.get(i).y,64/2F, 64/2F, 64, 64,1,1, rotation);
            else
                GAME.batch.draw(new TextureRegion(SNAKE.snakeBody), SNAKE.snakeParts.get(i).x, SNAKE.snakeParts.get(i).y); // <-- draw only one thing per method
        }
    }

    private void updateSnakeHead(){
        if (isMovingRight)
            SNAKE.snakeParts.get(0).x += speedOfSnake;
        else if (isMovingLeft)
            SNAKE.snakeParts.get(0).x += -speedOfSnake;
        else if (isMovingUp)
            SNAKE.snakeParts.get(0).y += speedOfSnake;
        else if (isMovingDown)
            SNAKE.snakeParts.get(0).y += -speedOfSnake;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 150, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        GAME.batch.setProjectionMatrix(camera.combined);

        // begin drawing stuff on screen.
        GAME.batch.begin();
        //GAME.font.draw(GAME.batch, ); // <-- draw some text on screen
        drawEverySnakePart();
        GAME.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !isMovingRight) {
            isMovingUp = false;
            isMovingRight = false;
            isMovingDown = false;
            isMovingLeft = true;
            rotation = 180;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !isMovingLeft){
            isMovingUp = false;
            isMovingRight = true;
            isMovingDown = false;
            isMovingLeft = false;
            rotation = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isMovingDown){
            isMovingUp = true;
            isMovingRight = false;
            isMovingDown = false;
            isMovingLeft = false;
            rotation = 90;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && !isMovingUp){
            isMovingUp = false;
            isMovingRight = false;
            isMovingDown = true;
            isMovingLeft = false;
            rotation = 270;
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        SNAKE.addNewSnakePart(2);
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
        SNAKE.snakeHead.dispose();
        SNAKE.snakeBody.dispose();
        SNAKE.snakeTail.dispose();
    }
}
