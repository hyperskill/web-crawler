package webcrawler.workers;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import webcrawler.crawl.Html;
import webcrawler.crawl.HtmlPageParser;
import webcrawler.source.PageSourceReader;

public class ParseLinksWorker extends SwingWorker<Void, Void> {

  private final Html rootPage;
  private final DefaultTableModel linksTable;

  private final PageSourceReader pageReader;
  private final HtmlPageParser pageParser;

  public ParseLinksWorker(Html rootPage, DefaultTableModel linksTable,
      PageSourceReader pageReader, HtmlPageParser pageParser) {
    this.rootPage = rootPage;
    this.linksTable = linksTable;
    this.pageReader = pageReader;
    this.pageParser = pageParser;
  }


  @Override
  protected Void doInBackground() throws Exception {
    for (String link : rootPage.getLinks()) {
      if (pageReader.isHtml(link)) {
        final String linkText = pageReader.readPageSource(link);
        Html htmlLink = pageParser.parse(linkText, link);
        linksTable.addRow(new Object[]{link, htmlLink.getTitle()});
      }
    }
    return null;
  }
}
