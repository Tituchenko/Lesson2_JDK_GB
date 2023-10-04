package org.example.circle;

import org.example.common.Interactable;
import org.example.common.MainCanvas;
import org.example.common.Sprite;

import java.awt.*;
import java.util.Random;

public class Ball extends Sprite implements Interactable {
    private static Random rnd=new Random();
    private final Color color;
    private float vX;
    private float vY;
    Ball(){
        halfHeight=20+(float)(Math.random()*50f);
        halfWidth=halfHeight;
        color = new Color(rnd.nextInt());
        vX=100f+(float) (Math.random()*200f);
        vY=100f+(float) (Math.random()*200f);
    }

    @Override
    public void render(MainCanvas canvas, Graphics g) {
        g.setColor(color);
        g.fillOval((int) getLeft(),(int) getTop(),(int) getWidth(),(int) getHeight());
    }

    @Override
    public void update(MainCanvas canvas, float deltaTime) {
        x+=vX*deltaTime;
        y+=vY*deltaTime;
        if (getLeft()<canvas.getLeft()){
            setLeft(canvas.getLeft());
            vX=-vX;
        }
        if (getRight()>canvas.getRight()){
            setRight(canvas.getRight());
            vX=-vX;
        }
        if(getTop()<canvas.getTop()){
            setTop(canvas.getTop());
            vY=-vY;
        }
        if (getBottom()>canvas.getBottom()){
            setBottom(canvas.getBottom());
            vY=-vY;
        }
    }
}
