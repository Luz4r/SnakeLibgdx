package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class FailScreen implements Screen {

    private Learning game;
    private OrthographicCamera camera;
    private int howManyApples = 0;

    FailScreen(Learning game, int howManyApples){
        this.game = game;
        this.howManyApples = howManyApples;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.game.screenWidth, this.game.screenHeight);
    }

    @Override
    public void render(float delta){
        String failMessage = "You Failed";
        String score = "You Have ate " + howManyApples + " apples";

        Gdx.gl.glClearColor(0, 0, 150, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(Color.RED);
        game.font.draw(game.batch, failMessage, game.screenWidth/2F - 50, game.screenHeight/2F);
        game.font.setColor(Color.GOLD);
        game.font.draw(game.batch, score, game.screenWidth/2F - 90, game.screenHeight/2F - 20);
        game.batch.end();
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
    }
}
