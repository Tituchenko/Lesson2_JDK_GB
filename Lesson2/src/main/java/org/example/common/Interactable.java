package org.example.common;

import org.example.common.MainCanvas;

import java.awt.*;

public interface Interactable
{
    void update(MainCanvas canvas, float deltaTime);
    void render (MainCanvas canvas, Graphics g);
}
