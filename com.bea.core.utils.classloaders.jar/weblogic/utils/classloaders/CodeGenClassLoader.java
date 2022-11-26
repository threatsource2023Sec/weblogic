package weblogic.utils.classloaders;

import java.net.URL;

public interface CodeGenClassLoader {
   Class defineCodeGenClass(String var1, byte[] var2, URL var3) throws ClassFormatError, ClassNotFoundException;

   Class getLoadedClass(String var1);
}
