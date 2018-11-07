package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class FailScreen implements Screen {

    private Learning game;
    private OrthographicCamera camera;

    private String failMessage = "You Failed";

    FailScreen(Learning game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.game.screenWidth, this.game.screenHeight);
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

        game.batch.begin();
        game.font.setColor(Color.RED);
        game.font.draw(game.batch, failMessage, game.screenWidth/2F - failMessage.length(), game.screenHeight/2F - failMessage.length());
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
