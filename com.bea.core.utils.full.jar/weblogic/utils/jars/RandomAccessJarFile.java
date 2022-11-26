package weblogic.utils.jars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import weblogic.utils.FileUtils;

public class RandomAccessJarFile {
   private static final boolean DEBUG = false;
   public static final String ARCHIVE_SEPARATOR = "#";
   private File innerJarFile;
   private File outerJarFile;
   private File jarDir;
   private File outerTempJarDir;
   private boolean cleanedUp;
   private boolean dirty;

   public RandomAccessJarFile(String jarFilePath) throws IOException {
      this(new File(jarFilePath));
   }

   public RandomAccessJarFile(File jarFile) throws IOException {
      this(jarFile.getAbsoluteFile().getParentFile(), jarFile);
   }

   public RandomAccessJarFile(File tmpDir, File jarFile) throws IOException {
      this.cleanedUp = false;
      this.dirty = false;
      File tmpFile = new File(tmpDir.getAbsolutePath());
      String p = jarFile.getPath();
      String prefix;
      if (isNestedArchive(p)) {
         this.outerJarFile = new File(this.outerArchive(p));
         this.innerJarFile = new File(this.innerArchive(p));
         this.setTemporaryDir(tmpFile, false, "");
         prefix = "";
         if (this.innerJarFile.getParent() != null) {
            prefix = File.separator + this.innerJarFile.getParent();
         }

         this.setTemporaryDir(tmpFile, true, prefix);
      } else {
         this.innerJarFile = new File(this.innerArchive(p));
         this.setTemporaryDir(tmpFile, false, "");
      }

      if (isNestedArchive(p)) {
         this.copyJarToJarDir(this.outerJarFile, this.outerTempJarDir);
         prefix = this.outerTempJarDir.getAbsolutePath();
         this.innerJarFile = new File(prefix + File.separator + this.innerArchive(p));
      }

      if (this.innerJarFile.exists()) {
         this.copyJarToJarDir(this.innerJarFile, this.jarDir);
      }

   }

   public OutputStream writeEntry(String pathName, boolean overwrite) throws IOException {
      File entry = new File(this.jarDir, pathName);
      File parentDir = entry.getParentFile();
      if (!parentDir.exists() && !parentDir.mkdirs()) {
         this.cleanUp();
         throw new IOException("Cannot create the temporary directory :" + parentDir.getAbsolutePath() + ".  Please ensure that you have write permission to create this directory.");
      } else {
         this.dirty = true;
         return new FileOutputStream(entry);
      }
   }

   public InputStream readEntry(String pathName) throws IOException {
      File jarEntry = new File(this.jarDir, pathName);
      return new FileInputStream(jarEntry);
   }

   public boolean deleteEntry(String pathName) {
      File jarEntry = new File(this.jarDir, pathName);
      return jarEntry.delete();
   }

   private void save(String jar, File jarDir) throws IOException {
      if (this.dirty) {
         JarFileUtils.createJarFileFromDirectory(jar, jarDir);
      }
   }

   public void close() throws IOException {
      if (this.dirty) {
         this.save(this.innerJarFile.getAbsolutePath(), this.jarDir);
         if (this.outerJarFile != null) {
            this.save(this.outerJarFile.getAbsolutePath(), this.outerTempJarDir);
         }
      }

      this.dirty = false;
      this.cleanUp();
   }

   private void setTemporaryDir(File tmpDirParent, boolean isOuterTempJarDir, String postfix) throws IOException {
      try {
         Thread.currentThread();
         Thread.sleep(50L);
      } catch (InterruptedException var5) {
      }

      File dir = new File(tmpDirParent, "jar" + Long.toString(System.currentTimeMillis() % 10000L) + postfix);
      if (dir.exists()) {
         FileUtils.remove(dir);
      }

      if (!dir.mkdirs() && !dir.exists()) {
         throw new IOException("Could not create temporary working directory:" + dir.getAbsolutePath() + ".  Please ensure that the parent directory exists and will allow subdirectories to be created.");
      } else {
         if (isOuterTempJarDir) {
            this.outerTempJarDir = dir;
         } else {
            this.jarDir = dir;
         }

      }
   }

   private void copyJarToJarDir(File jarFile, File dir) throws IOException {
      ZipInputStream jarIn = new ZipInputStream(new FileInputStream(jarFile));

      try {
         ZipEntry ent;
         try {
            while((ent = jarIn.getNextEntry()) != null) {
               String entJarPath = ent.getName();
               File entJarFile = new File(entJarPath);
               boolean entJarFileIsDir = entJarPath.endsWith("/") || entJarPath.endsWith("\\");
               File parent;
               if (entJarFile.getParent() != null) {
                  parent = new File(dir, entJarFile.getParent());
                  parent.mkdirs();
               }

               if (entJarFile.getParent() == null && entJarFileIsDir) {
                  parent = new File(dir, entJarPath);
                  parent.mkdirs();
               }

               if (!entJarFileIsDir) {
                  FileUtils.writeToFile(jarIn, new File(dir, entJarPath));
               }
            }
         } catch (IOException var12) {
            this.cleanUp();
            throw var12;
         }
      } finally {
         jarIn.close();
      }

   }

   private void cleanUp() {
      FileUtils.remove(this.jarDir);
      if (this.outerJarFile != null) {
         FileUtils.remove(this.outerTempJarDir);
      }

      this.cleanedUp = true;
   }

   public void finalize() {
      if (!this.cleanedUp) {
         this.cleanUp();
      }

   }

   private static boolean isNestedArchive(String filename) {
      return filename.indexOf("#") != -1;
   }

   private String outerArchive(String filename) {
      return isNestedArchive(filename) ? filename.substring(0, filename.indexOf("#")) : null;
   }

   private String innerArchive(String filename) {
      return isNestedArchive(filename) ? filename.substring(filename.indexOf("#") + 1, filename.length()) : filename;
   }
}
