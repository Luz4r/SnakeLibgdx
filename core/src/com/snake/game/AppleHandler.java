package com.snake.game;

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
	private Array<Rectangle> snakeParts;

	AppleHandler(int screenWidth, int screenHeight, Array<Rectangle> snakeParts){
		appleTexture = new Texture(Gdx.files.internal("core\\assets\\apple.png"));
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.snakeParts = snakeParts;

		appleContainer = new Array<Rectangle>();
	}

	void spawnApple(){
		if(TimeUtils.nanoTime() - timeSinceLastAppleRender > 4000000000L) {
			Rectangle apple = new Rectangle();
			boolean isOverlaping = true;
			apple.width = 64;
			apple.height = 64;
			while(isOverlaping) {
				apple.x = MathUtils.random(0, screenWidth / 64 - 1) * 64;
				apple.y = MathUtils.random(0, screenHeight / 64 - 1) * 64;
				for(Rectangle i : snakeParts) {
					isOverlaping = apple.overlaps(i);
					if(isOverlaping)
						break;
				}
				if(!isOverlaping) {
					for (Rectangle i : appleContainer) {
						isOverlaping = apple.overlaps(i);
						if (isOverlaping)
							break;
					}
				}
			}
			appleContainer.add(apple);
			timeSinceLastAppleRender = TimeUtils.nanoTime();
		}
	}
	
	boolean hasSnakeAteApple(Snake snake){
		try{
			for(int i = 0; i < appleContainer.size; i++){
				if(snakeParts.get(0).overlaps(appleContainer.get(i))){
					appleContainer.removeIndex(i);
					snake.addNewSnakePart();
					return true;
				}
			}
		}catch(Exception e){
			timeSinceLastAppleRender = 0;
			spawnApple();
		}
		return  false;
    }
}
