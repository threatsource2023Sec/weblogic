package weblogic.application;

import java.io.File;
import java.io.FileNotFoundException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.DomainMBean;

public abstract class MBeanFactory {
   public static MBeanFactory getMBeanFactory() {
      return MBeanFactory.MBeanFactorySingleton.SINGLETON;
   }

   public abstract ApplicationMBean initializeMBeans(DomainMBean var1, File var2, String var3, String var4, String var5, AppDeploymentMBean var6) throws weblogic.management.ApplicationException;

   public abstract void reconcileMBeans(AppDeploymentMBean var1, File var2) throws FileNotFoundException, weblogic.management.ApplicationException;

   public abstract void cleanupMBeans(DomainMBean var1, ApplicationMBean var2);

   private static final class MBeanFactorySingleton {
      private static final MBeanFactory SINGLETON;

      static {
         try {
            Class c = Class.forName("weblogic.application.internal.MBeanFactoryImpl");
            SINGLETON = (MBeanFactory)c.newInstance();
         } catch (Exception var1) {
            throw new AssertionError(var1);
         }
      }
   }
}
