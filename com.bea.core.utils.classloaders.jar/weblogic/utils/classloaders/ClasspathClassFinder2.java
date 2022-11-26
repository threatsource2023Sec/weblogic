package weblogic.utils.classloaders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StringUtils;

public class ClasspathClassFinder2 extends MultiClassFinder implements ClasspathClassFinderInt {
   private static final String JRE_INSTALL_DIR;

   public ClasspathClassFinder2() {
      this(System.getProperty("java.class.path"));
   }

   public ClasspathClassFinder2(String cpath) {
      this(cpath, new HashSet());
   }

   public ClasspathClassFinder2(String cpath, Set exclude) {
      if (exclude == null) {
         throw new IllegalArgumentException("Null exclude set");
      } else {
         this.setClasspath(cpath, exclude);
      }
   }

   public final void setClasspath(String cpath) {
      this.setClasspath(cpath, new HashSet());
   }

   protected boolean validatePathElement(String element) {
      return !element.startsWith(JRE_INSTALL_DIR);
   }

   private void setClasspath(String cpath, Set exclude) {
      this.close();
      String[] pathElements = StringUtils.splitCompletely(cpath, File.pathSeparator);

      for(int i = 0; i < pathElements.length; ++i) {
         String s = pathElements[i];

         try {
            File f = new File(s);
            if (f.exists()) {
               s = f.getCanonicalPath();
               f = new File(s);
               if (this.validatePathElement(s) && !exclude.contains(s)) {
                  exclude.add(s);
                  ClassFinder cf = this.getClassFinder(f, exclude);
                  this.addFinder(cf);
               }
            }
         } catch (IOException var8) {
         }
      }

   }

   protected ClassFinder getClassFinder(File f, Set exclude) throws IOException {
      return new JarClassFinder(f, exclude);
   }

   public final String getNoDupExpandedClassPath() {
      StringBuffer sb = new StringBuffer();
      ClassFinder[] var2 = this.getClassFinders();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ClassFinder finder = var2[var4];
         if (finder instanceof JarClassFinder) {
            JarClassFinder jar = (JarClassFinder)finder;
            if (sb.length() > 0) {
               sb.append(PlatformConstants.PATH_SEP);
            }

            sb.append(jar.getDelegate().getClassPath());
            ClassFinder manifestFinder = jar.getManifestFinder();
            if (manifestFinder != null && manifestFinder instanceof ClasspathClassFinderInt) {
               String mf = ((ClasspathClassFinderInt)manifestFinder).getNoDupExpandedClassPath();
               if (!mf.equals("")) {
                  sb.append(PlatformConstants.PATH_SEP);
                  sb.append(mf);
               }
            }
         }
      }

      return sb.toString();
   }

   static {
      try {
         String javahome = System.getProperty("java.home");
         if (javahome == null) {
            throw new AssertionError("java.home is null");
         } else {
            JRE_INSTALL_DIR = (new File(javahome)).getCanonicalPath();
         }
      } catch (IOException var1) {
         throw new AssertionError(var1);
      }
   }

   public static final class ExistingScanningLimits extends ClasspathClassFinder2 {
      public ExistingScanningLimits(String path, Set exclude) {
         super(path, exclude);
      }

      public ClassFinder[] getClassFindersForAnnotationScan() {
         ClassFinder[] fs = this.getClassFinders();
         List finders = new ArrayList();
         ClassFinder[] var3 = fs;
         int var4 = fs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ClassFinder f = var3[var5];
            finders.add(((JarClassFinder)f).getDelegate());
         }

         return (ClassFinder[])((ClassFinder[])finders.toArray(new ClassFinder[finders.size()]));
      }
   }

   public static final class NoValidate extends ClasspathClassFinder2 {
      public NoValidate(String path) {
         super(path);
      }

      protected boolean validatePathElement(String element) {
         return true;
      }
   }
}
