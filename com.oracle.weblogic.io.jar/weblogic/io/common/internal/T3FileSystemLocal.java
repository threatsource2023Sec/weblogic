package weblogic.io.common.internal;

import java.io.File;
import weblogic.common.T3Exception;
import weblogic.io.common.T3File;
import weblogic.io.common.T3FileInputStream;
import weblogic.io.common.T3FileOutputStream;
import weblogic.io.common.T3FileSystem;

public class T3FileSystemLocal implements T3FileSystem {
   public String separator() {
      return File.separator;
   }

   public String pathSeparator() {
      return File.pathSeparator;
   }

   public T3File getFile(String path) {
      return new T3FileLocal(path);
   }

   public T3File getFile(String path, String name) {
      return this.getFile(path + this.separator() + name);
   }

   public String getName() {
      return "";
   }

   public T3FileInputStream getFileInputStream(String path) throws T3Exception {
      return this.getFile(path).getFileInputStream();
   }

   public T3FileInputStream getFileInputStream(String path, int bufferSize, int readAhead) throws T3Exception {
      return this.getFile(path).getFileInputStream(bufferSize, readAhead);
   }

   public T3FileOutputStream getFileOutputStream(String path) throws T3Exception {
      return this.getFile(path).getFileOutputStream();
   }

   public T3FileOutputStream getFileOutputStream(String path, int bufferSize, int writeBehind) throws T3Exception {
      return this.getFile(path).getFileOutputStream(bufferSize, writeBehind);
   }
}
