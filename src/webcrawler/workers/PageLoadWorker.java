package webcrawler.workers;

import java.util.concurrent.ExecutionException;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import webcrawler.crawl.Html;
import webcrawler.crawl.HtmlPageParser;
import webcrawler.source.PageSourceReader;

public class PageLoadWorker extends SwingWorker<Html, Void> {

  private final String url;
  private final JLabel titleLabel;

  private DefaultTableModel linksTable;
  private final PageSourceReader pageReader;
  private final HtmlPageParser pageParser;

  public PageLoadWorker(String location, JLabel titleLabel, DefaultTableModel linksTable,
      PageSourceReader pageReader, HtmlPageParser pageParser) {
    this.url = location;
    this.titleLabel = titleLabel;
    this.linksTable = linksTable;
    this.pageReader = pageReader;
    this.pageParser = pageParser;
  }

  @Override
  protected Html doInBackground() throws Exception {
    final String siteText = pageReader.readPageSource(url);
    return pageParser.parse(siteText, url);
  }

  @Override
  protected void done() {
    try {
      Html html = get();
      titleLabel.setText(html.getTitle());
      ParseLinksWorker parseLinksWorker = new ParseLinksWorker(html, linksTable, pageReader,
          pageParser);
      parseLinksWorker.execute();
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
    }
  }
}
