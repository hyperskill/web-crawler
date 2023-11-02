package webcrawler;

import javax.swing.*;
import java.awt.*;

public class WebCrawler extends JFrame {
    public WebCrawler() {
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setPreferredSize(new Dimension(512, 512));
        super.setTitle("Web Crawler");

        // Create components and add them to this JFrame.

        super.setVisible(true);
        super.pack();
        super.setLocationRelativeTo(null);
    }
}