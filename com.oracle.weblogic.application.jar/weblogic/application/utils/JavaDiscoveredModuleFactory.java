package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.zip.ZipEntry;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

class JavaDiscoveredModuleFactory extends DiscoveredModuleFactory {
   private static String suffix = ".jar";

   public final DiscoveredModule claim(File f, String relPath) {
      if (!f.getName().endsWith(suffix)) {
         return null;
      } else {
         VirtualJarFile vjf = null;

         try {
            vjf = VirtualJarFactory.createVirtualJar(f);
            if (vjf.getManifest() != null && vjf.getManifest().getMainAttributes().get(Name.MAIN_CLASS) != null || vjf.getEntry("META-INF/application-client.xml") != null) {
               JavaDiscoveredModule var4 = new JavaDiscoveredModule(relPath);
               return var4;
            }
         } catch (IOException var8) {
         } finally {
            IOUtils.forceClose(vjf);
         }

         return null;
      }
   }

   public final DiscoveredModule claim(VirtualJarFile vjf, ZipEntry entry, String name) throws IOException {
      ZipEntry ze;
      if (entry.isDirectory()) {
         name = name.endsWith("/") ? name : name + "/";
         String entryName = name + "META-INF/MANIFEST.MF";
         ze = vjf.getEntry(entryName);
         if (ze != null) {
            InputStream is = null;

            JavaDiscoveredModule var8;
            try {
               is = vjf.getInputStream(ze);
               Manifest m = new Manifest(is);
               if (m.getMainAttributes().get(Name.MAIN_CLASS) == null) {
                  return vjf.getEntry(name + "META-INF/application-client.xml") != null ? new JavaDiscoveredModule(name) : null;
               }

               var8 = new JavaDiscoveredModule(name);
            } finally {
               if (is != null) {
                  try {
                     is.close();
                  } catch (IOException var29) {
                  }
               }

            }

            return var8;
         } else {
            return vjf.getEntry(name + "META-INF/application-client.xml") != null ? new JavaDiscoveredModule(name) : null;
         }
      } else {
         JarInputStream jis = null;

         try {
            jis = new JarInputStream(vjf.getInputStream(entry));
            if (jis.getManifest() != null && jis.getManifest().getMainAttributes().get(Name.MAIN_CLASS) != null) {
               JavaDiscoveredModule var34 = new JavaDiscoveredModule(name);
               return var34;
            }

            ze = null;

            while((ze = jis.getNextEntry()) != null) {
               if (ze.getName().equals("META-INF/application-client.xml")) {
                  JavaDiscoveredModule var6 = new JavaDiscoveredModule(name);
                  return var6;
               }
            }
         } finally {
            if (jis != null) {
               try {
                  jis.close();
               } catch (IOException var30) {
               }
            }

         }

         return null;
      }
   }

   public String[] claimedSuffixes() {
      return new String[]{suffix};
   }

   static class JavaDiscoveredModule implements DiscoveredModule {
      private final String relPath;

      public JavaDiscoveredModule(String relPath) {
         this.relPath = relPath;
      }

      public void createModule(ApplicationBean bean) {
         ModuleBean module = bean.createModule();
         module.setJava(this.relPath);
      }

      public String getURI() {
         return this.relPath;
      }
   }
}
