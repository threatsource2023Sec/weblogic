package org.apache.log.output.io.rotate;

import java.io.File;
import java.text.DecimalFormat;
import java.text.FieldPosition;

public class RevolvingFileStrategy implements FileStrategy {
   private static final String PATTERN = "'.'000000";
   private DecimalFormat m_decimalFormat;
   private int m_rotation;
   private int m_maxRotations;
   private File m_baseFile;

   public RevolvingFileStrategy(File baseFile, int initialRotation, int maxRotations) {
      this.m_decimalFormat = new DecimalFormat("'.'000000");
      this.m_baseFile = baseFile;
      this.m_rotation = initialRotation;
      this.m_maxRotations = maxRotations;
      if (-1 == initialRotation) {
      }

      if (-1 == this.m_maxRotations) {
         this.m_maxRotations = Integer.MAX_VALUE;
      }

      if (this.m_rotation > this.m_maxRotations) {
         this.m_rotation = this.m_maxRotations;
      }

      if (this.m_rotation < 0) {
         this.m_rotation = 0;
      }

   }

   public RevolvingFileStrategy(File baseFile, int maxRotations) {
      this(baseFile, -1, maxRotations);
   }

   public File nextFile() {
      StringBuffer sb = new StringBuffer();
      FieldPosition fp = new FieldPosition(0);
      sb.append(this.m_baseFile);
      StringBuffer result = this.m_decimalFormat.format((long)this.m_rotation, sb, fp);
      ++this.m_rotation;
      if (this.m_rotation >= this.m_maxRotations) {
         this.m_rotation = 0;
      }

      return new File(result.toString());
   }
}
