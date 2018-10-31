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

        Rectangle snakeHeadChunk = new Rectangle();
        Rectangle snakeTailChunk = new Rectangle();
        Rectangle snakeBodyChunk = new Rectangle();

        snakeParts = new Array<Rectangle>();

        snakeParts.add(snakeHeadChunk, snakeTailChunk, snakeBodyChunk);

        snakeParts.get(0).x = screenWidth/2F;
        snakeParts.get(0).y = screenHeight/2F;

        for(int i = 2; i < snakeParts.size; i++){
            if(i == 2){
                snakeParts.get(i).x = snakeParts.get(0).x - 64;
                snakeParts.get(i).y = snakeParts.get(0).y;
            }else {
                snakeParts.get(i).x = snakeParts.get(i-1).x - 64;
                snakeParts.get(i).y = snakeParts.get(i-1).y;
            }
        }
        snakeParts.get(1).x = snakeParts.get(snakeParts.size - 1).x - 64;
        snakeParts.get(1).y = snakeParts.get(snakeParts.size - 1).y;
    }

    void addNewSnakePart(int howManySnakes){
        for(int  i = 0; i < howManySnakes; i++) {
            Rectangle newSnakePart = new Rectangle();
            snakeParts.add(newSnakePart);
            snakeParts.get(snakeParts.size - 1).x = snakeParts.get(1).x;
            snakeParts.get(snakeParts.size - 1).y = snakeParts.get(1).y;
            snakeParts.get(1).x = snakeParts.get(snakeParts.size - 1).x - 64;
            snakeParts.get(1).y = snakeParts.get(snakeParts.size - 1).y;
        }
    }

}
