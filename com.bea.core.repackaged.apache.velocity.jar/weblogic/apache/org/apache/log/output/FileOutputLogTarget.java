package weblogic.apache.org.apache.log.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** @deprecated */
public class FileOutputLogTarget extends DefaultOutputLogTarget {
   private boolean m_append = false;

   public FileOutputLogTarget() {
   }

   public FileOutputLogTarget(String filename) throws IOException {
      this.setFilename(filename);
   }

   public FileOutputLogTarget(String filename, boolean append) throws IOException {
      this.m_append = append;
      this.setFilename(filename);
   }

   public void setAppend(boolean append) {
      this.m_append = append;
   }

   public void setFilename(String filename) throws IOException {
      File file = new File(filename);
      File parent = file.getAbsoluteFile().getParentFile();
      if (!parent.exists()) {
         parent.mkdirs();
      }

      this.m_output = new FileWriter(filename, this.m_append);
   }
}
