package webcrawler;

import webcrawler.crawl.HtmlPageParser;
import webcrawler.source.PageSourceReader;

public class ApplicationRunner {

  public static void main(String[] args) {
    HtmlPageParser pageParser = new HtmlPageParser();
    PageSourceReader pageSourceReader = new PageSourceReader();
    new WebCrawlerWindow(pageParser, pageSourceReader);
  }
}
