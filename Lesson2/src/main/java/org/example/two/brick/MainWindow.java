package org.example.two.brick;

import org.example.circle.Background;
import org.example.common.CanvasRepaintListener;
import org.example.common.Interactable;
import org.example.common.MainCanvas;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements CanvasRepaintListener {
    private static final int POS_X=200;
    private static final int POS_Y=200;
    private static final int WINDOW_WIDTH=800;
    private static final int WINDOW_HEIGHT=600;
    private final Interactable[] interactables=new Interactable[10];
    private final Background bg = new Background();
    private MainWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X,POS_Y,WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("Circles");

        for (int i = 0; i < interactables.length; i++) {
            interactables[i]=new Brick();
        }

        MainCanvas canvas = new MainCanvas(this);
        add (canvas);
        setVisible(true);
    }
    public void onDrawFrame(MainCanvas canvas,Graphics g, float deltaTime){
        update(canvas,deltaTime);
        render(canvas,g);
    }
    private void update(MainCanvas canvas, float deltaTime){
        for (int i = 0; i < interactables.length; i++) {
            interactables[i].update(canvas,deltaTime);
        }
        bg.update(canvas,deltaTime);

    }
    private void render(MainCanvas canvas, Graphics g){
        for (int i = 0; i < interactables.length; i++) {
            interactables[i].render(canvas,g);
        }
        bg.render(canvas,g);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
