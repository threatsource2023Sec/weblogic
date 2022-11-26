package weblogic.diagnostics.instrumentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import weblogic.diagnostics.instrumentation.engine.InstrumentationEngineConfiguration;
import weblogic.diagnostics.instrumentation.rtsupport.InstrumentationSupportImpl;
import weblogic.utils.PropertyHelper;
import weblogic.utils.classloaders.ClassPreProcessor;
import weblogic.utils.classloaders.GenericClassLoader;

public class DiagnosticClassPreProcessor implements ClassPreProcessor {
   private Map classLoaderMap = new WeakHashMap();
   private final InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
   private static final InstrumentationScopeBundle VOID_BUNDLE = new InstrumentationScopeBundle();
   private static final boolean DEBUG = false;

   public void initialize(Hashtable properties) {
   }

   public byte[] preProcess(String name, byte[] bytes) {
      if (!this.isEligibleClass(name)) {
         return bytes;
      } else {
         ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

         ClassLoader ccl;
         for(ccl = currentClassLoader; ccl != null && !(ccl instanceof GenericClassLoader); ccl = ccl.getParent()) {
         }

         if (ccl == null) {
            return bytes;
         } else {
            if (ccl instanceof GenericClassLoader) {
               GenericClassLoader gcl = (GenericClassLoader)ccl;
               InstrumentationScope scope = this.getInstrumentationScope(gcl);
               if (scope != null) {
                  byte[] retVal = scope.instrumentClass(currentClassLoader, name, bytes);
                  if (retVal != null) {
                     bytes = retVal;
                  }
               }
            }

            return bytes;
         }
      }
   }

   public byte[] preProcess(GenericClassLoader gcl, String name, byte[] bytes) {
      if (!this.isEligibleClass(name)) {
         return bytes;
      } else {
         InstrumentationScope scope = this.getInstrumentationScope(gcl);
         if (scope != null) {
            byte[] retVal = scope.instrumentClass(gcl, name, bytes);
            if (retVal != null) {
               bytes = retVal;
            }
         }

         return bytes;
      }
   }

   private boolean isEligibleClass(String name) {
      name = name.replace('/', '.');
      if (!this.manager.isEnabled()) {
         return false;
      } else if (this.getSimpleName(name).startsWith("$Proxy")) {
         return false;
      } else if (name.indexOf("_WLDF$INST_AUX_CLASS_") >= 0) {
         return false;
      } else {
         Iterator var2 = DiagnosticClassPreProcessor.ExcludedPackages.EXCLUDES.keySet().iterator();

         boolean allowed;
         do {
            String key;
            do {
               if (!var2.hasNext()) {
                  InstrumentationEngineConfiguration engineConf = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();
                  return engineConf.isEligibleClass(name);
               }

               key = (String)var2.next();
            } while(!name.startsWith(key));

            allowed = false;
            Set allowedSubPackages = (Set)DiagnosticClassPreProcessor.ExcludedPackages.EXCLUDES.get(key);
            Iterator var6 = allowedSubPackages.iterator();

            while(var6.hasNext()) {
               String allowedSubPackage = (String)var6.next();
               if (name.startsWith(allowedSubPackage)) {
                  allowed = true;
               }
            }
         } while(allowed);

         return false;
      }
   }

   private InstrumentationScope getInstrumentationScope(GenericClassLoader gcl) {
      InstrumentationScopeBundle bundle = null;
      synchronized(this.classLoaderMap) {
         bundle = (InstrumentationScopeBundle)this.classLoaderMap.get(gcl);
      }

      if (bundle == null) {
         InstrumentationScopeBundle tmpBundle = this.createBundle(gcl);
         synchronized(this.classLoaderMap) {
            bundle = (InstrumentationScopeBundle)this.classLoaderMap.get(gcl);
            if (bundle == null) {
               bundle = tmpBundle;
               this.classLoaderMap.put(gcl, tmpBundle);
            }
         }
      }

      return bundle == null ? null : bundle.scope;
   }

   private InstrumentationScopeBundle createBundle(GenericClassLoader gcl) {
      String appName = InstrumentationSupportImpl.getInstrumentationScopeName(gcl);
      if (appName == null) {
         return VOID_BUNDLE;
      } else {
         InstrumentationScope scope = this.manager.findInstrumentationScope(appName);
         if (scope == null) {
            return null;
         } else {
            this.manager.associateClassloaderWithScope(gcl, scope);
            InstrumentationScopeBundle bundle = new InstrumentationScopeBundle();
            bundle.scope = scope;
            return bundle;
         }
      }
   }

   private String getSimpleName(String maybeLongName) {
      int index = maybeLongName.lastIndexOf(46);
      return index == -1 ? maybeLongName : maybeLongName.substring(index + 1);
   }

   private static final class ExcludedPackages {
      static final Map EXCLUDES = new HashMap() {
         {
            this.put("java.", new HashSet());
            this.put("javax.", new HashSet() {
               {
                  this.add("javax.faces.");
               }
            });
            this.put("schema.", new HashSet());
            this.put("com.bea.xbean.", new HashSet());
            this.put("com.bea.xml.", new HashSet());
            this.put("weblogic.xml.", new HashSet());
            if (!PropertyHelper.getBoolean("weblogic.diagnostics.instrumentComSunFaces")) {
               this.put("com.sun.faces.", new HashSet());
            }

         }
      };
   }

   private static class InstrumentationScopeBundle {
      InstrumentationScope scope;

      private InstrumentationScopeBundle() {
      }

      // $FF: synthetic method
      InstrumentationScopeBundle(Object x0) {
         this();
      }
   }
}
