package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;

/** @deprecated */
@Deprecated
public interface NetworkChannelMBean extends DeploymentMBean {
   String getName();

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   String getDescription();

   void setDescription(String var1) throws InvalidAttributeValueException;

   int getListenPort();

   void setListenPort(int var1) throws InvalidAttributeValueException;

   boolean isListenPortEnabled();

   void setListenPortEnabled(boolean var1) throws InvalidAttributeValueException;

   int getSSLListenPort();

   void setSSLListenPort(int var1) throws InvalidAttributeValueException;

   boolean isSSLListenPortEnabled();

   void setSSLListenPortEnabled(boolean var1) throws InvalidAttributeValueException;

   String getClusterAddress();

   void setClusterAddress(String var1) throws InvalidAttributeValueException;

   boolean isT3Enabled();

   void setT3Enabled(boolean var1) throws InvalidAttributeValueException;

   boolean isT3SEnabled();

   void setT3SEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isHTTPEnabled();

   void setHTTPEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isHTTPSEnabled();

   void setHTTPSEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isCOMEnabled();

   /** @deprecated */
   @Deprecated
   void setCOMEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isOutgoingEnabled();

   void setOutgoingEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isBoundOutgoingEnabled();

   int getChannelWeight();

   void setChannelWeight(int var1) throws InvalidAttributeValueException;

   int getAcceptBacklog();

   void setAcceptBacklog(int var1) throws InvalidAttributeValueException;

   int getLoginTimeoutMillis();

   void setLoginTimeoutMillis(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getLoginTimeoutMillisSSL();

   void setLoginTimeoutMillisSSL(int var1);

   boolean isTunnelingEnabled();

   void setTunnelingEnabled(boolean var1) throws DistributedManagementException;

   int getTunnelingClientPingSecs();

   void setTunnelingClientPingSecs(int var1) throws InvalidAttributeValueException;

   int getTunnelingClientTimeoutSecs();

   void setTunnelingClientTimeoutSecs(int var1) throws InvalidAttributeValueException;

   int getCompleteT3MessageTimeout();

   void setCompleteT3MessageTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getCompleteHTTPMessageTimeout();

   void setCompleteHTTPMessageTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getCompleteCOMMessageTimeout();

   /** @deprecated */
   @Deprecated
   void setCompleteCOMMessageTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaxT3MessageSize();

   void setMaxT3MessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaxHTTPMessageSize();

   void setMaxHTTPMessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getMaxCOMMessageSize();

   /** @deprecated */
   @Deprecated
   void setMaxCOMMessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isIIOPEnabled();

   void setIIOPEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isIIOPSEnabled();

   void setIIOPSEnabled(boolean var1) throws InvalidAttributeValueException;

   int getCompleteIIOPMessageTimeout();

   void setCompleteIIOPMessageTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaxIIOPMessageSize();

   void setMaxIIOPMessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getIdleIIOPConnectionTimeout();

   void setIdleIIOPConnectionTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getDefaultIIOPUser();

   void setDefaultIIOPUser(String var1) throws InvalidAttributeValueException;

   String getDefaultIIOPPassword();

   void setDefaultIIOPPassword(String var1) throws InvalidAttributeValueException;

   byte[] getDefaultIIOPPasswordEncrypted();

   void setDefaultIIOPPasswordEncrypted(byte[] var1);
}
