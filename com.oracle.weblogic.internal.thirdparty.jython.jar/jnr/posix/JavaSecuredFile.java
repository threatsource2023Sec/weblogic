package jnr.posix;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;

public class JavaSecuredFile extends File {
   public JavaSecuredFile(String pathname) {
      super(pathname);
   }

   public JavaSecuredFile(String parent, String child) {
      super(parent, child);
   }

   public JavaSecuredFile(File parent, String child) {
      super(parent, child);
   }

   public JavaSecuredFile(URI uri) {
      super(uri);
   }

   public File getParentFile() {
      String path = this.getParent();
      return path == null ? null : new JavaSecuredFile(path);
   }

   public File getAbsoluteFile() {
      String path = this.getAbsolutePath();
      return path == null ? null : new JavaSecuredFile(path);
   }

   public File getCanonicalFile() throws IOException {
      String path = this.getCanonicalPath();
      return path == null ? null : new JavaSecuredFile(path);
   }

   public boolean canRead() {
      try {
         return super.canRead();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean canWrite() {
      try {
         return super.canWrite();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean exists() {
      try {
         return super.exists();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean isDirectory() {
      try {
         return super.isDirectory();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean isFile() {
      try {
         return super.isFile();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean isHidden() {
      try {
         return super.isHidden();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean delete() {
      try {
         return super.delete();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean mkdir() {
      try {
         return super.mkdir();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean mkdirs() {
      try {
         return super.mkdirs();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public boolean renameTo(File dest) {
      try {
         return super.renameTo(dest);
      } catch (SecurityException var3) {
         return false;
      }
   }

   public boolean setLastModified(long time) {
      try {
         return super.setLastModified(time);
      } catch (SecurityException var4) {
         return false;
      }
   }

   public boolean setReadOnly() {
      try {
         return super.setReadOnly();
      } catch (SecurityException var2) {
         return false;
      }
   }

   public String getCanonicalPath() throws IOException {
      try {
         return super.getCanonicalPath();
      } catch (SecurityException var2) {
         throw new IOException(var2);
      }
   }

   public boolean createNewFile() throws IOException {
      try {
         return super.createNewFile();
      } catch (SecurityException var2) {
         throw new IOException(var2);
      }
   }

   public String[] list() {
      try {
         return super.list();
      } catch (SecurityException var2) {
         return null;
      }
   }

   public String[] list(FilenameFilter filter) {
      try {
         return super.list(filter);
      } catch (SecurityException var3) {
         return null;
      }
   }

   public File[] listFiles() {
      try {
         return super.listFiles();
      } catch (SecurityException var2) {
         return null;
      }
   }

   public File[] listFiles(FileFilter filter) {
      try {
         return super.listFiles(filter);
      } catch (SecurityException var3) {
         return null;
      }
   }

   public long lastModified() {
      try {
         return super.lastModified();
      } catch (SecurityException var2) {
         return 0L;
      }
   }

   public long length() {
      try {
         return super.length();
      } catch (SecurityException var2) {
         return 0L;
      }
   }
}
