package weblogic.diagnostics.image;

import java.io.File;
import java.io.FilenameFilter;

class ImageFilenameFilter implements FilenameFilter {
   private String filenameToFilter;

   ImageFilenameFilter(String filter) {
      this.filenameToFilter = filter;
   }

   public boolean accept(File dir, String name) {
      return name.startsWith(this.filenameToFilter);
   }
}
