package weblogic.io.common;

import weblogic.common.T3Exception;

/** @deprecated */
@Deprecated
public interface T3FileSystem {
   /** @deprecated */
   @Deprecated
   String separator();

   /** @deprecated */
   @Deprecated
   String pathSeparator();

   /** @deprecated */
   @Deprecated
   T3File getFile(String var1);

   T3File getFile(String var1, String var2);

   /** @deprecated */
   @Deprecated
   String getName();

   /** @deprecated */
   @Deprecated
   T3FileInputStream getFileInputStream(String var1) throws T3Exception;

   /** @deprecated */
   @Deprecated
   T3FileInputStream getFileInputStream(String var1, int var2, int var3) throws T3Exception;

   /** @deprecated */
   @Deprecated
   T3FileOutputStream getFileOutputStream(String var1) throws T3Exception;

   /** @deprecated */
   @Deprecated
   T3FileOutputStream getFileOutputStream(String var1, int var2, int var3) throws T3Exception;
}
