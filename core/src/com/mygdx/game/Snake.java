package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

class Snake {
    Texture snakeBody;
    Texture snakeTail;
    Texture snakeHead;
    Array<Rectangle> snakeParts;


    Snake(int screenWidth, int screenHeight){
        snakeBody = new Texture(Gdx.files.internal("snakeBody.png"));
        snakeTail = new Texture(Gdx.files.internal("snakeTail.png"));
        snakeHead = new Texture(Gdx.files.internal("snakeHead.png"));

        snakeParts = new Array<Rectangle>();

        snakeParts.add(new Rectangle(), new Rectangle());

        snakeParts.get(0).x = screenWidth/2F;
        snakeParts.get(0).y = screenHeight/2F;
        snakeParts.get(0).width = 64;
        snakeParts.get(0).height = 64;

        for(int i = 1; i < snakeParts.size; i++){
            snakeParts.get(i).x = snakeParts.get(i - 1).x - 64;
            snakeParts.get(i).y = snakeParts.get(i - 1).y;
            snakeParts.get(i).width = 64;
            snakeParts.get(i).height = 64;
        }
    }

    void addNewSnakePart(int howManySnakes){
        for(int  i = 0; i < howManySnakes; i++) {
            snakeParts.add(new Rectangle());
        }
    }

}
