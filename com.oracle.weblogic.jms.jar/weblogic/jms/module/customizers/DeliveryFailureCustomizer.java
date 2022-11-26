package weblogic.jms.module.customizers;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.customizers.DeliveryFailureParamsBeanCustomizer;

public class DeliveryFailureCustomizer extends ParamsCustomizer implements DeliveryFailureParamsBeanCustomizer {
   private DestinationBean myParent;

   public DeliveryFailureCustomizer(DeliveryFailureParamsBean paramCustomized) {
      super((DescriptorBean)paramCustomized);
      DescriptorBean db = (DescriptorBean)paramCustomized;
      DescriptorBean parent = db.getParentBean();
      if (parent instanceof DestinationBean) {
         this.myParent = (DestinationBean)parent;
      }

   }

   public TemplateBean getTemplateBean() {
      return super.getTemplateBean();
   }

   public String findSubDeploymentName() {
      return this.myParent == null ? null : this.myParent.getSubDeploymentName();
   }
}
