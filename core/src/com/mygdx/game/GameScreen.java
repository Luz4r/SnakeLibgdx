package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class GameScreen implements Screen {

    private final Snake snake;
    private final Learning game;
    private OrthographicCamera camera;
    private boolean isMovingUp = false, isMovingRight = true, isMovingDown = false, isMovingLeft = false;
    private float rotation = 0;

    private enum SnakePart {SNAKEHEAD, SNAKETAIL, SNAKEBODY};

    GameScreen(final Learning game){
        this.game = game;

        snake = new Snake();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    private void drawEverySnakePart(){
        for(int i = 0; i < snake.snakeParts.size; i++){
            if(i == 0)
                game.batch.draw(new TextureRegion(snake.snakeHead), changeSnakeChunkX(i), changeSnakeChunkY(i),64/2F, 64/2F, 64, 64,1,1, rotation);
            else if(i == 1)
                game.batch.draw(new TextureRegion(snake.snakeTail), changeSnakeChunkX(i), changeSnakeChunkY(i),64/2F, 64/2F, 64, 64,1,1, rotation);
            else
                game.batch.draw(new TextureRegion(snake.snakeBody), changeSnakeChunkX(i), changeSnakeChunkY(i)); // <-- draw only one thing per method
        }
    }

    private float changeSnakeChunkX(int j){
        if(j == 0){
            if(isMovingRight) {
                return snake.snakeParts.get(j).x += 1;
            }else if(isMovingLeft) {
                return snake.snakeParts.get(j).x -= 1;
            }else
                return snake.snakeParts.get(j).x;
        }else{
            if((snake.snakeParts.get(j).x < snake.snakeParts.get(0).x) && (snake.snakeParts.get(j).y == snake.snakeParts.get(0).y)) {
                return snake.snakeParts.get(j).x += 1;
            }else if((snake.snakeParts.get(j).x < snake.snakeParts.get(0).x) && (snake.snakeParts.get(j).y != snake.snakeParts.get(0).y)){
                return snake.snakeParts.get(j).x += 1;
            }else if((snake.snakeParts.get(j).x > snake.snakeParts.get(0).x) && (snake.snakeParts.get(j).y == snake.snakeParts.get(0).y)) {
                return snake.snakeParts.get(j).x -= 1;
            }else if((snake.snakeParts.get(j).x > snake.snakeParts.get(0).x) && (snake.snakeParts.get(j).y != snake.snakeParts.get(0).y)) {
                return snake.snakeParts.get(j).x -= 1;
            }else {
                return snake.snakeParts.get(j).x;
            }
        }
    }

    private float changeSnakeChunkY(int j){
        if(j == 0) {
            if (isMovingUp) {
                return snake.snakeParts.get(j).y += 1;
            } else if (isMovingDown) {
                return snake.snakeParts.get(j).y -= 1;
            } else
                return snake.snakeParts.get(j).y;
        }else{
            if((snake.snakeParts.get(j).y < snake.snakeParts.get(0).y) && (snake.snakeParts.get(j).x == snake.snakeParts.get(0).x)) {
                return snake.snakeParts.get(j).y += 1;
            }else if((snake.snakeParts.get(j).y > snake.snakeParts.get(0).y) && (snake.snakeParts.get(j).x == snake.snakeParts.get(0).x)){
                return snake.snakeParts.get(j).y -= 1;
            }else
                return snake.snakeParts.get(j).y;
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
        snake.addNewSnakePart(5);
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
