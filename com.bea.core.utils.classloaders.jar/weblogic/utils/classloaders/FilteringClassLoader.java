package weblogic.utils.classloaders;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;
import weblogic.utils.classloaders.debug.SupportedClassLoader;
import weblogic.utils.enumerations.EmptyEnumerator;

public class FilteringClassLoader extends GenericClassLoader {
   private volatile List classPatterns = Collections.emptyList();
   private volatile List resourcePatterns = Collections.emptyList();
   private volatile boolean enableResourceInternalSearch = false;
   private static final DebugLogger vDebugLogger;
   private static final DebugLogger ctDebugLogger;

   public FilteringClassLoader(ClassLoader parent) {
      super(parent);
      if (parent == null) {
         throw new IllegalArgumentException("parent ClassLoader must be non null");
      }
   }

   public FilteringClassLoader(ClassLoader parent, List filterList) {
      super(parent);
      this.classPatterns = makePatterns(filterList);
   }

   public synchronized void setFilterList(List filterList) {
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "setFilterList", filterList.toString(), "Invoked from", (new Exception()).getStackTrace()[1].toString());
      }

      this.classPatterns = makePatterns(filterList);
   }

   protected boolean isClassPatternListEmpty() {
      return this.classPatterns.isEmpty();
   }

   public synchronized void setResourceFilterList(List resourceFilterList) {
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "setResourceFilterList", resourceFilterList.toString(), "Invoked from", (new Exception()).getStackTrace()[1].toString());
      }

      this.resourcePatterns = makePatterns(resourceFilterList);
   }

   protected boolean isResourcePatternListEmpty() {
      return this.resourcePatterns.isEmpty();
   }

   public synchronized void setResourceInternalSearch(boolean enable) {
      this.enableResourceInternalSearch = enable;
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "loadClass", name);
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

   public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "loadClass", name);
      }

      try {
         Class c = this.findClass(name);
         if (resolve) {
            this.resolveClass(c);
         }

         return c;
      } catch (Error var5) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var5);
         }

         throw var5;
      } catch (ClassNotFoundException var6) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var6);
         }

         throw var6;
      }
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace || beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "findClass", name);
      }

      if (!this.isClassPatternListEmpty() && this.matchesClassFilterList(name)) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "findClass", name, "Found match");
         }

         ClassNotFoundException e = new ClassNotFoundException(name);
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)e);
         }

         throw e;
      } else {
         return this.getParent().loadClass(name);
      }
   }

   protected URL getResourceInternal(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResourceInternal", name);
      }

      if (!this.isResourcePatternListEmpty() && this.matchesResourceFilterList(name)) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getResourceInternal", name, "Blocked on pattern match");
         }

         if (doTrace) {
            ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResourceInternal", name, (Object)null);
         }

         return null;
      } else if (this.isClassPatternListEmpty()) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getResourceInternal", name, "Delegating to parent");
         }

         if (this.enableResourceInternalSearch) {
            ClassLoader parent = this.getParent();
            if (parent instanceof GenericClassLoader) {
               URL u = ((GenericClassLoader)parent).getResourceInternal(name);
               if (doTrace) {
                  ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResourceInternal", name, u);
               }

               return u;
            }
         }

         URL u = this.getParent().getResource(name);
         if (doTrace) {
            ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResourceInternal", name, u);
         }

         return u;
      } else {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getResourceInternal", name, "Blocked");
         }

         if (doTrace) {
            ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResourceInternal", name, (Object)null);
         }

         return null;
      }
   }

   protected boolean isResourceSearchOrderPreferred(String name) {
      if (this.isClassPatternListEmpty()) {
         return super.isResourceSearchOrderPreferred(name);
      } else {
         return this.isResourcePatternListEmpty() || !this.isResourcePatternListEmpty() && !this.matchesResourceFilterList(name);
      }
   }

   public URL getResource(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResource", name);
      }

      URL u;
      if (!this.isResourcePatternListEmpty() && this.matchesResourceFilterList(name)) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getResource", name, "Resource lookup blocked on pattern match");
         }

         u = null;
         if (doTrace) {
            ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResource", name, u);
         }
      } else {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getResource", name, "Delegating to parent");
         }

         u = this.getParent().getResource(name);
         if (doTrace || beVerbose) {
            ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResource", name, u);
         }
      }

      if (doTrace && u == null) {
         ClassLoaderDebugger.debug(this, (String)name);
      }

      return u;
   }

   public Enumeration getResources(String name) throws IOException {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      if (beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResources", name);
      }

      Object e;
      if (!this.isResourcePatternListEmpty() && this.matchesResourceFilterList(name)) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getResources", name, "Blocked on pattern match");
         }

         e = new EmptyEnumerator();
      } else {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getResources", name, "Adopting default behavior");
         }

         Enumeration e = this.getParent().getResources(name);
         if (this.isClassPatternListEmpty()) {
            e = e instanceof ResourceEnumeration ? e : new ResourceEnumeration(e);
         } else {
            e = new FilteredResourceEnumeration(e);
         }
      }

      if (beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getResources", name, e);
      }

      return (Enumeration)e;
   }

   public Enumeration findResources(String name) throws IOException {
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "findResources", name);
      }

      return new EmptyEnumerator();
   }

   public List getClassPatterns() {
      return this.classPatterns;
   }

   public List getResourcePatterns() {
      return this.resourcePatterns;
   }

   private boolean matchesClassFilterList(String name) {
      Iterator var2 = this.classPatterns.iterator();

      Matcher matcher;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         Pattern pattern = (Pattern)var2.next();
         matcher = pattern.matcher(name);
      } while(!matcher.find());

      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "matchesClassFilterList", name, matcher.group() + " index : " + matcher.start() + " end : " + matcher.end());
      }

      return true;
   }

   protected boolean matchesResourceFilterList(String name) {
      Iterator var2 = this.resourcePatterns.iterator();

      Matcher matcher;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         Pattern pattern = (Pattern)var2.next();
         matcher = pattern.matcher(name);
      } while(!matcher.find());

      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "matchesResourceFilterList", name, matcher.group() + " index : " + matcher.start() + " end : " + matcher.end());
      }

      return true;
   }

   private static List makePatterns(List filter) {
      if (filter != null && !filter.isEmpty()) {
         List patterns = new ArrayList();
         Iterator i = filter.iterator();

         while(i.hasNext()) {
            String pat = (String)i.next();
            if (pat.endsWith("*")) {
               pat = pat.substring(0, pat.length() - 1);
            }

            if (pat.endsWith(".")) {
               pat = pat + "{0,1}";
            }

            Pattern p = Pattern.compile("^" + pat);
            patterns.add(p);
         }

         return patterns;
      } else {
         return Collections.emptyList();
      }
   }

   protected Package getPackage(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace || beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getPackage", name);
      }

      if (!this.isClassPatternListEmpty() && this.matchesClassFilterList(name)) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getPackage", name, "Found match");
         }

         return null;
      } else {
         return super.getPackage(name);
      }
   }

   protected Package[] getPackages() {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace || beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.FCL, "getPackages", "");
      }

      Package[] packages = super.getPackages();
      if (!this.isClassPatternListEmpty()) {
         List filteredPackages = new LinkedList();
         Package[] var5 = packages;
         int var6 = packages.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Package aPackage = var5[var7];
            String name = aPackage.getName();
            if (this.matchesClassFilterList(name)) {
               if (beVerbose) {
                  ClassLoaderDebugger.verbose(this, SupportedClassLoader.FCL, "getPackage", name, "Found match");
               }
            } else {
               filteredPackages.add(aPackage);
            }
         }

         packages = (Package[])filteredPackages.toArray(new Package[filteredPackages.size()]);
      }

      return packages;
   }

   static {
      ClassLoader.registerAsParallelCapable();
      vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");
      ctDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingContextualTrace");
   }
}
