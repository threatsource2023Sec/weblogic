package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.management.DistributedManagementException;

/** @deprecated */
@Deprecated
public interface JMSTemplateMBean extends JMSDestCommonMBean, ConfigurationMBean, JMSConstants {
   JMSDestinationMBean[] getDestinations();

   boolean addDestination(JMSDestinationMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean removeDestination(JMSDestinationMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean isMessagesPagingEnabled();

   /** @deprecated */
   @Deprecated
   void setMessagesPagingEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isBytesPagingEnabled();

   /** @deprecated */
   @Deprecated
   void setBytesPagingEnabled(boolean var1) throws InvalidAttributeValueException;

   void useDelegates(DomainMBean var1, JMSBean var2, TemplateBean var3);
}
