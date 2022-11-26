package weblogic.management.security.audit;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface ContextHandlerMBean extends StandardInterface, DescriptorBean {
   String[] getSupportedContextHandlerEntries();

   String[] getActiveContextHandlerEntries();

   void setActiveContextHandlerEntries(String[] var1) throws InvalidAttributeValueException;
}
