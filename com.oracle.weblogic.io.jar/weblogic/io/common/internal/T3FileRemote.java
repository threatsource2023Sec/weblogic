package weblogic.io.common.internal;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import weblogic.common.T3Exception;
import weblogic.common.T3ServicesDef;
import weblogic.io.common.T3File;
import weblogic.io.common.T3FileInputStream;
import weblogic.io.common.T3FileOutputStream;

public final class T3FileRemote extends File implements T3File {
   private static final long serialVersionUID = 5169544571923577797L;
   private static final int DEFAULT_BUFFERSIZE = 102400;
   private static final int DEFAULT_READ_AHEAD = 1;
   private static final int DEFAULT_WRITE_BEHIND = 1;
   private T3ServicesDef svc;
   private T3FileSystemProxy rfs;
   private String path;
   private String separator;
   private char separatorChar;
   private String pathSeparator;
   private char pathSeparatorChar;

   public T3FileRemote(T3ServicesDef svc, T3FileSystemProxy rfs, String path) {
      super(path);
      this.svc = svc;
      this.rfs = rfs;
      this.path = path;
      this.separator = rfs.separator();
      this.pathSeparator = rfs.pathSeparator();
      this.separatorChar = this.separator.charAt(0);
      this.pathSeparatorChar = this.pathSeparator.charAt(0);
   }

   public T3FileInputStream getFileInputStream() throws T3Exception {
      return new T3FileInputStreamRemote(this.rfs, this, 102400, 1);
   }

   public T3FileInputStream getFileInputStream(int bufferSize, int readAhead) throws T3Exception {
      return new T3FileInputStreamRemote(this.rfs, this, bufferSize, readAhead);
   }

   public T3FileOutputStream getFileOutputStream() throws T3Exception {
      return new T3FileOutputStreamRemote(this.rfs, this, 102400, 1);
   }

   public T3FileOutputStream getFileOutputStream(int bufferSize, int writeBehind) throws T3Exception {
      return new T3FileOutputStreamRemote(this.rfs, this, bufferSize, writeBehind);
   }

   public T3File extend(String suffix) {
      return new T3FileRemote(this.svc, this.rfs, this.path + this.separator + suffix);
   }

   public String getName() {
      return this.rfs.getName(this.path);
   }

   public String getPath() {
      return this.path;
   }

   public String getAbsolutePath() {
      return this.path;
   }

   public String getCanonicalPath() throws IOException {
      return this.rfs.getCanonicalPath(this.path);
   }

   public String getParent() {
      String par;
      try {
         String canPath = this.getCanonicalPath();
         par = this.rfs.getParent(canPath);
      } catch (IOException var3) {
         par = null;
      }

      return par;
   }

   public boolean exists() {
      return this.rfs.exists(this.path) || this.rfs.absoluteExists(this.path);
   }

   public boolean canWrite() {
      return this.rfs.canWrite(this.path);
   }

   public boolean canRead() {
      return this.rfs.canRead(this.path);
   }

   public boolean isFile() {
      return this.rfs.isFile(this.path);
   }

   public boolean isDirectory() {
      return this.rfs.isDirectory(this.path) || this.rfs.isAbsoluteDirectory(this.path);
   }

   public boolean isAbsolute() {
      return true;
   }

   public long lastModified() {
      return this.rfs.lastModified(this.path);
   }

   public long length() {
      return this.rfs.length(this.path);
   }

   public boolean mkdir() {
      return this.rfs.mkdir(this.path);
   }

   public boolean mkdirs() {
      return this.rfs.mkdirs(this.path);
   }

   public String[] list() {
      return this.rfs.list(this.path);
   }

   public String[] list(FilenameFilter filter) {
      return this.rfs.list(this.path, filter);
   }

   public boolean delete() {
      return this.rfs.delete(this.path);
   }

   public String toString() {
      return this.path;
   }

   public int hashCode() {
      return this.path.hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof T3FileRemote && this.rfs.equals(((T3FileRemote)obj).rfs) ? this.path.equals(((T3FileRemote)obj).getPath()) : false;
   }

   public boolean renameTo(T3File dest) {
      boolean success = false;
      if (dest instanceof T3FileRemote && this.rfs.equals(((T3FileRemote)dest).rfs)) {
         success = this.rfs.renameTo(this.path, ((T3FileRemote)dest).getPath());
      }

      return success;
   }
}
