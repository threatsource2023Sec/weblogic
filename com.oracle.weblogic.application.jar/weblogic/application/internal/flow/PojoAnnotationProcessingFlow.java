package weblogic.application.internal.flow;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.interceptor.Interceptor;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.PojoAnnotationProcessor;
import weblogic.application.PojoAnnotationSupportingModule;
import weblogic.application.internal.ModuleAttributes;
import weblogic.application.naming.Environment;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.ModuleRegistryImpl;
import weblogic.application.utils.BaseModuleExtensionContext;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.MailSessionBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.GenericClassLoader;

public class PojoAnnotationProcessingFlow extends BaseFlow {
   private ErrorCollectionException errors;

   public PojoAnnotationProcessingFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      super.prepare();
      if (this.appCtx.isPojoAnnotationEnabled()) {
         if (this.isDebugEnabled()) {
            this.debug("Processing POJO annotations for " + this.appCtx.getApplicationId());
         }

         this.processPOJOsInEarScope();
         this.processPOJOsInModuleScopes();
      } else if (this.isDebugEnabled()) {
         this.debug("Skipped annotation processing because isPojoAnnotationEnabled returned false");
      }

   }

   private void processPOJOsInEarScope() throws DeploymentException {
      if (this.appCtx.isEar()) {
         try {
            PojoEnvironmentBean bean = this.processAnnotations(this.appCtx.getClassInfoFinder(), this.appCtx.getAppClassLoader(), Collections.EMPTY_SET, (ModuleRegistry)null);
            this.validateBeansInEARScope(bean);
            this.appCtx.getEnvironment().contributeEnvEntries(bean, (WeblogicEnvironmentBean)null, (AuthenticatedSubject)null);
            this.appCtx.setPojoEnvironmentBean(bean);
         } catch (ErrorCollectionException var2) {
            throw new DeploymentException(var2);
         }
      }

   }

   private void validateBeansInEARScope(PojoEnvironmentBean bean) {
      if (bean != null) {
         int var3;
         int var4;
         if (bean.getEjbLocalRefs() != null) {
            EjbLocalRefBean[] var2 = bean.getEjbLocalRefs();
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               EjbLocalRefBean b = var2[var4];
               this.validateJNDIValue(b.getEjbRefName());
               this.validateJNDIValue(b.getLookupName());
            }
         }

         if (bean.getEjbRefs() != null) {
            EjbRefBean[] var6 = bean.getEjbRefs();
            var3 = var6.length;

            for(var4 = 0; var4 < var3; ++var4) {
               EjbRefBean b = var6[var4];
               this.validateJNDIValue(b.getEjbRefName());
               this.validateJNDIValue(b.getLookupName());
            }
         }

         if (bean.getPersistenceContextRefs() != null) {
            PersistenceContextRefBean[] var7 = bean.getPersistenceContextRefs();
            var3 = var7.length;

            for(var4 = 0; var4 < var3; ++var4) {
               PersistenceContextRefBean b = var7[var4];
               this.validateJNDIValue(b.getPersistenceContextRefName());
            }
         }

         if (bean.getPersistenceUnitRefs() != null) {
            PersistenceUnitRefBean[] var8 = bean.getPersistenceUnitRefs();
            var3 = var8.length;

            for(var4 = 0; var4 < var3; ++var4) {
               PersistenceUnitRefBean b = var8[var4];
               this.validateJNDIValue(b.getPersistenceUnitRefName());
            }
         }

         if (bean.getResourceRefs() != null) {
            ResourceRefBean[] var9 = bean.getResourceRefs();
            var3 = var9.length;

            for(var4 = 0; var4 < var3; ++var4) {
               ResourceRefBean b = var9[var4];
               this.validateJNDIValue(b.getResRefName());
               this.validateJNDIValue(b.getLookupName());
            }
         }

         if (bean.getServiceRefs() != null) {
            ServiceRefBean[] var10 = bean.getServiceRefs();
            var3 = var10.length;

            for(var4 = 0; var4 < var3; ++var4) {
               ServiceRefBean b = var10[var4];
               this.validateJNDIValue(b.getServiceRefName());
            }
         }

         if (bean.getMessageDestinationRefs() != null) {
            MessageDestinationRefBean[] var11 = bean.getMessageDestinationRefs();
            var3 = var11.length;

            for(var4 = 0; var4 < var3; ++var4) {
               MessageDestinationRefBean b = var11[var4];
               this.validateJNDIValue(b.getMessageDestinationRefName());
            }
         }

         if (bean.getJmsConnectionFactories() != null) {
            JmsConnectionFactoryBean[] var12 = bean.getJmsConnectionFactories();
            var3 = var12.length;

            for(var4 = 0; var4 < var3; ++var4) {
               JmsConnectionFactoryBean b = var12[var4];
               this.validateJNDIValue(b.getName());
            }
         }

         if (bean.getJmsDestinations() != null) {
            JmsDestinationBean[] var13 = bean.getJmsDestinations();
            var3 = var13.length;

            for(var4 = 0; var4 < var3; ++var4) {
               JmsDestinationBean b = var13[var4];
               this.validateJNDIValue(b.getName());
            }
         }

         if (bean.getMailSessions() != null) {
            MailSessionBean[] var14 = bean.getMailSessions();
            var3 = var14.length;

            for(var4 = 0; var4 < var3; ++var4) {
               MailSessionBean b = var14[var4];
               this.validateJNDIValue(b.getName());
            }
         }

         if (bean.getAdministeredObjects() != null) {
            AdministeredObjectBean[] var16 = bean.getAdministeredObjects();
            var3 = var16.length;

            for(var4 = 0; var4 < var3; ++var4) {
               AdministeredObjectBean b = var16[var4];
               this.validateJNDIValue(b.getName());
            }
         }

         if (bean.getDataSources() != null) {
            DataSourceBean[] var18 = bean.getDataSources();
            var3 = var18.length;

            for(var4 = 0; var4 < var3; ++var4) {
               DataSourceBean b = var18[var4];
               this.validateJNDIValue(b.getName());
            }
         }

         if (bean.getResourceEnvRefs() != null) {
            ResourceEnvRefBean[] var20 = bean.getResourceEnvRefs();
            var3 = var20.length;

            for(var4 = 0; var4 < var3; ++var4) {
               ResourceEnvRefBean b = var20[var4];
               this.validateJNDIValue(b.getResourceEnvRefName());
               this.validateJNDIValue(b.getLookupName());
            }
         }

         if (bean.getEnvEntries() != null) {
            EnvEntryBean[] var22 = bean.getEnvEntries();
            var3 = var22.length;

            for(var4 = 0; var4 < var3; ++var4) {
               EnvEntryBean b = var22[var4];
               this.validateJNDIValue(b.getEnvEntryName());
               this.validateJNDIValue(b.getLookupName());
            }
         }

      }
   }

   private void validateJNDIValue(String s) {
      if (s != null) {
         if (s.startsWith("java:comp") || s.startsWith("java:module")) {
            this.addProcessingError("java:comp and java:module can not be applied in class of the EAR scope.");
         }

      }
   }

   private void processPOJOsInModuleScopes() throws DeploymentException {
      Module[] var1 = this.appCtx.getApplicationModules();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module m = var1[var3];
         ModuleAttributes attributes = this.appCtx.getModuleAttributes(m.getId());
         ModuleContext modCtx = attributes.getModuleContext();
         PojoAnnotationSupportingModule pModule = (PojoAnnotationSupportingModule)attributes.getUnwrappedModule(PojoAnnotationSupportingModule.class);
         if (pModule != null && !pModule.isMetadataComplete()) {
            if (this.isDebugEnabled()) {
               this.debug("PojoAnnotationSupportingModule found" + m.getId());
            }

            ModuleExtensionContext modExtCtx = pModule.getModuleExtensionContext();
            if (modExtCtx != null) {
               if (this.isDebugEnabled()) {
                  this.debug("PojoAnnotationSupportingModule has non-null ModuleExtensionContext: " + m.getId());
               }

               Set excludedClasses = modCtx.getRegistry().getAnnotationProcessedClasses();

               try {
                  PojoEnvironmentBean bean = this.processAnnotations(modExtCtx.getClassInfoFinder(), modCtx.getClassLoader(), excludedClasses, modCtx.getRegistry());
                  ((BaseModuleExtensionContext)modExtCtx).setPojoEnvironmentBean(bean);
                  Iterator var11 = modExtCtx.getEnvironments().iterator();

                  while(var11.hasNext()) {
                     Environment env = (Environment)var11.next();
                     env.contributeEnvEntries(bean, (WeblogicEnvironmentBean)null, (AuthenticatedSubject)null);
                  }
               } catch (ErrorCollectionException var13) {
                  throw new DeploymentException(var13);
               }
            }
         }
      }

   }

   public PojoEnvironmentBean processAnnotations(ClassInfoFinder classInfoFinder, GenericClassLoader classLoader, Set excludedClasses, ModuleRegistry moduleRegistry) throws ErrorCollectionException {
      PojoEnvironmentBean bean = (PojoEnvironmentBean)(new DescriptorManager()).createDescriptorRoot(PojoEnvironmentBean.class).getRootBean();
      PojoAnnotationProcessor annotationProcessor = (PojoAnnotationProcessor)GlobalServiceLocator.getServiceLocator().getService(PojoAnnotationProcessor.class, new Annotation[0]);
      Set classesToProcess = new HashSet();
      Set classesToProcessAndSubTypes = new HashSet();
      CollectorFilter filter = new CollectorFilter(classesToProcess, classesToProcessAndSubTypes, classLoader, excludedClasses, classInfoFinder);
      classInfoFinder.getAnnotatedClassesByTargetsAndSources(annotationProcessor.getSupportedAnnotationNames(), filter, false, classLoader).values();
      if (this.isDebugEnabled()) {
         this.debug("POJO annotation processing for application " + this.appCtx.getApplicationId());
         this.debug("POJO annotation processing for with class loader  " + classLoader);
         this.debug("POJO classes that will be processed with annotation processor are " + classesToProcess);
         this.debug("List of all processed POJO classes and sub-types " + classesToProcessAndSubTypes);
      }

      if (moduleRegistry != null && moduleRegistry instanceof ModuleRegistryImpl) {
         ((ModuleRegistryImpl)moduleRegistry).setAnnotatedPojoClasses(classesToProcessAndSubTypes);
      }

      Iterator var10 = classesToProcess.iterator();

      while(var10.hasNext()) {
         Class clz = (Class)var10.next();

         try {
            annotationProcessor.processJ2eeAnnotations(clz, bean, true);
         } catch (ErrorCollectionException var13) {
            this.addProcessingError((Exception)var13);
         }
      }

      this.throwProcessingErrors();
      return bean;
   }

   private Class loadClass(ClassLoader classLoader, String name) {
      Class clazz = null;

      try {
         clazz = classLoader.loadClass(name);
      } catch (ClassNotFoundException var5) {
      }

      return clazz;
   }

   private void addProcessingError(String message) {
      this.addProcessingError((Exception)(new DeploymentException(message)));
   }

   private void addProcessingError(Exception e) {
      if (this.errors == null) {
         this.errors = new ErrorCollectionException();
      }

      this.errors.add(e);
   }

   private void throwProcessingErrors() throws ErrorCollectionException {
      if (this.errors != null && !this.errors.isEmpty()) {
         throw this.errors;
      }
   }

   private class CollectorFilter implements ClassInfoFinder.Filter {
      private final Set classesToProcess;
      private final Set classesToProcessAndSubTypes;
      private final ClassLoader classLoader;
      private final Set excludedClasses;
      private final ClassInfoFinder classInfoFinder;

      private CollectorFilter(Set classesToProcess, Set classesToProcessAndSubTypes, ClassLoader classLoader, Set excludedClasses, ClassInfoFinder classInfoFinder) {
         this.classesToProcess = classesToProcess;
         this.classesToProcessAndSubTypes = classesToProcessAndSubTypes;
         this.classLoader = classLoader;
         this.excludedClasses = excludedClasses;
         this.classInfoFinder = classInfoFinder;
      }

      public boolean accept(ClassInfoFinder.Target target) {
         return true;
      }

      public boolean accept(ClassInfoFinder.Target target, URL codeSourceURL, CharSequence annotationName, CharSequence className) {
         String name = className.toString();
         Class clazz = PojoAnnotationProcessingFlow.this.loadClass(this.classLoader, name);
         if (clazz != null && (clazz.isAnnotationPresent(Interceptor.class) || !this.excludedClasses.contains(name))) {
            this.classesToProcess.add(clazz);
            this.classesToProcessAndSubTypes.add(clazz);
            Set subClassNames = this.classInfoFinder.getAllSubClassNames(name);
            if (subClassNames != null) {
               Iterator var8 = subClassNames.iterator();

               while(var8.hasNext()) {
                  String subClassName = (String)var8.next();
                  Class subClazz = PojoAnnotationProcessingFlow.this.loadClass(this.classLoader, subClassName);
                  if (subClazz != null) {
                     this.classesToProcessAndSubTypes.add(subClazz);
                  }
               }
            }
         }

         return true;
      }

      // $FF: synthetic method
      CollectorFilter(Set x1, Set x2, ClassLoader x3, Set x4, ClassInfoFinder x5, Object x6) {
         this(x1, x2, x3, x4, x5);
      }
   }
}
