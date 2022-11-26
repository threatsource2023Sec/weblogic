package weblogic.servlet.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;

public class BeaExtensionProcessor extends AbstractWarExtensionProcessor {
   private static final String BEA_EXTN_DIR = "/WEB-INF/bea-ext";
   private static final String EXTENSION_EXTRACT_ROOT = "beaext";
   private static final FileFilter WEBAPP_EXTN = FileUtils.makeExtensionFilter(new String[]{".jar", ".war"});
   private War war;

   public BeaExtensionProcessor(War war) throws IOException {
      this.war = war;
   }

   protected File getWarExtractRoot() {
      return this.war.getExtractDir();
   }

   protected String getExtensionExtractRoot() {
      return "beaext";
   }

   protected String getWarUri() {
      return this.war.getURI();
   }

   protected List getExtensionJarFiles() {
      List extensionJars = new ArrayList();
      ClassFinder classfinder = this.war.getClassFinder();
      String uri = this.war.getURI();
      Enumeration e = classfinder.getSources(uri + "#" + "/WEB-INF/bea-ext");

      while(e.hasMoreElements()) {
         Source s = (Source)e.nextElement();
         URL url = s.getURL();
         if (!"file".equals(url.getProtocol())) {
            throw new AssertionError("Unknown protocol " + url.getProtocol());
         }

         String path = url.getPath();
         File extensionDir = new File(path);
         if (extensionDir.isDirectory()) {
            File[] jarFiles = extensionDir.listFiles(WEBAPP_EXTN);
            if (jarFiles != null) {
               extensionJars.addAll(Arrays.asList(jarFiles));
            }
         }
      }

      return extensionJars;
   }

   public boolean isSupport() {
      return this.war.getResourceFinder("/").getSource("/WEB-INF/bea-ext") != null;
   }
}
