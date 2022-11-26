package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.PasswordValidationServiceImpl;
import com.bea.common.security.servicecfg.PasswordValidationServiceConfig;
import java.lang.reflect.Method;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.PasswordValidatorMBean;

public class PasswordValidationServiceConfigHelper {
   public static String getServiceName(RealmMBean realmMBean) {
      return PasswordValidationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   public static void addToConfig(ServiceEngineConfig serviceEngineConfig, String classLoaderName, RealmMBean realmMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(realmMBean), PasswordValidationServiceImpl.class.getName(), false);
      serviceConfig.setClassLoader(classLoaderName);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
   }

   private static class ConfigImpl implements PasswordValidationServiceConfig {
      private String[] faceNames;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);

         try {
            Method m = realmMBean.getClass().getMethod("getPasswordValidators");
            if (m != null) {
               PasswordValidatorMBean[] providerMBeans = null;
               providerMBeans = (PasswordValidatorMBean[])((PasswordValidatorMBean[])m.invoke(realmMBean));
               this.faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

               for(int i = 0; i < this.faceNames.length; ++i) {
                  this.faceNames[i] = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
                  serviceConfig.addDependency(this.faceNames[i]);
               }
            }
         } catch (Throwable var6) {
            this.faceNames = new String[0];
         }

      }

      public String[] getValidationProviderNames() {
         return this.faceNames;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, Object x2) {
         this(x0, x1);
      }
   }
}
