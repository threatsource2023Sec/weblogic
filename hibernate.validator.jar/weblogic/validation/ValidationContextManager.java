package weblogic.validation;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.ResettableThreadLocal;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class ValidationContextManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugValidation");
   private static final Object PLACEHOLDER = new Object();
   private Map validationContexts = new ConcurrentWeakHashMap();
   private ResettableThreadLocal vc = new ResettableThreadLocal();
   private ValidationDescriptorClassFinder validationDescriptorClassFinder = new ValidationDescriptorClassFinder();
   private static ValidationContextManager validationContextManager;
   private static ThreadLocal cachedValidationContext = new ThreadLocal();
   private static ThreadLocal validationPhase = new ThreadLocal();
   private WeakHashSet knownClassLoaders = new WeakHashSet();

   public static ValidationContextManager getInstance() {
      if (validationContextManager == null) {
         validationContextManager = new ValidationContextManager();
      }

      return validationContextManager;
   }

   public boolean hasConstraintMapping(String path) {
      if (this.validationContexts.size() == 0) {
         return false;
      } else {
         Iterator var2 = this.validationContexts.keySet().iterator();

         ValidationContext validationContext;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            validationContext = (ValidationContext)var2.next();
         } while(validationContext == null || !validationContext.hasConstraintMappingResource(path));

         return true;
      }
   }

   public synchronized ValidationContext registerURL(Boolean useJndi, URL url) {
      this.registerVCClassFinder();
      ValidationContext valCtx = ValidationContext.getNewValidationContextForUrl(url);
      this.validationContexts.put(valCtx, PLACEHOLDER);
      if (useJndi) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Using ThreadLocal for URL, " + url);
         }

         this.vc.set((Object)null);
      } else {
         this.vc.set(valCtx);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Using JNDI for URL, " + url);
         }
      }

      return valCtx;
   }

   public ValidationContext getValidationContext() {
      ValidationContext valContext = (ValidationContext)this.vc.get();
      if (valContext == null) {
         valContext = (ValidationContext)cachedValidationContext.get();
      }

      if (valContext == null && !this.isValidationPhase()) {
         try {
            Context jndi = new InitialContext();
            if (jndi != null) {
               valContext = (ValidationContext)jndi.lookup(Jndi.VALIDATION_CONTEXT.key);
            }
         } catch (NameNotFoundException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(Jndi.VALIDATION_CONTEXT.key + " not found in Jndi.");
            }
         } catch (NamingException var5) {
            ValidationLogger.errorUnableToFindValidationContext(new RuntimeException(var5));
         }
      }

      return valContext;
   }

   public void clearCachedValidationContext() {
      cachedValidationContext.remove();
   }

   public void cacheValidationContext(ValidationContext valCtx) {
      cachedValidationContext.set(valCtx);
   }

   public void registerVCClassFinder() {
      try {
         for(ClassLoader cl = Thread.currentThread().getContextClassLoader(); cl instanceof GenericClassLoader && !this.knownClassLoaders.contains(cl); cl = cl.getParent()) {
            GenericClassLoader gcl = (GenericClassLoader)cl;
            this.knownClassLoaders.add(gcl);
            gcl.addClassFinderFirst(this.validationDescriptorClassFinder);
         }
      } catch (SecurityException var3) {
         assert false : "Internal code should have the necessary credentials. " + var3;
      }

   }

   public void setValidationPhase(boolean validationPhase) {
      ValidationContextManager.validationPhase.set(validationPhase);
   }

   public boolean isValidationPhase() {
      Boolean b = (Boolean)validationPhase.get();
      return b != null && b;
   }

   static class WeakKey extends WeakReference {
      public WeakKey(Object referent) {
         super(referent);
      }

      public int hashCode() {
         return this.get().hashCode();
      }

      public boolean equals(Object obj) {
         obj = obj == null ? null : (obj instanceof WeakKey ? ((WeakKey)obj).get() : obj);
         Object thisReferent = this.get();
         return thisReferent.equals(obj);
      }
   }

   static class WeakHashSet {
      Set classLoaders = Collections.newSetFromMap(new ConcurrentHashMap());

      boolean contains(Object cl) {
         return this.classLoaders.contains(new WeakKey(cl));
      }

      void add(Object cl) {
         this.classLoaders.add(new WeakKey(cl));
      }
   }
}
