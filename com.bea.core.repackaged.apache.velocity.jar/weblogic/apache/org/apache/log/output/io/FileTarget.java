package weblogic.apache.org.apache.log.output.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.apache.org.apache.log.format.Formatter;

public class FileTarget extends StreamTarget {
   private File m_file;
   private boolean m_append;

   public FileTarget(File file, boolean append, Formatter formatter) throws IOException {
      super((OutputStream)null, formatter);
      if (null != file) {
         this.setFile(file, append);
         this.openFile();
      }

   }

   protected synchronized void setFile(File file, boolean append) throws IOException {
      if (null == file) {
         throw new NullPointerException("file property must not be null");
      } else if (this.isOpen()) {
         throw new IOException("target must be closed before file property can be set");
      } else {
         this.m_append = append;
         this.m_file = file;
      }
   }

   protected synchronized void openFile() throws IOException {
      if (this.isOpen()) {
         this.close();
      }

      File file = this.getFile().getCanonicalFile();
      File parent = file.getParentFile();
      if (null != parent && !parent.exists()) {
         parent.mkdirs();
      }

      FileOutputStream outputStream = new FileOutputStream(file.getPath(), this.m_append);
      this.setOutputStream(outputStream);
      this.open();
   }

   protected synchronized File getFile() {
      return this.m_file;
   }
}
