package webcrawler;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import webcrawler.crawl.Html;
import webcrawler.crawl.HtmlPageParser;

public class WebCrawlerWindow extends JFrame {

  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  private final JTextArea textArea;
  private final JTextField location;
  private final JButton goButton;
  private final JLabel titleLabelInfo;
  private final JLabel titleLabel;

  private final HtmlPageParser pageParser;

  public WebCrawlerWindow(HtmlPageParser pageParser) {
    this.pageParser = pageParser;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 600);

    this.location = new JTextField("http://example.com");
    this.goButton = new JButton("Get text!");
    this.textArea = new JTextArea();
    this.titleLabelInfo = new JLabel("Title: ");
    this.titleLabel = new JLabel();
    initLayout();
    initActions();
    setVisible(true);
  }

  private void initActions() {
    goButton.addActionListener(e ->
        SwingUtilities.invokeLater(() -> {
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
            Html html = pageParser.parse(siteText);
            textArea.setText(siteText);
            titleLabel.setText(html.getTitle());
          } catch (IOException e1) {
            textArea.setText("No content");
          }
        }));
  }

  private void initLayout() {
    var rootPanel = getContentPane();

    var locationPanel = new JPanel();
    locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.LINE_AXIS));
    locationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    locationPanel.add(location);
    locationPanel.add(goButton);

    var titlePanel = new JPanel();
    titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    titlePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    titlePanel.add(titleLabelInfo);
    titlePanel.add(titleLabel);

    var scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 400));

    var areaPanel = new JPanel();
    areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.PAGE_AXIS));
    areaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    areaPanel.add(scrollPane);

    rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
    rootPanel.add(locationPanel);
    rootPanel.add(titlePanel);
    rootPanel.add(areaPanel);

    pack();
  }
}