package weblogic.core.base.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ClassLoaderService {
   String APPLICATION = "Application";

   Class loadClass(String var1, String var2, boolean var3) throws ClassNotFoundException;

   Class loadClass(String var1, String var2, String var3, ClassLoader var4) throws ClassNotFoundException;

   Class loadClass(String var1, String var2, String var3, ClassLoader var4, boolean var5) throws ClassNotFoundException;

   Class loadClass(String var1, String var2, String var3) throws ClassNotFoundException;

   Class loadClass(String var1, String var2) throws ClassNotFoundException;
}
