package weblogic.jms.module.customizers;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;

public class ParamsCustomizer {
   private DescriptorBean customized;

   public ParamsCustomizer(DescriptorBean paramCustomized) {
      this.customized = paramCustomized;
   }

   public TemplateBean getTemplateBean() {
      if (this.customized == null) {
         return null;
      } else {
         DescriptorBean parent = this.customized.getParentBean();
         if (!(parent instanceof DestinationBean)) {
            return null;
         } else {
            DestinationBean destination = (DestinationBean)parent;
            return destination.getTemplate();
         }
      }
   }
}
