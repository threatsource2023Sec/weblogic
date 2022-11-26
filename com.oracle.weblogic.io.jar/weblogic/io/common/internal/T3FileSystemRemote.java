package weblogic.io.common.internal;

import weblogic.common.T3Exception;
import weblogic.common.T3ServicesDef;
import weblogic.io.common.T3File;
import weblogic.io.common.T3FileInputStream;
import weblogic.io.common.T3FileOutputStream;
import weblogic.io.common.T3FileSystem;

public final class T3FileSystemRemote implements T3FileSystem {
   private T3FileSystemProxy rfs;
   private T3ServicesDef svc;
   private String separator = null;
   private String pathSeparator = null;

   public T3FileSystemRemote(T3ServicesDef svc, T3FileSystemProxy rfs) {
      this.svc = svc;
      this.rfs = rfs;
   }

   public String separator() {
      if (this.separator == null) {
         this.separator = this.rfs.separator();
      }

      return this.separator;
   }

   public String pathSeparator() {
      if (this.pathSeparator == null) {
         this.pathSeparator = this.rfs.pathSeparator();
      }

      return this.pathSeparator;
   }

   public String getName() {
      return this.rfs.getName();
   }

   public T3File getFile(String path) {
      return new T3FileRemote(this.svc, this.rfs, path);
   }

   public T3File getFile(String path, String name) {
      return this.getFile(path + this.separator() + name);
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
