package webcrawler.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PageSourceReader {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");
  private static final String TEXT_HTML = "text/html";
  public static final String USER_AGENT_MOZILLA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0";

  public boolean isHtml(String url) {
    try {
      URLConnection urlConnection = new URL(url).openConnection();
      urlConnection.setRequestProperty("User-Agent", USER_AGENT_MOZILLA);
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
    final URLConnection urlConnection;
    try {
      urlConnection = new URL(url).openConnection();
      urlConnection.setRequestProperty("User-Agent", USER_AGENT_MOZILLA);
      return readFromConnection(urlConnection);
    } catch (IOException e) {
      System.out.println("Error on parse page " + url);
      return "";
    }
  }

  private String readFromConnection(URLConnection urlConnection) {
    try (
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
    ) {
      final StringBuilder stringBuilder = new StringBuilder();
      String nextLine;
      while ((nextLine = reader.readLine()) != null) {
        stringBuilder.append(nextLine);
        stringBuilder.append(LINE_SEPARATOR);
      }
      return stringBuilder.toString();
    } catch (IOException e) {
      return "";
    }
  }
}
