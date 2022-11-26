package weblogic.application.ddconvert;

import java.io.File;
import weblogic.application.archive.ApplicationArchive;
import weblogic.utils.jars.VirtualJarFile;

public interface Converter {
   void convertDDs(ConvertCtx var1, VirtualJarFile var2, File var3) throws DDConvertException;

   /** @deprecated */
   @Deprecated
   void convertDDs(ConvertCtx var1, ApplicationArchive var2, File var3) throws DDConvertException;

   void printStartMessage(String var1);

   void printEndMessage(String var1);
}
