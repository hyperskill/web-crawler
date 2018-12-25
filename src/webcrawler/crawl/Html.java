package webcrawler.crawl;

import java.util.Set;

public class Html {

  private final String title;
  private final Set<String> links;

  public Html(String title, Set<String> links) {
    this.title = title;
    this.links = links;
  }

  public String getTitle() {
    return title;
  }

  public Set<String> getLinks() {
    return links;
  }
}
