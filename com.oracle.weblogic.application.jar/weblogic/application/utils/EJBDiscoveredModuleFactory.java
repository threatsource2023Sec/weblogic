package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

class EJBDiscoveredModuleFactory extends DiscoveredModuleFactory {
   private static String suffix = ".jar";
   private static final String STANDARD_DD = "META-INF/ejb-jar.xml";
   private final AnnotationDetector ejbAnnotationDetector;

   EJBDiscoveredModuleFactory() {
      Class[] annotations = null;

      try {
         annotations = new Class[]{Class.forName("javax.ejb.MessageDriven"), Class.forName("javax.ejb.Stateful"), Class.forName("javax.ejb.Stateless"), Class.forName("javax.ejb.Singleton")};
      } catch (Throwable var3) {
         annotations = new Class[0];
      }

      this.ejbAnnotationDetector = new AnnotationDetector(annotations);
   }

   public DiscoveredModule claim(File f, String relPath) {
      if (f.getName().endsWith(suffix)) {
         VirtualJarFile vjf = null;

         try {
            vjf = VirtualJarFactory.createVirtualJar(f);
            if (vjf.getEntry("META-INF/ejb-jar.xml") != null || !f.isDirectory() && this.ejbAnnotationDetector.isAnnotated((ZipFile)vjf.getJarFile()) || f.isDirectory() && this.ejbAnnotationDetector.isAnnotated(vjf.getDirectory())) {
               EjbDiscoveredModule var4 = new EjbDiscoveredModule(relPath);
               return var4;
            }
         } catch (IOException var8) {
         } finally {
            IOUtils.forceClose(vjf);
         }
      }

      return null;
   }

   public DiscoveredModule claim(VirtualJarFile vjf, ZipEntry entry, String relPath) throws IOException {
      if (entry.isDirectory()) {
         String entryName = entry.getName();
         if (!entryName.endsWith("/")) {
            entryName = entryName + "/";
         }

         entryName = entryName + "META-INF/ejb-jar.xml";
         if (vjf.getEntry(entryName) != null) {
            return new EjbDiscoveredModule(relPath);
         } else {
            Iterator it = vjf.getEntries(entry.getName());
            return this.ejbAnnotationDetector.isAnnotated(vjf, it) ? new EjbDiscoveredModule(relPath) : null;
         }
      } else {
         ZipInputStream jis = null;

         ZipEntry ze;
         try {
            jis = new ZipInputStream(vjf.getInputStream(entry));

            try {
               ze = null;

               while((ze = jis.getNextEntry()) != null) {
                  if (ze.getName().equals("META-INF/ejb-jar.xml")) {
                     EjbDiscoveredModule var6 = new EjbDiscoveredModule(relPath);
                     return var6;
                  }
               }
            } catch (IOException var23) {
            } finally {
               if (jis != null) {
                  jis.close();
               }

               jis = null;
            }

            jis = new ZipInputStream(vjf.getInputStream(entry));
            if (this.ejbAnnotationDetector.isAnnotated(jis)) {
               EjbDiscoveredModule var27 = new EjbDiscoveredModule(relPath);
               return var27;
            }

            ze = null;
         } finally {
            if (jis != null) {
               try {
                  jis.close();
                  jis = null;
               } catch (IOException var22) {
               }
            }

         }

         return ze;
      }
   }

   public String[] claimedSuffixes() {
      return new String[]{suffix};
   }

   static class EjbDiscoveredModule implements DiscoveredModule {
      private final String relPath;

      public EjbDiscoveredModule(String relPath) {
         this.relPath = relPath;
      }

      public void createModule(ApplicationBean bean) {
         ModuleBean module = bean.createModule();
         module.setEjb(this.relPath);
      }

      public String getURI() {
         return this.relPath;
      }
   }
}
