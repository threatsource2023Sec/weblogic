package weblogic.xml.saaj.mime4j.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class SimpleTempStorage extends TempStorage {
   private TempPath rootPath = null;
   private Random random = new Random();

   public SimpleTempStorage() {
      this.rootPath = new SimpleTempPath(System.getProperty("java.io.tmpdir"));
   }

   private long getRandomLong() {
      long x;
      do {
         x = this.random.nextLong();
      } while(x == -2147483648L);

      return Math.abs(x);
   }

   private TempPath createTempPath(TempPath parent, String prefix) throws IOException {
      if (prefix == null) {
         prefix = "";
      }

      File p = null;
      int count = 1000;

      do {
         long n = this.getRandomLong();
         p = new File(parent.getAbsolutePath(), prefix + n);
         --count;
      } while(p.exists() && count > 0);

      if (!p.exists() && p.mkdirs()) {
         return new SimpleTempPath(p);
      } else {
         throw new IOException("Creating dir '" + p.getAbsolutePath() + "' failed.");
      }
   }

   private TempFile createTempFile(TempPath parent, String prefix, String suffix) throws IOException {
      if (prefix == null) {
         prefix = "";
      }

      if (suffix == null) {
         suffix = ".tmp";
      }

      File f = null;
      int count = 1000;
      synchronized(this) {
         do {
            long n = this.random.nextLong();
            f = new File(parent.getAbsolutePath(), prefix + n + suffix);
            --count;
         } while(f.exists() && count > 0);

         if (f.exists()) {
            throw new IOException("Creating temp file failed: Unable to find unique file name");
         }

         try {
            f.createNewFile();
         } catch (IOException var10) {
            throw new IOException("Creating dir '" + f.getAbsolutePath() + "' failed.");
         }
      }

      return new SimpleTempFile(f);
   }

   public TempPath getRootTempPath() {
      return this.rootPath;
   }

   private class SimpleTempFile implements TempFile {
      private File file;

      private SimpleTempFile(String file) {
         this.file = null;
         this.file = new File(file);
         this.file.deleteOnExit();
      }

      private SimpleTempFile(File file) {
         this.file = null;
         this.file = file;
         this.file.deleteOnExit();
      }

      public InputStream getInputStream() throws IOException {
         return new BufferedInputStream(new FileInputStream(this.file));
      }

      public OutputStream getOutputStream() throws IOException {
         return new BufferedOutputStream(new FileOutputStream(this.file));
      }

      public String getAbsolutePath() {
         return this.file.getAbsolutePath();
      }

      public void delete() {
      }

      public boolean isInMemory() {
         return false;
      }

      public long length() {
         return this.file.length();
      }

      // $FF: synthetic method
      SimpleTempFile(File x1, Object x2) {
         this((File)x1);
      }
   }

   private class SimpleTempPath implements TempPath {
      private File path;

      private SimpleTempPath(String path) {
         this.path = null;
         this.path = new File(path);
      }

      private SimpleTempPath(File path) {
         this.path = null;
         this.path = path;
      }

      public TempFile createTempFile() throws IOException {
         return SimpleTempStorage.this.createTempFile(this, (String)null, (String)null);
      }

      public TempFile createTempFile(String prefix, String suffix) throws IOException {
         return SimpleTempStorage.this.createTempFile(this, prefix, suffix);
      }

      public TempFile createTempFile(String prefix, String suffix, boolean allowInMemory) throws IOException {
         return SimpleTempStorage.this.createTempFile(this, prefix, suffix);
      }

      public String getAbsolutePath() {
         return this.path.getAbsolutePath();
      }

      public void delete() {
      }

      public TempPath createTempPath() throws IOException {
         return SimpleTempStorage.this.createTempPath(this, (String)null);
      }

      public TempPath createTempPath(String prefix) throws IOException {
         return SimpleTempStorage.this.createTempPath(this, prefix);
      }

      // $FF: synthetic method
      SimpleTempPath(String x1, Object x2) {
         this((String)x1);
      }

      // $FF: synthetic method
      SimpleTempPath(File x1, Object x2) {
         this((File)x1);
      }
   }
}
