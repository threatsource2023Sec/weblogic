package org.python.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JavaImportHelper {
   private static final String DOT = ".";

   protected static boolean tryAddPackage(String packageName, PyObject fromlist) {
      boolean packageAdded = false;
      if (packageName != null) {
         List stringFromlist = getFromListAsStrings(fromlist);
         Iterator var4 = stringFromlist.iterator();

         String lastDottedName;
         while(var4.hasNext()) {
            lastDottedName = (String)var4.next();
            if (isJavaClass(packageName, lastDottedName)) {
               packageAdded = addPackage(packageName, packageAdded);
            }
         }

         int dotPos = packageName.lastIndexOf(".");
         String parentPackageName;
         if (dotPos > 0) {
            lastDottedName = packageName.substring(dotPos + 1);
            parentPackageName = packageName.substring(0, dotPos);
            if (isJavaClass(parentPackageName, lastDottedName)) {
               packageAdded = addPackage(parentPackageName, packageAdded);
            }
         }

         if (!packageAdded) {
            Map packages = buildLoadedPackages();
            parentPackageName = packageName;
            if (isLoadedPackage(packageName, packages)) {
               packageAdded = addPackage(packageName, packageAdded);
            }

            int dotPos = false;

            do {
               dotPos = parentPackageName.lastIndexOf(".");
               if (dotPos > 0) {
                  parentPackageName = parentPackageName.substring(0, dotPos);
                  if (isLoadedPackage(parentPackageName, packages)) {
                     packageAdded = addPackage(parentPackageName, packageAdded);
                  }
               }
            } while(dotPos > 0);

            Iterator var7 = stringFromlist.iterator();

            while(var7.hasNext()) {
               String fromName = (String)var7.next();
               String fromPackageName = packageName + "." + fromName;
               if (isLoadedPackage(fromPackageName, packages)) {
                  packageAdded = addPackage(fromPackageName, packageAdded);
               }
            }
         }
      }

      return packageAdded;
   }

   protected static boolean isLoadedPackage(String packageName) {
      return isLoadedPackage(packageName, buildLoadedPackages());
   }

   private static final List getFromListAsStrings(PyObject fromlist) {
      List stringFromlist = new ArrayList();
      if (fromlist != null && fromlist != Py.EmptyTuple && fromlist instanceof PyTuple) {
         Iterator iterator = ((PyTuple)fromlist).iterator();

         while(iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof PyString) {
               obj = ((PyString)obj).getString();
            }

            if (obj instanceof String) {
               String fromName = (String)obj;
               if (!"*".equals(fromName)) {
                  stringFromlist.add(fromName);
               }
            }
         }
      }

      return stringFromlist;
   }

   private static boolean isLoadedPackage(String javaPackageName, Map packages) {
      boolean isLoaded = false;
      if (javaPackageName != null) {
         isLoaded = packages.containsKey(javaPackageName);
      }

      return isLoaded;
   }

   private static Map buildLoadedPackages() {
      TreeMap packageMap = new TreeMap();
      Package[] packages = Package.getPackages();

      for(int i = 0; i < packages.length; ++i) {
         String packageName = packages[i].getName();
         packageMap.put(packageName, "");
         int dotPos = false;

         int dotPos;
         do {
            dotPos = packageName.lastIndexOf(".");
            if (dotPos > 0) {
               packageName = packageName.substring(0, dotPos);
               packageMap.put(packageName, "");
            }
         } while(dotPos > 0);
      }

      return packageMap;
   }

   private static boolean isJavaClass(String packageName, String className) {
      return className != null && className.length() > 0 && Py.findClass(packageName + "." + className) != null;
   }

   private static boolean addPackage(String packageName, boolean packageAdded) {
      PyObject modules = Py.getSystemState().modules;
      String internedPackageName = packageName.intern();
      PyObject module = modules.__finditem__(internedPackageName);
      if (module == null || module == Py.None) {
         int dotPos = 0;

         do {
            PyJavaPackage p = PySystemState.add_package(packageName);
            if (dotPos == 0) {
               modules.__setitem__((String)internedPackageName, p);
            } else {
               module = modules.__finditem__(internedPackageName);
               if (module == null || module == Py.None) {
                  modules.__setitem__((String)internedPackageName, p);
               }
            }

            dotPos = packageName.lastIndexOf(".");
            if (dotPos > 0) {
               packageName = packageName.substring(0, dotPos);
               internedPackageName = packageName.intern();
            }
         } while(dotPos > 0);

         packageAdded = true;
      }

      return packageAdded;
   }
}
