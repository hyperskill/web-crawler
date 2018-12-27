package webcrawler;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import webcrawler.crawl.HtmlPageParser;
import webcrawler.source.PageSourceReader;
import webcrawler.workers.ExportTableWorker;
import webcrawler.workers.PageLoadWorker;

public class WebCrawlerWindow extends JFrame {

  private final JTable table;
  private final JTextField location;
  private final JButton goButton;
  private final JLabel titleLabelInfo;
  private final JLabel titleLabel;
  private final JLabel urlLabel;
  private final JLabel exportLabel;
  private final JTextField exportLocation;
  private final JButton exportButton;

  private final DefaultTableModel tableModel;

  private final PageSourceReader pageReader;
  private final HtmlPageParser pageParser;

  public WebCrawlerWindow(HtmlPageParser pageParser, PageSourceReader pageReader) {
    this.pageParser = pageParser;
    this.pageReader = pageReader;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 600);

    this.urlLabel = new JLabel("URL: ");
    this.location = new JTextField("https://wikipedia.org");
    this.goButton = new JButton("Parse");
    this.table = new JTable();
    this.tableModel = new DefaultTableModel(new String[]{"URL", "Title"}, 0);
    this.titleLabelInfo = new JLabel("Title: ");
    this.titleLabel = new JLabel();
    this.exportLabel = new JLabel("Export: ");
    this.exportLocation = new JTextField("export/links.txt");
    this.exportButton = new JButton("Save");

    initLayout();
    initActions();
    setVisible(true);
  }

  private void initActions() {
    goButton.addActionListener(e ->
        SwingUtilities.invokeLater(() -> {
          String url = location.getText();
          PageLoadWorker pageLoadWorker = new PageLoadWorker(url, titleLabel, tableModel,
              pageReader,
              pageParser);
          pageLoadWorker.execute();
        }));

    exportButton.addActionListener(e ->
        SwingUtilities.invokeLater(() -> {
          String file = exportLocation.getText();
          ExportTableWorker pageLoadWorker = new ExportTableWorker(Paths.get(file), tableModel);
          pageLoadWorker.execute();
        }));
  }

  private void initLayout() {
    var rootPanel = getContentPane();

    var locationPanel = new JPanel();
    locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.LINE_AXIS));
    locationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    locationPanel.add(urlLabel);
    locationPanel.add(location);
    locationPanel.add(goButton);

    table.setModel(tableModel);
    var titlePanel = new JPanel();
    titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    titlePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    titlePanel.add(titleLabelInfo);
    titlePanel.add(titleLabel);

    var scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(400, 400));

    var areaPanel = new JPanel();
    areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.PAGE_AXIS));
    areaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    areaPanel.add(scrollPane);

    var exportPanel = new JPanel();
    exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.LINE_AXIS));
    exportPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    exportPanel.add(exportLabel);
    exportPanel.add(exportLocation);
    exportPanel.add(exportButton);

    rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
    rootPanel.add(locationPanel);
    rootPanel.add(titlePanel);
    rootPanel.add(areaPanel);
    rootPanel.add(exportPanel);

    pack();
  }
}