package weblogic.apache.org.apache.log.output.io.rotate;

import java.io.File;
import java.io.IOException;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.format.Formatter;
import weblogic.apache.org.apache.log.output.io.FileTarget;

public class RotatingFileTarget extends FileTarget {
   private RotateStrategy m_rotateStrategy;
   private FileStrategy m_fileStrategy;

   public RotatingFileTarget(Formatter formatter, RotateStrategy rotateStrategy, FileStrategy fileStrategy) throws IOException {
      super((File)null, false, formatter);
      this.m_rotateStrategy = rotateStrategy;
      this.m_fileStrategy = fileStrategy;
      this.rotate();
   }

   protected synchronized void rotate() throws IOException {
      this.close();
      File file = this.m_fileStrategy.nextFile();
      this.setFile(file, false);
      this.openFile();
   }

   protected synchronized void write(String data) {
      super.write(data);
      boolean rotate = this.m_rotateStrategy.isRotationNeeded(data, this.getFile());
      if (rotate) {
         try {
            this.rotate();
         } catch (IOException var4) {
            this.getErrorHandler().error("Error rotating file", var4, (LogEvent)null);
         }
      }

   }
}
