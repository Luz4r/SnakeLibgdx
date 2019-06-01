package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import java.sql.Struct;

class Snake {
    Texture snakeBody;
    //Texture snakeTail;
    Texture snakeHead;
    Array<Rectangle> snakeParts;
    private int screenWidth,
        screenHeight;

    Snake(int screenWidth, int screenHeight){
        snakeBody = new Texture(Gdx.files.internal("core\\assets\\snakeBody.png"));
        //snakeTail = new Texture(Gdx.files.internal("snakeTail.png"));
        snakeHead = new Texture(Gdx.files.internal("core\\assets\\snakeHead.png"));

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

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

    void updateBodyParts(){
        for(int i = snakeParts.size - 1; i > 0; i--){
            snakeParts.get(i).x = snakeParts.get(i - 1).x;
            snakeParts.get(i).y = snakeParts.get(i - 1).y;
        }
    }

    boolean hasSnakeDied(){
        if(snakeParts.get(0).x > screenWidth - 64 || snakeParts.get(0).y > screenHeight - 64)
            return true;
        else if(snakeParts.get(0).x < 0 || snakeParts.get(0).y < 0)
            return true;

        for(int i = 1; i < snakeParts.size; i++)
            if(snakeParts.get(0).overlaps(snakeParts.get(i)))
                return true;

        return false;
    }

    void addNewSnakePart(){
        Rectangle newChunk = new Rectangle();

        newChunk.width = 64;
        newChunk.height = 64;
        newChunk.x = snakeParts.get(snakeParts.size - 1).x;
        newChunk.y = snakeParts.get(snakeParts.size - 1).y;
        snakeParts.add(newChunk);
    }
}