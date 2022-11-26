package weblogic.jms.module.customizers;

import weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.GroupParamsBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.customizers.TemplateBeanCustomizer;

public class TemplateCustomizer implements TemplateBeanCustomizer {
   private TemplateBean customized;

   public TemplateCustomizer(TemplateBean paramCustomized) {
      this.customized = paramCustomized;
   }

   public DestinationBean findErrorDestination(String groupName) {
      if (this.customized != null && groupName != null) {
         DeliveryFailureParamsBean dfpb = this.customized.getDeliveryFailureParams();
         GroupParamsBean gpb = this.customized.lookupGroupParams(groupName);
         if (gpb == null) {
            return dfpb.getErrorDestination();
         } else {
            DestinationBean errorDestination = gpb.getErrorDestination();
            if (errorDestination != null) {
               return errorDestination;
            } else {
               return gpb.isSet("ErrorDestination") ? null : dfpb.getErrorDestination();
            }
         }
      } else {
         return null;
      }
   }
}
