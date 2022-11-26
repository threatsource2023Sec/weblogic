package weblogic.managedbean;

import java.util.Collections;
import java.util.Set;
import javax.annotation.ManagedBean;
import javax.enterprise.deploy.shared.ModuleType;
import javax.interceptor.Interceptor;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleExtensionFactory;
import weblogic.descriptor.Descriptor;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.WebAppBean;

public class ManagedBeanModuleExtensionFactory implements ModuleExtensionFactory {
   public static final Class[] ANNOTATIONS = new Class[]{ManagedBean.class, Interceptor.class};

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.WAR, ModuleType.EJB};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return ANNOTATIONS;
   }

   private boolean canHaveManagedBeans(Descriptor standardDD) {
      if (standardDD == null) {
         return true;
      } else if (!(standardDD.getRootBean() instanceof EjbJarBean)) {
         if (standardDD.getRootBean() instanceof WebAppBean) {
            try {
               return (double)Float.parseFloat(standardDD.getOriginalVersionInfo()) > 2.5;
            } catch (Exception var3) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return "3.1".equals(standardDD.getOriginalVersionInfo()) || "3.2".equals(standardDD.getOriginalVersionInfo());
      }
   }

   public ModuleExtension create(ModuleExtensionContext extensionCtx, ApplicationContextInternal appCtx, Module extensibleModule, Descriptor standardDD) throws ModuleException {
      if (!this.canHaveManagedBeans(standardDD)) {
         return null;
      } else {
         try {
            Set beanClasses = extensionCtx.getAnnotatedClasses(true, new Class[]{ManagedBean.class});
            Set appLevelBeanClasses = appCtx.getAnnotatedClasses(new Class[]{ManagedBean.class});
            if (appLevelBeanClasses != null && !appLevelBeanClasses.isEmpty()) {
               beanClasses.addAll(appLevelBeanClasses);
            }

            if (beanClasses != null && !beanClasses.isEmpty()) {
               Set interceptorClasses = extensionCtx.getAnnotatedClasses(true, new Class[]{Interceptor.class});
               Set appLevelInterceptors = appCtx.getAnnotatedClasses(new Class[]{Interceptor.class});
               if (appLevelInterceptors != null && !appLevelInterceptors.isEmpty()) {
                  interceptorClasses.addAll(appLevelInterceptors);
               }

               if (interceptorClasses == null) {
                  interceptorClasses = Collections.emptySet();
               }

               return new ManagedBeanModuleExtension(extensionCtx, appCtx, extensibleModule, beanClasses, interceptorClasses);
            } else {
               return null;
            }
         } catch (AnnotationProcessingException var9) {
            throw new ModuleException("Error getting classes annotated @ManagedBean, @Interceptor or @Interceptors", var9);
         }
      }
   }
}
