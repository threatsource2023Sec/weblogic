package weblogic.utils.classloaders;

import com.oracle.classloader.SymLinkDetectorFilenameFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;
import weblogic.utils.enumerations.FileEnumeration;
import weblogic.utils.io.FilenameEncoder;

public class DirectoryClassFinder extends AbstractClassFinder {
   protected static final boolean WIN_32 = System.getProperty("os.name", "unknown").toLowerCase().indexOf("windows") >= 0;
   private final boolean enforceCase;
   private final String path;
   private volatile DirectoryIndex dirIndex;

   public DirectoryClassFinder(File f) throws IOException {
      this(f, false);
   }

   public DirectoryClassFinder(File f, boolean enforceCase) throws IOException {
      this.dirIndex = null;
      this.path = f.getCanonicalPath();
      this.enforceCase = enforceCase;
   }

   public String getPath() {
      return this.path;
   }

   public Source getSource(String name) {
      if (!WIN_32 || name.indexOf(File.separator) == -1 && !name.toLowerCase().endsWith("::$data")) {
         String fileName = name.replace('/', File.separatorChar);

         try {
            File f = FilenameEncoder.getSafeFile(this.path, fileName);
            if (this.dirIndex != null && !this.dirIndex.contains(fileName)) {
               return null;
            }

            if (!f.exists()) {
               return null;
            }

            if (this.enforceCase && !this.matchCase(f, fileName)) {
               return null;
            }

            return new FileSource(this.path, f);
         } catch (FilenameEncoder.UnsafeFilenameException var4) {
         } catch (IOException var5) {
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean matchCase(File f, String fileName) throws IOException {
      if (fileName.length() < 1) {
         return true;
      } else {
         String canonicalPath = f.getCanonicalPath();
         if (canonicalPath == null) {
            return false;
         } else {
            fileName = this.trimTrailingSlashes(fileName);
            if (fileName.length() < 1) {
               return true;
            } else {
               fileName = FilenameEncoder.resolveRelativeFilePath(fileName);
               return canonicalPath.endsWith(fileName);
            }
         }
      }
   }

   private String trimTrailingSlashes(String fileName) {
      int last = fileName.length() - 1;

      int endIndex;
      for(endIndex = last; endIndex >= 0 && fileName.charAt(endIndex) == File.separatorChar; --endIndex) {
      }

      if (endIndex == last) {
         return fileName;
      } else {
         fileName = fileName.substring(0, endIndex + 1);
         return fileName;
      }
   }

   public String getClassPath() {
      return this.path;
   }

   public String toString() {
      return super.toString() + " - path: '" + this.path + "'";
   }

   protected FilenameFilter getFilenameFilter() {
      boolean skipFilenameFilter = !ClassLoaderDebugger.checkConsistency();
      FilenameFilter filenameFilter = skipFilenameFilter ? null : new SymLinkDetectorFilenameFilter(new SymLinkDetectorFilenameFilter.Reporter() {
         public void symlinkCycleDetected(String suspectedSymlinkPath, String cannonicalPath) {
            ClassLoadersLogger.symlinkCycleDetected(suspectedSymlinkPath, cannonicalPath);
         }
      });
      return filenameFilter;
   }

   public Enumeration entries() {
      try {
         return new FileEnumeration(new File(this.path), this.getFilenameFilter(), true) {
            public Object nextElement() {
               return new FileSource(DirectoryClassFinder.this.path, (File)super.nextElement());
            }
         };
      } catch (FileNotFoundException var2) {
         return super.entries();
      }
   }

   public synchronized void indexFiles() throws IOException {
      this.dirIndex = new DirectoryIndex(this.path);
   }
}
