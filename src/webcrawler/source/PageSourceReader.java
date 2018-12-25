package webcrawler.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PageSourceReader {

  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  public static final String TEXT_HTML = "text/html";

  public boolean isHtml(String url) {
    try {
      URLConnection urlConnection = new URL(url).openConnection();
      if (urlConnection.getContentType() != null && urlConnection.getContentType()
          .contains(TEXT_HTML)) {
        return true;
      }
    } catch (IOException e) {
      System.out.println("Error " + e);
      return false;
    }
    return false;
  }

  public String readPageSource(String url) {
    try (
        InputStream inputStream = new URL(url).openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
    ) {
      final StringBuilder stringBuilder = new StringBuilder();
      String nextLine;
      while ((nextLine = reader.readLine()) != null) {
        stringBuilder.append(nextLine);
        stringBuilder.append(LINE_SEPARATOR);
      }
      return stringBuilder.toString();
    } catch (
        IOException e1) {
      System.out.println("Error on parse page " + url);
      return "";
    }
  }
}
