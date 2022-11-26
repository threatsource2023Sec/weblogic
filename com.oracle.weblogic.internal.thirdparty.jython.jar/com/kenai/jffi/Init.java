package com.kenai.jffi;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class Init {
   private static volatile boolean loaded = false;
   static final String stubLoaderClassName = Init.class.getPackage().getName() + ".internal.StubLoader";

   private Init() {
   }

   static void load() {
      if (!loaded) {
         List failureCauses = new ArrayList();
         List loaders = getClassLoaders();
         Iterator var2 = loaders.iterator();

         while(var2.hasNext()) {
            ClassLoader cl = (ClassLoader)var2.next();

            try {
               Class c = Class.forName(stubLoaderClassName, true, cl);
               Method isLoaded = c.getDeclaredMethod("isLoaded");
               loaded |= (Boolean)Boolean.class.cast(isLoaded.invoke(c));
               if (!loaded) {
                  Method getFailureCause = c.getDeclaredMethod("getFailureCause");
                  throw (Throwable)Throwable.class.cast(getFailureCause.invoke(c));
               }
            } catch (IllegalAccessException var7) {
               failureCauses.add(var7);
            } catch (InvocationTargetException var8) {
               failureCauses.add(var8);
            } catch (ClassNotFoundException var9) {
               failureCauses.add(var9);
            } catch (Throwable var10) {
               if (var10 instanceof UnsatisfiedLinkError) {
                  throw (UnsatisfiedLinkError)var10;
               }

               throw newLoadError(var10);
            }
         }

         if (!loaded && !failureCauses.isEmpty()) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            Iterator var13 = failureCauses.iterator();

            while(var13.hasNext()) {
               Throwable t = (Throwable)var13.next();
               t.printStackTrace(pw);
            }

            throw new UnsatisfiedLinkError(sw.toString());
         }
      }
   }

   private static List getClassLoaders() {
      List loaders = new ArrayList();

      try {
         loaders.add(ClassLoader.getSystemClassLoader());
      } catch (SecurityException var4) {
      }

      try {
         loaders.add(Thread.currentThread().getContextClassLoader());
      } catch (SecurityException var3) {
      }

      loaders.add(Init.class.getClassLoader());
      int nullCount = 0;
      Iterator it = loaders.iterator();

      while(it.hasNext()) {
         if (it.next() == null) {
            ++nullCount;
            if (nullCount > 1) {
               it.remove();
            }
         }
      }

      return Collections.unmodifiableList(loaders);
   }

   private static UnsatisfiedLinkError newLoadError(Throwable cause) {
      UnsatisfiedLinkError error = new UnsatisfiedLinkError();
      error.initCause(cause);
      return error;
   }
}
