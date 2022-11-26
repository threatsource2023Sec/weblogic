package weblogic.application.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class PersistenceUtils {
   private static final FileFilter JARFILE = new FileFilter() {
      public boolean accept(File f) {
         return f.getName().endsWith(".jar") && f.isFile();
      }
   };

   public static void addRootPersistenceJars(GenericClassLoader gcl, String applicationName, ApplicationBean appBean) throws IOException {
      File[] rootFiles = getApplicationRoots(gcl, applicationName, false);
      Set jarModules = null;

      for(int count = 0; count < rootFiles.length; ++count) {
         File[] jarFiles = rootFiles[count].listFiles(JARFILE);
         if (jarFiles != null) {
            for(int i = 0; i < jarFiles.length; ++i) {
               if (jarModules == null) {
                  jarModules = new HashSet();
                  loadJarModuleUris(appBean, jarModules);
               }

               File jarFile = jarFiles[i];
               String simpleName = getSimpleName(jarFile);
               if (!jarModules.contains(simpleName)) {
                  VirtualJarFile vjf = null;

                  try {
                     vjf = VirtualJarFactory.createVirtualJar(jarFile);
                     if (vjf.getEntries("META-INF/persistence.xml").hasNext()) {
                        gcl.addClassFinder(new JarClassFinder(jarFile));
                     }
                  } finally {
                     IOUtils.forceClose(vjf);
                  }
               }
            }
         }
      }

   }

   private static String getSimpleName(File f) {
      String name = f.getName();
      int lastSep = name.lastIndexOf(File.pathSeparator);
      return lastSep == -1 ? name : name.substring(lastSep + 1);
   }

   private static void loadJarModuleUris(ApplicationBean appBean, Set set) {
      ModuleBean[] modules = appBean.getModules();
      if (modules != null) {
         for(int count = 0; count < modules.length; ++count) {
            ModuleBean module = modules[count];
            if (module.isEjbSet() && module.getEjb() != null) {
               set.add(module.getEjb());
            } else if (module.isJavaSet() && module.getJava() != null) {
               set.add(module.getJava());
            }
         }
      }

   }

   public static File[] getApplicationRoots(GenericClassLoader classLoader, String applicationName, boolean canonicalize) throws IOException {
      List rootURLList = new ArrayList();
      Enumeration sources = classLoader.getClassFinder().getSources(applicationName + "#/");
      addSourceRoots(sources, rootURLList, canonicalize);
      return (File[])rootURLList.toArray(new File[0]);
   }

   public static File[] getSplitDirSourceRoots(GenericClassLoader gcl, String applicationName, String path, boolean canonicalize) throws IOException {
      List rootURLList = new ArrayList();
      Enumeration sources = gcl.getClassFinder().getSources(applicationName + "#/" + path);
      addSourceRoots(sources, rootURLList, canonicalize);
      return (File[])rootURLList.toArray(new File[0]);
   }

   private static void addSourceRoots(Enumeration sources, List rootsList, boolean canonicalize) throws IOException {
      while(sources.hasMoreElements()) {
         File rootFile = new File(((Source)sources.nextElement()).getURL().getFile());
         if (canonicalize) {
            rootsList.add(rootFile.getCanonicalFile());
         } else {
            rootsList.add(rootFile);
         }
      }

   }

   public static File[] concatRootFiles(File[] a, File[] b) {
      File[] mergedFiles = (File[])Arrays.copyOf(a, a.length + b.length);
      System.arraycopy(b, 0, mergedFiles, a.length, b.length);
      return mergedFiles;
   }
}
