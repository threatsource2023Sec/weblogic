package weblogic.management.provider.internal;

import java.util.Iterator;
import javax.inject.Inject;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.internal.PartitionResourceProcessor;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;

@PerLookup
@Service
public class PartitionResourceProcessorImpl implements PartitionResourceProcessor {
   @Inject
   private PartitionProcessor partitionProcessor;
   private final DebugLogger dbg = DebugLogger.getDebugLogger("DebugPartitionLifecycle");

   public void process(DescriptorDiff diff) throws Exception {
      Iterator var2 = diff.iterator();

      DomainMBean domain;
      do {
         if (!var2.hasNext()) {
            return;
         }

         BeanUpdateEvent event = (BeanUpdateEvent)var2.next();
         domain = this.findDomain(event.getSourceBean());
      } while(domain == null);

      this.partitionProcessor.updateClones(domain);
   }

   private DomainMBean findDomain(DescriptorBean bean) {
      while(bean != null) {
         if (bean instanceof DomainMBean) {
            return (DomainMBean)bean;
         }

         bean = bean.getParentBean();
      }

      return null;
   }
}
