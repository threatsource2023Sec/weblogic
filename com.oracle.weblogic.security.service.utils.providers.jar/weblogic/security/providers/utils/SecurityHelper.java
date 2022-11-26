package weblogic.security.providers.utils;

import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.utils.CSSPlatformProxy;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.management.security.ProviderMBean;
import weblogic.security.service.SecurityManager;

public class SecurityHelper {
   private static String SEPARATOR = "_";
   private static Map servicesMap = new Hashtable();
   protected String realmName = null;
   protected volatile ExtendedSecurityServices extendedSecurityServices = null;
   protected ArrayList beanUpdateListenerHolder = null;

   private static void startRealm(String realmName) {
      if (realmName != null) {
         CSSPlatformProxy.getInstance().startRealm(realmName);
      }

   }

   public static boolean isRealmShutdown(String realmName) {
      return realmName == null ? false : CSSPlatformProxy.getInstance().isRealmShutdown(realmName);
   }

   public void setExtendedSecurityServices(ExtendedSecurityServices extendedSecurityServices) {
      this.extendedSecurityServices = extendedSecurityServices;
   }

   public boolean isExtendedSecurityServicesAvailable() {
      return this.extendedSecurityServices != null;
   }

   public ExtendedSecurityServices getExtendedSecurityServices() {
      if (this.extendedSecurityServices == null) {
         startRealm(this.realmName);
      }

      return this.extendedSecurityServices;
   }

   public static String getKey(ProviderMBean mBean) {
      return mBean.getRealm().getName() + SEPARATOR + mBean.getName();
   }

   public static boolean isExtendedSecurityServicesAvailable(ProviderMBean mbean) {
      return servicesMap.containsKey(getKey(mbean));
   }

   public static ExtendedSecurityServices getExtendedSecurityServices(ProviderMBean mbean) {
      SecurityManager.checkKernelPermission();
      String key = getKey(mbean);
      ExtendedSecurityServices retVal = (ExtendedSecurityServices)servicesMap.get(key);
      if (retVal == null) {
         startRealm(mbean.getRealm().getName());
         retVal = (ExtendedSecurityServices)servicesMap.get(key);
      }

      return retVal;
   }

   public static void setExtendedSecurityServices(ProviderMBean mbean, ExtendedSecurityServices services) {
      SecurityManager.checkKernelPermission();
      if (services == null) {
         if (isRealmShutdown(mbean.getRealm().getName())) {
            servicesMap.remove(getKey(mbean));
         }

      } else {
         servicesMap.put(getKey(mbean), services);
      }
   }

   protected boolean haveExtendedSecurityServices() {
      if (this.extendedSecurityServices == null) {
         startRealm(this.realmName);
      }

      return this.extendedSecurityServices != null;
   }

   public boolean isRealmShutdown() {
      return isRealmShutdown(this.realmName);
   }

   public synchronized ArrayList createBeanUpdateListenerHolder() {
      this.beanUpdateListenerHolder = new ArrayList();
      return this.beanUpdateListenerHolder;
   }

   public synchronized void addBeanUpdateListenerToHolder(BeanUpdateListener listener) {
      if (this.beanUpdateListenerHolder != null && listener != null) {
         this.beanUpdateListenerHolder.add(listener);
      }

   }
}
