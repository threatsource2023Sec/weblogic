package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.invoke.MethodHandles;
import java.security.PrivilegedAction;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class LoadClass implements PrivilegedAction {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String HIBERNATE_VALIDATOR_CLASS_NAME = "org.hibernate.validator";
   private final String className;
   private final ClassLoader classLoader;
   private final ClassLoader initialThreadContextClassLoader;
   private final boolean fallbackOnTCCL;

   public static LoadClass action(String className, ClassLoader classLoader) {
      return action(className, classLoader, true);
   }

   public static LoadClass action(String className, ClassLoader classLoader, boolean fallbackOnTCCL) {
      return new LoadClass(className, classLoader, (ClassLoader)null, fallbackOnTCCL);
   }

   public static LoadClass action(String className, ClassLoader classLoader, ClassLoader initialThreadContextClassLoader) {
      return new LoadClass(className, classLoader, initialThreadContextClassLoader, true);
   }

   private LoadClass(String className, ClassLoader classLoader, ClassLoader initialThreadContextClassLoader, boolean fallbackOnTCCL) {
      this.className = className;
      this.classLoader = classLoader;
      this.initialThreadContextClassLoader = initialThreadContextClassLoader;
      this.fallbackOnTCCL = fallbackOnTCCL;
   }

   public Class run() {
      return this.className.startsWith("org.hibernate.validator") ? this.loadClassInValidatorNameSpace() : this.loadNonValidatorClass();
   }

   private Class loadClassInValidatorNameSpace() {
      ClassLoader loader = HibernateValidator.class.getClassLoader();

      Object exception;
      try {
         return Class.forName(this.className, true, HibernateValidator.class.getClassLoader());
      } catch (ClassNotFoundException var6) {
         exception = var6;
      } catch (RuntimeException var7) {
         exception = var7;
      }

      if (this.fallbackOnTCCL) {
         ClassLoader contextClassLoader = this.initialThreadContextClassLoader != null ? this.initialThreadContextClassLoader : Thread.currentThread().getContextClassLoader();
         if (contextClassLoader != null) {
            try {
               return Class.forName(this.className, false, contextClassLoader);
            } catch (ClassNotFoundException var5) {
               throw LOG.getUnableToLoadClassException(this.className, contextClassLoader, var5);
            }
         } else {
            throw LOG.getUnableToLoadClassException(this.className, loader, (Exception)exception);
         }
      } else {
         throw LOG.getUnableToLoadClassException(this.className, loader, (Exception)exception);
      }
   }

   private Class loadNonValidatorClass() {
      Exception exception = null;
      if (this.classLoader != null) {
         try {
            return Class.forName(this.className, false, this.classLoader);
         } catch (ClassNotFoundException var7) {
            exception = var7;
         } catch (RuntimeException var8) {
            exception = var8;
         }
      }

      if (this.fallbackOnTCCL) {
         ClassLoader loader;
         try {
            loader = this.initialThreadContextClassLoader != null ? this.initialThreadContextClassLoader : Thread.currentThread().getContextClassLoader();
            if (loader != null) {
               return Class.forName(this.className, false, loader);
            }
         } catch (ClassNotFoundException var5) {
         } catch (RuntimeException var6) {
         }

         loader = LoadClass.class.getClassLoader();

         try {
            return Class.forName(this.className, true, loader);
         } catch (ClassNotFoundException var4) {
            throw LOG.getUnableToLoadClassException(this.className, loader, var4);
         }
      } else {
         throw LOG.getUnableToLoadClassException(this.className, this.classLoader, (Exception)exception);
      }
   }
}
