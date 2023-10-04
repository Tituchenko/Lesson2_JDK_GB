package org.example.common;

import java.awt.*;

public interface CanvasRepaintListener {
    public void onDrawFrame(MainCanvas canvas, Graphics g,float deltaTime);
}
