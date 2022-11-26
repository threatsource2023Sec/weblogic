package com.bea.util.jam.internal.javadoc;

import com.sun.javadoc.RootDoc;
import java.lang.reflect.Method;

public class JavadocResults {
   private static final JavadocResults INSTANCE = new JavadocResults();
   private ThreadLocal mRootsPerThread = new ThreadLocal();

   public static void prepare() {
      Thread.currentThread().setContextClassLoader(JavadocResults.class.getClassLoader());
   }

   public static void setRoot(RootDoc root) {
      try {
         Object holder = getHolder();
         Method setter = holder.getClass().getMethod("_setRoot", RootDoc.class);
         setter.invoke(holder, root);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException();
      }
   }

   public static RootDoc getRoot() {
      try {
         Object holder = getHolder();
         Method getter = holder.getClass().getMethod("_getRoot");
         RootDoc out = (RootDoc)getter.invoke(holder, (Object[])null);
         Method setter = holder.getClass().getMethod("_remove");
         setter.invoke(holder, (Object[])null);
         return out;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalStateException();
      }
   }

   public void _setRoot(RootDoc root) {
      this.mRootsPerThread.set(root);
   }

   public RootDoc _getRoot() {
      return (RootDoc)this.mRootsPerThread.get();
   }

   public void _remove() {
      this.mRootsPerThread.remove();
   }

   public static JavadocResults getInstance() {
      return INSTANCE;
   }

   private static Object getHolder() throws Exception {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      Class clazz = classLoader.loadClass(JavadocResults.class.getName());
      Method method = clazz.getMethod("getInstance");
      return method.invoke((Object)null);
   }
}
