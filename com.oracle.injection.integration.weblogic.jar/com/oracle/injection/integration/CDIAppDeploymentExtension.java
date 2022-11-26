package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.InjectionException;
import com.oracle.injection.ejb.EjbDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.DefinitionException;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.management.DeploymentException;

class CDIAppDeploymentExtension extends BaseAppDeploymentExtension {
   private static Logger m_logger = Logger.getLogger(CDIAppDeploymentExtension.class.getName());

   public void activate(ApplicationContextInternal appCtx) throws DeploymentException {
      if (!appCtx.getCdiPolicy().equals("Disabled")) {
         this.initCdi(appCtx);
      } else {
         try {
            CDIUtils.getInjectionContainer(appCtx);
         } catch (Throwable var4) {
            String msgPrefix = this.getDeploymentErrorMsgPrefix(var4);
            throw new DeploymentException(msgPrefix + var4.getMessage(), var4);
         }
      }

   }

   public void deactivate(ApplicationContextInternal appCtx) throws DeploymentException {
      if (!appCtx.getCdiPolicy().equals("Disabled")) {
         this.unInitCdi(appCtx);
      }

   }

   public void prepare(ApplicationContextInternal appCtx) throws DeploymentException {
   }

   private void initCdi(ApplicationContextInternal appCtx) throws DeploymentException {
      ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();

      try {
         InjectionContainer injectionContainer = CDIUtils.getInjectionContainer(appCtx);
         Thread.currentThread().setContextClassLoader(appCtx.getAppClassLoader());
         this.processLibArchives(appCtx, injectionContainer);
         this.updateArchiveHolders(appCtx);
         injectionContainer.initialize();
         injectionContainer.deploy();
         injectionContainer.start();
         this.checkServletsForDefinitionErrors(appCtx, injectionContainer);
         this.checkEjbsForDefinitionErrors(appCtx);
         this.bindBeanManagerIntoComponentContexts(appCtx, injectionContainer);
         this.publishInjectionContainerToModuleRegistries(appCtx, injectionContainer);
      } catch (Throwable var8) {
         String msgPrefix = this.getDeploymentErrorMsgPrefix(var8);
         throw new DeploymentException(msgPrefix + var8.getMessage(), var8);
      } finally {
         Thread.currentThread().setContextClassLoader(oldContextClassLoader);
      }

   }

   private String getDeploymentErrorMsgPrefix(Throwable t) {
      if (t instanceof DefinitionException) {
         return "CDI definition failure:";
      } else if (t instanceof javax.enterprise.inject.spi.DeploymentException) {
         return "CDI deployment failure:";
      } else {
         Throwable cause = t.getCause();
         return cause != t && cause != null ? this.getDeploymentErrorMsgPrefix(cause) : "CDI deployment failure:";
      }
   }

   private static List getEjbNames(InjectionArchive injectionArchive) {
      List ejbNames = new ArrayList();
      Collection ejbDescriptors = injectionArchive.getEjbDescriptors();
      if (ejbDescriptors != null) {
         Iterator var3 = ejbDescriptors.iterator();

         while(var3.hasNext()) {
            EjbDescriptor oneEjbDescriptor = (EjbDescriptor)var3.next();
            ejbNames.add(oneEjbDescriptor.getEjbName());
         }
      }

      return ejbNames;
   }

   protected void updateArchiveHolders(ApplicationContextInternal appCtx) {
      InjectionDeploymentHelper deploymentHelper = this.getDeploymentHelper(appCtx);
      if (deploymentHelper != null) {
         Iterator var3 = deploymentHelper.getArchiveHolders().iterator();

         while(true) {
            InjectionDeploymentHelper.ArchiveHolder archiveHolder;
            ModuleContext moduleContext;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               archiveHolder = (InjectionDeploymentHelper.ArchiveHolder)var3.next();
               moduleContext = archiveHolder.getModuleContext();
            } while(!moduleContext.getType().equals(ModuleType.EJB.toString()));

            List ejbNames = getEjbNames(archiveHolder.getInjectionArchive());
            Iterator var7 = ejbNames.iterator();

            while(var7.hasNext()) {
               String oneEjbName = (String)var7.next();
               ModuleExtensionContext moduleExtensionContext = archiveHolder.getModuleExtensionContext();
               Context context = moduleExtensionContext.getEnvironment(oneEjbName).getCompContext();
               archiveHolder.addComponentContext(context);
            }
         }
      }
   }

   public void unprepare(ApplicationContextInternal appCtx) throws DeploymentException {
      this.removeInjectionContainerObjectsFromAppRegistry(appCtx);
   }

   private void unInitCdi(ApplicationContextInternal appCtx) throws DeploymentException {
      try {
         InjectionContainer injectionContainer = (InjectionContainer)appCtx.getUserObject(InjectionContainer.class.getName());
         if (injectionContainer != null) {
            injectionContainer.stop();
         }

      } catch (InjectionException var3) {
         throw new DeploymentException("Error while shutting down InjectionContainer", var3);
      }
   }

   private String extractArchiveName(String archivePath) {
      int ndx = archivePath.lastIndexOf(File.separator);
      return ndx != -1 && archivePath.length() != 1 ? archivePath.substring(ndx + 1) : archivePath;
   }

   private boolean doesInjectionArchiveExist(String canonicalizedArchivePath, Collection injectionArchives) {
      if (injectionArchives != null) {
         Iterator var3 = injectionArchives.iterator();

         while(var3.hasNext()) {
            InjectionArchive oneInjectionArchive = (InjectionArchive)var3.next();
            if (this.canonicalizePath(oneInjectionArchive.getClassPathArchiveName()).equals(canonicalizedArchivePath)) {
               return true;
            }

            if (oneInjectionArchive.getArchiveType().equals(InjectionArchiveType.RAR) && this.doesInjectionArchiveExist(canonicalizedArchivePath, oneInjectionArchive.getEmbeddedArchives())) {
               return true;
            }
         }
      }

      return false;
   }

   private String canonicalizePath(String pathToCanonicalize) {
      try {
         return (new File(pathToCanonicalize)).getCanonicalPath();
      } catch (IOException var3) {
         m_logger.warning("Could not create canonicalized path for " + pathToCanonicalize + "." + var3.getMessage());
         return pathToCanonicalize;
      }
   }

   protected void processLibArchives(ApplicationContextInternal appCtx, InjectionContainer injectionContainer) throws InjectionException {
      Collection injectionArchives = injectionContainer.getInjectionArchives();
      ArrayList appPaths = CDIUtils.getArchivePaths(appCtx.getAppClassLoaderClassPath());
      int archiveCount = false;
      Iterator var6 = appPaths.iterator();

      while(var6.hasNext()) {
         String onePath = (String)var6.next();
         String adjustedPath = onePath.replace(File.separator, "/");
         File file = new File(onePath);

         try {
            if (file.exists() && !adjustedPath.contains("cache/EJBCompilerCache") && adjustedPath.length() > 0 && !this.doesInjectionArchiveExist(file.getCanonicalPath(), injectionArchives)) {
               String archivePrefix = null;
               File[] var11 = appCtx.getApplicationPaths();
               int var12 = var11.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  File appCtxPath = var11[var13];
                  String prefix = appCtxPath.getPath();
                  if (onePath.startsWith(appCtxPath.getPath())) {
                     archivePrefix = prefix;
                     break;
                  }
               }

               String archiveName;
               if (archivePrefix != null) {
                  archiveName = onePath.substring(archivePrefix.length());
                  if (archiveName.startsWith(File.separator)) {
                     archiveName = archiveName.substring(1);
                  }
               } else {
                  archiveName = this.extractArchiveName(onePath);
               }

               archiveName = archiveName + "lib";
               InjectionArchive embeddedInjectionArchive = new EmbeddedLibraryInjectionArchive(appCtx, archiveName, onePath);
               if (embeddedInjectionArchive.getBeanClassNames().size() > 0) {
                  injectionContainer.addInjectionArchive(embeddedInjectionArchive);
                  m_logger.finer("Created application library injection archive: " + embeddedInjectionArchive.getClassPathArchiveName());
               }
            }
         } catch (IOException var16) {
            throw new InjectionException("Exception processing " + onePath, var16);
         }
      }

   }

   protected void checkServletsForDefinitionErrors(ApplicationContextInternal appCtx, InjectionContainer injectionContainer) throws InjectionException {
      InjectionDeploymentHelper deploymentHelper = this.getDeploymentHelper(appCtx);
      if (deploymentHelper != null) {
         InjectionDeployment injectionDeployment = injectionContainer.getDeployment();
         Iterator var5 = deploymentHelper.getArchiveHolders().iterator();

         while(var5.hasNext()) {
            InjectionDeploymentHelper.ArchiveHolder archiveHolder = (InjectionDeploymentHelper.ArchiveHolder)var5.next();
            InjectionArchive injectionArchive = archiveHolder.getInjectionArchive();
            BeanManager cdiBeanManager = this.getCDIBeanManager(injectionDeployment, injectionArchive);
            Iterator var9 = cdiBeanManager.getBeans(Servlet.class, new Annotation[0]).iterator();

            while(var9.hasNext()) {
               Bean bean = (Bean)var9.next();
               Iterator var11 = bean.getInjectionPoints().iterator();

               while(var11.hasNext()) {
                  InjectionPoint injectionPoint = (InjectionPoint)var11.next();
                  if (injectionPoint.getType().equals(InjectionPoint.class) && injectionPoint.getQualifiers().contains(new AnnotationLiteral() {
                  })) {
                     throw new DefinitionException("Servlet bean " + bean.getBeanClass() + "cannot have an @Inject annotation defined on a file of type InjectionPoint");
                  }
               }
            }
         }
      }

   }

   protected void checkEjbsForDefinitionErrors(ApplicationContextInternal appCtx) throws InjectionException {
      InjectionDeploymentHelper deploymentHelper = this.getDeploymentHelper(appCtx);
      if (deploymentHelper != null) {
         Iterator var3 = deploymentHelper.getArchiveHolders().iterator();

         while(true) {
            InjectionArchive injectionArchive;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               InjectionDeploymentHelper.ArchiveHolder archiveHolder = (InjectionDeploymentHelper.ArchiveHolder)var3.next();
               injectionArchive = archiveHolder.getInjectionArchive();
            } while(!this.isEjbArchive(injectionArchive) && !this.isWebArchive(injectionArchive));

            Iterator var6 = injectionArchive.getEjbDescriptors().iterator();

            while(var6.hasNext()) {
               EjbDescriptor ejbDescriptor = (EjbDescriptor)var6.next();
               if (ejbDescriptor instanceof EjbDescriptorAdapter) {
                  EjbDescriptorAdapter adapter = (EjbDescriptorAdapter)ejbDescriptor;
                  Class ejbBeanClass = adapter.getNonGeneratedEjbBeanClass();
                  if (BeanTypeUtils.isSpecializingBean(ejbBeanClass)) {
                     this.checkForNamedOverrideDefinitionError(ejbBeanClass);
                     this.checkForSpecializationOfNonSessionBeanDefinitionError(ejbBeanClass);
                  }

                  this.checkForDisposesMethodOnNonBusinessMethod(ejbDescriptor, ejbBeanClass);
                  this.checkForProducesOnNonBusinessMethod(ejbDescriptor, ejbBeanClass);
               }
            }
         }
      }
   }

   void checkForDisposesMethodOnNonBusinessMethod(EjbDescriptor ejbDescriptor, Class ejbBeanClass) throws InjectionException {
      Method[] var3 = ejbBeanClass.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         Annotation[][] var7 = method.getParameterAnnotations();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Annotation[] paramAnnotations = var7[var9];
            Annotation[] var11 = paramAnnotations;
            int var12 = paramAnnotations.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               Annotation paramAnnotation = var11[var13];
               if (paramAnnotation.annotationType().equals(Disposes.class) && !this.isBusinessMethod(method, ejbDescriptor)) {
                  throw new InjectionException("EJB class = " + ejbBeanClass + " contains a @Disposes method = " + method.getName() + " that is not a part of the session bean's business interface.  This is a CDI definition error.");
               }
            }
         }
      }

   }

   void checkForProducesOnNonBusinessMethod(EjbDescriptor ejbDescriptor, Class ejbBeanClass) throws InjectionException {
      Method[] var3 = ejbBeanClass.getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];
         if (method.isAnnotationPresent(Produces.class) && !this.isBusinessMethod(method, ejbDescriptor)) {
            throw new InjectionException("EJB class = " + ejbBeanClass + " contains a @Produces method = " + method.getName() + " that is not a part of the session bean's business interface.  This is a CDI definition error.");
         }
      }

   }

   private boolean isBusinessMethod(Method method, EjbDescriptor ejbDescriptor) {
      boolean foundMatch = false;
      Iterator var4 = ejbDescriptor.getLocalBusinessInterfaceClasses().iterator();

      while(var4.hasNext()) {
         Class businessInterface = (Class)var4.next();

         try {
            Method interfaceMethod = businessInterface.getMethod(method.getName(), method.getParameterTypes());
            if (interfaceMethod != null) {
               foundMatch = true;
            }
         } catch (NoSuchMethodException var7) {
         }
      }

      return foundMatch;
   }

   private void checkForSpecializationOfNonSessionBeanDefinitionError(Class ejbBeanClass) throws InjectionException {
      if (!BeanTypeUtils.isSessionBeanClass(ejbBeanClass.getSuperclass())) {
         throw new InjectionException("EJB class = " + ejbBeanClass + " includes the @Specializes Annotation, but does not directly extend a Session Bean.  This is a CDI definition error");
      }
   }

   private void checkForNamedOverrideDefinitionError(Class ejbBeanClass) throws InjectionException {
      if (ejbBeanClass.getAnnotation(Named.class) != null) {
         Named namedAnnotationInHierarchy = this.findNamedAnnotationInHierarchy(ejbBeanClass.getSuperclass());
         if (namedAnnotationInHierarchy != null) {
            throw new InjectionException("EJB class = " + ejbBeanClass + "cannot be deployed due to a CDI definition error.  A Specializing bean is attempting to redefine a @Name for this bean, which it inherits from the supertype.");
         }
      }

   }

   protected void publishInjectionContainerToModuleRegistries(ApplicationContextInternal appCtx, InjectionContainer ic) {
      InjectionDeploymentHelper deploymentHelper = this.getDeploymentHelper(appCtx);
      if (deploymentHelper != null) {
         Iterator var4 = deploymentHelper.getArchiveHolders().iterator();

         while(var4.hasNext()) {
            InjectionDeploymentHelper.ArchiveHolder archiveHolder = (InjectionDeploymentHelper.ArchiveHolder)var4.next();
            ModuleRegistry registry = archiveHolder.getModuleContext().getRegistry();
            registry.put(InjectionContainer.class.getName(), ic);
         }
      }

   }

   private boolean isWebArchive(InjectionArchive injectionArchive) {
      return injectionArchive.getArchiveType() == InjectionArchiveType.WAR;
   }

   private boolean isEjbArchive(InjectionArchive injectionArchive) {
      return injectionArchive.getArchiveType() == InjectionArchiveType.EJB_JAR;
   }

   Named findNamedAnnotationInHierarchy(Class beanClass) {
      while(beanClass != null) {
         Named named = (Named)beanClass.getAnnotation(Named.class);
         if (named != null) {
            return named;
         }

         beanClass = beanClass.getSuperclass();
      }

      return null;
   }

   private void bindBeanManagerIntoComponentContexts(ApplicationContextInternal appCtx, InjectionContainer injectionContainer) throws NamingException, DeploymentException {
      InjectionDeploymentHelper deploymentHelper = this.getDeploymentHelper(appCtx);
      if (deploymentHelper != null) {
         InjectionDeployment injectionDeployment = injectionContainer.getDeployment();
         Iterator var5 = deploymentHelper.getArchiveHolders().iterator();

         while(var5.hasNext()) {
            InjectionDeploymentHelper.ArchiveHolder archiveHolder = (InjectionDeploymentHelper.ArchiveHolder)var5.next();
            List contexts = archiveHolder.getComponentContexts();
            Iterator var8 = contexts.iterator();

            while(var8.hasNext()) {
               Context oneContext = (Context)var8.next();
               CDIUtils.bindBeanManager(oneContext, archiveHolder.getInjectionArchive(), injectionDeployment);
            }
         }
      }

   }

   private BeanManager getCDIBeanManager(InjectionDeployment injectionDeployment, InjectionArchive injectionArchive) {
      com.oracle.injection.BeanManager beanManager = injectionDeployment.getBeanManager(injectionArchive.getArchiveName());
      return (BeanManager)beanManager.getInternalBeanManager();
   }

   private InjectionDeploymentHelper getDeploymentHelper(ApplicationContextInternal appCtx) {
      return (InjectionDeploymentHelper)appCtx.getUserObject(InjectionDeploymentHelper.class.getName());
   }

   private void removeInjectionContainerObjectsFromAppRegistry(ApplicationContextInternal appCtx) {
      appCtx.removeUserObject(InjectionContainer.class.getName());
      appCtx.removeUserObject(InjectionDeploymentHelper.class.getName());
      appCtx.removeUserObject(WeblogicContainerIntegrationService.class.getName());
   }
}
