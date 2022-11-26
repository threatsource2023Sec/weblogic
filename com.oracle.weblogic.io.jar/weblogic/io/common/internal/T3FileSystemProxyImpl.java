package weblogic.io.common.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import weblogic.common.T3Exception;
import weblogic.common.T3MiscLogger;
import weblogic.common.T3ServicesDef;
import weblogic.t3.srvr.T3Srvr;

public class T3FileSystemProxyImpl implements T3FileSystemProxy {
   private String fileSystemName;
   private String prefix;
   private String canonicalPrefix;
   private T3ServicesDef svc;

   public String getName() {
      return this.fileSystemName;
   }

   public T3FileSystemProxyImpl(String fileSystemName, String prefix) {
      this.fileSystemName = fileSystemName;
      this.svc = T3Srvr.getT3Srvr().getT3Services();
      this.prefix = prefix;

      try {
         this.canonicalPrefix = this.validatePath(prefix);
      } catch (IOException var4) {
         this.canonicalPrefix = null;
         T3MiscLogger.logBadCreate(prefix, var4);
         return;
      }

      T3MiscLogger.logCreate(prefix);
   }

   private String validatePath(String prefix) throws IOException {
      String newCanonicalPrefix;
      File pfile;
      try {
         pfile = new File(prefix);
         newCanonicalPrefix = pfile.getCanonicalPath();
      } catch (IOException var5) {
         throw new IOException("Problem with path " + prefix + ", " + var5);
      }

      if (!pfile.isDirectory()) {
         throw new IOException("Problem with path " + newCanonicalPrefix + ", it is NOT a directory");
      } else {
         return newCanonicalPrefix;
      }
   }

   private String getCanonicalPrefix() throws IOException {
      if (this.canonicalPrefix == null) {
         throw new IOException();
      } else {
         return this.canonicalPrefix;
      }
   }

   private File getActualFile(String path) throws IllegalArgumentException {
      File candidate = new File(this.prefix + File.separator + path);

      try {
         String canonicalPath = candidate.getCanonicalPath();
         if (!canonicalPath.startsWith(this.getCanonicalPrefix())) {
            throw new IllegalArgumentException("Remote file name " + path + " references above the mount point");
         }
      } catch (IOException var4) {
      }

      return candidate;
   }

   private File getAbsoluteFile(String path) throws IllegalArgumentException {
      return this.getActualFile(path);
   }

   public boolean absoluteExists(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.exists();
   }

   public boolean isAbsoluteDirectory(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.isDirectory();
   }

   public String separator() {
      return File.separator;
   }

   public String pathSeparator() {
      return File.pathSeparator;
   }

   public OneWayInputServer createInputStream(OneWayInputClient onewayClObj, String path, int bufferSize, int readAhead) throws T3Exception {
      File actualFile = this.getAbsoluteFile(path);
      InputStream fis = null;

      try {
         fis = new FileInputStream(actualFile);
      } catch (FileNotFoundException var8) {
         T3MiscLogger.logFindRemote(actualFile.getPath(), var8);
         throw new T3Exception("Unable to find remote file " + path);
      }

      T3RemoteInputStreamProxy isp = new T3RemoteInputStreamProxy(this.fileSystemName + ":" + path, fis, bufferSize, readAhead, onewayClObj);
      return isp;
   }

   public OneWayOutputServer createOutputStream(OneWayOutputClient onewayClObj, String path, int bufferSize) throws T3Exception {
      File actualFile = this.getAbsoluteFile(path);
      OutputStream fos = null;

      try {
         fos = new FileOutputStream(actualFile);
      } catch (IOException var7) {
         T3MiscLogger.logOpenRemote(actualFile.getPath(), var7);
         throw new T3Exception("Unable to open remote file " + path);
      }

      T3RemoteOutputStreamProxy osp = new T3RemoteOutputStreamProxy(this.fileSystemName + ":" + path, fos, bufferSize, onewayClObj);
      return osp;
   }

   public String getName(String path) {
      if (path != null && !path.equals("")) {
         File tempFile = this.getAbsoluteFile(path);
         return tempFile.getName();
      } else {
         return this.fileSystemName;
      }
   }

   public String getCanonicalPath(String path) throws IOException {
      File tempFile = this.getAbsoluteFile(path);
      String cp = tempFile.getCanonicalPath();
      int index = cp.indexOf(this.getCanonicalPrefix());
      if (index != -1) {
         return this.getCanonicalPrefix().length() >= cp.length() ? "" : cp.substring(this.getCanonicalPrefix().length() + 1);
      } else {
         throw new IOException("Invalid path name");
      }
   }

   public String getParent(String path) {
      File tempFile = this.getAbsoluteFile(path);
      String prt = tempFile.getParent();
      return prt != null && prt.length() > this.prefix.length() ? prt.substring(this.prefix.length() + 1) : null;
   }

   public boolean exists(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.exists();
   }

   public boolean canWrite(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.canWrite();
   }

   public boolean canRead(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.canRead();
   }

   public boolean isFile(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.isFile();
   }

   public boolean isDirectory(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.isDirectory();
   }

   public long lastModified(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.lastModified();
   }

   public long length(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.length();
   }

   public boolean mkdir(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.mkdir();
   }

   public boolean mkdirs(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.mkdirs();
   }

   public String[] list(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.list();
   }

   public String[] list(String path, FilenameFilter filter) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.list(filter);
   }

   public boolean delete(String path) {
      File tempFile = this.getAbsoluteFile(path);
      return tempFile.delete();
   }

   public boolean renameTo(String oldPath, String newPath) {
      File oldFile = this.getAbsoluteFile(oldPath);
      File newFile = this.getAbsoluteFile(newPath);
      return oldFile.renameTo(newFile);
   }
}
