package weblogic.io.common.internal;

import java.io.FilenameFilter;
import java.io.IOException;
import java.rmi.Remote;
import weblogic.common.T3Exception;

public interface T3FileSystemProxy extends Remote {
   String separator();

   String pathSeparator();

   String getName();

   String getName(String var1);

   String getCanonicalPath(String var1) throws IOException;

   String getParent(String var1);

   boolean exists(String var1);

   boolean canWrite(String var1);

   boolean canRead(String var1);

   boolean isFile(String var1);

   boolean isDirectory(String var1);

   long lastModified(String var1);

   long length(String var1);

   boolean mkdir(String var1);

   boolean mkdirs(String var1);

   String[] list(String var1);

   String[] list(String var1, FilenameFilter var2);

   boolean delete(String var1);

   boolean renameTo(String var1, String var2);

   OneWayInputServer createInputStream(OneWayInputClient var1, String var2, int var3, int var4) throws T3Exception;

   OneWayOutputServer createOutputStream(OneWayOutputClient var1, String var2, int var3) throws T3Exception;

   boolean absoluteExists(String var1);

   boolean isAbsoluteDirectory(String var1);
}
