package webcrawler.workers;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class ExportTableWorker extends SwingWorker<Void, Void> {

  private Path exportPath;
  private DefaultTableModel tableModel;

  public ExportTableWorker(Path exportPath, DefaultTableModel tableModel) {
    this.exportPath = exportPath;
    this.tableModel = tableModel;
  }

  @Override
  protected Void doInBackground() throws Exception {
    Files.createDirectories(exportPath.getParent());
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(exportPath);
        PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
      int rowCount = tableModel.getRowCount();
      for (int i = 0; i < rowCount; i++) {
        printWriter.printf("%s\n%s\n", tableModel.getValueAt(i, 0), tableModel.getValueAt(i, 1));
      }
    }
    return null;
  }
}
