package weblogic.servlet.internal.fragment;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.WebFragmentBean;

public class NullMerger implements Merger {
   public boolean accept(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      String property = update.getPropertyName();
      return bean instanceof WebFragmentBean && ("Icons".equals(property) || "DisplayNames".equals(property) || "Descriptions".equals(property) || "MetadataComplete".equals(property));
   }

   public void merge(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
   }
}
