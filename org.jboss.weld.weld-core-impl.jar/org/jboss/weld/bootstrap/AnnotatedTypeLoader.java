package org.jboss.weld.bootstrap;

import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.bootstrap.events.ContainerLifecycleEvents;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.reflection.Reflections;

class AnnotatedTypeLoader {
   final ResourceLoader resourceLoader;
   final ClassTransformer classTransformer;
   final MissingDependenciesRegistry missingDependenciesRegistry;
   final ContainerLifecycleEvents containerLifecycleEvents;
   static final String MODULEINFO_CLASS_NAME = "module-info";

   AnnotatedTypeLoader(BeanManagerImpl manager, ClassTransformer transformer, ContainerLifecycleEvents containerLifecycleEvents) {
      this.resourceLoader = (ResourceLoader)manager.getServices().get(ResourceLoader.class);
      this.classTransformer = transformer;
      this.missingDependenciesRegistry = (MissingDependenciesRegistry)manager.getServices().get(MissingDependenciesRegistry.class);
      this.containerLifecycleEvents = containerLifecycleEvents;
   }

   public SlimAnnotatedTypeContext loadAnnotatedType(String className, String bdaId) {
      return this.loadAnnotatedType(this.loadClass(className), bdaId);
   }

   public SlimAnnotatedTypeContext loadAnnotatedType(Class clazz, String bdaId) {
      return this.createContext(this.internalLoadAnnotatedType(clazz, bdaId));
   }

   protected Class loadClass(String className) {
      if (this.isModuleInfo(className)) {
         return null;
      } else {
         try {
            return (Class)Reflections.cast(this.resourceLoader.classForName(className));
         } catch (ResourceLoadingException var3) {
            this.missingDependenciesRegistry.handleResourceLoadingException(className, var3);
            return null;
         }
      }
   }

   protected SlimAnnotatedType internalLoadAnnotatedType(Class clazz, String bdaId) {
      if (clazz != null && !clazz.isAnnotation() && !clazz.isAnonymousClass() && !clazz.isLocalClass()) {
         try {
            if (!Beans.isVetoed(clazz)) {
               this.containerLifecycleEvents.preloadProcessAnnotatedType(clazz);

               try {
                  return this.classTransformer.getBackedAnnotatedType(clazz, bdaId);
               } catch (ResourceLoadingException var4) {
                  this.missingDependenciesRegistry.handleResourceLoadingException(clazz.getName(), var4);
               }
            }
         } catch (ArrayStoreException var5) {
            this.missingDependenciesRegistry.handleResourceLoadingException(clazz.getName(), var5);
         }
      }

      return null;
   }

   protected SlimAnnotatedTypeContext createContext(SlimAnnotatedType type) {
      return type != null ? SlimAnnotatedTypeContext.of(type) : null;
   }

   protected boolean isModuleInfo(String className) {
      return "module-info".equals(className);
   }
}
