package weblogic.io.common.internal;

import java.security.AccessController;
import weblogic.common.T3Exception;
import weblogic.io.common.T3File;
import weblogic.io.common.T3FileInputStream;
import weblogic.io.common.T3FileOutputStream;
import weblogic.management.configuration.FileT3MBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class T3FileSystemLocalMountPoint extends T3FileSystemLocal {
   private String fileSystemName = null;
   private String mountPoint = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static String getFileSystemName(String path) {
      if (path != null && path.length() > 2) {
         char separator = path.charAt(0);
         int fsnEnd = path.indexOf(92, 2);
         int fsnEnd2 = path.indexOf(47, 2);
         if ((separator == '/' || separator == '\\') && path.charAt(1) == separator) {
            if (fsnEnd != -1) {
               return path.substring(2, fsnEnd);
            } else {
               return fsnEnd2 != -1 ? path.substring(2, fsnEnd2) : "";
            }
         } else {
            return "";
         }
      } else {
         return "";
      }
   }

   public static String getFileName(String path) {
      if (path != null && path.length() > 2) {
         char separator = path.charAt(0);
         int fsnEnd = path.indexOf(92, 2);
         int fsnEnd2 = path.indexOf(47, 2);
         if ((separator == '/' || separator == '\\') && path.charAt(1) == separator) {
            if (fsnEnd != -1) {
               return path.substring(fsnEnd + 1);
            } else {
               return fsnEnd2 != -1 ? path.substring(fsnEnd2 + 1) : path;
            }
         } else {
            return path;
         }
      } else {
         return path;
      }
   }

   public T3FileSystemLocalMountPoint(String filSysName) throws T3Exception {
      FileT3MBean mbean = null;
      this.fileSystemName = filSysName;
      mbean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupFileT3(this.fileSystemName);
      if (mbean == null) {
         throw new T3Exception("Unknown file system " + this.fileSystemName);
      } else {
         this.mountPoint = mbean.getPath() + this.separator();
         if (!FileService.getFileService().isFileSystemMounted(filSysName)) {
            throw new T3Exception("Unknown file system " + this.fileSystemName);
         }
      }
   }

   public T3File getFile(String path) {
      return new T3FileLocal(this.mountPoint + path);
   }

   public T3File getFile(String path, String name) {
      return this.getFile(this.mountPoint + path + this.separator() + name);
   }

   public String getName() {
      return this.fileSystemName;
   }

   public T3FileInputStream getFileInputStream(String path) throws T3Exception {
      path = this.stripLeadingSlash(path);
      return this.getFile(path).getFileInputStream();
   }

   public T3FileInputStream getFileInputStream(String path, int bufferSize, int readAhead) throws T3Exception {
      path = this.stripLeadingSlash(path);
      return this.getFile(path).getFileInputStream(bufferSize, readAhead);
   }

   public T3FileOutputStream getFileOutputStream(String path) throws T3Exception {
      path = this.stripLeadingSlash(path);
      return this.getFile(path).getFileOutputStream();
   }

   public T3FileOutputStream getFileOutputStream(String path, int bufferSize, int writeBehind) throws T3Exception {
      path = this.stripLeadingSlash(path);
      return this.getFile(path).getFileOutputStream(bufferSize, writeBehind);
   }

   private String stripLeadingSlash(String path) {
      return path.startsWith(this.pathSeparator()) ? path.substring(1) : path;
   }
}
