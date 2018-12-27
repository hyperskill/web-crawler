package webcrawler.workers;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import webcrawler.crawl.Html;
import webcrawler.crawl.HtmlPageParser;
import webcrawler.source.PageSourceReader;

public class ParseLinksWorker extends SwingWorker<Vector<Vector<String>>, Void> {

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
  protected Vector<Vector<String>> doInBackground() throws Exception {
    Vector<Vector<String>> allData = new Vector<>();
    for (String link : rootPage.getLinks()) {
      if (pageReader.isHtml(link)) {
        Vector<String> res = new Vector<>();
        final String linkText = pageReader.readPageSource(link);
        Html htmlLink = pageParser.parse(linkText, link);
        res.add(link);
        res.add(htmlLink.getTitle());
        allData.add(res);
      }
    }
    return allData;
  }

  @Override
  protected void done() {
    try {
      Vector<Vector<String>> allData = get();
      linksTable.setDataVector(allData, new Vector<>(List.of("URL", "Title")));
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
    }
  }
}
