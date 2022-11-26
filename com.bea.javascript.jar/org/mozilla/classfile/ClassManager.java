package org.mozilla.classfile;

import java.lang.reflect.Method;

/** @deprecated */
public class ClassManager {
   // $FF: synthetic field
   static Class class$java$lang$String;
   // $FF: synthetic field
   static Class array$B;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public static Class defineClass(ClassLoader var0, String var1, byte[] var2) {
      try {
         Class[] var3 = new Class[]{class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = class$("java.lang.String")), array$B != null ? array$B : (array$B = class$("[B"))};
         Method var4 = var0.getClass().getMethod("defineClass", var3);
         Object[] var5 = new Object[]{var1, var2};
         return (Class)var4.invoke(var0, var5);
      } catch (Exception var6) {
         return null;
      }
   }

   public static Class loadClass(ClassLoader var0, String var1, boolean var2) {
      try {
         Class[] var3 = new Class[]{class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = class$("java.lang.String")), Boolean.TYPE};
         Method var4 = var0.getClass().getMethod("loadClass", var3);
         Object[] var5 = new Object[]{var1, new Boolean(var2)};
         return (Class)var4.invoke(var0, var5);
      } catch (Exception var6) {
         return null;
      }
   }
}
