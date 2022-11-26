package weblogic.io.common;

import java.io.FilenameFilter;
import java.io.IOException;
import weblogic.common.T3Exception;

/** @deprecated */
@Deprecated
public interface T3File {
   /** @deprecated */
   @Deprecated
   T3FileInputStream getFileInputStream() throws T3Exception;

   /** @deprecated */
   @Deprecated
   T3FileInputStream getFileInputStream(int var1, int var2) throws T3Exception;

   /** @deprecated */
   @Deprecated
   T3FileOutputStream getFileOutputStream() throws T3Exception;

   /** @deprecated */
   @Deprecated
   T3FileOutputStream getFileOutputStream(int var1, int var2) throws T3Exception;

   /** @deprecated */
   @Deprecated
   T3File extend(String var1);

   /** @deprecated */
   @Deprecated
   String getName();

   /** @deprecated */
   @Deprecated
   String getPath();

   /** @deprecated */
   @Deprecated
   String getAbsolutePath();

   /** @deprecated */
   @Deprecated
   String getCanonicalPath() throws IOException;

   /** @deprecated */
   @Deprecated
   String getParent();

   /** @deprecated */
   @Deprecated
   boolean exists() throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean canWrite() throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean canRead() throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean isFile() throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean isDirectory() throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean isAbsolute();

   /** @deprecated */
   @Deprecated
   long lastModified() throws SecurityException;

   /** @deprecated */
   @Deprecated
   long length() throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean mkdir() throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean renameTo(T3File var1) throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean mkdirs() throws SecurityException;

   /** @deprecated */
   @Deprecated
   String[] list() throws SecurityException;

   /** @deprecated */
   @Deprecated
   String[] list(FilenameFilter var1) throws SecurityException;

   /** @deprecated */
   @Deprecated
   boolean delete() throws SecurityException;

   /** @deprecated */
   @Deprecated
   int hashCode();

   /** @deprecated */
   @Deprecated
   boolean equals(Object var1);

   /** @deprecated */
   @Deprecated
   String toString();
}
