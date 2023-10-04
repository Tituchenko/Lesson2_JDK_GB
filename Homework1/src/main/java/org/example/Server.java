package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Server extends JFrame{
    private static final String TITLE="Chat server";
    private static final String MSG_ALLREADY_START="Сервер уже запущен";
    private static final String MSG_ALLREADY_STOP="Сервер уже остановлен";
    private static final String MSG_START="Сервер запущен";
    private static final String MSG_STOP="Сервер остановлен";
    private static final String MSG_ALLREADY_CLIENT="Клиент уже подключен";
    private static final String HAS_INPUT="В чат вошел ";
    private static final String HAS_EXIT="Чат покинул ";
    private static final String NO_CLIENT="Такого клиента нет";

    private static final int WIDTH=397;
    private static final int HEIGHT=304;
    private static final String BTN_START="Start";
    private static final String BTN_STOP="Stop";
    private JTextArea textTA;
    private JButton startBtn,stopBtn;
    private ArrayList<Client> clients;
    private boolean isStart=false;
    Server(){
        clients=new ArrayList<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        add(createBottomPanel(),BorderLayout.SOUTH);
        textTA=new JTextArea();
        add(textTA,BorderLayout.CENTER);
        setVisible(true);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isStart) {
                    System.out.println(MSG_ALLREADY_START);
                    return;
                }

                isStart=true;
                System.out.println(MSG_START);
            }
        });
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStart){
                    System.out.println(MSG_ALLREADY_STOP);
                }
                isStart=false;
                System.out.println(MSG_STOP);
            }
        });
    }
    private boolean checkClientName (String name){
        for (Client client:clients) {
            if (client.getUserName().equals(name)) return true;

        }
        return false;

    }
    public boolean addClient(Client client){
        if (!isStart) return false;
        if (clients.contains(client) || checkClientName(client.getUserName())) {
            System.out.println(MSG_ALLREADY_CLIENT);
            return false;
        }
        addText(HAS_INPUT+ client.getUserName());
        clients.add(client);
        if (Logger.hasHistory(client.getUserName())){
            client.addHistory(Logger.getHistory(client.getUserName()));
        }
        return true;
    }
    public boolean removeClient(Client client){
        if (!isStart) return false;
        if (!clients.contains(client)){
            System.out.println(NO_CLIENT);
            return false;
        }

        clients.remove(client);
        addText(HAS_EXIT+ client.getUserName());

        return true;
    }
    public void addText(String m,String login,Client curClient){
        if (!isStart) return;
        if (!clients.contains(curClient)) return;
        if (!m.equals("")){
            if (!textTA.getText().equals("")) textTA.setText(textTA.getText()+"\n");
            textTA.setText(textTA.getText()+createMessage(login,m));
            Logger.addText(login,createMessage(login,m));
        }
        for (Client client:clients) {
            if (curClient!=client) client.addText(m,login);
        }
    }
    private String createMessage(String login,String m){
        return login+":"+m;
    }
    public void addText(String m){
        if (!isStart) return;
        if (!m.equals("")){
            if (!textTA.getText().equals("")) textTA.setText(textTA.getText()+"\n");
            textTA.setText(textTA.getText()+m);
        }
        for (Client client:clients) {
            client.addText(m);
        }
    }
    private JPanel createBottomPanel(){
        JPanel panBottom = new JPanel(new GridLayout(1,2));
        startBtn = new JButton(BTN_START);
        stopBtn = new JButton(BTN_STOP);
        panBottom.add(startBtn);
        panBottom.add(stopBtn);
        return panBottom;
    }
}
