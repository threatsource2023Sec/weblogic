package weblogic.utils.classloaders;

import com.oracle.classloader.PolicyClassLoader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.GenericSignatureFormatError;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.SecureClassLoader;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsr166e.LongAdder;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.ClassLoaderPerfCounter;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Classpath;
import weblogic.utils.Hex;
import weblogic.utils.PlatformConstants;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;
import weblogic.utils.classloaders.debug.SupportedClassLoader;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.MappingEnumerator;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.FilenameEncoder;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public class GenericClassLoader extends SecureClassLoader implements ClassLoaderPerfCounter {
   private static final ClassPreProcessor.ClassPreProcessorSupport support;
   private static final Annotation DEFAULT;
   private static final AtomicBoolean isOptimizedEnvironment;
   private final Set excludeClasses;
   private final PerfCounter perf;
   private final CodeGenClassFinder finder;
   private volatile Annotation annotation;
   private volatile ClassPreProcessor.ClassPreProcessorSupport instancePreProcessors;
   private static final boolean CLASSLOAD_CHECK_JAR_SIGNAGE;
   private volatile List classPatterns;
   private volatile List resourcePatterns;
   private final ClassLoader parent;
   private volatile GenericClassLoader altParent;
   private final Map packages;
   private static final DebugLogger vDebugLogger;
   private static final DebugLogger ctDebugLogger;
   private boolean ascendantsCached;
   private Set ascendantRefs;
   private Set ascendantHashCodes;
   private final boolean delegateToParent;
   private static boolean writeMagicFailureDetails;
   private static volatile String expandedClasspath;
   private int cachedHashCode;

   public GenericClassLoader(ClassLoader parent) {
      this(parent, true);
   }

   public GenericClassLoader(ClassLoader parent, boolean delegateToParent) {
      this(NullClassFinder.NULL_FINDER, parent, true, delegateToParent);
   }

   public GenericClassLoader(ClassFinder finder) {
      this(finder, Thread.currentThread().getContextClassLoader());
   }

   public GenericClassLoader(ClassFinder finder, ClassLoader parent) {
      this(finder, parent, false);
   }

   public GenericClassLoader(ClassFinder finder, ClassLoader parent, boolean noParentOK) {
      this(finder, parent, noParentOK, parent != null);
   }

   private GenericClassLoader(ClassFinder finder, ClassLoader parent, boolean noParentOK, boolean delegateToParent) {
      super(parent);
      this.excludeClasses = Collections.newSetFromMap(new ConcurrentHashMap());
      this.perf = new PerfCounter();
      this.annotation = DEFAULT;
      this.classPatterns = Collections.emptyList();
      this.resourcePatterns = Collections.emptyList();
      this.altParent = null;
      this.packages = new ConcurrentHashMap();
      this.ascendantsCached = false;
      this.ascendantRefs = null;
      this.ascendantHashCodes = null;
      this.cachedHashCode = 0;
      if (finder == null) {
         throw new IllegalArgumentException("finder is null");
      } else if (!noParentOK && parent == null) {
         throw new IllegalArgumentException("parent is null");
      } else {
         this.parent = parent;
         this.finder = new CodeGenClassFinder(finder);
         if (isOptimizedEnvironment.get()) {
            GenericClassLoaderRegistry reg = (GenericClassLoaderRegistry)GlobalServiceLocator.getServiceLocator().getService(GenericClassLoaderRegistry.class, new java.lang.annotation.Annotation[0]);
            if (reg != null) {
               reg.registerGenericClassLoader(this);
            }
         }

         if (parent != null) {
            this.delegateToParent = delegateToParent;
         } else {
            this.delegateToParent = false;
         }

      }
   }

   public static GenericClassLoader getRootClassLoader(ClassFinder finder) {
      return new GenericClassLoader(finder, (ClassLoader)null, true);
   }

   public synchronized void setAltParent(GenericClassLoader altParent) {
      if (altParent == null) {
         throw new IllegalArgumentException("Altrenate Parent may not be null");
      } else if (this.altParent != null) {
         throw new IllegalStateException("Alternate Parent has alread been set to " + this.altParent + ".It may not be reset to " + altParent);
      } else {
         this.altParent = altParent;
      }
   }

   public GenericClassLoader getAltParent() {
      return this.altParent;
   }

   public static void addClassPreProcessor(String className) {
      support.addClassPreProcessor(className);
   }

   public synchronized void addInstanceClassPreProcessor(ClassPreProcessor cpp) {
      if (this.instancePreProcessors == null) {
         this.instancePreProcessors = new ClassPreProcessor.ClassPreProcessorSupport(false);
      }

      this.instancePreProcessors.addClassPreProcessor(cpp);
   }

   public final synchronized void setAnnotation(Annotation a) {
      this.annotation = a;
   }

   public final Annotation getAnnotation() {
      return this.annotation;
   }

   public synchronized void setFilterList(List filterList) {
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "setFilterList", filterList.toString(), "Invoked from", (new Exception()).getStackTrace()[1].toString());
      }

      this.classPatterns = makePatterns(filterList);
   }

   protected boolean isClassPatternListEmpty() {
      return this.classPatterns == null || this.classPatterns.isEmpty();
   }

   public synchronized void setResourceFilterList(List resourceFilterList) {
      if (vDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "setResourceFilterList", resourceFilterList.toString(), "Invoked from", (new Exception()).getStackTrace()[1].toString());
      }

      this.resourcePatterns = makePatterns(resourceFilterList);
   }

   protected boolean isResourcePatternListEmpty() {
      return this.resourcePatterns == null || this.resourcePatterns.isEmpty();
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
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "matchesClassFilterList", name, matcher.group() + " index : " + matcher.start() + " end : " + matcher.end());
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
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "matchesResourceFilterList", name, matcher.group() + " index : " + matcher.start() + " end : " + matcher.end());
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

   protected URL getResourceInternal(String name) {
      return this.getResourceInternal(name, !this.delegateToParent);
   }

   protected boolean getResourceInternalEntryTrace(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      boolean doTraceOrBeVerbose = doTrace || beVerbose;
      if (doTraceOrBeVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResourceInternal", name);
      }

      return beVerbose;
   }

   protected void getResourceMatchesFilterListEntryTrace(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResourceInternal", name, "Blocked on pattern match");
      }

      if (ctDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResourceInternal", name, (Object)null);
      }

   }

   protected void getResourceNoMatchFilterListEntryTrace(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResourceInternal", name, "Delegating to parent");
      }

   }

   protected URL doParentGetResourceInternal(String name) {
      return ((GenericClassLoader)this.parent).getResourceInternal(name);
   }

   protected URL doParentGetResource(String name) {
      return this.parent.getResource(name);
   }

   protected void getResourceNoMatchFilterListExitTrace(String name, URL u) {
      if (ctDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResourceInternal", name, u);
      }

   }

   protected void getResourceNoPatternListEntryTrace(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResourceInternal", name, "Delegating to parent");
      }

   }

   protected void getResourceNoPatternListExitTrace(String name, URL u) {
      if (ctDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResourceInternal", name, u);
      }

   }

   protected void getResourcePatternListEntryTrace(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResourceInternal", name, "Blocked");
      }

      if (ctDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResourceInternal", name, (Object)null);
      }

   }

   protected URL doAltParentGetResource(String name) {
      return this.altParent.getResourceInternal(name, !this.altParent.delegateToParent);
   }

   protected void getResourcePostParentDelegateTrace(String name, boolean beVerbose, URL u) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResourceInternal", name, "URL from parent getResourceInternal1", u);
      }

   }

   protected void getResourcePostFindResourceTrace(String name, boolean beVerbose, URL u) {
      if (beVerbose && u != null) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResourceInternal", name, "findResource returned", u);
      }

   }

   protected void getResourceInternalExitTrace(String name, URL u) {
      if (ctDebugLogger.isDebugEnabled()) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResourceInternal", name, u);
      }

   }

   protected URL getResourceInternal(String name, boolean disableParentDelegation) {
      boolean beVerbose = this.getResourceInternalEntryTrace(name);
      URL u = null;
      if (this.isParentGenericClassLoader()) {
         if (!this.isResourcePatternListEmpty()) {
            if (this.matchesResourceFilterList(name)) {
               this.getResourceMatchesFilterListEntryTrace(name, beVerbose);
            } else {
               this.getResourceNoMatchFilterListEntryTrace(name, beVerbose);
               if (!disableParentDelegation) {
                  u = this.doParentGetResourceInternal(name);
               } else {
                  u = this.doParentGetResource(name);
               }

               this.getResourceNoMatchFilterListExitTrace(name, u);
            }
         } else if (this.isClassPatternListEmpty()) {
            this.getResourceNoPatternListEntryTrace(name, beVerbose);
            if (!disableParentDelegation) {
               u = this.doParentGetResourceInternal(name);
            } else {
               u = this.doParentGetResource(name);
            }

            this.getResourceNoPatternListExitTrace(name, u);
         } else {
            this.getResourcePatternListEntryTrace(name, beVerbose);
         }

         if (u == null && this.shouldAltParentDelegate(disableParentDelegation)) {
            u = this.doAltParentGetResource(name);
         }

         this.getResourcePostParentDelegateTrace(name, beVerbose, u);
         if (u == null) {
            u = this.findResource(name);
            this.getResourcePostFindResourceTrace(name, beVerbose, u);
         }
      } else {
         u = this.getParentResource(name);
         if (u == null) {
            u = this.findResource(name);
         }
      }

      this.getResourceInternalExitTrace(name, u);
      return u;
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "loadClass", name);
      }

      try {
         return this.loadClass(name, false);
      } catch (Error var5) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var5);
         }

         throw var5;
      } catch (IllegalStateException var6) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var6);
         }

         IllegalStateException ise2 = new IllegalStateException("class: " + name + ",loader: " + this, var6);
         throw ise2;
      } catch (ClassNotFoundException var7) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var7);
         }

         throw var7;
      }
   }

   protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      return this.loadClass(name, resolve, false);
   }

   protected Class loadClass(String name, boolean resolve, boolean sharedModeLoading) throws ClassNotFoundException {
      long startTime = System.nanoTime();
      long startParentDelegate = 0L;
      long endParentDelegate = 0L;
      long startFindClass = 0L;
      long endFindClass = 0L;

      Class var17;
      try {
         boolean bVerbose = this.loadClassEntryTrace(name);
         synchronized(this.getClassLoadingLock(name)) {
            Class clz = this.findLoadedClass(name);
            if (clz == null) {
               if (this.shouldParentDelegate(sharedModeLoading, name, bVerbose)) {
                  startParentDelegate = System.nanoTime();

                  try {
                     clz = this.doParentDelegate(name);
                  } finally {
                     endParentDelegate = System.nanoTime();
                  }
               }

               if (clz == null && this.shouldAltParentDelegate(sharedModeLoading)) {
                  if (startParentDelegate == 0L) {
                     startParentDelegate = System.nanoTime();
                  }

                  try {
                     clz = this.doAltParentDelegate(name, resolve, true);
                  } finally {
                     endParentDelegate = System.nanoTime();
                  }
               }

               if (clz == null) {
                  startFindClass = System.nanoTime();

                  try {
                     clz = this.doFindClass(name);
                  } finally {
                     endFindClass = System.nanoTime();
                  }
               }
            }

            if (resolve) {
               this.resolveClass(clz);
            }

            var17 = clz;
         }
      } finally {
         this.perf.recordStats(startTime, startParentDelegate, endParentDelegate, startFindClass, endFindClass);
      }

      return var17;
   }

   protected boolean loadClassEntryTrace(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace || beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "loadClass", name);
      }

      return beVerbose;
   }

   protected boolean shouldParentDelegate(boolean sharedModeLoading, String name, boolean bVerbose) {
      boolean disableParentDelegation = this.parent == null || sharedModeLoading && !this.delegateToParent;
      boolean isSkip = disableParentDelegation || !this.isClassPatternListEmpty() && this.matchesClassFilterList(name);
      if (isSkip && bVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "loadClass", name, "Parent delegation disabled or found filter match. Not checking with parent.");
      }

      return !isSkip;
   }

   protected boolean shouldAltParentDelegate(boolean sharedModeLoading) {
      boolean disableParentDelegation = sharedModeLoading && !this.delegateToParent;
      return this.altParent != null && !disableParentDelegation;
   }

   protected Class doParentDelegate(String name) {
      try {
         return this.parent == null ? null : this.parent.loadClass(name);
      } catch (Error var3) {
         if (ctDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.debug(this, (Throwable)var3);
         }

         throw var3;
      } catch (ClassNotFoundException var4) {
         return null;
      }
   }

   protected Class doAltParentDelegate(String name, boolean resolve, boolean sharedModeLoading) {
      try {
         return this.altParent.loadClass(name, resolve, sharedModeLoading);
      } catch (Error var5) {
         if (ctDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.debug(this, (Throwable)var5);
         }

         throw var5;
      } catch (ClassNotFoundException var6) {
         return null;
      }
   }

   protected Class doFindClass(String name) throws ClassNotFoundException {
      try {
         return this.findClass(name);
      } catch (Error var3) {
         if (ctDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.debug(this, (Throwable)var3);
         }

         throw var3;
      } catch (ClassNotFoundException var4) {
         if (ctDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.debug(this, (Throwable)var4);
         }

         throw var4;
      }
   }

   protected void getResourceEntryTrace(String name) {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResource", name);
      }

   }

   protected void getResourceExitTrace(String name, URL u) {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      boolean doTraceOrBeVerbose = doTrace || vDebugLogger.isDebugEnabled();
      if (doTraceOrBeVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResource", name, u);
      }

      if (doTrace && u == null) {
         ClassLoaderDebugger.debug(this, name);
      }

   }

   protected URL getParentResource(String name) {
      return this.parent.getResource(name);
   }

   public URL getResource(String name) {
      long startTime = System.nanoTime();
      long startParentDelegate = 0L;
      long endParentDelegate = 0L;

      URL var10;
      try {
         this.getResourceEntryTrace(name);
         boolean beVerbose = this.getResourceInternalEntryTrace(name);
         URL u = null;
         if (this.isParentGenericClassLoader()) {
            if (!this.isResourcePatternListEmpty()) {
               if (this.matchesResourceFilterList(name)) {
                  this.getResourceMatchesFilterListEntryTrace(name, beVerbose);
               } else {
                  this.getResourceNoMatchFilterListEntryTrace(name, beVerbose);
                  startParentDelegate = System.nanoTime();

                  try {
                     u = this.doParentGetResourceInternal(name);
                  } finally {
                     endParentDelegate = System.nanoTime();
                  }

                  this.getResourceNoMatchFilterListExitTrace(name, u);
               }
            } else if (this.isClassPatternListEmpty()) {
               this.getResourceNoPatternListEntryTrace(name, beVerbose);
               startParentDelegate = System.nanoTime();

               try {
                  u = this.doParentGetResourceInternal(name);
               } finally {
                  endParentDelegate = System.nanoTime();
               }

               this.getResourceNoPatternListExitTrace(name, u);
            } else {
               this.getResourcePatternListEntryTrace(name, beVerbose);
            }

            if (u == null && this.shouldAltParentDelegate(false)) {
               if (startParentDelegate == 0L) {
                  startParentDelegate = System.nanoTime();
               }

               try {
                  u = this.doAltParentGetResource(name);
               } finally {
                  endParentDelegate = System.nanoTime();
               }
            }

            this.getResourcePostParentDelegateTrace(name, beVerbose, u);
            if (u == null) {
               u = this.findResource(name);
               this.getResourcePostFindResourceTrace(name, beVerbose, u);
            }
         } else {
            startParentDelegate = System.nanoTime();

            try {
               u = this.getParentResource(name);
            } finally {
               endParentDelegate = System.nanoTime();
            }

            if (u == null) {
               u = this.findResource(name);
            }
         }

         this.getResourceInternalExitTrace(name, u);
         if (u == null && this.isResourceSearchOrderPreferred(name)) {
            if (endParentDelegate != 0L) {
               startParentDelegate = System.nanoTime() - (endParentDelegate - startParentDelegate);
            }

            try {
               u = this.getParentResource(name);
               if (u == null) {
                  u = this.findResource(name);
               }
            } finally {
               endParentDelegate = System.nanoTime();
            }
         }

         this.getResourceExitTrace(name, u);
         var10 = u;
      } finally {
         this.perf.recordResourceStats(startTime, startParentDelegate, endParentDelegate);
      }

      return var10;
   }

   public Enumeration getResources(String name) throws IOException {
      return this.getResources(name, !this.delegateToParent);
   }

   protected boolean getResourcesEntryTrace(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      if (beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResources", name);
      }

      return beVerbose;
   }

   protected boolean isParentGenericClassLoader() {
      return this.parent != null && this.parent instanceof GenericClassLoader;
   }

   protected void getResourcesParentEntryTrace(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResources", name, "getResources calling parent");
      }

   }

   protected Enumeration getResourcesParentDelegationDisabled(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResources", name, "Parent delegation disabled or blocked on pattern match");
      }

      return new ResourceEnumeration(new EmptyEnumerator());
   }

   protected Enumeration getResourcesMatchesResourceFilterList(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResources", name, "Parent delegation disabled or blocked on pattern match");
      }

      return new ResourceEnumeration(new EmptyEnumerator());
   }

   protected Enumeration wrapAsResourceEnumeration(Enumeration urls) {
      return (Enumeration)(urls instanceof ResourceEnumeration ? urls : new ResourceEnumeration(urls));
   }

   protected Enumeration getParentResources(String name) throws IOException {
      return this.parent == null ? null : this.parent.getResources(name);
   }

   protected void getResourcesNoMatchResourceFilterListEntryTrace(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResources", name, "Delegating to parent");
      }

   }

   protected void getResourcesNoPatternListEntryTrace(String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getResources", name, "Adopting default behavior");
      }

   }

   protected Enumeration wrapAsFilteredResourceEnumeration(Enumeration urls) {
      return new FilteredResourceEnumeration(urls);
   }

   protected void doAltParentGetResources(Enumeration urls, String name) throws IOException {
      ((ResourceEnumeration)urls).addEnumeration(this.altParent.getResources(name, !this.altParent.delegateToParent));
   }

   protected void doFindResources(Enumeration urls, String name) throws IOException {
      ((ResourceEnumeration)urls).addEnumeration(this.findResources(name));
   }

   protected void getResourcesExitTrace(Enumeration urls, String name, boolean beVerbose) {
      if (beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResources", name, urls);
      }

   }

   protected Enumeration compound(final Enumeration one, final Enumeration two) {
      if (one == null) {
         return two;
      } else {
         return two == null ? one : new Enumeration() {
            public boolean hasMoreElements() {
               return one.hasMoreElements() || two.hasMoreElements();
            }

            public URL nextElement() {
               return one.hasMoreElements() ? (URL)one.nextElement() : (URL)two.nextElement();
            }
         };
      }
   }

   protected Enumeration getResources(String name, boolean disableParentDelegation) throws IOException {
      long startTime = System.nanoTime();
      long startParentDelegate = 0L;
      long endParentDelegate = 0L;

      Enumeration parentResources;
      try {
         boolean beVerbose = this.getResourcesEntryTrace(name);
         Enumeration urls;
         if (this.isParentGenericClassLoader()) {
            this.getResourcesParentEntryTrace(name, beVerbose);
            if (disableParentDelegation) {
               urls = this.getResourcesParentDelegationDisabled(name, beVerbose);
            } else if (!this.isResourcePatternListEmpty()) {
               if (this.matchesResourceFilterList(name)) {
                  urls = this.getResourcesMatchesResourceFilterList(name, beVerbose);
               } else {
                  this.getResourcesNoMatchResourceFilterListEntryTrace(name, beVerbose);
                  urls = this.wrapAsResourceEnumeration(this.getParentResources(name));
               }
            } else {
               this.getResourcesNoPatternListEntryTrace(name, beVerbose);
               startParentDelegate = System.nanoTime();

               try {
                  urls = this.getParentResources(name);
                  if (this.isClassPatternListEmpty()) {
                     urls = this.wrapAsResourceEnumeration(urls);
                  } else {
                     urls = this.wrapAsFilteredResourceEnumeration(urls);
                  }
               } finally {
                  endParentDelegate = System.nanoTime();
               }
            }

            if (this.shouldAltParentDelegate(disableParentDelegation)) {
               if (startParentDelegate == 0L) {
                  startParentDelegate = System.nanoTime();
               }

               try {
                  this.doAltParentGetResources(urls, name);
               } finally {
                  endParentDelegate = System.nanoTime();
               }
            }

            this.doFindResources(urls, name);
         } else {
            startParentDelegate = System.nanoTime();

            try {
               parentResources = this.getParentResources(name);
            } finally {
               endParentDelegate = System.nanoTime();
            }

            urls = this.compound(parentResources, this.findResources(name));
         }

         this.getResourcesExitTrace(urls, name, beVerbose);
         parentResources = urls;
      } finally {
         this.perf.recordResourceStats(startTime, startParentDelegate, endParentDelegate);
      }

      return parentResources;
   }

   protected boolean isResourceSearchOrderPreferred(String name) {
      if (this.isClassPatternListEmpty()) {
         for(ClassLoader loader = this.parent; loader != null; loader = (ClassLoader)AccessController.doPrivileged(new GetClassLoaderParentAction(loader))) {
            if (loader instanceof GenericClassLoader) {
               return ((GenericClassLoader)loader).isResourceSearchOrderPreferred(name);
            }
         }

         return false;
      } else {
         return this.isResourcePatternListEmpty() || !this.isResourcePatternListEmpty() && !this.matchesResourceFilterList(name);
      }
   }

   protected URL findResource(String name) {
      boolean doTraceOrBeVerbose = ctDebugLogger.isDebugEnabled() || vDebugLogger.isDebugEnabled();
      if (doTraceOrBeVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "findResource", name);
      }

      name = FilenameEncoder.resolveRelativeURIPath(name, false);
      if (name == null) {
         return null;
      } else {
         Source s = this.finder.getSource(name);
         if (s == null) {
            return null;
         } else {
            URL url = s.getURL();
            if (doTraceOrBeVerbose) {
               ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "findResource", name, url);
            }

            return url;
         }
      }
   }

   protected Enumeration findResources(String name) throws IOException {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      if (beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "findResources", name);
      }

      name = FilenameEncoder.resolveRelativeURIPath(name, false);
      if (name == null) {
         return null;
      } else {
         Enumeration e = this.finder.getSources(name);
         if (beVerbose) {
            ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "findResources", name, e);
         }

         return new MappingEnumerator(e) {
            protected URL map(Source s) {
               return s.getURL();
            }
         };
      }
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "findClass", name);
      }

      String baseName = name;
      if (name.indexOf(36) != -1) {
         baseName = name.substring(0, name.indexOf(36));
      }

      if (this.excludeClasses.contains(baseName)) {
         if (vDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "findClass", name, "Arg found to be on the exclude set");
         }

         ClassNotFoundException e = new ClassNotFoundException(name);
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)e);
         }

         throw e;
      } else {
         ClassNotFoundException ex;
         try {
            return name.startsWith("[") && name.endsWith(";") ? this.findArrayClass(name) : this.findLocalClass(name);
         } catch (UnsupportedClassVersionError var6) {
            ClassLoadersLogger.wrongCompilerVersion(name, var6);
            ex = new ClassNotFoundException(ClassLoadersTextTextFormatter.getInstance().wrongCompilerVersion(name), var6);
            if (doTrace) {
               ClassLoaderDebugger.debug(this, (Throwable)ex);
            }

            throw ex;
         } catch (final GenericSignatureFormatError var7) {
            AssertionError ae = new AssertionError("GenericSignatureError not expecded in findClass") {
               {
                  this.initCause(var7);
               }
            };
            if (doTrace) {
               ClassLoaderDebugger.debug(this, (Throwable)ae);
            }

            throw ae;
         } catch (ClassFormatError var8) {
            ClassLoadersLogger.unexpectedClassFormatError(name, var8);
            ex = new ClassNotFoundException("Class bytes found but defineClass()failed for: '" + name + "'", var8);
            if (doTrace) {
               ClassLoaderDebugger.debug(this, (Throwable)ex);
            }

            throw ex;
         } catch (Error var9) {
            if (doTrace) {
               ClassLoaderDebugger.debug(this, (Throwable)var9);
            }

            throw var9;
         } catch (ClassNotFoundException var10) {
            if (doTrace) {
               ClassLoaderDebugger.debug(this, (Throwable)var10);
            }

            throw var10;
         }
      }
   }

   private Class findLocalClass(String name) throws ClassNotFoundException {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      if (beVerbose) {
         ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "findLocalClass", name, "Classpath in use", this.finder.getClassPath());
      }

      Source s = this.finder.getClassSource(name);
      if (s == null) {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "findLocalClass", name, "not found");
         }

         throw new ClassNotFoundException(name);
      } else if (s instanceof SharedSource) {
         return ((SharedSource)s).getSharedClass(name);
      } else {
         if (beVerbose) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "findLocalClass", name, "Found class");
         }

         return this.defineClass(name, s);
      }
   }

   public Class defineClass(String name, Source s) throws ClassNotFoundException {
      long startTime = System.nanoTime();

      Class var5;
      try {
         var5 = this.defineClassInternal(name, s);
      } finally {
         this.perf.recordDefineClassStats(startTime);
      }

      return var5;
   }

   private Class defineClassInternal(String name, Source s) throws ClassNotFoundException {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "defineClass", name);
      }

      Manifest man = null;
      Certificate[] certs = null;
      byte[] bytes = null;
      this.finder.addCodeGenSource(name, s);
      if (s instanceof JarSource) {
         JarSource js = (JarSource)s;

         try {
            man = js.getManifest();
            if (CLASSLOAD_CHECK_JAR_SIGNAGE) {
               bytes = js.getBytes();
               certs = js.getCertificates();
            }
         } catch (IOException var12) {
            ClassLoadersLogger.errorReadingJarFile(s.getURL().toString(), var12);
         }
      }

      try {
         if (bytes == null) {
            bytes = s.getBytes();
         }
      } catch (IOException var17) {
         ClassNotFoundException e = new ClassNotFoundException(name, var17);
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)e);
         }

         throw e;
      }

      try {
         try {
            this.checkMagicNumber(bytes, name);
         } catch (ClassNotFoundException var11) {
            throw this.getExtendedMajicFailureException(name, s, bytes, var11);
         }

         bytes = this.doPreProcess(bytes, name);
         URL codeSourceURL = s.getCodeSourceURL();

         try {
            this.definePackage(name, man, codeSourceURL);
         } catch (IllegalArgumentException var14) {
            int i = name.lastIndexOf(46);
            if (i >= 0) {
               String pname = name.substring(0, i);
               if (this.getPackage(pname) == null) {
                  throw var14;
               }
            }
         }

         CodeSource codeSource = new CodeSource(codeSourceURL, certs);

         Class c;
         try {
            c = this.defineClass(name, bytes, 0, bytes.length, codeSource);
         } catch (LinkageError var13) {
            c = this.findLoadedClass(name);
            if (c == null) {
               throw var13;
            }
         }

         if (vDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "defineClass", name, "Defined class", c);
         }

         return c;
      } catch (Error var15) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var15);
         }

         throw var15;
      } catch (ClassNotFoundException var16) {
         if (doTrace) {
            ClassLoaderDebugger.debug(this, (Throwable)var16);
         }

         throw var16;
      }
   }

   private ClassNotFoundException getExtendedMajicFailureException(String name, Source s, byte[] bytes, ClassNotFoundException cnfe) {
      if (!writeMagicFailureDetails) {
         return cnfe;
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         PrintWriter pw = new PrintWriter(baos);
         pw.println();
         pw.println("Failed to read the magic number from class " + name);
         pw.println("   location: " + s.getCodeSourceURL() + ", " + s.getURL());
         pw.print("   class bytes start after the arrow -->");
         byte[] var7 = bytes;
         int var8 = bytes.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            byte b = var7[var9];
            pw.print(b);
         }

         pw.println("<-- class bytes end before the arrow.");
         pw.flush();
         return new ClassNotFoundException(baos.toString());
      }
   }

   public byte[] doPreProcess(byte[] bytes, String name) {
      if (this.instancePreProcessors != null) {
         bytes = this.instancePreProcessors.preProcess(name, bytes);
      }

      return support.preProcess(name, bytes);
   }

   private Class findArrayClass(String name) throws ClassNotFoundException {
      int arrayDim;
      for(arrayDim = 0; name.charAt(arrayDim) == '['; ++arrayDim) {
      }

      name = name.substring(arrayDim + 1, name.length() - 1);
      Class c = this.loadClass(name);
      return Array.newInstance(c, new int[arrayDim]).getClass();
   }

   public String getClassPath() {
      StringBuilder sb = new StringBuilder();
      this.getClassPath(sb, true);
      return sb.toString();
   }

   private void getClassPath(StringBuilder sb, boolean includeParentCP) {
      if (includeParentCP) {
         this.getParentClassPath(sb);
      }

      String finderClasspath = this.getFinderClassPath();
      if (finderClasspath != null && !finderClasspath.equals("")) {
         if (sb.length() > 0) {
            sb.append(PlatformConstants.PATH_SEP);
         }

         sb.append(finderClasspath);
      }

   }

   protected final void getParentClassPath(StringBuilder sb) {
      String parentClasspath;
      if (this.parent instanceof GenericClassLoader) {
         parentClasspath = ((GenericClassLoader)this.parent).getClassPath();
      } else {
         parentClasspath = getExpanded();
      }

      if (parentClasspath != null && !parentClasspath.equals("")) {
         if (sb.length() > 0) {
            sb.append(PlatformConstants.PATH_SEP);
         }

         sb.append(parentClasspath);
      }

      if (this.altParent != null) {
         this.altParent.getClassPath(sb, false);
      }

   }

   public final String getFinderClassPath() {
      return this.finder.getClassPath();
   }

   public final void excludeClass(String name) {
      this.excludeClasses.add(name);
   }

   private void checkMagicNumber(byte[] bytes, String name) throws ClassNotFoundException {
      try {
         int magic = DataIO.readInt(new UnsyncByteArrayInputStream(bytes));
         if (magic != -889275714) {
            if (vDebugLogger.isDebugEnabled()) {
               ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "checkMagicNumber", name, "Bad bytes for class", Hex.dump(bytes));
            }

            throw new ClassNotFoundException(name);
         }
      } catch (IOException var4) {
         if (vDebugLogger.isDebugEnabled()) {
            ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "checkMagicNumber", name, "Exception while trying to verify magic", var4.getMessage());
            var4.printStackTrace();
         }

         throw new ClassNotFoundException(name);
      }
   }

   private static String getAttributeValue(Attributes.Name n, Attributes a, Attributes b) {
      String returnValue = null;
      if (a != null) {
         returnValue = a.getValue(n);
      }

      if (returnValue == null && b != null) {
         returnValue = b.getValue(n);
      }

      return returnValue;
   }

   private void definePackage(String className, Manifest man, URL codeSourceURL) {
      int i = className.lastIndexOf(46);
      if (i != -1) {
         String pname = className.substring(0, i);
         Package p = this.getPackage(pname);
         if (p == null) {
            Attributes pathAttributes = null;
            Attributes mainAttributes = null;
            String specTitle;
            if (man != null) {
               specTitle = pname.replace('.', '/').concat("/");
               pathAttributes = man.getAttributes(specTitle);
               mainAttributes = man.getMainAttributes();
            }

            specTitle = getAttributeValue(Name.SPECIFICATION_TITLE, pathAttributes, mainAttributes);
            String specVersion = getAttributeValue(Name.SPECIFICATION_VERSION, pathAttributes, mainAttributes);
            String specVendor = getAttributeValue(Name.SPECIFICATION_VENDOR, pathAttributes, mainAttributes);
            String implTitle = getAttributeValue(Name.IMPLEMENTATION_TITLE, pathAttributes, mainAttributes);
            String implVersion = getAttributeValue(Name.IMPLEMENTATION_VERSION, pathAttributes, mainAttributes);
            String implVendor = getAttributeValue(Name.IMPLEMENTATION_VENDOR, pathAttributes, mainAttributes);
            String sealed = getAttributeValue(Name.SEALED, pathAttributes, mainAttributes);
            URL sealBase = "true".equalsIgnoreCase(sealed) ? codeSourceURL : null;
            this.definePackage(pname, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);
         }
      }
   }

   public void freezeClassFinder() {
      final long obt = System.nanoTime();
      Runnable onBeginIndexing = new Runnable() {
         public void run() {
            GenericClassLoader.this.perf.startingIndexing();
         }
      };
      Runnable onFinishIndexing = new Runnable() {
         public void run() {
            GenericClassLoader.this.perf.finishedIndexing(System.nanoTime() - obt);
         }
      };
      this.freezeClassFinder(onBeginIndexing, onFinishIndexing);
   }

   protected void freezeClassFinder(Runnable onBeginOptimization, Runnable onEndOptimization) {
      this.finder.freeze(onBeginOptimization, onEndOptimization);
   }

   public final void addClassFinder(ClassFinder newFinder) {
      this.finder.addFinder(newFinder);
   }

   public final void addClassFinderFirst(ClassFinder newFinder) {
      this.finder.addFinderFirst(newFinder);
   }

   public final ClassFinder getClassFinder() {
      return this.finder;
   }

   public final void close() {
      this.finder.close();
      if (isOptimizedEnvironment.get()) {
         GenericClassLoaderRegistry reg = (GenericClassLoaderRegistry)GlobalServiceLocator.getServiceLocator().getService(GenericClassLoaderRegistry.class, new java.lang.annotation.Annotation[0]);
         if (reg != null) {
            reg.unregisterGenericClassLoader(this);
         }
      }

   }

   public final String toString() {
      return super.toString() + " finder: " + this.finder + " annotation: " + this.getAnnotation();
   }

   public final Class defineCodeGenClass(String className, byte[] bytes, URL codebase) throws ClassFormatError, ClassNotFoundException {
      this.finder.addCodeGenSource(className, new ByteArraySource(bytes, codebase));
      return Class.forName(className, true, this);
   }

   public static String getExpanded() {
      if (expandedClasspath == null) {
         Class var0 = GenericClassLoader.class;
         synchronized(GenericClassLoader.class) {
            if (expandedClasspath == null) {
               ClasspathClassFinderInt cf = null;

               try {
                  cf = new ClasspathClassFinder2.NoValidate(Classpath.get());
                  expandedClasspath = cf.getNoDupExpandedClassPath();
               } finally {
                  if (cf != null) {
                     cf.close();
                  }

               }
            }
         }
      }

      return expandedClasspath;
   }

   public InputStream getResourceAsStream(String name) {
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getResourceAsStream", name);
      }

      InputStream stream = super.getResourceAsStream(name);
      if (doTrace && stream == null) {
         ClassLoaderDebugger.debug(this, name);
      }

      return stream;
   }

   protected Package definePackage(String name, String specTitle, String specVersion, String specVendor, String implTitle, String implVersion, String implVendor, URL sealBase) throws IllegalArgumentException {
      synchronized(this.packages) {
         Package pkg = this.getPackage(name);
         if (pkg != null) {
            throw new IllegalArgumentException(name);
         } else {
            pkg = super.definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);
            this.packages.put(name, pkg);
            return pkg;
         }
      }
   }

   protected Package getPackage(String name) {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace || beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getPackage", name);
      }

      Package pkg;
      synchronized(this.packages) {
         pkg = (Package)this.packages.get(name);
      }

      if (pkg == null) {
         if (!this.isClassPatternListEmpty() && this.matchesClassFilterList(name)) {
            if (beVerbose) {
               ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getPackage", name, "Found match, filtering parent check for pacakge");
            }
         } else {
            pkg = super.getPackage(name);
            if (pkg != null) {
               synchronized(this.packages) {
                  Package pkg2 = (Package)this.packages.get(name);
                  if (pkg2 == null) {
                     this.packages.put(name, pkg);
                  } else {
                     pkg = pkg2;
                  }
               }
            }
         }
      }

      return pkg;
   }

   protected Package[] getPackages() {
      boolean beVerbose = vDebugLogger.isDebugEnabled();
      boolean doTrace = ctDebugLogger.isDebugEnabled();
      if (doTrace || beVerbose) {
         ClassLoaderDebugger.debug(this, SupportedClassLoader.GCL, "getPackages", "");
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
                  ClassLoaderDebugger.verbose(this, SupportedClassLoader.GCL, "getPackage", name, "Found match, filtering from a list of packages");
               }
            } else {
               filteredPackages.add(aPackage);
            }
         }

         packages = (Package[])filteredPackages.toArray(new Package[filteredPackages.size()]);
      }

      return packages;
   }

   public long getLoadClassTime() {
      return this.perf.beforeIndexingLoadClassTime.sum() + this.perf.duringIndexingLoadClassTime.sum() + this.perf.afterIndexingLoadClassTime.sum();
   }

   public long getLoadClassCount() {
      return this.perf.beforeIndexingLoadClassCount.sum() + this.perf.duringIndexingLoadClassCount.sum() + this.perf.afterIndexingLoadClassCount.sum();
   }

   public long getFindClassTime() {
      return this.perf.beforeIndexingFindClassTime.sum() + this.perf.duringIndexingFindClassTime.sum() + this.perf.afterIndexingFindClassTime.sum();
   }

   public long getFindClassCount() {
      return this.perf.beforeIndexingFindClassCount.sum() + this.perf.duringIndexingFindClassCount.sum() + this.perf.afterIndexingFindClassCount.sum();
   }

   public long getResourceTime() {
      return this.perf.beforeIndexingResourceTime.sum() + this.perf.duringIndexingResourceTime.sum() + this.perf.afterIndexingResourceTime.sum();
   }

   public long getResourceCount() {
      return this.perf.beforeIndexingResourceCount.sum() + this.perf.duringIndexingResourceCount.sum() + this.perf.afterIndexingResourceCount.sum();
   }

   public long getDefineClassTime() {
      return this.perf.defineClassTime.sum();
   }

   public long getDefineClassCount() {
      return this.perf.defineClassCount.sum();
   }

   public long getParentDelegationTime() {
      return this.perf.parentDelegationTime.sum();
   }

   public long getParentDelegationCount() {
      return this.perf.parentDelegationCount.sum();
   }

   public long getIndexingTime() {
      return this.perf.optimizationTime.sum();
   }

   public long getBeforeIndexingLoadClassTime() {
      return this.perf.beforeIndexingLoadClassTime.sum();
   }

   public long getBeforeIndexingLoadClassCount() {
      return this.perf.beforeIndexingLoadClassCount.sum();
   }

   public long getBeforeIndexingFindClassTime() {
      return this.perf.beforeIndexingFindClassTime.sum();
   }

   public long getBeforeIndexingFindClassCount() {
      return this.perf.beforeIndexingFindClassCount.sum();
   }

   public long getBeforeIndexingResourceTime() {
      return this.perf.beforeIndexingResourceTime.sum();
   }

   public long getBeforeIndexingResourceCount() {
      return this.perf.beforeIndexingResourceCount.sum();
   }

   public long getDuringIndexingLoadClassTime() {
      return this.perf.duringIndexingLoadClassTime.sum();
   }

   public long getDuringIndexingLoadClassCount() {
      return this.perf.duringIndexingLoadClassCount.sum();
   }

   public long getDuringIndexingFindClassTime() {
      return this.perf.duringIndexingFindClassTime.sum();
   }

   public long getDuringIndexingFindClassCount() {
      return this.perf.duringIndexingFindClassCount.sum();
   }

   public long getDuringIndexingResourceTime() {
      return this.perf.duringIndexingResourceTime.sum();
   }

   public long getDuringIndexingResourceCount() {
      return this.perf.duringIndexingResourceCount.sum();
   }

   public long getAfterIndexingLoadClassTime() {
      return this.perf.afterIndexingLoadClassTime.sum();
   }

   public long getAfterIndexingLoadClassCount() {
      return this.perf.afterIndexingLoadClassCount.sum();
   }

   public long getAfterIndexingFindClassTime() {
      return this.perf.afterIndexingFindClassTime.sum();
   }

   public long getAfterIndexingFindClassCount() {
      return this.perf.afterIndexingFindClassCount.sum();
   }

   public long getAfterIndexingResourceTime() {
      return this.perf.afterIndexingResourceTime.sum();
   }

   public long getAfterIndexingResourceCount() {
      return this.perf.afterIndexingResourceCount.sum();
   }

   public int hashCode() {
      int h = this.cachedHashCode;
      if (h == 0) {
         h = super.hashCode();
         this.cachedHashCode = h;
      }

      return h;
   }

   public static void setOptimizedEnvironment() {
      isOptimizedEnvironment.set(true);
   }

   public void optimize() {
      PolicyClassLoader.clearLockMap(this);
   }

   private synchronized void cacheAscendantsIfNotDone() {
      if (!this.ascendantsCached) {
         if (this.parent != null) {
            if (this.parent instanceof GenericClassLoader) {
               this.cacheAscendantsForParentIfNotDoneAndAddToSelf((GenericClassLoader)this.parent);
            } else {
               ClassLoader parentCL;
               for(parentCL = this.parent; parentCL != null && !(parentCL instanceof GenericClassLoader); parentCL = parentCL.getParent()) {
                  this.instantiateAscendantCachesIfNotDone();
                  this.ascendantRefs.add(parentCL);
                  this.ascendantHashCodes.add(parentCL.hashCode());
               }

               if (parentCL instanceof GenericClassLoader) {
                  this.cacheAscendantsForParentIfNotDoneAndAddToSelf((GenericClassLoader)parentCL);
               }
            }
         }

         if (this.altParent != null) {
            this.cacheAscendantsForParentIfNotDoneAndAddToSelf(this.altParent);
         }

         this.ascendantsCached = true;
      }

   }

   private synchronized void instantiateAscendantCachesIfNotDone() {
      int averageNumberOfAscendants = 8;
      if (this.ascendantRefs == null) {
         this.ascendantRefs = Collections.synchronizedSet(new HashSet(averageNumberOfAscendants));
      }

      if (this.ascendantHashCodes == null) {
         this.ascendantHashCodes = Collections.synchronizedSet(new HashSet(averageNumberOfAscendants));
      }

   }

   private synchronized void cacheAscendantsForParentIfNotDoneAndAddToSelf(GenericClassLoader parentCL) {
      parentCL.cacheAscendantsIfNotDone();
      this.instantiateAscendantCachesIfNotDone();
      this.ascendantRefs.addAll(parentCL.ascendantRefs);
      this.ascendantRefs.add(parentCL);
      this.ascendantHashCodes.addAll(parentCL.ascendantHashCodes);
      this.ascendantHashCodes.add(parentCL.hashCode());
   }

   public boolean isChildOf(ClassLoader cl) {
      this.cacheAscendantsIfNotDone();
      return this.ascendantRefs.contains(cl);
   }

   public boolean isChildOf(int clHashCode) {
      this.cacheAscendantsIfNotDone();
      return this.ascendantHashCodes.contains(clHashCode);
   }

   static {
      Class ensureLoaded = LockSupport.class;
      ClassLoader.registerAsParallelCapable();
      support = new ClassPreProcessor.ClassPreProcessorSupport();
      DEFAULT = new Annotation("");
      isOptimizedEnvironment = new AtomicBoolean(false);
      CLASSLOAD_CHECK_JAR_SIGNAGE = !Boolean.getBoolean("weblogic.classloader.noJarSigners");
      vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");
      ctDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingContextualTrace");
      writeMagicFailureDetails = Boolean.getBoolean("weblogic.GenericClassLoader.write_magic_failure_details");
      expandedClasspath = null;
   }

   private static final class PerfCounter {
      private final LongAdder optimizationTime;
      private final LongAdder beforeIndexingLoadClassTime;
      private final LongAdder beforeIndexingLoadClassCount;
      private final LongAdder beforeIndexingFindClassTime;
      private final LongAdder beforeIndexingFindClassCount;
      private final LongAdder beforeIndexingResourceTime;
      private final LongAdder beforeIndexingResourceCount;
      private final LongAdder duringIndexingLoadClassTime;
      private final LongAdder duringIndexingLoadClassCount;
      private final LongAdder duringIndexingFindClassTime;
      private final LongAdder duringIndexingFindClassCount;
      private final LongAdder duringIndexingResourceTime;
      private final LongAdder duringIndexingResourceCount;
      private final LongAdder afterIndexingLoadClassTime;
      private final LongAdder afterIndexingLoadClassCount;
      private final LongAdder afterIndexingFindClassTime;
      private final LongAdder afterIndexingFindClassCount;
      private final LongAdder afterIndexingResourceTime;
      private final LongAdder afterIndexingResourceCount;
      private final LongAdder defineClassTime;
      private final LongAdder defineClassCount;
      private final LongAdder parentDelegationTime;
      private final LongAdder parentDelegationCount;
      private Phase phase;

      private PerfCounter() {
         this.optimizationTime = new LongAdder();
         this.beforeIndexingLoadClassTime = new LongAdder();
         this.beforeIndexingLoadClassCount = new LongAdder();
         this.beforeIndexingFindClassTime = new LongAdder();
         this.beforeIndexingFindClassCount = new LongAdder();
         this.beforeIndexingResourceTime = new LongAdder();
         this.beforeIndexingResourceCount = new LongAdder();
         this.duringIndexingLoadClassTime = new LongAdder();
         this.duringIndexingLoadClassCount = new LongAdder();
         this.duringIndexingFindClassTime = new LongAdder();
         this.duringIndexingFindClassCount = new LongAdder();
         this.duringIndexingResourceTime = new LongAdder();
         this.duringIndexingResourceCount = new LongAdder();
         this.afterIndexingLoadClassTime = new LongAdder();
         this.afterIndexingLoadClassCount = new LongAdder();
         this.afterIndexingFindClassTime = new LongAdder();
         this.afterIndexingFindClassCount = new LongAdder();
         this.afterIndexingResourceTime = new LongAdder();
         this.afterIndexingResourceCount = new LongAdder();
         this.defineClassTime = new LongAdder();
         this.defineClassCount = new LongAdder();
         this.parentDelegationTime = new LongAdder();
         this.parentDelegationCount = new LongAdder();
         this.phase = new Phase();
      }

      public void recordStats(long startTime, long startParentDelegate, long endParentDelegate, long startFindClass, long endFindClass) {
         long loadClassTime = startTime != 0L ? System.nanoTime() - startTime : 0L;
         long findClassTime = startFindClass != 0L ? endFindClass - startFindClass : 0L;
         this.phase.recordStats(loadClassTime, findClassTime);
         if (startParentDelegate != 0L) {
            this.parentDelegationTime.add(endParentDelegate - startParentDelegate);
            this.parentDelegationCount.increment();
         }

      }

      public void recordResourceStats(long startTime, long startParentDelegate, long endParentDelegate) {
         this.phase.recordResourceStats(startTime);
         if (startParentDelegate != 0L) {
            this.parentDelegationTime.add(endParentDelegate - startParentDelegate);
            this.parentDelegationCount.increment();
         }

      }

      public void recordDefineClassStats(long startTime) {
         this.defineClassTime.add(System.nanoTime() - startTime);
         this.defineClassCount.increment();
      }

      public synchronized void startingIndexing() {
         this.phase = new DuringPhase();
      }

      public synchronized void finishedIndexing(long optimizationTime) {
         this.phase = new AfterPhase();
         this.optimizationTime.add(optimizationTime);
      }

      // $FF: synthetic method
      PerfCounter(Object x0) {
         this();
      }

      private class AfterPhase extends Phase {
         private AfterPhase() {
            super(null);
         }

         void recordStats(long loadClassTime, long findClassTime) {
            PerfCounter.this.afterIndexingLoadClassTime.add(loadClassTime);
            PerfCounter.this.afterIndexingLoadClassCount.increment();
            if (findClassTime > 0L) {
               PerfCounter.this.afterIndexingFindClassTime.add(findClassTime);
               PerfCounter.this.afterIndexingFindClassCount.increment();
            }

         }

         void recordResourceStats(long startTime) {
            if (startTime > 0L) {
               long time = System.nanoTime() - startTime;
               PerfCounter.this.afterIndexingResourceTime.add(time);
               PerfCounter.this.afterIndexingResourceCount.increment();
            }

         }

         // $FF: synthetic method
         AfterPhase(Object x1) {
            this();
         }
      }

      private class DuringPhase extends Phase {
         private DuringPhase() {
            super(null);
         }

         void recordStats(long loadClassTime, long findClassTime) {
            PerfCounter.this.duringIndexingLoadClassTime.add(loadClassTime);
            PerfCounter.this.duringIndexingLoadClassCount.increment();
            if (findClassTime > 0L) {
               PerfCounter.this.duringIndexingFindClassTime.add(findClassTime);
               PerfCounter.this.duringIndexingFindClassCount.increment();
            }

         }

         void recordResourceStats(long startTime) {
            if (startTime > 0L) {
               long time = System.nanoTime() - startTime;
               PerfCounter.this.duringIndexingResourceTime.add(time);
               PerfCounter.this.duringIndexingResourceCount.increment();
            }

         }

         // $FF: synthetic method
         DuringPhase(Object x1) {
            this();
         }
      }

      private class Phase {
         private Phase() {
         }

         void recordStats(long loadClassTime, long findClassTime) {
            PerfCounter.this.beforeIndexingLoadClassTime.add(loadClassTime);
            PerfCounter.this.beforeIndexingLoadClassCount.increment();
            if (findClassTime > 0L) {
               PerfCounter.this.beforeIndexingFindClassTime.add(findClassTime);
               PerfCounter.this.beforeIndexingFindClassCount.increment();
            }

         }

         void recordResourceStats(long startTime) {
            if (startTime > 0L) {
               long time = System.nanoTime() - startTime;
               PerfCounter.this.beforeIndexingResourceTime.add(time);
               PerfCounter.this.beforeIndexingResourceCount.increment();
            }

         }

         // $FF: synthetic method
         Phase(Object x1) {
            this();
         }
      }
   }

   private class GetClassLoaderParentAction implements PrivilegedAction {
      ClassLoader clLoader = null;

      public GetClassLoaderParentAction(ClassLoader loader) {
         this.clLoader = loader;
      }

      public Object run() {
         return this.clLoader != null ? this.clLoader.getParent() : this.clLoader;
      }
   }
}
