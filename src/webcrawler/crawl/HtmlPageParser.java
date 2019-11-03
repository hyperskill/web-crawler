package webcrawler.crawl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlPageParser {

  private Pattern titlePattern;

  public HtmlPageParser() {
    this.titlePattern = Pattern.compile("<title>(.*)</title>");
  }

  public Html parse(String siteText) {
    Matcher matcher = titlePattern.matcher(siteText);
    boolean found = matcher.find();
    return found ? new Html(matcher.group(1)) : new Html("No title");
  }
}
