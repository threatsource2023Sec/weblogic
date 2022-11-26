package weblogic.application.archive.utils;

import java.io.File;
import java.io.FileFilter;

public class FileFilters {
   public static final FileFilter ACCEPT_ALL = new FileFilter() {
      public boolean accept(File arg0) {
         return true;
      }
   };
   public static final FileFilter ADE_PATH = new FileFilter() {
      public boolean accept(File file) {
         return !".ade_path".equals(file.getName());
      }
   };
}
