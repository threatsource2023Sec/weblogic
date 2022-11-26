package weblogic.management.provider.internal;

import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.utils.ActiveBeanUtil;

@Service
@Singleton
public class ActiveBeanUtilImpl extends ActiveBeanUtilBaseImpl implements ActiveBeanUtil {
   public boolean isInPartition(ConfigurationMBean bean) {
      ConfigurationMBean originalBean = this.toOriginalBean(bean);
      boolean answer = false;

      for(WebLogicMBean parent = originalBean.getParent(); !answer && parent != null; parent = parent.getParent()) {
         answer = parent instanceof PartitionMBean;
      }

      return answer;
   }

   public PartitionMBean findContainingPartition(ConfigurationMBean bean) {
      return this.findContainingPartition((WebLogicMBean)this.toOriginalBean(bean));
   }

   private PartitionMBean findContainingPartition(WebLogicMBean container) {
      while(container != null) {
         if (container instanceof PartitionMBean) {
            return (PartitionMBean)container;
         }

         container = this.toOriginalBean((ConfigurationMBean)((WebLogicMBean)container).getParent());
      }

      return null;
   }
}
