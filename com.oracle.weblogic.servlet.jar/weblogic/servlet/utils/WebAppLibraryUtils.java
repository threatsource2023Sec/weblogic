package weblogic.servlet.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.library.LibraryReferencer;
import weblogic.application.metadatacache.MetadataType;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.StaleProber;
import weblogic.servlet.internal.War;
import weblogic.servlet.internal.WarDefinition;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class WebAppLibraryUtils {
   private WebAppLibraryUtils() {
   }

   public static LibraryManager getEmptyWebAppLibraryManager() {
      return getEmptyWebAppLibraryManager((String)null);
   }

   public static LibraryManager getEmptyWebAppLibraryManager(String uri) {
      return new LibraryManager(getLibraryReferencer(uri), "DOMAIN");
   }

   public static void initWebAppLibraryManager(LibraryManager mgr, WeblogicWebAppBean wlBean, String uri) throws ToolFailureException {
      if (wlBean != null) {
         if (wlBean.getLibraryRefs() != null) {
            LibraryReference[] ref = getWebLibRefs(wlBean, uri);
            mgr.lookup(ref);
            if (mgr.hasUnresolvedReferences()) {
               throw new ToolFailureException("Error: " + mgr.getUnresolvedReferencesError());
            }
         }
      }
   }

   public static LibraryReference[] getWebLibRefs(WeblogicWebAppBean wlBean, String uri) throws ToolFailureException {
      LibraryReference[] ref = null;

      try {
         ref = LibraryReferenceFactory.getWebLibReference(wlBean.getLibraryRefs());
      } catch (IllegalSpecVersionTypeException var4) {
         throw new ToolFailureException(HTTPLogger.logIllegalWebLibSpecVersionRefLoggable(uri == null ? "" : uri, var4.getSpecVersion()).getMessage());
      }

      return (LibraryReference[])(ref == null ? new LibraryReference[0] : ref);
   }

   public static War addWebAppLibraries(LibraryManager mgr, String uri) throws IOException {
      War war = getEmptyWar(uri);
      addWebAppLibraries(mgr, war);
      return war;
   }

   private static War getEmptyWar(String uri) throws IOException {
      WarDefinition warDefinition = new WarDefinition();
      warDefinition.setUri(uri);
      return warDefinition.extract((File)null, (StaleProber)null);
   }

   public static void addWebAppLibraries(LibraryManager mgr, War war) throws IOException {
      Library[] lib = mgr.getReferencedLibraries();

      for(int i = 0; i < lib.length; ++i) {
         war.addLibrary(lib[i]);
      }

   }

   public static void writeWar(War war, Library[] libraries, File outputDir) throws IOException {
      ClassFinder cf = war.getResourceFinder("/");
      String cp = cf.getClassPath();
      cf.close();
      String[] paths = StringUtils.splitCompletely(cp, File.pathSeparator);

      for(int i = 0; i < paths.length; ++i) {
         File f = new File(paths[i]);
         if (f.exists()) {
            Set exclude = getExcludedFiles(f);
            FileUtils.copyNoOverwritePreservePermissions(f, outputDir, exclude);
         }
      }

      writeJarSharedLib(libraries, outputDir);
   }

   private static void writeJarSharedLib(Library[] referencedLibraries, File outputDir) throws IOException {
      if (referencedLibraries != null && referencedLibraries.length != 0) {
         File output = new File(outputDir, "WEB-INF/lib");
         if (!output.exists()) {
            output.mkdirs();
         }

         Library[] var3 = referencedLibraries;
         int var4 = referencedLibraries.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Library library = var3[var5];
            if (library.getLocation().getPath().endsWith(".jar")) {
               FileUtils.copyNoOverwritePreservePermissions(library.getLocation(), output);
            }
         }

      }
   }

   public static void removeLibraryReferences(WeblogicWebAppBean wlBean) {
      if (wlBean != null) {
         LibraryRefBean[] libs = wlBean.getLibraryRefs();
         if (libs != null) {
            for(int i = 0; i < libs.length; ++i) {
               wlBean.destroyLibraryRef(libs[i]);
            }

         }
      }
   }

   private static Set getExcludedFiles(File root) {
      Set rtn = new HashSet(7);
      rtn.add(new File(root, "WEB-INF/web.xml"));
      rtn.add(new File(root, "WEB-INF/weblogic.xml"));
      rtn.add(new File(root, MetadataType.TLD.getName()));
      rtn.add(new File(root, MetadataType.FACE_BEANS.getName()));
      rtn.add(new File(root, MetadataType.TAG_HANDLERS.getName()));
      rtn.add(new File(root, MetadataType.TAG_LISTENERS.getName()));
      rtn.add(new File(root, MetadataType.CLASSLEVEL_INFOS.getName()));
      return rtn;
   }

   public static LibraryReferencer getLibraryReferencer(String uri) {
      String errorMessage = "Unresolved WebApp library references defined in weblogic.xml";
      if (uri != null) {
         errorMessage = errorMessage + ", of module '" + uri + "'";
      }

      return new LibraryReferencer((String)null, (RuntimeMBean)null, errorMessage);
   }

   public static LibraryReference[] initAllWebLibRefs(File srcDir) throws ToolFailureException {
      Collection rtn = new ArrayList();
      File[] weblogicDDs = FileUtils.find(srcDir, new FileFilter() {
         public boolean accept(File f) {
            return f.isFile() && f.getName().equals("weblogic.xml") && f.getParentFile().getName().equals("WEB-INF");
         }
      });

      for(int i = 0; i < weblogicDDs.length; ++i) {
         LibraryReference[] refs = initWebLibRefs(weblogicDDs[i].getParentFile().getParentFile());
         if (refs != null) {
            rtn.addAll(Arrays.asList(refs));
         }
      }

      return (LibraryReference[])((LibraryReference[])rtn.toArray(new LibraryReference[rtn.size()]));
   }

   private static LibraryReference[] initWebLibRefs(File srcDir) throws ToolFailureException {
      VirtualJarFile vjf = null;
      LibraryReference[] rtn = null;

      try {
         vjf = VirtualJarFactory.createVirtualJar(srcDir);
         WebAppDescriptor desc = new WebAppDescriptor(vjf);
         WeblogicWebAppBean wlBean = WarUtils.getWlWebAppBean(desc);
         rtn = getWebLibRefs(wlBean, srcDir.getName());
      } catch (IOException var12) {
         throw new ToolFailureException("Error parsing weblogic.xml");
      } finally {
         if (vjf != null) {
            try {
               vjf.close();
            } catch (IOException var11) {
            }
         }

      }

      return rtn;
   }
}
