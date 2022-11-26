package weblogic.utils.classloaders;

import java.io.IOException;
import java.net.URL;
import java.security.AccessControlException;
import java.util.Enumeration;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;
import weblogic.utils.classloaders.debug.SupportedClassLoader;
import weblogic.utils.collections.ConcurrentHashMap;

public class ChangeAwareClassLoader extends GenericClassLoader {
   private final ConcurrentHashMap modTimes = new ConcurrentHashMap();
   private final ConcurrentHashMap cachedClasses = new ConcurrentHashMap();
   private volatile long lastChecked;
   private volatile boolean upToDate = true;
   private volatile boolean childFirst;
   private static final DebugLogger vDebugLogger;
   private static final DebugLogger ctDebugLogger;

   public ChangeAwareClassLoader(ClassFinder finder, boolean childFirst, ClassLoader parent) {
      super(finder, parent);
      this.childFirst = childFirst;
      this.lastChecked = System.currentTimeMillis();
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.CACL, "loadClass", name);
      }

      try {
         return this.loadClass(name, false);
      } catch (Error var4) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var4);
         }

         throw var4;
      } catch (ClassNotFoundException var5) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var5);
         }

         throw var5;
      }
   }

   protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      synchronized(this.getClassLoadingLock(name)) {
         Class res = (Class)this.cachedClasses.get(name);
         if (res != null) {
            return res;
         } else if (!this.childFirst) {
            return super.loadClass(name, resolve);
         } else if (!name.startsWith("java.") && (!name.startsWith("javax.") || name.startsWith("javax.xml") || name.startsWith("javax.wsdl")) && !name.startsWith("weblogic.") && !name.startsWith("com.sun.org.")) {
            Class var10000;
            try {
               Class clazz = this.findClass(name);
               if (resolve) {
                  this.resolveClass(clazz);
               }

               var10000 = clazz;
            } catch (ClassNotFoundException var7) {
               return super.loadClass(name, resolve);
            }

            return var10000;
         } else {
            return super.loadClass(name, resolve);
         }
      }
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.CACL, "findClass", name);
      }

      Class res = (Class)this.cachedClasses.get(name);
      if (res != null) {
         return res;
      } else {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.CACL, "findClass", name, "About to loadClass");
         }

         try {
            res = super.findClass(name);
         } catch (Error var8) {
            if (doTrace) {
               ClassLoaderDebugger.debug(this, (Throwable)var8);
            }

            throw var8;
         } catch (ClassNotFoundException var9) {
            if (doTrace) {
               ClassLoaderDebugger.debug(this, (Throwable)var9);
            }

            throw var9;
         }

         this.cachedClasses.put(name, res);
         Source source = this.getClassFinder().getClassSource(name);
         if (source != null) {
            long lastMod = source.lastModified();
            this.modTimes.put(res.getName(), new Long(lastMod));
         }

         return res;
      }
   }

   public long getLastChecked() {
      return this.lastChecked;
   }

   public void forceToBounce() {
      this.upToDate = false;
   }

   public boolean upToDate() {
      if (!this.upToDate) {
         return false;
      } else {
         this.lastChecked = System.currentTimeMillis();
         Iterator i = this.modTimes.keySet().iterator();

         long oldModtime;
         long currentModtime;
         do {
            if (!i.hasNext()) {
               return true;
            }

            String name = (String)i.next();
            oldModtime = (Long)this.modTimes.get(name);
            Source source = this.getClassFinder().getClassSource(name);
            if (source == null) {
               return true;
            }

            currentModtime = source.lastModified();
         } while(currentModtime == oldModtime);

         this.upToDate = false;
         return false;
      }
   }

   public URL getResource(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.CACL, "getResource", name);
      }

      URL url;
      try {
         if (!this.childFirst) {
            url = this.getSuperResource(name);
         } else {
            url = this.findResource(name);
            if (url == null) {
               url = this.getSuperResource(name);
            }
         }
      } catch (AccessControlException var6) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.CACL, "getResource", name, "Resource does not seem to be visible in this security context", var6.getMessage());
         }

         url = null;
      }

      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.CACL, "getResource", name, url);
         if (url == null) {
            ClassLoaderDebugger.debug(this, (String)name);
         }
      }

      return url;
   }

   public Enumeration getResources(String name) throws IOException {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.CACL, "getResources", name);
      }

      if (!this.childFirst) {
         return super.getResources(name);
      } else {
         Enumeration urls = this.compound(this.findResources(name), this.getParentResources(name));
         if (doTrace) {
            ClassLoaderDebugger.debug(this, SupportedClassLoader.CACL, "getResources", name, urls);
            if (urls == null) {
               ClassLoaderDebugger.debug(this, (String)name);
            }
         }

         return urls;
      }
   }

   protected URL getSuperResource(String name) {
      return super.getResource(name);
   }

   public boolean isChildFirst() {
      return this.childFirst;
   }

   public void setChildFirst(boolean val) {
      this.childFirst = val;
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.CACL, "setChildFirst", Boolean.toString(val), "Set preferWebInfClasses to ", Boolean.toString(val));
      }

   }

   public String getClassPath() {
      if (!this.childFirst) {
         return super.getClassPath();
      } else {
         StringBuilder sb = new StringBuilder();
         String finderClasspath = this.getFinderClassPath();
         if (finderClasspath != null && !finderClasspath.equals("")) {
            sb.append(finderClasspath);
         }

         this.getParentClassPath(sb);
         return sb.toString();
      }
   }

   static {
      ClassLoader.registerAsParallelCapable();
      vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");
      ctDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingContextualTrace");
   }
}
