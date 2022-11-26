package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.ExtensionBeanInfoProvider;
import com.oracle.weblogic.diagnostics.expressions.Utils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.Locale;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.archive.InstrumentationEventBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.logging.LogEventBean;

@Service
@Singleton
public class WatchBeanInfoProvider implements ExtensionBeanInfoProvider {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final String PROVIDER_NAME = WatchBeanInfoProvider.class.getSimpleName();

   public String getName() {
      return PROVIDER_NAME;
   }

   public BeanInfo getBeanInfo(Class beanClass, Locale l) {
      BeanInfo beanInfo = this.getCustomBeanInfo(beanClass, l);
      return beanInfo;
   }

   public BeanInfo getBeanInfo(String ns, String name, Locale l) {
      if (ns == null || ns.equals("wls")) {
         switch (name) {
            case "resource":
               return this.getCustomBeanInfo(WatchConfiguration.ResourceExpressionBean.class, l);
            case "log":
               return this.getCustomBeanInfo(LogEventBean.class, l);
            case "instrumentationEvent":
               return this.getCustomBeanInfo(InstrumentationEventBean.class, l);
         }
      }

      return null;
   }

   private BeanInfo getCustomBeanInfo(Class beanClass, Locale l) {
      BeanInfo beanInfo = null;
      if (beanClass == WatchConfiguration.ResourceExpressionBean.class || beanClass == WatchConfiguration.WatchExpressionBean.class) {
         try {
            beanInfo = Utils.buildLocalizedExpressionBeanInfo(beanClass, l);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Created bean info for " + beanClass.getName() + ": " + beanInfo);
            }
         } catch (IntrospectionException var5) {
            throw new RuntimeException(var5);
         }
      }

      return beanInfo;
   }
}
