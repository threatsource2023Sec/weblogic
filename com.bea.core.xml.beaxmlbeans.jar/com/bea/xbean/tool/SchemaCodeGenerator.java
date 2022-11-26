package com.bea.xbean.tool;

import com.bea.xbean.util.FilerImpl;
import com.bea.xml.Filer;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.SystemProperties;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import repackage.Repackager;

public class SchemaCodeGenerator {
   private static Set deleteFileQueue = new HashSet();
   private static int triesRemaining = 0;

   /** @deprecated */
   public static void saveTypeSystem(SchemaTypeSystem system, File classesDir, File sourceFile, Repackager repackager, XmlOptions options) throws IOException {
      Filer filer = new FilerImpl(classesDir, (File)null, repackager, false, false);
      system.save(filer);
   }

   static void deleteObsoleteFiles(File rootDir, File srcDir, Set seenFiles) {
      if (rootDir.isDirectory() && srcDir.isDirectory()) {
         String absolutePath = srcDir.getAbsolutePath();
         if (absolutePath.length() > 5) {
            if (!absolutePath.startsWith("/home/") || absolutePath.indexOf("/", 6) < absolutePath.length() - 1 && absolutePath.indexOf("/", 6) >= 0) {
               File[] files = srcDir.listFiles();

               for(int i = 0; i < files.length; ++i) {
                  if (files[i].isDirectory()) {
                     deleteObsoleteFiles(rootDir, files[i], seenFiles);
                  } else if (!seenFiles.contains(files[i])) {
                     deleteXmlBeansFile(files[i]);
                     deleteDirRecursively(rootDir, files[i].getParentFile());
                  }
               }

            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static void deleteXmlBeansFile(File file) {
      if (file.getName().endsWith(".java")) {
         file.delete();
      }

   }

   private static void deleteDirRecursively(File root, File dir) {
      for(String[] list = dir.list(); list != null && list.length == 0 && !dir.equals(root); list = dir.list()) {
         dir.delete();
         dir = dir.getParentFile();
      }

   }

   protected static File createTempDir() throws IOException {
      File tmpFile;
      try {
         tmpFile = new File(SystemProperties.getProperty("java.io.tmpdir"));
         tmpFile.mkdirs();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      tmpFile = File.createTempFile("xbean", (String)null);
      String path = tmpFile.getAbsolutePath();
      if (!path.endsWith(".tmp")) {
         throw new IOException("Error: createTempFile did not create a file ending with .tmp");
      } else {
         path = path.substring(0, path.length() - 4);
         File tmpSrcDir = null;

         for(int count = 0; count < 100; ++count) {
            String name = path + ".d" + (count == 0 ? "" : Integer.toString(count++));
            tmpSrcDir = new File(name);
            if (!tmpSrcDir.exists()) {
               boolean created = tmpSrcDir.mkdirs();

               assert created : "Could not create " + tmpSrcDir.getAbsolutePath();
               break;
            }
         }

         tmpFile.deleteOnExit();
         return tmpSrcDir;
      }
   }

   protected static void tryHardToDelete(File dir) {
      tryToDelete(dir);
      if (dir.exists()) {
         tryToDeleteLater(dir);
      }

   }

   private static void tryToDelete(File dir) {
      if (dir.exists()) {
         if (dir.isDirectory()) {
            String[] list = dir.list();
            if (list != null) {
               for(int i = 0; i < list.length; ++i) {
                  tryToDelete(new File(dir, list[i]));
               }
            }
         }

         if (!dir.delete()) {
            return;
         }
      }

   }

   private static boolean tryNowThatItsLater() {
      ArrayList files;
      synchronized(deleteFileQueue) {
         files = new ArrayList(deleteFileQueue);
         deleteFileQueue.clear();
      }

      List retry = new ArrayList();
      Iterator i = files.iterator();

      while(i.hasNext()) {
         File file = (File)i.next();
         tryToDelete(file);
         if (file.exists()) {
            retry.add(file);
         }
      }

      synchronized(deleteFileQueue) {
         if (triesRemaining > 0) {
            --triesRemaining;
         }

         if (triesRemaining > 0 && retry.size() != 0) {
            deleteFileQueue.addAll(retry);
         } else {
            triesRemaining = 0;
         }

         return triesRemaining <= 0;
      }
   }

   private static void giveUp() {
      synchronized(deleteFileQueue) {
         deleteFileQueue.clear();
         triesRemaining = 0;
      }
   }

   private static void tryToDeleteLater(File dir) {
      synchronized(deleteFileQueue) {
         deleteFileQueue.add(dir);
         if (triesRemaining == 0) {
            new Thread() {
               public void run() {
                  try {
                     while(!SchemaCodeGenerator.tryNowThatItsLater()) {
                        Thread.sleep(3000L);
                     }

                  } catch (InterruptedException var2) {
                     SchemaCodeGenerator.giveUp();
                  }
               }
            };
         }

         if (triesRemaining < 10) {
            triesRemaining = 10;
         }

      }
   }
}
