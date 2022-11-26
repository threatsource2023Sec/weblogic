package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.PartitionPlugin;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.jvnet.hk2.annotations.Service;

public class ServiceJarClassLoader extends URLClassLoader {
   private URL url;

   public ServiceJarClassLoader(URL url, ClassLoader loader) {
      super(new URL[]{url}, loader);
      this.url = url;
   }

   public List getClasses() throws IOException {
      List classes = new ArrayList();
      JarFile jar = null;
      jar = new JarFile(this.url.getFile());
      Enumeration entries = jar.entries();

      while(entries.hasMoreElements()) {
         JarEntry entry = (JarEntry)entries.nextElement();
         if (entry.getName().endsWith("class")) {
            String classname = entry.getName();
            String classNameWithDot = classname.replace("/", ".");
            classNameWithDot = classNameWithDot.substring(0, classNameWithDot.length() - 6);
            classes.add(classNameWithDot);
         }
      }

      return classes;
   }

   public Map loadClasses() {
      List classes = new ArrayList();
      Map result = new TreeMap();
      String pluginType = "none";

      try {
         List classNames = this.getClasses();
         Iterator var5 = classNames.iterator();

         while(var5.hasNext()) {
            String className = (String)var5.next();

            Class loadedClass;
            try {
               loadedClass = this.loadClass(className);
            } catch (ClassNotFoundException var10) {
               var10.printStackTrace();
               continue;
            }

            Annotation annotation = loadedClass.getAnnotation(Service.class);
            if (annotation != null) {
               if (PartitionPlugin.class.isAssignableFrom(loadedClass)) {
                  Service service = (Service)Service.class.cast(annotation);
                  pluginType = service.name();
               }

               classes.add(loadedClass);
            }
         }

         result.put(pluginType, classes);
         return result;
      } catch (Exception var11) {
         return null;
      }
   }

   public void close() throws IOException {
      URLConnection connection = this.url.openConnection();
      if (connection instanceof JarURLConnection) {
         ((JarURLConnection)connection).getJarFile().close();
      }

   }
}
