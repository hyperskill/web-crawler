package webcrawler.crawl;

import java.util.Set;

public class HtmlBuilder {


  private String title;
  private Set<String> links;

  public HtmlBuilder() {
    title = "no title";
    links = Set.of();
  }

  HtmlBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  HtmlBuilder withLinks(Set<String> links) {
    this.links = links;
    return this;
  }

  Html build() {
    return new Html(title, links);
  }
}
