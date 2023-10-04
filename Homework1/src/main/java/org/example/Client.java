package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Client extends JFrame{
    private static final String TITLE="Chat client";
    private static final String BTN_SEND="send";
    private static final String BTN_LOGIN="login";
    private static final String BTN_DISC="disconnect";
    private static final String MSG_SRV_CONNECT="Соединение установлено";
    private static final String MSG_SRV_DISCONNECT="Соединение разорвано";
    private static final String MSG_SRV_OFFLINE="Не удалось соединиться с сервером";
    private static final int WIDTH=397;
    private static final int HEIGHT=304;
    private static final String DEF_IP="127.0.0.1";
    private static final String DEF_PORT="8189";

    private JTextField ipTF,portTF,loginTF,passTF,messageTF;
    private JButton loginBtn,sendBtn,dscBtn;
    private JTextArea textTA;
    Client curClient;

    Client(Server server,String defLogin,String defPass) {
        curClient=this;
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        add(createBottomPanel(),BorderLayout.SOUTH);
        add(createTopPanel(defLogin,defPass),BorderLayout.NORTH);
        textTA=new JTextArea();
        textTA.setEditable(false);
        add(textTA,BorderLayout.CENTER);
        setVisible(true);

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(server);

            }
        });
        messageTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    sendMessage(server);
                }
            }
        });

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.addClient(curClient)) {
                    inverseLoginButton();
                    System.out.println(MSG_SRV_CONNECT);
                } else {
                    System.out.println(MSG_SRV_OFFLINE);
                }

            }
        });
        dscBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (server.removeClient(curClient)) {
                    inverseLoginButton();
                    System.out.println(MSG_SRV_DISCONNECT);
                } else {
                    System.out.println(MSG_SRV_OFFLINE);
                }
            }
        });

    }
    private void sendMessage(Server server){
        addText(messageTF.getText(),loginTF.getText());
        server.addText(messageTF.getText(),loginTF.getText(),curClient);
        messageTF.setText("");
    }
    public String getUserName(){
        return loginTF.getText();
    }
    public void addText(String m,String login){
        if (!m.equals("")){
            if (!textTA.getText().equals("")) textTA.setText(textTA.getText()+"\n");
            textTA.setText(textTA.getText()+login+":"+m);
        }
    }
    public void addText(String m) {
        if (!m.equals("")) {
            if (!textTA.getText().equals("")) textTA.setText(textTA.getText() + "\n");
            textTA.setText(textTA.getText() + m);
        }
    }
    public void addHistory(String history){
        addText(history);
    }
    private void inverseLoginButton(){
        loginBtn.setVisible(!loginBtn.isVisible());
        dscBtn.setVisible(!dscBtn.isVisible());
        ipTF.setEditable(!ipTF.isEditable());
        loginTF.setEditable(!loginTF.isEditable());
        portTF.setEditable(!portTF.isEditable());
        passTF.setEditable(!passTF.isEditable());
    }

    private JPanel createTopPanel(String defLogin,String defPass){
        JPanel panTop=new JPanel(new GridLayout(2,3));
        ipTF=new JTextField(DEF_IP);
        portTF=new JTextField(DEF_PORT);
        loginTF=new JTextField(defLogin);
        passTF=new JPasswordField(defPass);
        loginBtn=new JButton(BTN_LOGIN);
        dscBtn=new JButton(BTN_DISC);
        dscBtn.setVisible(false);
        panTop.add(ipTF);
        panTop.add(portTF);
        panTop.add(dscBtn);
        panTop.add(loginTF);
        panTop.add(passTF);
        panTop.add(loginBtn);
        return panTop;
    }
    private JPanel createBottomPanel(){
        JPanel panBottom = new JPanel();
        panBottom.setLayout(new BoxLayout(panBottom, BoxLayout.X_AXIS));
        messageTF = new JTextField(20);
        sendBtn = new JButton(BTN_SEND);
        panBottom.add(messageTF);
        panBottom.add(sendBtn);
        return panBottom;
    }
}
