package weblogic.security.providers.authorization;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authorization.AdjudicatorMBean;

public interface DefaultAdjudicatorMBean extends StandardInterface, DescriptorBean, AdjudicatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   boolean isRequireUnanimousPermit();

   void setRequireUnanimousPermit(boolean var1) throws InvalidAttributeValueException;

   String getName();
}
