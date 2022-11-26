package org.apache.log.output.io.rotate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UniqueFileStrategy implements FileStrategy {
   private File m_baseFile;
   private SimpleDateFormat m_formatter;
   private String m_suffix;

   public UniqueFileStrategy(File baseFile) {
      this.m_baseFile = baseFile;
   }

   public UniqueFileStrategy(File baseFile, String pattern) {
      this(baseFile);
      this.m_formatter = new SimpleDateFormat(pattern);
   }

   public UniqueFileStrategy(File baseFile, String pattern, String suffix) {
      this(baseFile, pattern);
      this.m_suffix = suffix;
   }

   public File nextFile() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.m_baseFile);
      if (this.m_formatter == null) {
         sb.append(System.currentTimeMillis());
      } else {
         String dateString = this.m_formatter.format(new Date());
         sb.append(dateString);
      }

      if (this.m_suffix != null) {
         sb.append(this.m_suffix);
      }

      return new File(sb.toString());
   }
}
