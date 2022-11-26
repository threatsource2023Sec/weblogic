package weblogic.store.admin.util;

import java.io.File;
import java.security.AccessController;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.DomainDir;
import weblogic.management.PartitionDir;
import weblogic.management.RuntimeDir;
import weblogic.management.RuntimeDir.Current;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.io.file.Heap;

public class PartitionFileSystemUtils {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ComponentInvocationContextManager cicm;

   public static ManagedInvocationContext pushStoreCIC(ComponentInvocationContext cic) {
      ManagedInvocationContext mic = null;
      if (cic != null && cicm != null) {
         if (cic.equals(cicm.getCurrentComponentInvocationContext())) {
            mic = null;
         } else {
            mic = cicm.setCurrentComponentInvocationContext(cic);
         }
      } else {
         mic = null;
      }

      return mic;
   }

   public static String locateDefaultStoreDirectory(ComponentInvocationContext cic, String dirName, String serverName, String storeName) {
      String storeDirPath = null;
      if (dirName != null && dirName.length() != 0) {
         storeDirPath = canonicalizeDirectoryName(dirName);
      } else {
         storeDirPath = DomainDir.getPathRelativeServersStoreDataDir(serverName, storeName);
      }

      return storeDirPath;
   }

   public static String locateEjbTimerStoreDirectory(ComponentInvocationContext cic, String dirName, String storeName) {
      String storeDirPath = null;
      StringBuilder storePath = new StringBuilder();
      ManagedInvocationContext mic = pushStoreCIC(cic);
      Throwable var6 = null;

      try {
         ComponentInvocationContext curCIC = cicm.getCurrentComponentInvocationContext();
         RuntimeDir runtimeDir = Current.get();
         storePath.append("store");
         if (dirName != null && dirName.trim().length() > 0) {
            File storeDir = new File(dirName);
            if (isRelativePath(storeDir)) {
               addToPath(storePath, dirName);
            } else if (storeDir.compareTo(new File(((RuntimeDir)runtimeDir).getRootDir())) != 0) {
               String partRoot = storeDir.getAbsolutePath();
               if (!curCIC.isGlobalRuntime()) {
                  partRoot = partRoot + File.separator + getPartitionStem(curCIC.getPartitionName());
               }

               runtimeDir = new PartitionDir(partRoot, curCIC.getPartitionName());
            }
         }

         storePath.append(File.separator).append(storeName);
         storeDirPath = ((RuntimeDir)runtimeDir).getPathRelativeRootDir(storePath.toString());
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var17) {
                  var6.addSuppressed(var17);
               }
            } else {
               mic.close();
            }
         }

      }

      return storeDirPath;
   }

   public static String locateStoreDirectory(ComponentInvocationContext cic, String serverName, String dirName, String storeName) {
      boolean wasAbsolutePath = false;
      String storeDirPath = null;
      StringBuilder storePath = new StringBuilder();
      ManagedInvocationContext mic = pushStoreCIC(cic);
      Throwable var8 = null;

      try {
         ComponentInvocationContext curCIC = cicm.getCurrentComponentInvocationContext();
         RuntimeDir runtimeDir = Current.get();
         if (!curCIC.isGlobalRuntime()) {
            storePath.append("store");
         }

         if (dirName != null && dirName.length() > 0) {
            File storeDir = new File(dirName);
            if (isRelativePath(storeDir)) {
               addToPath(storePath, dirName);
            } else {
               wasAbsolutePath = true;
               if (storeDir.compareTo(new File(((RuntimeDir)runtimeDir).getRootDir())) != 0) {
                  String partRoot = storeDir.getAbsolutePath();
                  if (!curCIC.isGlobalRuntime()) {
                     partRoot = partRoot + File.separator + getPartitionStem(curCIC.getPartitionName());
                  }

                  runtimeDir = new PartitionDir(partRoot, curCIC.getPartitionName());
               }
            }
         }

         if (!curCIC.isGlobalRuntime()) {
            addToPath(storePath, storeName);
            storeDirPath = ((RuntimeDir)runtimeDir).getPathRelativeRootDir(storePath.toString());
         } else if (storePath.length() == 0) {
            if (wasAbsolutePath) {
               storeDirPath = ((RuntimeDir)runtimeDir).getRootDir();
            } else {
               storeDirPath = ((RuntimeDir)runtimeDir).getPathRelativeServersStoreDataDir(serverName, storeName);
            }
         } else {
            storeDirPath = ((RuntimeDir)runtimeDir).getPathRelativeRootDir(storePath.toString());
         }
      } catch (Throwable var20) {
         var8 = var20;
         throw var20;
      } finally {
         if (mic != null) {
            if (var8 != null) {
               try {
                  mic.close();
               } catch (Throwable var19) {
                  var8.addSuppressed(var19);
               }
            } else {
               mic.close();
            }
         }

      }

      return storeDirPath;
   }

   public static String locatePagingDirectory(ComponentInvocationContext cic, String serverName, String pagingDirStr) {
      boolean wasAbsolutePath = false;
      String locatedPagingDir = null;
      StringBuilder pagingPath = new StringBuilder();
      ManagedInvocationContext mic = pushStoreCIC(cic);
      Throwable var7 = null;

      try {
         ComponentInvocationContext curCIC = cicm.getCurrentComponentInvocationContext();
         RuntimeDir runtimeDir = Current.get();
         if (!curCIC.isGlobalRuntime()) {
            pagingPath.append("paging");
         }

         if (pagingDirStr != null && pagingDirStr.length() > 0) {
            File pagingDir = new File(pagingDirStr);
            if (isRelativePath(pagingDir)) {
               addToPath(pagingPath, pagingDirStr);
            } else {
               wasAbsolutePath = true;
               if (pagingDir.compareTo(new File(((RuntimeDir)runtimeDir).getRootDir())) != 0) {
                  String partRoot = pagingDir.getAbsolutePath();
                  if (!curCIC.isGlobalRuntime()) {
                     partRoot = partRoot + File.separator + getPartitionStem(curCIC.getPartitionName());
                  }

                  runtimeDir = new PartitionDir(partRoot, curCIC.getPartitionName());
               }
            }
         }

         if (!curCIC.isGlobalRuntime()) {
            locatedPagingDir = ((RuntimeDir)runtimeDir).getPathRelativeRootDir(pagingPath.toString());
         } else if (pagingPath.length() == 0) {
            if (wasAbsolutePath) {
               locatedPagingDir = ((RuntimeDir)runtimeDir).getRootDir();
            } else {
               locatedPagingDir = ((RuntimeDir)runtimeDir).getTempDirForServer(serverName);
            }
         } else {
            locatedPagingDir = ((RuntimeDir)runtimeDir).getPathRelativeRootDir(pagingPath.toString());
         }
      } catch (Throwable var19) {
         var7 = var19;
         throw var19;
      } finally {
         if (mic != null) {
            if (var7 != null) {
               try {
                  mic.close();
               } catch (Throwable var18) {
                  var7.addSuppressed(var18);
               }
            } else {
               mic.close();
            }
         }

      }

      return locatedPagingDir;
   }

   public static String locateCacheDirectory(ComponentInvocationContext cic, String domainName, String dirName) {
      String cacheDirPath = null;
      boolean wasAbsolutePath = false;
      StringBuilder cachePath = new StringBuilder();
      ManagedInvocationContext mic = pushStoreCIC(cic);
      Throwable var8 = null;

      try {
         ComponentInvocationContext curCIC = cicm.getCurrentComponentInvocationContext();
         RuntimeDir runtimeDir = Current.get();
         String cacheDirStr;
         if (!curCIC.isGlobalRuntime()) {
            cachePath.append("tmp");
            if (dirName != null && dirName.length() != 0) {
               cacheDirStr = dirName;
            } else {
               cacheDirStr = Heap.OS_TMP_DIR;
               if (domainName != null && domainName.length() > 0) {
                  cacheDirStr = cacheDirStr + File.separator + domainName;
               }
            }
         } else {
            cacheDirStr = dirName;
         }

         if (cacheDirStr != null && cacheDirStr.length() > 0) {
            File cacheDir = new File(cacheDirStr);
            if (isRelativePath(cacheDir)) {
               addToPath(cachePath, cacheDirStr);
            } else {
               wasAbsolutePath = true;
               if (cacheDir.compareTo(new File(((RuntimeDir)runtimeDir).getRootDir())) != 0) {
                  String partRoot = cacheDir.getAbsolutePath();
                  if (!curCIC.isGlobalRuntime()) {
                     partRoot = partRoot + File.separator + getPartitionStem(curCIC.getPartitionName());
                  }

                  runtimeDir = new PartitionDir(partRoot, curCIC.getPartitionName());
               }
            }
         }

         if (!curCIC.isGlobalRuntime()) {
            cacheDirPath = ((RuntimeDir)runtimeDir).getPathRelativeRootDir(cachePath.toString());
         } else if (cachePath.length() == 0) {
            if (wasAbsolutePath) {
               cacheDirPath = ((RuntimeDir)runtimeDir).getRootDir();
            }
         } else {
            cacheDirPath = ((RuntimeDir)runtimeDir).getPathRelativeRootDir(cachePath.toString());
         }
      } catch (Throwable var20) {
         var8 = var20;
         throw var20;
      } finally {
         if (mic != null) {
            if (var8 != null) {
               try {
                  mic.close();
               } catch (Throwable var19) {
                  var8.addSuppressed(var19);
               }
            } else {
               mic.close();
            }
         }

      }

      return cacheDirPath;
   }

   public static String canonicalizeDirectoryName(String dirName) {
      if (dirName != null && dirName.length() > 0) {
         File dirFile = new File(dirName);
         if (isRelativePath(dirFile)) {
            dirName = DomainDir.getPathRelativeRootDir(dirName);
         }
      }

      return dirName;
   }

   private static StringBuilder addToPath(StringBuilder path, String pathElement) {
      if (path != null) {
         if (path.length() > 0) {
            path.append(File.separator);
         }

         path.append(pathElement);
      }

      return path;
   }

   private static boolean isRelativePath(File dirPath) {
      return !dirPath.isAbsolute() && !dirPath.getPath().startsWith(File.separator);
   }

   private static String getPartitionStem(String partitionName) {
      StringBuilder sb = new StringBuilder();
      sb.append("partitions");
      sb.append(File.separator).append(partitionName);
      sb.append(File.separator).append("system");
      return sb.toString();
   }

   static {
      cicm = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }
}
