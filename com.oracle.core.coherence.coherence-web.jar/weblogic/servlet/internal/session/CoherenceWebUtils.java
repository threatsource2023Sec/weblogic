package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.AbstractHttpSessionCollection;
import com.tangosol.coherence.servlet.SessionHelper;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.run.xml.XmlElement;
import weblogic.common.internal.PackageInfo;
import weblogic.servlet.internal.WebAppServletContext;

public class CoherenceWebUtils {
   public static boolean isVersionGreaterThan(PackageInfo packageInfo, int major, int minor, int serivcePack) {
      if (packageInfo.getMajor() > major) {
         return true;
      } else {
         if (packageInfo.getMajor() == major) {
            if (packageInfo.getMinor() > minor) {
               return true;
            }

            if (packageInfo.getMinor() == minor) {
               return packageInfo.getServicePack() > serivcePack;
            }
         }

         return false;
      }
   }

   public static void configureCoherenceWeb(ClassLoader cl, boolean defaultCacheEnabled, boolean federatedCacheEnabled) {
      WrappedClassLoader defaultFederatedCacheLoader;
      ConfigurableCacheFactory federatedCacheFactory;
      if (defaultCacheEnabled) {
         defaultFederatedCacheLoader = new WrappedClassLoader(cl);
         federatedCacheFactory = CacheFactory.getCacheFactoryBuilder().getConfigurableCacheFactory("default-session-cache-config.xml", defaultFederatedCacheLoader);
         federatedCacheFactory.ensureCache("session-storage", defaultFederatedCacheLoader);
         federatedCacheFactory.ensureCache("session-management", defaultFederatedCacheLoader);
         federatedCacheFactory.ensureCache("session-overflow", defaultFederatedCacheLoader);
      }

      if (federatedCacheEnabled) {
         defaultFederatedCacheLoader = new WrappedClassLoader(cl);
         federatedCacheFactory = CacheFactory.getCacheFactoryBuilder().getConfigurableCacheFactory("default-federated-session-cache-config.xml", defaultFederatedCacheLoader);
         federatedCacheFactory.ensureCache("session-storage", defaultFederatedCacheLoader);
         federatedCacheFactory.ensureCache("session-management", defaultFederatedCacheLoader);
         federatedCacheFactory.ensureCache("session-overflow", defaultFederatedCacheLoader);
      }

   }

   public static void shutdownCoherenceWeb() {
      CoherenceWebReapingManager.getInstance().stopReapTimer();
   }

   public static boolean isCompatibilityModeEnabled(WebAppServletContext sci, XmlElement xml) {
      String controllerClass = null;
      boolean isCompatibilityMode = false;
      if (sci != null) {
         controllerClass = sci.getInitParameter("coherence-scopecontroller-class");
      } else if (xml != null) {
         controllerClass = xml.getSafeElement("coherence-scopecontroller-class").getString((String)null);
      }

      if (!AbstractHttpSessionCollection.GlobalScopeController.class.getName().equals(controllerClass)) {
         isCompatibilityMode = true;
      }

      if (sci != null) {
         isCompatibilityMode = SessionHelper.parseBoolean(sci, "coherence-session-weblogic-compatibility-mode", isCompatibilityMode);
      } else if (xml != null) {
         isCompatibilityMode = xml.getSafeElement("coherence-session-weblogic-compatibility-mode").getBoolean(isCompatibilityMode);
      }

      return isCompatibilityMode;
   }

   static class WrappedClassLoader extends ClassLoader {
      public WrappedClassLoader(ClassLoader parent) {
         super(parent);
      }
   }
}
