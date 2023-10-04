package org.example.png;


import org.example.common.CanvasRepaintListener;
import org.example.common.Interactable;
import org.example.common.MainCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainWindow extends JFrame implements Interactable,CanvasRepaintListener, Thread.UncaughtExceptionHandler{
    private static final int POS_X=200;
    private static final int POS_Y=200;
    private static final int WINDOW_WIDTH=800;
    private static final int WINDOW_HEIGHT=600;
    private final ArrayList<Interactable> interactables=new ArrayList<>();
//    private final Background bg = new Background();
    private MainWindow(){
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X,POS_Y,WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("Images");

        for (int i = 0; i < 10; i++) {
            interactables.add(new Img());
        }

        MainCanvas canvas = new MainCanvas(this);
        add (canvas);
        setVisible(true);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()){
                    case MouseEvent.BUTTON1:
                        addCircle();
                        break;
                    case MouseEvent.BUTTON3:
                        delCircle();
                        break;
                }

            }
        });
    }
    private void addCircle(){

        if (interactables.size()>=15) {
            throw new ArrayIndexOutOfBoundsException("Нельзя больше 15 котиков!!!");
        } else {
            interactables.add(new Img());
        }
    }
    private void delCircle(){
        if (interactables.size()>0) interactables.remove(interactables.get(interactables.size()-1));

    }
     public void onDrawFrame(MainCanvas canvas,Graphics g, float deltaTime){
        update(canvas,deltaTime);
        render(canvas,g);
    }
    public void update(MainCanvas canvas, float deltaTime){
        for (int i = 0; i < interactables.size(); i++) {
            interactables.get(i).update(canvas,deltaTime);
        }
//        bg.update(canvas,deltaTime);

    }
    public void render(MainCanvas canvas, Graphics g){
        for (int i = 0; i < interactables.size(); i++) {
            interactables.get(i).render(canvas,g);
        }
//        bg.render(canvas,g);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        JOptionPane.showMessageDialog(null,e.getMessage(),"Нельзя!",JOptionPane.ERROR_MESSAGE);
    }
}
