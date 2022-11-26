package org.python.core.packagecache;

import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;
import org.python.core.Py;
import org.python.core.PyJavaPackage;
import org.python.core.PyList;
import org.python.core.PySystemState;

public class SysPackageManager extends PathPackageManager {
   protected void message(String msg) {
      Py.writeMessage("*sys-package-mgr*", msg);
   }

   protected void warning(String warn) {
      Py.writeWarning("*sys-package-mgr*", warn);
   }

   protected void comment(String msg) {
      Py.writeComment("*sys-package-mgr*", msg);
   }

   protected void debug(String msg) {
      Py.writeDebug("*sys-package-mgr*", msg);
   }

   public SysPackageManager(File cachedir, Properties registry) {
      if (this.useCacheDir(cachedir)) {
         this.initCache();
         this.findAllPackages(registry);
         this.saveCache();
      }

   }

   public void addJar(String jarfile, boolean cache) {
      this.addJarToPackages(new File(jarfile), cache);
      if (cache) {
         this.saveCache();
      }

   }

   public void addJarDir(String jdir, boolean cache) {
      this.addJarDir(jdir, cache, cache);
   }

   private void addJarDir(String jdir, boolean cache, boolean saveCache) {
      File file = new File(jdir);
      if (file.isDirectory()) {
         String[] files = file.list();

         for(int i = 0; i < files.length; ++i) {
            String entry = files[i];
            if (entry.endsWith(".jar") || entry.endsWith(".zip")) {
               this.addJarToPackages(new File(jdir, entry), cache);
            }
         }

         if (saveCache) {
            this.saveCache();
         }

      }
   }

   private void addJarPath(String path) {
      StringTokenizer tok = new StringTokenizer(path, File.pathSeparator);

      while(tok.hasMoreTokens()) {
         String entry = tok.nextToken();
         this.addJarDir(entry, true, false);
      }

   }

   private void findAllPackages(Properties registry) {
      String paths = registry.getProperty("python.packages.paths", "java.class.path,sun.boot.class.path");
      String directories = registry.getProperty("python.packages.directories", "java.ext.dirs");
      String fakepath = registry.getProperty("python.packages.fakepath", (String)null);
      StringTokenizer tok = new StringTokenizer(paths, ",");

      String entry;
      while(tok.hasMoreTokens()) {
         String entry = tok.nextToken().trim();
         entry = registry.getProperty(entry);
         if (entry != null) {
            this.addClassPath(entry);
         }
      }

      Double vesion = Double.parseDouble(System.getProperty("java.specification.version"));
      if (vesion > 1.8) {
         this.addJImageToPackages(registry);
      }

      tok = new StringTokenizer(directories, ",");

      while(tok.hasMoreTokens()) {
         entry = tok.nextToken().trim();
         String tmp = registry.getProperty(entry);
         if (tmp != null) {
            this.addJarPath(tmp);
         }
      }

      if (fakepath != null) {
         this.addClassPath(fakepath);
      }

   }

   public void notifyPackageImport(String pkg, String name) {
      if (pkg != null && pkg.length() > 0) {
         name = pkg + '.' + name;
      }

      Py.writeComment("import", "'" + name + "' as java package");
   }

   public Class findClass(String pkg, String name) {
      Class c = super.findClass(pkg, name);
      if (c != null) {
         Py.writeComment("import", "'" + name + "' as java class");
      }

      return c;
   }

   public Class findClass(String pkg, String name, String reason) {
      if (pkg != null && pkg.length() > 0) {
         name = pkg + '.' + name;
      }

      return Py.findClassEx(name, reason);
   }

   public PyList doDir(PyJavaPackage jpkg, boolean instantiate, boolean exclpkgs) {
      PyList basic = this.basicDoDir(jpkg, instantiate, exclpkgs);
      PyList ret = new PyList();
      this.doDir(this.searchPath, ret, jpkg, instantiate, exclpkgs);
      PySystemState system = Py.getSystemState();
      if (system.getClassLoader() == null) {
         this.doDir(system.path, ret, jpkg, instantiate, exclpkgs);
      }

      return this.merge(basic, ret);
   }

   public boolean packageExists(String pkg, String name) {
      if (this.packageExists(this.searchPath, pkg, name)) {
         return true;
      } else {
         PySystemState system = Py.getSystemState();
         return system.getClassLoader() == null && this.packageExists(Py.getSystemState().path, pkg, name);
      }
   }
}
