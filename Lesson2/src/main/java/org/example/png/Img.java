package org.example.png;

import org.example.common.Interactable;
import org.example.common.MainCanvas;
import org.example.common.Sprite;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Img extends Sprite implements Interactable {
    private static Random rnd=new Random();
    private final Color color;
    private float vX;
    private float vY;
    private ArrayList<Image> arrayImg;
    private Image img;
    Img(){
//        halfHeight=20+(float)(Math.random()*50f);
//        halfWidth=halfHeight;
        color = new Color(rnd.nextInt());
        vX=100f+(float) (Math.random()*200f);
        vY=100f+(float) (Math.random()*200f);
        arrayImg=getAllImg();
        img=arrayImg.get((int) (Math.random()*arrayImg.size()));
        halfHeight=(float) img.getHeight(null)/2;
        halfWidth=(float) img.getWidth(null)/2;
    }

    @Override
    public void render(MainCanvas canvas, Graphics g) {
        //g.setColor(color);
        //g.fillOval((int) getLeft(),(int) getTop(),(int) getWidth(),(int) getHeight());
        //img = new ImageIcon("./img/x3196249-1101711892.png").getImage();
        g.drawImage(img,(int) getLeft(),(int) getTop(),(int) getWidth(),(int) getHeight(),null,null);
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
    private ArrayList<Image> getAllImg(){
        ArrayList<Image> images=new ArrayList<>();
        File folder = new File("./img");
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".png")) {
                images.add(new ImageIcon("./img/"+file.getName()).getImage());
//                System.out.println(file.getName());
            }
        }
        return images;

    }
}
