package webcrawler;

import webcrawler.crawl.HtmlPageParser;

public class ApplicationRunner {

  public static void main(String[] args) {
    HtmlPageParser pageParser = new HtmlPageParser();
    new WebCrawlerWindow(pageParser);
  }
}
