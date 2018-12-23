package webcrawler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WebCrawler extends JFrame {

  private final JTextArea textArea;

  public WebCrawler() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 300);
    setVisible(true);

    this.textArea = new JTextArea();
    initLayout();
  }

  private void initLayout() {
    var rootPanel = getContentPane();

    textArea.setText("HTML code?");
    var scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(200, 200));

    var areaPanel = new JPanel();
    areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.PAGE_AXIS));
    areaPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
    areaPanel.add(scrollPane);

    rootPanel.add(areaPanel, BorderLayout.CENTER);
    pack();
  }
}