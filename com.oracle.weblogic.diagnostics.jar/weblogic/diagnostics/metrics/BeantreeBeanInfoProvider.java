package weblogic.diagnostics.metrics;

import com.oracle.weblogic.diagnostics.expressions.ExtensionBeanInfoProvider;
import com.oracle.weblogic.diagnostics.expressions.Utils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.Locale;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

@Service
@Singleton
public class BeantreeBeanInfoProvider implements ExtensionBeanInfoProvider {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsBeanInfoProviders");
   private static final String PROVIDER_NAME = BeantreeBeanInfoProvider.class.getSimpleName();
   private BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();

   public String getName() {
      return PROVIDER_NAME;
   }

   public BeanInfo getBeanInfo(Class beanClass, Locale l) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("BeantreeBeanInfoProvider looking up BeanInfo for class " + beanClass.getName() + " for Locale " + l.getDisplayCountry());
      }

      BeanInfo bi = this.getCustomBeanInfo(beanClass, l);
      if (bi == null) {
         if (this.beanInfoAccess != null) {
            Class iface = this.findWebLogicMBeanInterface(beanClass);
            if (iface != null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("BeantreeBeanInfoProvider - " + beanClass.getName() + "implements WebLogicMBean interface " + iface.getName() + ", looking up generated BeanInfo via BeanInfoAccess service");
               }

               bi = this.beanInfoAccess.getBeanInfoForInterface(iface.getName(), true, (String)null);
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("beanInfoAccess is null, unable to perform lookups");
         }
      }

      return bi;
   }

   private Class findWebLogicMBeanInterface(Class beanClass) {
      Class wlInterface = null;
      if (beanClass.isInterface() && WebLogicMBean.class.isAssignableFrom(beanClass)) {
         wlInterface = beanClass;
      } else {
         Class[] var3 = beanClass.getInterfaces();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class iface = var3[var5];
            if (WebLogicMBean.class.isAssignableFrom(iface)) {
               wlInterface = iface;
               break;
            }
         }
      }

      return wlInterface;
   }

   public BeanInfo getBeanInfo(String ns, String name, Locale l) {
      if (ns != null && ns.equals("wls")) {
         switch (name) {
            case "partition":
               return this.getCustomBeanInfo(PartitionBean.class, l);
         }
      }

      return null;
   }

   private BeanInfo getCustomBeanInfo(Class beanClass, Locale l) {
      if (beanClass == PartitionBean.class) {
         try {
            return Utils.buildLocalizedExpressionBeanInfo(PartitionBean.class, l);
         } catch (IntrospectionException var4) {
            throw new RuntimeException(var4);
         }
      } else {
         return null;
      }
   }
}
