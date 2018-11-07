package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

class AppleHandler {
	Texture appleTexture;
	Array<Rectangle> appleContainer;
	private int screenWidth, screenHeight;
	private long timeSinceLastAppleRender = 0;

	AppleHandler(int screenWidth, int screenHeight){
		appleTexture = new Texture(Gdx.files.internal("apple.png"));
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		appleContainer = new Array<Rectangle>();
	}

	void spawnApple(){
		if(TimeUtils.nanoTime() - timeSinceLastAppleRender > 10000000000L) {
			Rectangle apple = new Rectangle();
			apple.x = MathUtils.random(0, screenWidth/64 - 1) * 64;
			apple.y = MathUtils.random(0, screenHeight/64 - 1) * 64;
			apple.width = 64;
			apple.height = 64;

			appleContainer.add(apple);
			timeSinceLastAppleRender = TimeUtils.nanoTime();
		}
	}

	void checkIfSnakeColapse(Snake snake){
		try{
			for(int i = 0; i < appleContainer.size; i++){
				for(int j = 0; j < snake.snakeParts.size; j++){
					if(snake.snakeParts.get(j).overlaps(appleContainer.get(i))){
						appleContainer.removeIndex(i);
						snake.addNewSnakePart(1);
					}
				}
			}
		}catch(Exception e){
			timeSinceLastAppleRender = 0;
			spawnApple();
		}
    }
}
