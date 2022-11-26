package weblogic.utils.classloaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;

public final class DefaultFilteringClassLoader extends GenericClassLoader {
   private static final String EXCLUDE_PROPERIES_FILE = "META-INF/_wl_filterpackages.properties";
   private static final String FILTER_LIST_KEY = "Filter-List";
   private static final DebugLogger vDebugLogger;

   public static DefaultFilteringClassLoader getDefaultFilteringClassLoader() {
      return DefaultFilteringClassLoader.DefaultFilteringClassLoaderSingleton.SINGLETON;
   }

   private DefaultFilteringClassLoader(ClassLoader parent) throws IOException, IllegalArgumentException {
      super(parent);
      this.populatePatternList();
   }

   private void populatePatternList() throws IOException {
      Enumeration e = this.getParent().getResources("META-INF/_wl_filterpackages.properties");
      if (e != null) {
         if (!e.hasMoreElements() && vDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.verbose(this, "populatePatternList", "", "NO Entries matching theDefaultFilteringClassLoader resource in the System classloader");
         }

         Set set = new HashSet();

         while(e.hasMoreElements()) {
            URL url = (URL)e.nextElement();
            if (vDebugLogger.isDebugEnabled()) {
               ClassLoaderDebugger.verbose(this, "populatePatternList", "", "properties file :" + url);
            }

            InputStream is = null;

            try {
               Properties p = new Properties();
               is = url.openStream();
               p.load(is);
               this.addPatterns(p, set, url);
            } finally {
               if (is != null) {
                  try {
                     is.close();
                  } catch (IOException var11) {
                  }
               }

            }
         }

         List list = new ArrayList();
         list.addAll(set);
         super.setFilterList(list);
      }
   }

   private void addPatterns(Properties p, Set set, URL u) throws IllegalArgumentException {
      String filterLst = p.getProperty("Filter-List");
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, "addPatterns", "", "Found Filterlist : '" + filterLst + "' in file: " + u);
      }

      String[] patterns = filterLst.split(",");
      if (patterns != null) {
         for(int i = 0; i < patterns.length; ++i) {
            String pattern = patterns[i].trim();
            if ("".equals(pattern)) {
               throw new IllegalArgumentException("Invalid pattern " + pattern + " found in " + u);
            }

            if (pattern.endsWith("*")) {
               pattern = pattern.substring(0, pattern.length() - 1);
            }

            if (!pattern.endsWith(".")) {
               pattern = pattern + ".";
            }

            set.add(pattern);
         }
      }

   }

   public URL getResource(String name) {
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, "getResource", name, "Method invoked");
      }

      return this.getParent().getResource(name);
   }

   public Enumeration getResources(String name) throws IOException {
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, "getResources", name, "Method invoked");
      }

      return this.getParent().getResources(name);
   }

   public void setFilterList(List patterns) {
      throw new UnsupportedOperationException("setFilterList not supported  on DefaultFilteringClassLoader");
   }

   // $FF: synthetic method
   DefaultFilteringClassLoader(ClassLoader x0, Object x1) throws IOException, IllegalArgumentException {
      this(x0);
   }

   static {
      ClassLoader.registerAsParallelCapable();
      vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");
   }

   private static final class DefaultFilteringClassLoaderSingleton {
      private static final DefaultFilteringClassLoader SINGLETON;

      static {
         try {
            SINGLETON = new DefaultFilteringClassLoader(AugmentableClassLoaderManager.getAugmentableSystemClassLoader());
         } catch (Throwable var1) {
            throw new AssertionError(var1);
         }
      }
   }
}
