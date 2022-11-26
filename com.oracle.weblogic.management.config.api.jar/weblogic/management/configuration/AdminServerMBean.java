package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

/** @deprecated */
@Deprecated
public interface AdminServerMBean extends ConfigurationMBean {
   /** @deprecated */
   @Deprecated
   ServerMBean getServer();

   /** @deprecated */
   @Deprecated
   DomainMBean getActiveDomain();

   /** @deprecated */
   @Deprecated
   String getName();

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;
}
