package weblogic.application.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class ModuleDiscovery {
   private static final int MAX_NESTED_DIR_DEPTH = 6;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static String[] knownExtensions = DiscoveredModuleFactory.knownExtensions();
   private static final FileFilter EXT_OR_DIR = new FileFilter() {
      public boolean accept(File f) {
         if (f.isDirectory()) {
            return true;
         } else {
            String name = f.getName();

            for(int i = 0; i < ModuleDiscovery.knownExtensions.length; ++i) {
               if (name.endsWith(ModuleDiscovery.knownExtensions[i])) {
                  return true;
               }
            }

            return false;
         }
      }
   };

   private ModuleDiscovery() {
   }

   public static ApplicationBean discoverModules(VirtualJarFile vjf) throws IOException {
      if (vjf.isDirectory()) {
         File[] roots = vjf.getRootFiles();
         return roots.length == 1 ? discoverModules(roots[0]) : discoverModules(roots);
      } else {
         return discoverModulesArchived(vjf);
      }
   }

   public static ApplicationBean discoverModules(File root) throws IOException {
      List modules = new LinkedList();
      findModules(root, modules);
      return populateAppBean(modules);
   }

   public static ApplicationBean discoverModules(File[] roots) throws IOException {
      if (roots != null && roots.length != 0) {
         List modules = new LinkedList();

         for(int i = 0; i < roots.length; ++i) {
            findModules(roots[i], roots[i], 0, modules, true);
         }

         return populateAppBean(modules);
      } else {
         return null;
      }
   }

   static void findModules(File root, List modules) throws IOException {
      findModules(root, root, 0, modules, false);
   }

   private static void findModules(File root, File path, int depth, List modules, boolean isSplitSrc) throws IOException {
      assert path.isDirectory();

      ++depth;
      if (depth > 6) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(" MAX_NESTED_DIR_DEPTH exceeded " + path.getAbsolutePath());
         }

      } else {
         URI rootURI = root.toURI();
         File[] files = path.listFiles(EXT_OR_DIR);

         for(int i = 0; i < files.length; ++i) {
            URI rel = rootURI.relativize(files[i].toURI());
            String relPath = rel.toString();
            if (relPath.endsWith("/")) {
               relPath = relPath.substring(0, relPath.length() - 1);
            }

            if (!"APP-INF".equals(relPath) && !"lib".equals(relPath) && !"META-INF".equals(relPath) && (!isSplitSrc || !sawRelativePath(relPath, modules))) {
               DiscoveredModule module = DiscoveredModuleFactory.makeDiscoveredModule(files[i], relPath);
               if (module != null) {
                  modules.add(module);
               } else if (files[i].isDirectory()) {
                  findModules(root, files[i], depth, modules, isSplitSrc);
               }
            }
         }

      }
   }

   private static boolean sawRelativePath(String relPath, List modules) {
      Iterator i = modules.iterator();

      DiscoveredModule module;
      do {
         if (!i.hasNext()) {
            return false;
         }

         module = (DiscoveredModule)i.next();
      } while(!relPath.startsWith(module.getURI()));

      return true;
   }

   private static ApplicationBean populateAppBean(List modules) {
      ApplicationBean bean = null;
      if (modules.size() > 0) {
         bean = (ApplicationBean)(new DescriptorManager()).createDescriptorRoot(ApplicationBean.class).getRootBean();
         bean.setVersion(bean.getVersion());
         Iterator i = modules.iterator();

         while(i.hasNext()) {
            DiscoveredModule module = (DiscoveredModule)i.next();
            module.createModule(bean);
         }
      }

      return bean;
   }

   public static ApplicationBean discoverModulesArchived(VirtualJarFile vjf) throws IOException {
      List modules = new ArrayList();
      Set claimedPaths = new HashSet();

      try {
         assert !vjf.isDirectory();

         Iterator entries = vjf.entries();

         while(entries.hasNext()) {
            ZipEntry entry = (ZipEntry)entries.next();
            String name = entry.getName();
            if (name.endsWith("/")) {
               name = name.substring(0, name.length() - 1);
            }

            if (!name.startsWith("APP-INF") && !name.startsWith("lib") && !name.startsWith("META-INF") && !alreadyClaimedPath(claimedPaths, name)) {
               DiscoveredModule module = DiscoveredModuleFactory.makeDiscoveredModule(vjf, entry, name);
               if (module != null) {
                  claimedPaths.add(name);
                  modules.add(module);
               }
            }
         }
      } catch (IOException var7) {
      }

      return populateAppBean(modules);
   }

   private static boolean alreadyClaimedPath(Set claimedPaths, String name) {
      if (claimedPaths.contains(name)) {
         return true;
      } else {
         Iterator i = claimedPaths.iterator();

         String path;
         do {
            if (!i.hasNext()) {
               return false;
            }

            path = (String)i.next();
         } while(!name.startsWith(path));

         return true;
      }
   }

   private static void dumpBean(ApplicationBean appBean) throws IOException {
      (new DescriptorManager()).writeDescriptorAsXML(((DescriptorBean)appBean).getDescriptor(), System.out);
   }

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         System.out.println("Usage: java " + ModuleDiscovery.class.getName() + " <ear | dir>");
      } else {
         ApplicationBean b = null;
         long t = System.currentTimeMillis();
         VirtualJarFile vjf = null;

         try {
            vjf = VirtualJarFactory.createVirtualJar(new File(args[0]));
            b = discoverModules(vjf);
            long t2 = System.currentTimeMillis();
            if (b != null) {
               dumpBean(b);
            } else {
               System.out.println("AppBean is null");
            }

            System.out.println("Elapsed: " + (t2 - t));
         } finally {
            if (vjf != null) {
               try {
                  vjf.close();
               } catch (IOException var12) {
               }
            }

         }

      }
   }
}
