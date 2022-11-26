package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface WebServiceBufferingQueueMBean extends ConfigurationMBean {
   String getName();

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   Boolean isEnabled();

   void setEnabled(Boolean var1);

   String getConnectionFactoryJndiName();

   void setConnectionFactoryJndiName(String var1);

   Boolean isTransactionEnabled();

   void setTransactionEnabled(Boolean var1);
}
