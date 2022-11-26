package weblogic.management.provider.internal.federatedconfig;

import java.io.File;
import java.io.FilenameFilter;

public class FileFragmentFilenameFilter implements FilenameFilter {
   private String suffix;

   public FileFragmentFilenameFilter(String suffix) {
      this.suffix = suffix;
   }

   public boolean accept(File dir, String name) {
      return name.endsWith(this.suffix);
   }
}
