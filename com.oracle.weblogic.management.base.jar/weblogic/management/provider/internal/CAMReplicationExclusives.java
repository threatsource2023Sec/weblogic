package weblogic.management.provider.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;

public class CAMReplicationExclusives {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final String MBEANS_DIRECTORY_NAME = "mbeans";
   private ArrayList exclusives = new ArrayList();

   public void addExclusiveList(CAMReplicationExclusive list) {
      if (list != null) {
         this.exclusives.add(list);
      }

   }

   private static List findExclusiveListFiles(File camDir) {
      FileFilter directoryFilter = new FileFilter() {
         public boolean accept(File file) {
            return file.isDirectory() && !"mbeans".equalsIgnoreCase(file.getName());
         }
      };
      if (camDir.exists() && camDir.isDirectory()) {
         File[] componentDirs = camDir.listFiles(directoryFilter);
         if (componentDirs != null && componentDirs.length != 0) {
            List allFiles = new ArrayList();
            File[] var4 = componentDirs;
            int var5 = componentDirs.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               File compDir = var4[var6];
               File file = new File(compDir, ".wls.replication.exclusive.list");
               if (file.exists() && file.isFile()) {
                  allFiles.add(file);
               }
            }

            return allFiles;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static CAMReplicationExclusives parseCAMDirectory(File camDir) {
      CAMReplicationExclusives result = new CAMReplicationExclusives();
      List files = findExclusiveListFiles(camDir);
      if (files != null && files.size() > 0) {
         Iterator var3 = files.iterator();

         while(var3.hasNext()) {
            File file = (File)var3.next();

            try {
               CAMReplicationExclusive list = new CAMReplicationExclusive(file);
               result.addExclusiveList(list);
            } catch (Exception var6) {
               ManagementLogger.logBadCAMReplicaitonExclusiveFile(file.getAbsolutePath(), var6.getMessage());
            }
         }
      }

      return result;
   }

   private boolean matches(String fileName) {
      Iterator var2 = this.exclusives.iterator();

      CAMReplicationExclusive list;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         list = (CAMReplicationExclusive)var2.next();
      } while(!list.matches(fileName));

      return true;
   }

   public File[] removeExclusiveFiles(File[] files, File base) {
      if (files != null && files.length != 0) {
         ArrayList origFiles = new ArrayList();
         File[] var4 = files;
         int var5 = files.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File file = var4[var6];
            origFiles.add(file);
         }

         ArrayList result = this.removeExclusiveFiles(origFiles, base);
         return (File[])result.toArray(new File[result.size()]);
      } else {
         return files;
      }
   }

   public Set removeExclusiveFiles(Set files, File base) {
      if (files != null && files.size() != 0) {
         ArrayList origFiles = new ArrayList(files);
         ArrayList result = this.removeExclusiveFiles(origFiles, base);
         return new HashSet(result);
      } else {
         return files;
      }
   }

   private ArrayList removeExclusiveFiles(ArrayList files, File base) {
      String basePath;
      try {
         basePath = base.getCanonicalPath();
      } catch (IOException var9) {
         return files;
      }

      ArrayList result = new ArrayList(files);
      Iterator var5 = files.iterator();

      while(var5.hasNext()) {
         File file = (File)var5.next();

         try {
            String filePath = file.getCanonicalPath();
            if (filePath.startsWith(basePath) && filePath.length() > basePath.length() + 1 && this.matches(filePath.substring(basePath.length() + 1))) {
               result.remove(file);
            }
         } catch (IOException var8) {
         }
      }

      if (debugLogger.isDebugEnabled()) {
         StringBuffer buf = new StringBuffer();
         buf.append("CAMReplicationExclusives before filter:\n");
         Iterator var11 = files.iterator();

         File file;
         while(var11.hasNext()) {
            file = (File)var11.next();
            buf.append("  ").append(file.getAbsolutePath()).append("\n");
         }

         buf.append(" After filter: ");
         if (result.size() == 0) {
            buf.append("empty");
         } else {
            buf.append("\n");
            var11 = result.iterator();

            while(var11.hasNext()) {
               file = (File)var11.next();
               buf.append("  ").append(file.getAbsolutePath()).append("\n");
            }
         }

         debugLogger.debug(buf.toString());
      }

      return result;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      Iterator var2 = this.exclusives.iterator();

      while(var2.hasNext()) {
         CAMReplicationExclusive exclusive = (CAMReplicationExclusive)var2.next();
         buf.append(exclusive).append("\n");
      }

      return buf.toString();
   }
}
