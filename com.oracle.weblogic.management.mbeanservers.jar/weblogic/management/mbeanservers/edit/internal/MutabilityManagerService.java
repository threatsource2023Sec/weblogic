package weblogic.management.mbeanservers.edit.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.inject.Named;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.jmx.modelmbean.MutabilityManager;
import weblogic.server.AbstractServerService;

@Service
@Singleton
@Named
public class MutabilityManagerService extends AbstractServerService implements MutabilityManager {
   public boolean isImmutableSubtreeRoot(Object mbean) {
      if (mbean instanceof SystemResourceMBean) {
         SystemResourceMBean systemResourceMBean = (SystemResourceMBean)mbean;
         if (systemResourceMBean.getParent() instanceof ResourceGroupMBean || systemResourceMBean.getParent() instanceof ServerMBean) {
            try {
               Method getDelegateBeanMethod = mbean.getClass().getMethod("_getDelegateBean");
               if (getDelegateBeanMethod.invoke(mbean) != null) {
                  return true;
               }
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException var4) {
            }
         }
      }

      return false;
   }
}
