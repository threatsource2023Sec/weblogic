package weblogic.servlet.utils;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import weblogic.core.base.api.ClassLoaderService;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.internal.session.SessionObjectReplacer;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public final class ServletObjectInputStream extends ObjectInputStream {
   private static final boolean DEBUG = false;
   private static final SessionObjectReplacer replacer = SessionObjectReplacer.getInstance();
   private Class lastClass;

   private ServletObjectInputStream(UnsyncByteArrayInputStream in) throws IOException {
      super(in);
      this.enableResolveObject(true);
   }

   private static ClassLoaderService getClassLoaderService() {
      ClassLoaderService cls = (ClassLoaderService)GlobalServiceLocator.getServiceLocator().getService(ClassLoaderService.class, "Application", new Annotation[0]);
      if (cls == null) {
         throw new RuntimeException("Implementation of weblogic.common.internal.ClassLoaderService with name of Application not found on classpath");
      } else {
         return cls;
      }
   }

   public Object resolveObject(Object o) throws IOException {
      return replacer.resolveObject(o);
   }

   protected Class resolveClass(ObjectStreamClass c) {
      return this.lastClass;
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      String annotation = this.readUTF();
      ClassLoader appClassLoader = null;
      ArrayList list = new ArrayList();

      for(int i = 0; i < interfaces.length; ++i) {
         try {
            Class c = getClassLoaderService().loadClass(interfaces[i], annotation);
            list.add(c);
            ClassLoader gcl = c.getClassLoader();
            if (appClassLoader != null && gcl instanceof GenericClassLoader) {
               appClassLoader = gcl;
            }
         } catch (ClassNotFoundException var8) {
         }
      }

      Class[] classes = (Class[])((Class[])list.toArray(new Class[list.size()]));
      if (appClassLoader == null) {
         appClassLoader = classes[0].getClassLoader();
      }

      return Proxy.getProxyClass(appClassLoader, classes);
   }

   protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
      String classname = this.readUTF();
      long UID = this.readLong();
      String annotation = this.readUTF();
      Class c = getClassLoaderService().loadClass(classname, annotation);
      ObjectStreamClass descriptor = ObjectStreamClass.lookup(c);
      if (descriptor.getSerialVersionUID() != UID) {
         throw new InvalidClassException(classname, "Expected uid: '" + UID + ", found uid: '" + descriptor.getSerialVersionUID() + "'");
      } else {
         this.lastClass = descriptor.forClass();
         return descriptor;
      }
   }

   public static ServletObjectInputStream getInputStream(byte[] input) throws IOException {
      return new ServletObjectInputStream(new UnsyncByteArrayInputStream(input));
   }

   public static void releaseInputStream(ServletObjectInputStream in) {
   }
}
