package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import weblogic.application.io.ExplodedJar;
import weblogic.utils.classloaders.ClassFinder;

public abstract class AbstractWarExtensionProcessor implements WarExtensionProcessor {
   private List extensions;

   public List getExtensions() throws IOException {
      if (this.extensions == null) {
         this.processExtensions();
      }

      return this.extensions;
   }

   protected abstract List getExtensionJarFiles();

   protected abstract File getWarExtractRoot();

   protected abstract String getExtensionExtractRoot();

   protected abstract String getWarUri();

   protected void processExtensions() throws IOException {
      List jarFiles = this.getExtensionJarFiles();
      if (jarFiles != null && !jarFiles.isEmpty()) {
         File rootDir = this.getWarExtractRoot();
         String subDir = this.getExtensionExtractRoot();
         String uri = this.getWarUri();
         this.extensions = new ArrayList();
         Iterator var5 = jarFiles.iterator();

         while(var5.hasNext()) {
            File jarFile = (File)var5.next();
            File extractDir = this.computeExtractDir(rootDir, subDir, jarFile);
            final ExplodedJar extension = new ArchivedWar(uri, extractDir, jarFile, War.WAR_CLASSPATH_INFO);
            final String name = jarFile.getName();
            this.extensions.add(new WarExtension() {
               public String getName() {
                  return name;
               }

               public ClassFinder getClassFinder() throws IOException {
                  return extension.getClassFinder();
               }

               public void remove() {
                  extension.remove();
               }

               public File[] getRoots() {
                  return extension.getDirs();
               }
            });
         }

      } else {
         this.extensions = Collections.EMPTY_LIST;
      }
   }

   public boolean isSupport() {
      return false;
   }

   private File computeExtractDir(File rootDir, String subDir, File extension) {
      String name = extension.getName();
      if (name.lastIndexOf(46) > 0) {
         name = name.substring(0, name.lastIndexOf(46));
      }

      return new File(rootDir, subDir + File.separator + name);
   }
}
