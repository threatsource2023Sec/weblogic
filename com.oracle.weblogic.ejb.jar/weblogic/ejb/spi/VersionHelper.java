package weblogic.ejb.spi;

import java.io.File;
import java.util.Collection;
import weblogic.utils.jars.VirtualJarFile;

public interface VersionHelper {
   boolean needsRecompile(String var1, ClassLoader var2) throws ClassNotFoundException;

   Collection needsRecompile(VirtualJarFile var1, boolean var2);

   Collection needsRecompile(File var1, boolean var2);

   boolean hasChecksum(File var1);

   void writeChecksum(File var1);
}
