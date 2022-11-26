package weblogic.connector.external;

import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.resource.ResourceException;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.ValidationMessage;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.wl.ConnectorWorkManagerBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.j2ee.descriptor.wl.WorkManagerBean;

public interface RAInfo {
   RAInfo createRAInfo(ConnectorBean var1, WeblogicConnectorBean var2, PermissionsBean var3, URL var4, String var5, ConnectorAPContext var6, boolean var7);

   RAInfo createRAInfo(ConnectorBean var1, WeblogicConnectorBean var2, URL var3, String var4, ConnectorAPContext var5);

   RAInfo createRAInfo(ConnectorBean var1, WeblogicConnectorBean var2, URL var3, String var4);

   List getOutboundInfos();

   OutboundInfo getOutboundInfo(String var1);

   List getInboundInfos() throws ElementNotFoundException;

   PropertyNameNormalizer getPropertyNameNormalizer();

   String getRADescription();

   String getDisplayName();

   String getSmallIcon();

   String getLargeIcon();

   String getVendorName();

   String getEisType();

   String getRAVersion();

   String getLicenseDescription();

   String[] getLicenseDescriptions();

   boolean getLicenseRequired();

   String getRAClass();

   List getAdminObjs();

   String getNativeLibDir();

   Hashtable getRAConfigProps();

   String getLinkref();

   void setBaseRA(RAInfo var1);

   String getConnectionFactoryName();

   String getSpecVersion();

   String getJndiName();

   List getSecurityPermissions();

   URL getURL();

   boolean isEnableAccessOutsideApp();

   boolean isEnableGlobalAccessToClasses();

   SecurityIdentityInfo getSecurityIdentityInfo();

   String[] getRequiredWorkContext();

   WorkManagerBean getWorkManager();

   ConnectorWorkManagerBean getConnectorWorkManager();

   String getModuleName();

   ConnectorBean getConnectorBean();

   WeblogicConnectorBean getWeblogicConnectorBean();

   PermissionsBean getPermissionsBean();

   String[] getRADescriptions();

   AdminObjInfo buildAdminObjectInfo(AdministeredObjectBean var1, UniversalResourceKey var2) throws ResourceException;

   OutboundInfo buildOutboundInfo(ConnectionFactoryResourceBean var1, UniversalResourceKey var2) throws ResourceException;

   AdminObjInfo getAdminObject(String var1);

   /** @deprecated */
   @Deprecated
   Hashtable getAdminObjectGroupProperties(String var1);

   Hashtable getAdminObjectGroupProperties(String var1, String var2);

   Hashtable getConnectionGroupConfigProperties(String var1);

   String getConnectionGroupTransactionSupport(String var1);

   AuthMechInfo[] getConnectionGroupAuthenticationMechanisms(String var1);

   String getConnectionGroupResAuth(String var1);

   boolean isConnectionGroupReauthenticationSupport(String var1);

   LoggingInfo getConnectionGroupLoggingProperties(String var1);

   PoolInfo getConnectionGroupPoolProperties(String var1);

   ConnectorAPContext getConnectorAPContext();

   boolean isDeployAsAWhole();

   String[] getCriticalSubComponents();

   Map getSubComponentsChild2ParentMap();

   ValidationMessage getValidationMessage();

   void setValidationMessage(ValidationMessage var1);

   boolean supportMessageListenerType(String var1);

   boolean isCustomizedClassloading();
}
