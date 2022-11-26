package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface IIOPMBean extends ConfigurationMBean {
   /** @deprecated */
   @Deprecated
   int getIdleConnectionTimeout();

   /** @deprecated */
   @Deprecated
   void setIdleConnectionTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getCompleteMessageTimeout();

   /** @deprecated */
   @Deprecated
   void setCompleteMessageTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getMaxMessageSize();

   /** @deprecated */
   @Deprecated
   void setMaxMessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getDefaultMinorVersion();

   void setDefaultMinorVersion(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getUseLocateRequest();

   void setUseLocateRequest(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getTxMechanism();

   void setTxMechanism(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getLocationForwardPolicy();

   void setLocationForwardPolicy(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getDefaultWideCharCodeset();

   void setDefaultWideCharCodeset(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getDefaultCharCodeset();

   void setDefaultCharCodeset(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getUseFullRepositoryIdList();

   void setUseFullRepositoryIdList(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getUseStatefulAuthentication();

   void setUseStatefulAuthentication(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getUseSerialFormatVersion2();

   void setUseSerialFormatVersion2(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getEnableIORServlet();

   void setEnableIORServlet(boolean var1);

   boolean getUseJavaSerialization();

   void setUseJavaSerialization(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getSystemSecurity();

   void setSystemSecurity(String var1) throws InvalidAttributeValueException, DistributedManagementException;
}
