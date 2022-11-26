package weblogic.deploy.internal;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.OptionalFeatureDeploymentMBean;
import weblogic.management.configuration.OptionalFeatureMBean;
import weblogic.management.internal.InternalApplication;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConfigurableInternalAppHelper {
   private OptionalFeatureDeploymentMBean switcher;
   private static ConfigurableInternalAppHelper instance;

   private ConfigurableInternalAppHelper() {
   }

   public boolean isDeployable(String featureName) {
      if (featureName == null) {
         return true;
      } else if (this.switcher == null && Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("field variable switcher is NULL!!!!");
         return true;
      } else {
         OptionalFeatureMBean iam = this.switcher.lookupOptionalFeature(featureName);
         if (iam != null && !iam.isEnabled()) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Dont deploy feature: " + featureName + " at WLS first startup!");
            }

            return false;
         } else {
            return true;
         }
      }
   }

   private static OptionalFeatureDeploymentMBean createOptionalFeaturesInDeployment(List internalApps) {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      OptionalFeatureDeploymentMBean ofd = ManagementService.getRuntimeAccess(kernelId).getDomain().getOptionalFeatureDeployment();
      HashMap featureMap = new HashMap();
      Iterator var4 = internalApps.iterator();

      while(var4.hasNext()) {
         InternalApplication app = (InternalApplication)var4.next();
         if (app.getOptionalFeatureName() != null) {
            OptionalFeatureMBean iam = ofd.lookupOptionalFeature(app.getOptionalFeatureName());
            if (iam == null) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("add OptionalFeatureMBean into deployment config for feature:" + app.getOptionalFeatureName() + " appName:" + app.getName());
               }

               featureMap.put(app.getOptionalFeatureName(), app.isOptionalFeatureEnabled());
            }
         }
      }

      if (featureMap.size() > 0) {
         DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
         ofd = domainMBean.getOptionalFeatureDeployment();

         Map.Entry entry;
         for(Iterator var8 = featureMap.entrySet().iterator(); var8.hasNext(); ofd.createOptionalFeature((String)entry.getKey()).setEnabled((Boolean)entry.getValue())) {
            entry = (Map.Entry)var8.next();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("create OptionalFeatureMBean :" + (String)entry.getKey() + " isEnabled :" + entry.getValue());
            }
         }
      }

      return ofd;
   }

   public static synchronized ConfigurableInternalAppHelper getInstance(List internalApps) {
      if (instance == null) {
         instance = new ConfigurableInternalAppHelper();
         instance.switcher = createOptionalFeaturesInDeployment(internalApps);
      }

      return instance;
   }
}
