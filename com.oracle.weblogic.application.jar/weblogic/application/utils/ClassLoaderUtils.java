package weblogic.application.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.PreferApplicationPackagesBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationResourcesBean;
import weblogic.utils.PlatformConstants;
import weblogic.utils.classloaders.GenericClassLoader;

public class ClassLoaderUtils {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugAppContainer");

   public static void initFilterPatterns(PreferApplicationPackagesBean cBean, PreferApplicationResourcesBean rBean, GenericClassLoader appCl) {
      String[] packagesList = null;
      if (cBean != null || rBean != null) {
         if (cBean != null) {
            packagesList = cBean.getPackageNames();
         }

         List classFilters = validatePackages(packagesList);
         List resourceFilters;
         if (rBean != null) {
            resourceFilters = Arrays.asList(rBean.getResourceNames());
         } else {
            resourceFilters = Collections.emptyList();
         }

         if (logger.isDebugEnabled()) {
            logger.debug("Filter list contains : " + classFilters.size() + " elements ");
            Iterator var6 = classFilters.iterator();

            String resourceFilter;
            while(var6.hasNext()) {
               resourceFilter = (String)var6.next();
               logger.debug("Class Pattern: " + resourceFilter);
            }

            var6 = resourceFilters.iterator();

            while(var6.hasNext()) {
               resourceFilter = (String)var6.next();
               logger.debug("Resource Pattern: " + resourceFilter);
            }
         }

         if (!classFilters.isEmpty() || !resourceFilters.isEmpty()) {
            if (!classFilters.isEmpty()) {
               appCl.setFilterList(classFilters);
            }

            if (!resourceFilters.isEmpty()) {
               appCl.setResourceFilterList(resourceFilters);
            }

         }
      }
   }

   private static List validatePackages(String[] packagesList) {
      if (packagesList != null && packagesList.length != 0) {
         List pkgs = Arrays.asList(packagesList);
         List ret = new ArrayList(pkgs.size());

         String pkg;
         for(Iterator i = pkgs.iterator(); i.hasNext(); ret.add(pkg)) {
            pkg = (String)i.next();
            if (pkg.endsWith("*")) {
               pkg = pkg.substring(0, pkg.length() - 1);
            }

            if (!pkg.endsWith(".")) {
               pkg = pkg + ".";
            }
         }

         return ret;
      } else {
         return Collections.emptyList();
      }
   }

   public static String getClassLoaderClassPath(GenericClassLoader classLoader) {
      String classPath = null;
      if (classLoader != null) {
         if (classLoader.getAltParent() != null) {
            classPath = classLoader.getAltParent().getClassFinder().getClassPath();
         }

         String finderClassPath = classLoader.getClassFinder().getClassPath();
         if (classPath != null && classPath.length() > 0) {
            classPath = classPath + PlatformConstants.PATH_SEP + finderClassPath;
         } else {
            classPath = finderClassPath;
         }
      }

      return classPath;
   }
}
