package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface WebServiceRequestBufferingQueueMBean extends WebServiceBufferingQueueMBean {
   void setName(String var1) throws InvalidAttributeValueException, ManagementException;
}
