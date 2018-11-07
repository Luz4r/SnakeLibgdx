package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import java.sql.Struct;

class Snake {
    Texture snakeBody;
    Texture snakeTail;
    Texture snakeHead;
    Array<Rectangle> snakeParts;
    // TODO prevent snake from overlaping
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

    void addNewSnakePart(int whichWay){
        // TODO make spawning new chunks better
        Rectangle newChunk = new Rectangle();

        newChunk.width = 64;
        newChunk.height = 64;
        switch(whichWay){
            case 0:
                newChunk.x = snakeParts.get(snakeParts.size - 1).x - 64;
                newChunk.y = snakeParts.get(snakeParts.size - 1).y;
                break;
            case 90:
                newChunk.y = snakeParts.get(snakeParts.size - 1).y - 64;
                newChunk.x = snakeParts.get(snakeParts.size - 1).x;
                break;
            case 180:
                newChunk.x = snakeParts.get(snakeParts.size - 1).x + 64;
                newChunk.y = snakeParts.get(snakeParts.size - 1).y;
                break;
            case 270:
                newChunk.y = snakeParts.get(snakeParts.size - 1).y + 64;
                newChunk.x = snakeParts.get(snakeParts.size - 1).x;
                break;
            default:
        }
        snakeParts.add(newChunk);
    }
}