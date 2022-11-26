package weblogic.utils.classloaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import weblogic.utils.enumerations.FileEnumeration;

public class IndexedDirectoryClassFinder extends DirectoryClassFinder implements PackageIndexedClassFinder {
   public IndexedDirectoryClassFinder(File f) throws IOException {
      super(f);
   }

   public IndexedDirectoryClassFinder(File f, boolean enforceCase) throws IOException {
      super(f, enforceCase);
   }

   public Collection getPackageNames() {
      FilenameFilter filenameFilter = this.getFilenameFilter();

      try {
         File root = new File(this.getPath());
         String absoluteRoot = root.getAbsolutePath();
         Enumeration files = new FileEnumeration(root, filenameFilter, true);
         Set set = new HashSet();

         while(files.hasMoreElements()) {
            File f = (File)files.nextElement();
            String absolute = f.getAbsolutePath();
            if (absolute.startsWith(absoluteRoot)) {
               set.add(MultiClassFinder.getResourceDirectoryPackageName(this.toRelative(absolute, absoluteRoot)));
            }
         }

         return set;
      } catch (FileNotFoundException var8) {
         return null;
      }
   }

   private String toRelative(String absolute, String absoluteRoot) {
      String relative = absolute.substring(absoluteRoot.length());
      return WIN_32 ? relative.replace(File.separatorChar, '/') : relative;
   }
}
