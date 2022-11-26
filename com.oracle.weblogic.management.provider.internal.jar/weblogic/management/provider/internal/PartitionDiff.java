package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.Iterator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.management.configuration.PartitionMBean;

public class PartitionDiff implements DescriptorDiff {
   DescriptorDiff originDiff;
   ArrayList updates;

   public PartitionDiff(DescriptorDiff originDiff) {
      this.originDiff = originDiff;
      this.updates = new ArrayList();
      Iterator var2 = originDiff.iterator();

      while(var2.hasNext()) {
         BeanUpdateEvent bue = (BeanUpdateEvent)var2.next();
         if (this.partitionUpdate(bue)) {
            this.updates.add(bue);
         }
      }

   }

   private boolean partitionUpdate(BeanUpdateEvent beanUpdateEvent) {
      for(DescriptorBean bean = beanUpdateEvent.getProposedBean(); bean != null; bean = bean.getParentBean()) {
         if (bean instanceof PartitionMBean) {
            return true;
         }
      }

      return false;
   }

   public Iterator iterator() {
      return this.updates.iterator();
   }

   public int size() {
      return this.updates.size();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.size() + " changes ------");
      Iterator iter = this.iterator();

      while(iter.hasNext()) {
         BeanUpdateEvent bue = (BeanUpdateEvent)iter.next();
         buf.append("\n " + bue.toString());
      }

      return buf.toString();
   }
}
