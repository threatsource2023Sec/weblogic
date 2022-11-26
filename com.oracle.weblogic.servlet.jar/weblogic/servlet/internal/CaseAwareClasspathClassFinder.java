package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import weblogic.servlet.spi.SecurityProvider;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.JarClassFinder;

public class CaseAwareClasspathClassFinder extends ClasspathClassFinder2 {
   private static final boolean WIN_32;
   private static final boolean ENFORCE_CASE;

   public CaseAwareClasspathClassFinder(String cpath) {
      super(cpath, new HashSet());
   }

   protected ClassFinder getClassFinder(File f, Set exclude) throws IOException {
      return new JarClassFinder(f, exclude, ENFORCE_CASE);
   }

   public static boolean isCaseInsensitive() {
      SecurityProvider provider = WebServerRegistry.getInstance().getSecurityProvider();
      if (provider == null) {
         return WIN_32;
      } else {
         return !provider.areWebAppFilesCaseInsensitive() && WIN_32;
      }
   }

   static {
      WIN_32 = System.getProperty("os.name", "unknown").toLowerCase(Locale.ENGLISH).indexOf("windows") >= 0;
      ENFORCE_CASE = isCaseInsensitive();
   }
}
