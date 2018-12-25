package webcrawler.crawl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlPageParser {

  private Pattern titlePattern;
  private Pattern linkPattern;

  public HtmlPageParser() {
    this.titlePattern = Pattern.compile("<title>(.*)</title>");
    this.linkPattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
  }

  public Html parse(String siteText, String rootHost) {
    HtmlBuilder htmlBuilder = new HtmlBuilder();
    buildTitle(htmlBuilder, siteText);
    buildLinks(htmlBuilder, siteText, rootHost);

    return htmlBuilder.build();
  }

  private void buildLinks(HtmlBuilder htmlBuilder, String siteText, String rootHost) {
    Matcher linksMatcher = linkPattern.matcher(siteText);
    boolean found = linksMatcher.find();
    HashSet<String> links = new HashSet<>();
    while (found) {
      String group = linksMatcher.group(2);
      String link = normalize(group, rootHost);
      links.add(link);
      found = linksMatcher.find();
    }
    htmlBuilder.withLinks(links);
  }

  private void buildTitle(HtmlBuilder htmlBuilder, String siteText) {
    Matcher titleMatcher = titlePattern.matcher(siteText);
    boolean found = titleMatcher.find();
    if (found) {
      htmlBuilder.withTitle(titleMatcher.group(1));
    }
  }

  private String normalize(String link, String rootHost) {
    if (link.startsWith("http://") || link.startsWith("https://")) {
      return link;
    } else if (link.startsWith("//")) {
      String protocol = rootHost.startsWith("https") ? "https:" : "http:";
      return protocol + link;
    } else {
      return rootHost + "/" + link;
    }
  }
}
