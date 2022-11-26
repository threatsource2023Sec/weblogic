package weblogic.application;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.naming.NamingConstants;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;

@Service
public final class ApplicationAccess implements ApplicationAccessService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final char appVerSeparator = '#';

   private ApplicationAccess() {
   }

   /** @deprecated */
   @Deprecated
   public static ApplicationAccess getApplicationAccess() {
      return ApplicationAccess.ApplicationAccessSingleton.SINGLETON;
   }

   public String digOutCurrentApplicationId() {
      String applicationId = this.getCurrentApplicationName();
      return applicationId == null ? this.getApplicationIdFromJndi() : applicationId;
   }

   private String getApplicationIdFromJndi() {
      Context initialCtx = null;

      Object var3;
      try {
         initialCtx = new InitialContext();
         String var2 = (String)initialCtx.lookup("java:app/" + NamingConstants.WLInternalNS + '/' + "ApplicationId");
         return var2;
      } catch (NamingException var13) {
         var3 = null;
      } finally {
         if (initialCtx != null) {
            try {
               initialCtx.close();
            } catch (NamingException var12) {
            }
         }

      }

      return (String)var3;
   }

   /** @deprecated */
   @Deprecated
   public String getCurrentApplicationName() {
      return this.getCurrentApplicationId();
   }

   public String getCurrentApplicationId() {
      Annotation annotation = this.getAnnotation(this.getContextClassLoader());
      return annotation != null ? annotation.getApplicationName() : null;
   }

   private ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return Thread.currentThread().getContextClassLoader();
         }
      });
   }

   public String getApplicationName(ClassLoader classLoader) {
      String applicationName = this.getApplicationName(this.getAnnotation(classLoader));
      return applicationName;
   }

   private String getApplicationName(Annotation annotation) {
      String applicationName = null;
      if (annotation != null) {
         applicationName = annotation.getApplicationName();
         if (applicationName != null && applicationName.length() > 0) {
            int sepLocation = applicationName.indexOf(35);
            if (sepLocation > -1) {
               applicationName = applicationName.substring(0, sepLocation);
            }
         }
      }

      return applicationName;
   }

   public String getModuleName(ClassLoader classLoader) {
      Annotation annotation = this.getAnnotation(classLoader);
      return annotation != null ? annotation.getModuleName() : null;
   }

   public String getApplicationVersion(ClassLoader classLoader) {
      Annotation annotation = this.getAnnotation(classLoader);
      if (annotation != null) {
         String applicationName = annotation.getApplicationName();
         if (applicationName != null && applicationName.length() > 0) {
            int sepLocation = applicationName.indexOf(35);
            if (sepLocation > -1) {
               return applicationName.substring(sepLocation + 1);
            }
         }
      }

      return null;
   }

   private Annotation getAnnotation(final ClassLoader classLoader) {
      while(classLoader != null) {
         if (classLoader instanceof GenericClassLoader) {
            return ((GenericClassLoader)classLoader).getAnnotation();
         }

         classLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               return classLoader.getParent();
            }
         });
      }

      return null;
   }

   public ApplicationContextInternal getCurrentApplicationContext() {
      return this.getApplicationContext(this.getCurrentApplicationName());
   }

   public ApplicationContextInternal getApplicationContext(String appName) {
      if (appName == null) {
         return null;
      } else {
         Deployment d = (Deployment)DeploymentManager.getDeploymentManager().findDeployment(appName);
         return d == null ? null : (ApplicationContextInternal)d.getApplicationContext();
      }
   }

   public String getCurrentModuleName() {
      try {
         Context ic = new InitialContext();
         return (String)ic.lookup("java:/bea/ModuleName");
      } catch (NamingException var2) {
         return null;
      }
   }

   public GenericClassLoader findModuleLoader(String appName, String moduleURI) {
      AppClassLoaderManager m = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new java.lang.annotation.Annotation[0]);
      return m.findModuleLoader(appName, moduleURI);
   }

   private static final class ApplicationAccessSingleton {
      private static final ApplicationAccess SINGLETON = (ApplicationAccess)LocatorUtilities.getService(ApplicationAccess.class);
   }
}
