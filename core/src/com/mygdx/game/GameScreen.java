package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {

    private Snake snake;
    private Learning game;
    private AppleHandler appleHandler;
    private OrthographicCamera camera;
    private boolean isMovingUp = false,
            isMovingRight = true,
            isMovingDown = false,
            isMovingLeft = false;
    private float rotation = 0; // 0 >, 90 /\, 180 <, 270 \/
    private long timeSinceLastSnakeRender = 0;
    private int speedOfSnake = 64; // how fast will snake move on screen

    GameScreen(Learning game){
        this.game = game;

        snake = new Snake(this.game.screenWidth, this.game.screenHeight);

        appleHandler = new AppleHandler(this.game.screenWidth, this.game.screenHeight);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.game.screenWidth, this.game.screenHeight);
    }

    private void drawEveryApple(){
        for(int i = 0; i < appleHandler.appleContainer.size; i++){
            game.batch.draw(new TextureRegion(appleHandler.appleTexture), appleHandler.appleContainer.get(i).x + 16, appleHandler.appleContainer.get(i).y + 16, 32/2F, 32/2F, 32, 32, 1, 1, 0);
        }
    }

    private void drawEverySnakePart(){
            if((TimeUtils.nanoTime() - timeSinceLastSnakeRender) > 1000000000L) {
                updateBodyParts();
                updateSnakeHead();
                timeSinceLastSnakeRender = TimeUtils.nanoTime();
            }
        for(int i = 0; i < snake.snakeParts.size; i++){
            if(i == 0)
                game.batch.draw(new TextureRegion(snake.snakeHead), snake.snakeParts.get(i).x, snake.snakeParts.get(i).y,64/2F, 64/2F, 64, 64,1,1, rotation);
            else if(i == snake.snakeParts.size - 1)
                game.batch.draw(new TextureRegion(snake.snakeTail), snake.snakeParts.get(i).x, snake.snakeParts.get(i).y,64/2F, 64/2F, 64, 64,1,1, rotation);
            else
                game.batch.draw(new TextureRegion(snake.snakeBody), snake.snakeParts.get(i).x, snake.snakeParts.get(i).y); // <-- draw only one thing per method
        }
    }

    private void updateSnakeHead(){
        if (isMovingRight)
            snake.snakeParts.get(0).x += speedOfSnake;
        else if (isMovingLeft)
            snake.snakeParts.get(0).x += -speedOfSnake;
        else if (isMovingUp)
            snake.snakeParts.get(0).y += speedOfSnake;
        else if (isMovingDown)
            snake.snakeParts.get(0).y += -speedOfSnake;
    }

    private void updateBodyParts(){
        for(int i = snake.snakeParts.size - 1; i > 0; i--){
            snake.snakeParts.get(i).x = snake.snakeParts.get(i - 1).x;
            snake.snakeParts.get(i).y = snake.snakeParts.get(i - 1).y;
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
        drawEveryApple();
        game.batch.end();

        appleHandler.spawnApple();

        appleHandler.checkIfSnakeColapse(snake);

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
        snake.addNewSnakePart(2);
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
        snake.snakeHead.dispose();
        snake.snakeBody.dispose();
        snake.snakeTail.dispose();
    }
}
