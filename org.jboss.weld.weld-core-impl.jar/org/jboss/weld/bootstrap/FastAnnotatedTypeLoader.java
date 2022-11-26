package org.jboss.weld.bootstrap;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collections;
import java.util.Set;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.bootstrap.events.ContainerLifecycleEvents;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassLoaderResourceLoader;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.spi.ClassFileInfo;
import org.jboss.weld.resources.spi.ClassFileInfoException;
import org.jboss.weld.resources.spi.ClassFileServices;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.resources.spi.ClassFileInfo.NestingType;
import org.jboss.weld.security.GetDeclaredMethodAction;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.reflection.Reflections;

class FastAnnotatedTypeLoader extends AnnotatedTypeLoader {
   private final ClassFileServices classFileServices;
   private final FastProcessAnnotatedTypeResolver resolver;
   private final AnnotatedTypeLoader fallback;
   private final boolean checkTypeModifiers;
   private static final String CLASSINFO_CLASS_NAME = "org.jboss.jandex.ClassInfo";

   FastAnnotatedTypeLoader(BeanManagerImpl manager, ClassTransformer transformer, ClassFileServices classFileServices, ContainerLifecycleEvents events, FastProcessAnnotatedTypeResolver resolver) {
      super(manager, transformer, events);
      this.fallback = new AnnotatedTypeLoader(manager, transformer, events);
      this.classFileServices = classFileServices;
      this.resolver = resolver;
      this.checkTypeModifiers = this.initCheckTypeModifiers();
   }

   public SlimAnnotatedTypeContext loadAnnotatedType(String className, String bdaId) {
      try {
         ClassFileInfo classFileInfo = this.classFileServices.getClassFileInfo(className);
         if ((classFileInfo.getModifiers() & 8192) != 0) {
            return null;
         } else if (classFileInfo.isVetoed()) {
            return null;
         } else if (!classFileInfo.getNestingType().equals(NestingType.NESTED_LOCAL) && !classFileInfo.getNestingType().equals(NestingType.NESTED_ANONYMOUS)) {
            Set observerMethods = Collections.emptySet();
            if (this.containerLifecycleEvents.isProcessAnnotatedTypeObserved()) {
               observerMethods = this.resolver.resolveProcessAnnotatedTypeObservers(this.classFileServices, className);
               if (!observerMethods.isEmpty()) {
                  return this.createContext(className, classFileInfo, observerMethods, bdaId);
               }
            }

            if (Beans.isDecoratorDeclaringInAppropriateConstructor(classFileInfo)) {
               BootstrapLogger.LOG.decoratorWithNonCdiConstructor(classFileInfo.getClassName());
            }

            return Beans.isTypeManagedBeanOrDecoratorOrInterceptor(classFileInfo, this.checkTypeModifiers) ? this.createContext(className, classFileInfo, observerMethods, bdaId) : null;
         } else {
            return null;
         }
      } catch (ClassFileInfoException var5) {
         BootstrapLogger.LOG.exceptionLoadingAnnotatedType(var5.getMessage());
         return this.fallback.loadAnnotatedType(className, bdaId);
      }
   }

   private SlimAnnotatedTypeContext createContext(String className, ClassFileInfo classFileInfo, Set observerMethods, String bdaId) {
      SlimAnnotatedType type = this.loadSlimAnnotatedType(this.loadClass(className), bdaId);
      return type != null ? SlimAnnotatedTypeContext.of(type, classFileInfo, observerMethods) : null;
   }

   private SlimAnnotatedType loadSlimAnnotatedType(Class clazz, String bdaId) {
      if (clazz != null) {
         try {
            return this.classTransformer.getBackedAnnotatedType(clazz, bdaId);
         } catch (ResourceLoadingException var4) {
            this.missingDependenciesRegistry.handleResourceLoadingException(clazz.getName(), var4);
         }
      }

      return null;
   }

   private boolean initCheckTypeModifiers() {
      Class classInfoclass = Reflections.loadClass("org.jboss.jandex.ClassInfo", new ClassLoaderResourceLoader(this.classFileServices.getClass().getClassLoader()));
      if (classInfoclass != null) {
         try {
            Method setFlags = (Method)AccessController.doPrivileged(GetDeclaredMethodAction.of(classInfoclass, "setFlags", Short.TYPE));
            return setFlags != null;
         } catch (Exception var3) {
            BootstrapLogger.LOG.usingOldJandexVersion();
            return false;
         }
      } else {
         return true;
      }
   }
}
