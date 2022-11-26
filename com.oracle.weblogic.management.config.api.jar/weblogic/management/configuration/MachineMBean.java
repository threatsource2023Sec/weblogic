package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface MachineMBean extends ConfigurationMBean {
   /** @deprecated */
   @Deprecated
   String[] getAddresses();

   /** @deprecated */
   @Deprecated
   void setAddresses(String[] var1) throws InvalidAttributeValueException;

   NodeManagerMBean getNodeManager();
}
