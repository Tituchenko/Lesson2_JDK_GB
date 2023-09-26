package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map  extends JPanel {
    private static final int DOT_PADDING=5;
    private static final  Random RANDOM = new Random();
    private int gameOverType;
    private static final int STATE_DRAW=0;
    private static final int STATE_WIN_HUMAN=1;
    private static final int STATE_WIN_AI=2;
    private static final String MSG_WIN_HUMAN="Победил игрок!";
    private static final String MSG_WIN_AI="Победил компьютер!";
    private static final String MSG_DRAW="Ничья";
    private final int HUMAN_DOT=1;
    private final int AI_DOT=2;
    private final int EMPTY_DOT=0;
    private int fieldSizeY;
    private int fieldSizeX;
    private int winLen;
    private char[][] field;
    private int panelWidth;
    private int panelHeight;
    private int cellHeight;
    private int cellWidth;
    private boolean isGameOver;
    private boolean isInitialized;
    Map(){
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e){
                update(e);

            }
        });
        isInitialized=false;
    }
    private void update(MouseEvent e){
        if (isGameOver || !isInitialized) return;
        int cellX=e.getX()/cellWidth;
        int cellY=e.getY()/cellHeight;
        if (!isValidCell(cellX,cellY) || !isEmptyCell(cellX,cellY)) return;
//        System.out.printf("x=%d, y=%d\n",cellX,cellY);
        field[cellY][cellX]=HUMAN_DOT;
        if (checkEndGame(HUMAN_DOT,STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if (checkEndGame(AI_DOT,STATE_WIN_AI)) return;
    }
    private boolean checkEndGame (int dot,int gameOverType){
        if (checkWin(dot)){
            this.gameOverType=gameOverType;
            isGameOver=true;
            repaint();
            return true;
        }
        if (isMapFull()){
            this.gameOverType=STATE_DRAW;
            isGameOver=true;
            repaint();
            return true;
        }
        return false;
    }
    void startNewGame(int mode,int fSzX,int fSzY,int wLen){
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWin Length: %d",mode,fSzX,fSzY,wLen);
        fieldSizeX=fSzX;
        fieldSizeY=fSzY;
        winLen=wLen;
        initMap();
        isGameOver=false;
        isInitialized=true;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
    }
    private void render(Graphics g){
        if (!isInitialized) return;
        panelWidth=getWidth();
        panelHeight=getHeight();
        cellHeight=panelHeight/fieldSizeY;
        cellWidth=panelWidth/fieldSizeX;
        g.setColor (Color.BLACK);
        for (int h=0;h<fieldSizeY;h++){
            int y=h*cellHeight;
            g.drawLine(0,y,panelWidth,y);
        }
        for (int w = 0; w < fieldSizeX; w++) {
            int x=w*cellWidth;
            g.drawLine(x,0,x,panelHeight);
        }
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x]== EMPTY_DOT) continue;
                if (field[y][x]==HUMAN_DOT){
                    g.setColor(Color.BLUE);
                    g.fillOval(x*cellWidth+DOT_PADDING, y*cellHeight+DOT_PADDING,cellWidth-DOT_PADDING*2,
                            cellHeight-DOT_PADDING*2);
                } else if (field[y][x]==AI_DOT){
                    g.setColor(Color.RED);
                    g.fillOval(x*cellWidth+DOT_PADDING, y*cellHeight+DOT_PADDING,cellWidth-DOT_PADDING*2,
                            cellHeight-DOT_PADDING*2);
                } else {
                    throw new RuntimeException("Unexpected value "+ field[y][x]+" in cell: x="+x+" y="+y);
                }

            }

        }
        if (isGameOver) showMessageGameOver(g);
    }
    private void showMessageGameOver(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,200,getWidth(),70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman",Font.BOLD,48));
        switch (gameOverType){
            case STATE_DRAW:
                g.drawString(MSG_DRAW,180,getHeight()/2);break;
            case STATE_WIN_AI:
                g.drawString(MSG_WIN_AI,20,getHeight()/2);break;
            case STATE_WIN_HUMAN:
                g.drawString(MSG_WIN_HUMAN,70,getHeight()/2);break;
            default:
                throw new RuntimeException("Unexpected gameOver state: "+gameOverType);
        }
    }
    private void initMap(){
        field= new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j]=EMPTY_DOT;
            }

        }
    }
    private boolean isValidCell(int x,int y){
        return x>=0 && x<fieldSizeX && y>=0 &&y<fieldSizeY;
    }
    private boolean isEmptyCell(int x,int y){
        return field[y][x]==EMPTY_DOT;
    }
    private void aiTurn(){
        int x,y;
        // проверим можем ли мы победить - и ходим тут же так!, нет меняем все взад и дальше пытаемся
        for (y = 0; y < fieldSizeY; y++) {
            for (x = 0; x < fieldSizeX; x++) {
                if (field[y][x]==EMPTY_DOT){
                    field[y][x]=AI_DOT;
                    if (checkWin(AI_DOT)){
                        return;
                    } else {
                        field[y][x]=EMPTY_DOT;
                    }
                }

            }

        }
        //проверяем что эти двуногие могут выиграть и ломаем им кайф, если не вилка)
        for (y = 0; y < fieldSizeY; y++) {
            for (x = 0; x < fieldSizeX; x++) {
                if (field[y][x]==EMPTY_DOT){
                    field[y][x]=HUMAN_DOT;
                    if (checkWin(HUMAN_DOT)){
                        field[y][x]=AI_DOT;
                        return;
                    } else {
                        field[y][x]=EMPTY_DOT;
                    }
                }

            }

        }
        // если нечего не получилось пусть решает Random

        do {
            x=RANDOM.nextInt(fieldSizeX);
            y=RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x,y));
        field[y][x]=AI_DOT;
    }
    private boolean checkWin(int c) {
        //check all row and column together
        //and Diagonals - main and anti main - hard!!!!
        int curLineX;
        int curLineY;

        int mainDiagPlus;
        int mainDiagMinus;
        int antiMainDiagPlus;
        int antiMainDiagMinus;

        int xMainDiagPlus;
        int xMainDiagMinus;
        int yAntiMainDiagPlus;
        int yAntiMainDiagMinus;

        for (int i = 0; i < fieldSizeY; i++) {
            curLineX=0;
            curLineY=0;
            mainDiagPlus=0;
            mainDiagMinus=0;
            antiMainDiagPlus=0;
            antiMainDiagMinus=0;

            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j]==c) {
                    curLineX++;
                } else {
                    curLineX=0;
                }
                if (field[j][i]==c) {
                    curLineY++;
                } else {
                    curLineY=0;
                }

                xMainDiagPlus=j+i;
                xMainDiagMinus=j-i;
                if (xMainDiagPlus<fieldSizeX && field[j][xMainDiagPlus] == c) {
                    mainDiagPlus++;
                } else {
                    mainDiagPlus = 0;
                }
                if (xMainDiagMinus>=0 && field[j][xMainDiagMinus] == c) {
                    mainDiagMinus++;
                } else {
                    mainDiagMinus = 0;
                }
                yAntiMainDiagPlus=fieldSizeY-1-(j+i);
                yAntiMainDiagMinus=fieldSizeY-1-(j-i);
                if (yAntiMainDiagPlus>=0 && field[yAntiMainDiagPlus][j]==c){
                    antiMainDiagPlus++;
                } else {
                    antiMainDiagPlus=0;
                }
                if (yAntiMainDiagMinus<fieldSizeY && field[yAntiMainDiagMinus][j]==c){
                    antiMainDiagMinus++;
                } else {
                    antiMainDiagMinus=0;
                }
                if (curLineX==winLen || curLineY==winLen ||
                        mainDiagPlus==winLen || mainDiagMinus==winLen ||
                        antiMainDiagPlus==winLen || antiMainDiagMinus==winLen) return true;


            }

        }

//        if (field[0][0]==c && field[0][1]==c && field[0][2]==c) return true;
//        if (field[1][0]==c && field[1][1]==c && field[1][2]==c) return true;
//        if (field[2][0]==c && field[2][1]==c && field[2][2]==c) return true;
//
//        if (field[0][0]==c && field[1][0]==c && field[2][0]==c) return true;
//        if (field[0][1]==c && field[1][1]==c && field[2][1]==c) return true;
//        if (field[0][2]==c && field[1][2]==c && field[2][2]==c) return true;
//
//        if (field[0][0]==c && field[1][1]==c && field[2][2]==c) return true;
//        if (field[0][2]==c && field[1][1]==c && field[2][0]==c) return true;
        return false;
    }
    private boolean isMapFull(){
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j]==EMPTY_DOT) return false;

            }

        }
        return true;
    }
}
