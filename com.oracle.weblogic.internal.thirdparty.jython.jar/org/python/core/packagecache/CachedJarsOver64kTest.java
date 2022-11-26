package org.python.core.packagecache;

import java.io.File;
import junit.framework.TestCase;
import org.python.core.PyJavaPackage;
import org.python.core.PyList;

public class CachedJarsOver64kTest extends TestCase {
   private TestCachePackageManager packageManager = null;
   private File jarFile = null;

   public void setUp() {
      this.packageManager = new TestCachePackageManager(new File(System.getProperty("java.io.tmpdir")));
      File cwd = new File(System.getProperty("python.test.source.dir"), this.getClass().getPackage().getName().replace(".", "/"));
      this.jarFile = new File(cwd, "vim25-small.jar");
   }

   public void testJarOver64k() {
      assertTrue(this.jarFile.exists());
      this.packageManager.addJarToPackages(this.jarFile, true);
      assertFalse(this.packageManager.failed);
   }

   private class TestCachePackageManager extends CachedJarsPackageManager {
      public boolean failed;

      public TestCachePackageManager(File cachedir) {
         if (this.useCacheDir(cachedir)) {
            this.initCache();
         }

      }

      protected void warning(String msg) {
         this.failed = true;
      }

      public void addDirectory(File dir) {
      }

      public void addJar(String jarfile, boolean cache) {
      }

      public void addJarDir(String dir, boolean cache) {
      }

      public PyList doDir(PyJavaPackage jpkg, boolean instantiate, boolean exclpkgs) {
         return null;
      }

      public Class findClass(String pkg, String name, String reason) {
         return null;
      }

      public boolean packageExists(String pkg, String name) {
         return false;
      }
   }
}
