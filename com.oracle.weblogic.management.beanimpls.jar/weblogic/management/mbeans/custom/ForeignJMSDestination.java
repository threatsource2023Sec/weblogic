package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class ForeignJMSDestination extends ForeignJNDIObject {
   public ForeignJMSDestination(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(ForeignDestinationBean paramDelegate) {
      super.useDelegates(paramDelegate);
   }
}
