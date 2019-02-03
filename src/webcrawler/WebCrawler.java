package webcrawler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebCrawler extends JFrame {
    private String url;

    public WebCrawler() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        InContent textCont = new InContent(getWidth() - 60,getHeight() - 110);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("WebCrawler");

        textCont.applyButton.setSize(90, 30);
        textCont.applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                url = textCont.askUrl.getText();
                textCont.getPageText(url);
            }
        });
        add(textCont.applyButton).setLocation(480, 10);

        textCont.askUrl.setSize(425, 30);
        add(textCont.askUrl).setLocation(30, 10);

        add(textCont.textArea).setLocation(30,50);
        setVisible(true);
        setLayout(null);
    }
}

class InContent extends JFrame {
    JTextField askUrl = new JTextField("Type URL here");
    JTextArea textArea = new JTextArea();
    JButton applyButton = new JButton("Start");

    InContent(int x, int y){
        textArea.setSize(x, y);
    }

    void getPageText(String url){
        StringBuilder str = new StringBuilder();
        String nextLine;
        try {
            InputStream inputStream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((nextLine = reader.readLine()) != null){
                str.append(nextLine);
                str.append("\n");
            }
            textArea.setText(str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}