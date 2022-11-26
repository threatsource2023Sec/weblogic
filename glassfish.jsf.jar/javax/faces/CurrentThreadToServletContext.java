package javax.faces;

import com.sun.faces.util.Util;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

final class CurrentThreadToServletContext {
   private static final Logger LOGGER = Logger.getLogger("javax.faces", "javax.faces.LogStrings");
   private final ServletContextFacesContextFactory servletContextFacesContextFactory = new ServletContextFacesContextFactory();
   ConcurrentMap factoryFinderMap = new ConcurrentHashMap();
   private AtomicBoolean logNullFacesContext = new AtomicBoolean();
   private AtomicBoolean logNonNullFacesContext = new AtomicBoolean();

   FactoryFinderInstance getFactoryFinder() {
      return this.getFactoryFinder(Util.getContextClassLoader2(), true);
   }

   FactoryFinderInstance getFactoryFinder(boolean create) {
      return this.getFactoryFinder(Util.getContextClassLoader2(), create);
   }

   private FactoryFinderInstance getFactoryFinder(ClassLoader classLoader, boolean create) {
      FacesContext facesContext = this.servletContextFacesContextFactory.getFacesContextWithoutServletContextLookup();
      boolean isSpecialInitializationCase = this.detectSpecialInitializationCase(facesContext);
      FactoryFinderCacheKey key = new FactoryFinderCacheKey(facesContext, classLoader, this.factoryFinderMap);
      FactoryFinderInstance factoryFinder = (FactoryFinderInstance)this.factoryFinderMap.get(key);
      FactoryFinderInstance toCopy = null;
      if (factoryFinder == null && create) {
         boolean createNewFactoryFinderInstance = false;
         if (!isSpecialInitializationCase) {
            createNewFactoryFinderInstance = true;
         } else {
            boolean classLoadersMatchButContextsDoNotMatch = false;
            boolean foundNoMatchInApplicationMap = true;
            Iterator var11 = this.factoryFinderMap.entrySet().iterator();

            while(var11.hasNext()) {
               Map.Entry cur = (Map.Entry)var11.next();
               FactoryFinderCacheKey curKey = (FactoryFinderCacheKey)cur.getKey();
               if (curKey.getClassLoader().equals(classLoader)) {
                  foundNoMatchInApplicationMap = false;
                  if (!Util.isAnyNull(key.getContext(), curKey.getContext()) && !key.getContext().equals(curKey.getContext())) {
                     classLoadersMatchButContextsDoNotMatch = true;
                     toCopy = (FactoryFinderInstance)cur.getValue();
                     break;
                  }

                  factoryFinder = (FactoryFinderInstance)cur.getValue();
                  break;
               }
            }

            createNewFactoryFinderInstance = foundNoMatchInApplicationMap || factoryFinder == null && classLoadersMatchButContextsDoNotMatch;
         }

         if (createNewFactoryFinderInstance) {
            FactoryFinderInstance newResult;
            if (toCopy != null) {
               newResult = new FactoryFinderInstance(facesContext, toCopy);
            } else {
               newResult = new FactoryFinderInstance(facesContext);
            }

            factoryFinder = (FactoryFinderInstance)Util.coalesce((FactoryFinderInstance)this.factoryFinderMap.putIfAbsent(key, newResult), newResult);
         }
      }

      return factoryFinder;
   }

   Object getFallbackFactory(FactoryFinderInstance brokenFactoryManager, String factoryName) {
      ClassLoader classLoader = Util.getContextClassLoader2();
      Iterator var4 = this.factoryFinderMap.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry cur = (Map.Entry)var4.next();
         if (((FactoryFinderCacheKey)cur.getKey()).getClassLoader().equals(classLoader) && !((FactoryFinderInstance)cur.getValue()).equals(brokenFactoryManager)) {
            Object factory = ((FactoryFinderInstance)cur.getValue()).getFactory(factoryName);
            if (factory != null) {
               return factory;
            }
         }
      }

      return null;
   }

   Object getServletContextForCurrentClassLoader() {
      return (new FactoryFinderCacheKey((FacesContext)null, Util.getContextClassLoader2(), this.factoryFinderMap)).getContext();
   }

   private boolean detectSpecialInitializationCase(FacesContext facesContext) {
      if (facesContext == null) {
         this.logNullFacesContext.compareAndSet(false, true);
      } else {
         this.logNonNullFacesContext.compareAndSet(false, true);
      }

      return this.logNullFacesContext.get() && this.logNonNullFacesContext.get();
   }

   void removeFactoryFinder() {
      ClassLoader classLoader = Util.getContextClassLoader2();
      FactoryFinderInstance factoryFinder = this.getFactoryFinder(classLoader, false);
      if (factoryFinder != null) {
         factoryFinder.clearInjectionProvider();
      }

      FacesContext facesContext = this.servletContextFacesContextFactory.getFacesContextWithoutServletContextLookup();
      boolean isSpecialInitializationCase = this.detectSpecialInitializationCase(facesContext);
      this.factoryFinderMap.remove(new FactoryFinderCacheKey(facesContext, classLoader, this.factoryFinderMap));
      if (isSpecialInitializationCase) {
         this.resetSpecialInitializationCaseFlags();
      }

   }

   void resetSpecialInitializationCaseFlags() {
      this.logNullFacesContext.set(false);
      this.logNonNullFacesContext.set(false);
   }

   private static final class FactoryFinderCacheKey {
      private ClassLoader classLoader;
      private Long marker;
      private Object context;
      private static final String MARKER_KEY = FactoryFinder.class.getName() + "." + FactoryFinderCacheKey.class.getSimpleName();
      private static final String INIT_TIME_CL_KEY;

      FactoryFinderCacheKey(FacesContext facesContext, ClassLoader classLoaderIn, Map factoryMap) {
         ExternalContext extContext = facesContext != null ? facesContext.getExternalContext() : null;
         Object servletContext = extContext != null ? extContext.getContext() : null;
         if (Util.isAnyNull(facesContext, extContext, servletContext)) {
            this.initFromFactoryMap(classLoaderIn, factoryMap);
         } else {
            this.initFromApplicationMap(extContext, classLoaderIn);
         }

      }

      private void initFromFactoryMap(ClassLoader classLoaderIn, Map factoryFinderMap) {
         Set keys = factoryFinderMap.keySet();
         FactoryFinderCacheKey matchingKey = null;
         if (keys.isEmpty()) {
            this.classLoader = classLoaderIn;
            this.marker = System.currentTimeMillis();
         } else {
            Iterator var5 = keys.iterator();

            while(var5.hasNext()) {
               FactoryFinderCacheKey currentKey = (FactoryFinderCacheKey)var5.next();
               ClassLoader matchingClassLoader = this.findMatchConsideringParentClassLoader(classLoaderIn, currentKey.classLoader);
               if (matchingClassLoader != null) {
                  if (matchingKey != null) {
                     CurrentThreadToServletContext.LOGGER.log(Level.WARNING, "Multiple Jakarta Server Faces Applications found on same ClassLoader.  Unable to safely determine which FactoryFinder instance to use. Defaulting to first match.");
                     break;
                  }

                  matchingKey = currentKey;
                  this.classLoader = matchingClassLoader;
               }
            }

            if (matchingKey != null) {
               this.marker = matchingKey.marker;
               this.context = matchingKey.context;
            }
         }

      }

      private ClassLoader findMatchConsideringParentClassLoader(ClassLoader argumentClassLoader, ClassLoader currentKeyCL) {
         for(ClassLoader currentClassLoader = argumentClassLoader; currentClassLoader != null; currentClassLoader = currentClassLoader.getParent()) {
            if (currentClassLoader.equals(currentKeyCL)) {
               return currentClassLoader;
            }
         }

         return null;
      }

      private void initFromApplicationMap(ExternalContext extContext, ClassLoader classLoaderIn) {
         Map applicationMap = extContext.getApplicationMap();
         Long val = (Long)applicationMap.get(MARKER_KEY);
         if (val == null) {
            this.marker = System.currentTimeMillis();
            applicationMap.put(MARKER_KEY, this.marker);
            applicationMap.put(INIT_TIME_CL_KEY, System.identityHashCode(classLoaderIn));
         } else {
            this.marker = val;
         }

         this.classLoader = this.resolveToFirstTimeUsedClassLoader(classLoaderIn, extContext);
         this.context = extContext.getContext();
      }

      private ClassLoader resolveToFirstTimeUsedClassLoader(ClassLoader classLoaderToResolve, ExternalContext extContext) {
         ClassLoader currentClassLoader = classLoaderToResolve;
         Map appMap = extContext.getApplicationMap();
         Integer webAppCLHashCode = (Integer)appMap.get(INIT_TIME_CL_KEY);
         boolean found = false;
         if (webAppCLHashCode != null) {
            int toResolveHashCode = System.identityHashCode(classLoaderToResolve);

            while(!found && currentClassLoader != null) {
               found = toResolveHashCode == webAppCLHashCode;
               if (!found) {
                  currentClassLoader = currentClassLoader.getParent();
                  toResolveHashCode = System.identityHashCode(currentClassLoader);
               }
            }
         }

         return found ? currentClassLoader : classLoaderToResolve;
      }

      ClassLoader getClassLoader() {
         return this.classLoader;
      }

      Object getContext() {
         return this.context;
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            FactoryFinderCacheKey other = (FactoryFinderCacheKey)obj;
            if (this.classLoader == other.classLoader || this.classLoader != null && this.classLoader.equals(other.classLoader)) {
               return this.marker == other.marker || this.marker != null && this.marker.equals(other.marker);
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         int hash = 7;
         hash = 97 * hash + (this.classLoader != null ? this.classLoader.hashCode() : 0);
         return 97 * hash + (this.marker != null ? this.marker.hashCode() : 0);
      }

      static {
         INIT_TIME_CL_KEY = MARKER_KEY + ".InitTimeCLKey";
      }
   }
}
