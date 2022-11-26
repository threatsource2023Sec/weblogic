package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.util.Comparator;

final class FileModtimeComparator implements Comparator {
   public int compare(Object o1, Object o2) {
      File f1 = (File)o1;
      File f2 = (File)o2;
      long m1 = f1.lastModified();
      long m2 = f2.lastModified();
      if (m1 < m2) {
         return -1;
      } else {
         return m1 > m2 ? 1 : f1.getName().compareTo(f2.getName());
      }
   }
}
