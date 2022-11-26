package weblogic.servlet.internal.fragment;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.j2ee.descriptor.WebAppBean;

public class DistributablesMerger extends AbstractMerger {
   public boolean accept(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      return "Distributables".equals(update.getPropertyName());
   }

   protected void handleRemoveEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      WebAppBean webAppBean = (WebAppBean)targetBean;
      EmptyBean[] var6 = webAppBean.getDistributables();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         EmptyBean bean = var6[var8];
         webAppBean.destroyDistributable(bean);
      }

   }
}
