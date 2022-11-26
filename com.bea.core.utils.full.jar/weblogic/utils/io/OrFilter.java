package weblogic.utils.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

public final class OrFilter implements FileFilter {
   public final FileFilter[] filters;

   public OrFilter(FileFilter[] a) {
      this.filters = new FileFilter[a.length];
      System.arraycopy(a, 0, this.filters, 0, a.length);
   }

   public OrFilter(Collection c) {
      this.filters = (FileFilter[])((FileFilter[])c.toArray(new FileFilter[c.size()]));
   }

   public boolean accept(File f) {
      for(int i = 0; i < this.filters.length; ++i) {
         if (this.filters[i].accept(f)) {
            return true;
         }
      }

      return false;
   }
}
