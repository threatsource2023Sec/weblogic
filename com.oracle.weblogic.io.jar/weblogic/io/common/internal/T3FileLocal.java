package weblogic.io.common.internal;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import weblogic.common.T3Exception;
import weblogic.io.common.T3File;
import weblogic.io.common.T3FileInputStream;
import weblogic.io.common.T3FileOutputStream;

public final class T3FileLocal extends File implements T3File {
   private static final long serialVersionUID = 5975049963833859248L;
   private static final boolean debug = false;
   private File javaFile;
   private String pathFromMP;

   public T3FileLocal(String path) {
      super(path);
      this.pathFromMP = path;

      try {
         this.javaFile = new File(this.getCanonicalPath());
      } catch (IOException var3) {
         this.javaFile = new File(path);
      }

   }

   public T3FileLocal(String dirName, String path) {
      super(dirName, path);

      try {
         this.javaFile = new File(this.getCanonicalPath());
      } catch (IOException var4) {
         this.javaFile = new File(dirName, path);
      }

   }

   private File getTryFile() {
      Object tryFile;
      if (this.javaFile == null) {
         tryFile = this;
      } else {
         tryFile = this.javaFile;
      }

      return (File)tryFile;
   }

   public T3FileInputStream getFileInputStream() throws T3Exception {
      return new T3FileInputStreamLocal(this);
   }

   public T3FileInputStream getFileInputStream(int bufferSize, int readAhead) throws T3Exception {
      return new T3FileInputStreamLocal(this);
   }

   public T3FileOutputStream getFileOutputStream() throws T3Exception {
      return new T3FileOutputStreamLocal(this);
   }

   public T3FileOutputStream getFileOutputStream(int bufferSize, int writeBehind) throws T3Exception {
      return new T3FileOutputStreamLocal(this);
   }

   public T3File extend(String suffix) {
      return new T3FileLocal(this.pathFromMP + File.separator + suffix);
   }

   public String getName() {
      return this.getTryFile().getName();
   }

   public String getPath() {
      return this.pathFromMP;
   }

   public String getAbsolutePath() {
      return this.getTryFile().getAbsolutePath();
   }

   public String getParent() {
      return super.getParent();
   }

   public boolean exists() {
      return this.getTryFile().exists();
   }

   public boolean canWrite() {
      return this.getTryFile().canWrite();
   }

   public boolean canRead() {
      return this.getTryFile().canRead();
   }

   public boolean isFile() {
      return this.getTryFile().isFile();
   }

   public boolean isDirectory() {
      return this.getTryFile().isDirectory();
   }

   public long lastModified() {
      return this.getTryFile().lastModified();
   }

   public long length() {
      return this.getTryFile().length();
   }

   public boolean mkdir() {
      return this.getTryFile().mkdir();
   }

   public boolean mkdirs() {
      return this.getTryFile().mkdirs();
   }

   public String[] list() {
      return this.getTryFile().list();
   }

   public String[] list(FilenameFilter filter) {
      return this.getTryFile().list(filter);
   }

   public boolean delete() {
      return this.getTryFile().delete();
   }

   public int hashCode() {
      return this.getTryFile().hashCode();
   }

   public String toString() {
      return this.getTryFile().toString();
   }

   public boolean renameTo(T3File dest) {
      return dest instanceof T3FileLocal ? this.getTryFile().renameTo(((T3FileLocal)dest).javaFile) : false;
   }

   public boolean equals(Object obj) {
      return obj instanceof T3FileLocal ? this.getTryFile().equals(((T3FileLocal)obj).javaFile) : false;
   }
}
