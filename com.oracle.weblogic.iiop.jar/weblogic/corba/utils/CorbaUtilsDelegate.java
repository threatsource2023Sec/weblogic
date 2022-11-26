package weblogic.corba.utils;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CorbaUtilsDelegate {
   Class loadClass(String var1, String var2, ClassLoader var3) throws ClassNotFoundException;

   String getAnnotation(ClassLoader var1);

   void verifyClassPermitted(Class var1);

   void checkLegacyBlacklistIfNeeded(String var1);
}
