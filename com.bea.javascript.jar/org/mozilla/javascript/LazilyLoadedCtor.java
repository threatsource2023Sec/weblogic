package org.mozilla.javascript;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class LazilyLoadedCtor {
   private static Method getter;
   private static Method setter;
   private String ctorName;
   private String className;
   private boolean sealed;
   private boolean isReplaced;

   public LazilyLoadedCtor(ScriptableObject var1, String var2, String var3, boolean var4) {
      this.className = var3;
      this.ctorName = var2;
      this.sealed = var4;
      if (getter == null) {
         Method[] var5 = FunctionObject.getMethodList(this.getClass());
         getter = FunctionObject.findMethods(var5, "getProperty")[0];
         setter = FunctionObject.findMethods(var5, "setProperty")[0];
      }

      try {
         var1.defineProperty(var2, this, getter, setter, 2);
      } catch (PropertyException var6) {
         throw WrappedException.wrapException(var6);
      }
   }

   public Object getProperty(ScriptableObject var1) {
      synchronized(var1){}

      Object var2;
      try {
         if (this.isReplaced) {
            return var1.get(this.ctorName, var1);
         }

         boolean var5 = false;
         Class var6 = null;

         try {
            var6 = Class.forName(this.className);
         } catch (ClassNotFoundException var23) {
            var5 = true;
         } catch (SecurityException var24) {
            var5 = true;
         }

         if (var6 != null) {
            try {
               ScriptableObject.defineClass(var1, var6, this.sealed);
               this.isReplaced = true;
            } catch (InstantiationException var17) {
               throw WrappedException.wrapException(var17);
            } catch (IllegalAccessException var18) {
               throw WrappedException.wrapException(var18);
            } catch (InvocationTargetException var19) {
               throw WrappedException.wrapException(var19);
            } catch (ClassDefinitionException var20) {
               throw WrappedException.wrapException(var20);
            } catch (PropertyException var21) {
               throw WrappedException.wrapException(var21);
            } catch (SecurityException var22) {
               var5 = true;
            }
         }

         if (!var5) {
            return var1.get(this.ctorName, var1);
         }

         var1.delete(this.ctorName);
         var2 = Scriptable.NOT_FOUND;
      } catch (Throwable var25) {
         throw var25;
      }

      return var2;
   }

   public Object setProperty(ScriptableObject var1, Object var2) {
      synchronized(var1){}

      Object var3;
      try {
         this.isReplaced = true;
         var3 = var2;
      } catch (Throwable var7) {
         throw var7;
      }

      return var3;
   }
}
