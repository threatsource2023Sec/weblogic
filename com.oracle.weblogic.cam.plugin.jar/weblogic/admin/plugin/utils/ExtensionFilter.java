package weblogic.admin.plugin.utils;

import java.io.File;
import java.io.FileFilter;

public final class ExtensionFilter implements FileFilter {
   private final String extension;

   public ExtensionFilter(String s) {
      this.extension = '.' + s.toUpperCase();
   }

   public boolean accept(File f) {
      return f.getName().toUpperCase().endsWith(this.extension);
   }
}
