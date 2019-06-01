package com.snake.game;

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
    private int rotation = 0; // 0 >, 90 /\, 180 <, 270 \/
    private long timeSinceLastSnakeRender = 0;
    private int howManyApples = 0;

    GameScreen(Learning game){
        this.game = game;

        snake = new Snake(this.game.screenWidth, this.game.screenHeight);

        appleHandler = new AppleHandler(this.game.screenWidth, this.game.screenHeight, snake.snakeParts);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.game.screenWidth, this.game.screenHeight);
    }

    private void drawEveryApple(){
        for(int i = 0; i < appleHandler.appleContainer.size; i++){
            game.batch.draw(new TextureRegion(appleHandler.appleTexture), appleHandler.appleContainer.get(i).x + 16, appleHandler.appleContainer.get(i).y + 16, 32/2F, 32/2F, 32, 32, 1, 1, 0);
        }
    }

    private void drawEverySnakePart(){
        for(int i = 0; i < snake.snakeParts.size; i++){
            if(i == 0)
                game.batch.draw(new TextureRegion(snake.snakeHead), snake.snakeParts.get(i).x, snake.snakeParts.get(i).y,64/2F, 64/2F, 64, 64,1,1, rotation);
            else
                game.batch.draw(new TextureRegion(snake.snakeBody), snake.snakeParts.get(i).x, snake.snakeParts.get(i).y); // <-- draw only one thing per method
        }
    }

    private void updateSnake(){
        if((TimeUtils.nanoTime() - timeSinceLastSnakeRender) > 500000000L) {
            snake.updateBodyParts();
            updateSnakeHead();
            timeSinceLastSnakeRender = TimeUtils.nanoTime();
        }
    }

    private void updateSnakeHead() {
        if (isMovingRight) {
            snake.snakeParts.get(0).x += 64;
            rotation = 0;
        } else if (isMovingLeft) {
            snake.snakeParts.get(0).x += -64;
            rotation =  180;
        } else if (isMovingUp) {
            snake.snakeParts.get(0).y += 64;
            rotation = 90;
        } else if (isMovingDown){
            snake.snakeParts.get(0).y += -64;
            rotation = 270;
        }
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        updateSnake();
        appleHandler.spawnApple();
        if(appleHandler.hasSnakeAteApple(snake))
            howManyApples++;

        if(snake.hasSnakeDied())
            this.game.setScreen(new FailScreen(this.game, howManyApples));

        // begin drawing stuff on screen.
        game.batch.begin();
        drawEverySnakePart();
        drawEveryApple();
        game.font.draw(game.batch, "You have eaten " + howManyApples + " apples", 50, game.screenHeight - game.screenHeight / 20F);// <-- draw some text on screen
        game.batch.end();

        checkInput();
    }

    private void checkInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !isMovingRight) {
            isMovingUp = false;
            isMovingRight = false;
            isMovingDown = false;
            isMovingLeft = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !isMovingLeft){
            isMovingUp = false;
            isMovingRight = true;
            isMovingDown = false;
            isMovingLeft = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isMovingDown){
            isMovingUp = true;
            isMovingRight = false;
            isMovingDown = false;
            isMovingLeft = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && !isMovingUp){
            isMovingUp = false;
            isMovingRight = false;
            isMovingDown = true;
            isMovingLeft = false;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show(){
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
        //snake.snakeTail.dispose();
        appleHandler.appleTexture.dispose();
    }
}
