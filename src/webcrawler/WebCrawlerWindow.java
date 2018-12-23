package webcrawler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WebCrawlerWindow extends JFrame {

  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  private final JTextArea textArea;
  private final JTextField location;
  private final JButton goButton;

  public WebCrawlerWindow() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 600);

    this.location = new JTextField("http://example.com");
    this.goButton = new JButton("Get text!");
    this.textArea = new JTextArea();
    initLayout();
    initActions();
    setVisible(true);
  }

  private void initActions() {
    goButton.addActionListener(e -> {
      String url = location.getText();
      try (
          InputStream inputStream = new URL(url).openStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
      ) {
        final StringBuilder stringBuilder = new StringBuilder();
        String nextLine;
        while ((nextLine = reader.readLine()) != null) {
          stringBuilder.append(nextLine);
          stringBuilder.append(LINE_SEPARATOR);
        }
        final String siteText = stringBuilder.toString();
        textArea.setText(siteText);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });
  }

  private void initLayout() {
    var rootPanel = getContentPane();

    var locationPanel = new JPanel();
    locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.LINE_AXIS));
    locationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    locationPanel.add(location);
    locationPanel.add(goButton);

    var scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 400));

    var areaPanel = new JPanel();
    areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.PAGE_AXIS));
    areaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    areaPanel.add(scrollPane);

    rootPanel.add(locationPanel, BorderLayout.PAGE_START);
    rootPanel.add(areaPanel, BorderLayout.CENTER);


  }
}