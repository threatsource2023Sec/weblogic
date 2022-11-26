package weblogic.j2eeclient.tools;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.naming.Context;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;

public class JarFileInjectionArchive implements InjectionArchive {
   private final ClassLoader loader;
   private final File jarFile;
   private LinkedList beanClassNames;

   public JarFileInjectionArchive(ClassLoader loader, File jarFile) {
      this.loader = loader;
      this.jarFile = jarFile;
   }

   public ClassLoader getClassLoader() {
      return this.loader;
   }

   public Collection getEjbDescriptors() {
      return Collections.emptyList();
   }

   public synchronized Collection getBeanClassNames() {
      if (this.beanClassNames != null) {
         return this.beanClassNames;
      } else {
         this.beanClassNames = new LinkedList();
         JarFile jarFile = null;

         try {
            jarFile = new JarFile(this.jarFile);
            Enumeration jarEntries = jarFile.entries();

            while(jarEntries.hasMoreElements()) {
               JarEntry next = (JarEntry)jarEntries.nextElement();
               if (next.getName().endsWith(".class")) {
                  String name = next.getName();
                  int indexOfExtension = name.indexOf(".class");
                  String nameWithoutExtension = name.substring(0, indexOfExtension);
                  String pathName = nameWithoutExtension.replace("/", ".");
                  this.beanClassNames.add(pathName);
               }
            }
         } catch (IOException var15) {
            throw new RuntimeException(var15);
         } finally {
            if (jarFile != null) {
               try {
                  jarFile.close();
               } catch (IOException var14) {
                  throw new RuntimeException(var14);
               }
            }

         }

         return this.beanClassNames;
      }
   }

   public URL getResource(String resourceName) {
      return this.loader.getResource(resourceName);
   }

   public Object getCustomContext() {
      return null;
   }

   public InjectionArchiveType getArchiveType() {
      return InjectionArchiveType.APP_CLIENT_JAR;
   }

   public String getArchiveName() {
      return this.jarFile.getAbsolutePath();
   }

   public String getClassPathArchiveName() {
      return this.jarFile.getAbsolutePath();
   }

   public Collection getEmbeddedArchives() {
      return Collections.emptyList();
   }

   public Collection getComponentClassNamesForProcessInjectionTarget() {
      return Collections.emptyList();
   }

   public PojoEnvironmentBean getPojoEnvironmentBean() {
      return null;
   }

   public Context getRootContext(String componentName) {
      return null;
   }
}
