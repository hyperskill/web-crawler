package webcrawler;

import javax.swing.*;
import java.awt.*;

public class WebCrawler extends JFrame {
    public WebCrawler() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        InContent textCont = new InContent(200,200);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("WebCrawler");
        add(textCont.textArea).setLocation(30,30);
        setVisible(true);
        setLayout(null);
    }
}

class InContent extends JFrame {
    JTextArea textArea = new JTextArea("HTML CODE??");

    InContent(int x, int y){
        textArea.setSize(x, y);
    }
}