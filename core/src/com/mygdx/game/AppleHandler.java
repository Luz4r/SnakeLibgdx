package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class AppleHandler {
	Texture appleTexture;
	Array<Rectangle> applesSpawned;
	private int screenWidth, screenHeight;

	AppleHandler(int screenWidth, int screenHeight){
		appleTexture = new Texture(Gdx.files.internal("apple.png"));
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	void spawnApple(){
	    Rectangle apple = new Rectangle();
		apple.x = MathUtils.random(0, screenWidth - 64);
        apple.y = MathUtils.random(0, screenHeight - 64);
        apple.width = 64;
        apple.height = 64;
        applesSpawned.add(apple);
	}

	void checkIfSnakeColapse(){

    }
}
