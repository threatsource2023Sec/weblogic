package weblogic.common.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import weblogic.application.AppClassLoaderManager;
import weblogic.core.base.api.ClassLoaderService;
import weblogic.kernel.KernelStatus;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.classloaders.GenericClassLoader;

public class ProxyClassResolver {
   private static final boolean DEBUG = false;
   private static final boolean isServer = KernelStatus.isServer();
   private static final boolean isApplet = KernelStatus.isApplet();
   private static final ClassLoader sysClassLoader;

   private static ClassLoaderService getClassLoaderService() {
      ClassLoaderService cls = (ClassLoaderService)GlobalServiceLocator.getServiceLocator().getService(ClassLoaderService.class, "Application", new Annotation[0]);
      if (cls == null) {
         throw new RuntimeException("Implementation of weblogic.common.internal.ClassLoaderService with name of Application not found on classpath");
      } else {
         return cls;
      }
   }

   public static Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      return resolveProxyClass(interfaces, (String)null, (String)null);
   }

   public static Class resolveProxyClass(String[] interfaces, String annotation, String codeBase) throws IOException, ClassNotFoundException {
      return resolveProxyClass(interfaces, annotation, codeBase, false);
   }

   public static Class resolveProxyClass(String[] interfaces, String annotation, String codeBase, boolean useDependencyClassLoader) throws IOException, ClassNotFoundException {
      ClassLoader loader = null;
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      ArrayList aList = new ArrayList();

      for(int i = 0; i < interfaces.length; ++i) {
         Class ci = null;

         try {
            ci = getClassLoaderService().loadClass(interfaces[i], annotation, codeBase, ccl, useDependencyClassLoader);
         } catch (ClassNotFoundException var11) {
         }

         if (ci == null && !useDependencyClassLoader) {
            try {
               AppClassLoaderManager manager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
               ci = manager.loadApplicationClass(new weblogic.utils.classloaders.Annotation(annotation), interfaces[i]);
            } catch (ClassNotFoundException var12) {
               if (isServer) {
                  throw var12;
               }
            }
         }

         if (ci != null) {
            aList.add(ci);
            if (loader == null && ci.getClassLoader() instanceof GenericClassLoader) {
               loader = ci.getClassLoader();
            }
         }
      }

      if (loader == null) {
         loader = getProxyLoader();
      }

      Class[] classObjs = new Class[aList.size()];
      classObjs = (Class[])((Class[])aList.toArray(classObjs));

      try {
         return Proxy.getProxyClass(loader, classObjs);
      } catch (IllegalArgumentException var10) {
         throw new ClassNotFoundException((String)null, var10);
      }
   }

   private static ClassLoader getProxyLoader() {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = sysClassLoader;
      }

      return loader;
   }

   static {
      if (isApplet) {
         sysClassLoader = null;
      } else {
         sysClassLoader = ClassLoader.getSystemClassLoader();
      }

   }
}
