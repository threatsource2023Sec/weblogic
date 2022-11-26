package weblogic.connector.external.impl;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.external.AuthMechInfo;
import weblogic.connector.external.ElementNotFoundException;
import weblogic.connector.external.InboundInfo;
import weblogic.connector.external.LoggingInfo;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.PoolInfo;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.SecurityIdentityInfo;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.PropertyNameNormalizerFactory;
import weblogic.connector.utils.ValidationMessage;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.IconBean;
import weblogic.j2ee.descriptor.InboundResourceAdapterBean;
import weblogic.j2ee.descriptor.MessageAdapterBean;
import weblogic.j2ee.descriptor.MessageListenerBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;
import weblogic.j2ee.descriptor.SecurityPermissionBean;
import weblogic.j2ee.descriptor.wl.AdminObjectGroupBean;
import weblogic.j2ee.descriptor.wl.AdminObjectInstanceBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConnectionInstanceBean;
import weblogic.j2ee.descriptor.wl.ConnectorWorkManagerBean;
import weblogic.j2ee.descriptor.wl.ResourceAdapterSecurityBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorExtensionBean;
import weblogic.j2ee.descriptor.wl.WorkManagerBean;

public class RAInfoImpl implements RAInfo {
   private static String DEFAULT_NATIVE_LIBDIR = "/temp/nativelibs/";
   private ConnectorBean connBean;
   private WeblogicConnectorBean wlConnBean;
   private RAInfo baseRA;
   private ResourceAdapterSecurityBean raSecurityBean;
   private WorkManagerBean workManagerBean;
   private PermissionsBean permissionsBean;
   protected ConnectorWorkManagerBean connectorWorkManagerBean;
   protected URL url;
   protected String moduleName;
   public static final RAInfo factoryHelper = new RAInfoImpl();
   private ValidationMessage validationMessage;
   private ArrayList inboundInfos;
   private ResourceAdapterBean raBean;
   private OutboundResourceAdapterBean outboundRABean;
   private weblogic.j2ee.descriptor.wl.OutboundResourceAdapterBean wlOutboundRABean;
   private InboundResourceAdapterBean inboundRABean;
   private MessageListenerBean[] msgListeners;
   private AdminObjectsHelperWL adminObjectsHelper;
   private ConnectionDefinitionHelper connectionDefinitionHelper;
   private ConnectorAPContext apContext;
   private PropertyNameNormalizer propertyNameNormalizer;
   private boolean customizedClassloading;

   public RAInfo createRAInfo(ConnectorBean connectorBean, WeblogicConnectorBean wlConnectorBean, PermissionsBean permissionsBean, URL url, String moduleName, ConnectorAPContext apcontext, boolean customizedClassloading) {
      return new RAInfoImpl(connectorBean, wlConnectorBean, permissionsBean, url, moduleName, apcontext, customizedClassloading);
   }

   public RAInfo createRAInfo(ConnectorBean connectorBean, WeblogicConnectorBean wlConnectorBean, URL url, String moduleName, ConnectorAPContext apcontext) {
      return new RAInfoImpl(connectorBean, wlConnectorBean, (PermissionsBean)null, url, moduleName, apcontext, false);
   }

   public RAInfo createRAInfo(ConnectorBean connectorBean, WeblogicConnectorBean wlConnectorBean, URL url, String moduleName) {
      return new RAInfoImpl(connectorBean, wlConnectorBean, (PermissionsBean)null, url, moduleName, ConnectorAPContext.NullContext, false);
   }

   private RAInfoImpl() {
      this.baseRA = null;
      this.inboundInfos = null;
      this.raBean = null;
      this.outboundRABean = null;
      this.wlOutboundRABean = null;
      this.inboundRABean = null;
      this.msgListeners = null;
   }

   protected RAInfoImpl(ConnectorBean connectorBean, WeblogicConnectorBean wlConnectorBean, URL url, String moduleName, ConnectorAPContext apcontext) {
      this(connectorBean, wlConnectorBean, (PermissionsBean)null, url, moduleName, apcontext, false);
   }

   protected RAInfoImpl(ConnectorBean connectorBean, WeblogicConnectorBean wlConnectorBean, PermissionsBean permissionsBean, URL url, String moduleName, ConnectorAPContext apcontext, boolean customizedClassloading) {
      this.baseRA = null;
      this.inboundInfos = null;
      this.raBean = null;
      this.outboundRABean = null;
      this.wlOutboundRABean = null;
      this.inboundRABean = null;
      this.msgListeners = null;
      this.customizedClassloading = customizedClassloading;
      Debug.println((Object)this, (String)("( moduleName = '" + moduleName + "' )"));
      if (wlConnectorBean == null) {
         Debug.throwAssertionError("WeblogicConnectorBean == null");
      }

      if (url == null) {
         Debug.throwAssertionError("URL == null");
      }

      if (moduleName == null || moduleName.trim().equals("")) {
         Debug.throwAssertionError("ModuleName == null or the empty string");
      }

      if (connectorBean == null) {
         Debug.parsing("connectorBean == null");
      }

      this.moduleName = moduleName;
      this.url = url;
      this.permissionsBean = permissionsBean;
      this.setConnBean(connectorBean);
      this.setWeblogicConnectorBean(wlConnectorBean);
      this.apContext = apcontext;
      this.adminObjectsHelper = new AdminObjectsHelperWL(connectorBean, wlConnectorBean);
      this.connectionDefinitionHelper = new ConnectionDefinitionHelper(connectorBean, wlConnectorBean);
      if (connectorBean != null) {
         this.propertyNameNormalizer = PropertyNameNormalizerFactory.getPropertyNameNormalizer(connectorBean.getVersion());
      }

      this.dump();
   }

   public PropertyNameNormalizer getPropertyNameNormalizer() {
      return this.propertyNameNormalizer;
   }

   public void dump() {
      if (Debug.isParsingEnabled()) {
         try {
            ByteArrayOutputStream wlcbeanXML;
            if (this.connBean != null) {
               wlcbeanXML = new ByteArrayOutputStream();
               ((DescriptorBean)this.connBean).getDescriptor().toXML(wlcbeanXML);
               Debug.parsing("ConnectorBean = \n" + wlcbeanXML.toString());
            }

            if (this.wlConnBean != null) {
               wlcbeanXML = new ByteArrayOutputStream();
               ((DescriptorBean)this.wlConnBean).getDescriptor().toXML(wlcbeanXML);
               Debug.parsing("WeblogicConnectorBean = \n" + wlcbeanXML.toString());
            }
         } catch (Exception var2) {
            var2.printStackTrace();
         }

      }
   }

   public void setBaseRA(RAInfo raInfo) {
      Debug.println((Object)this, (String)".setBaseRA()");
      if (raInfo == null) {
         Debug.throwAssertionError("RAInfo == null");
      }

      this.baseRA = raInfo;
      ConnectorBean cb = ((RAInfoImpl)this.baseRA).getConnectorBean();
      if (cb == null) {
         Debug.throwAssertionError("RAInfo.getConnBean() == null");
      }

      this.setConnBean(cb);
      if (this.getOutboundRABean() == null) {
         Debug.throwAssertionError("RAInfo.getOutboundRABean() == null");
      }

      ConnectionDefinitionBean[] connDefns = this.getOutboundRABean().getConnectionDefinitions();
      if (connDefns.length == 0) {
         Debug.throwAssertionError("connDefns.length == 0");
      }

      String connectionDefnRef = connDefns[0].getConnectionFactoryInterface();
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean[] wlConnDefnGroups = this.wlConnBean.getOutboundResourceAdapter().getConnectionDefinitionGroups();
      wlConnDefnGroups[0].setConnectionFactoryInterface(connectionDefnRef);
   }

   private void setConnBean(ConnectorBean connectorBean) {
      this.connBean = connectorBean;
      this.inboundInfos = null;
      this.raBean = null;
      this.outboundRABean = null;
      this.inboundRABean = null;
      this.msgListeners = null;
      if (this.connBean != null && this.connBean.getLicense() == null) {
         this.connBean.createLicense();
      }

   }

   private void setWeblogicConnectorBean(WeblogicConnectorBean wlConnectorBean) {
      this.wlConnBean = wlConnectorBean;
      this.raSecurityBean = this.wlConnBean.getSecurity();
      this.workManagerBean = this.wlConnBean.getWorkManager();
      this.connectorWorkManagerBean = this.wlConnBean.getConnectorWorkManager();
      Debug.deployment("====>setWeblogicConnectorBean: connectorWorkManagerBean:" + this.connectorWorkManagerBean);
      Debug.deployment("====>setWeblogicConnectorBean: workManagerBean:" + this.workManagerBean);

      assert this.connectorWorkManagerBean != null;

   }

   public WeblogicConnectorBean getWeblogicConnectorBean() {
      return this.wlConnBean;
   }

   public PermissionsBean getPermissionsBean() {
      return this.permissionsBean;
   }

   public List getOutboundInfos() {
      List outboundInfos = new ArrayList();
      ConnectionDefinitionBean[] connDefns = null;
      OutboundInfo baseOutboundInfo = null;
      if (this.baseRA != null) {
         baseOutboundInfo = (OutboundInfo)this.baseRA.getOutboundInfos().get(0);
      }

      if (this.getOutboundRABean() != null) {
         connDefns = this.getOutboundRABean().getConnectionDefinitions();
         Debug.println((Object)this, (String)(".getOutboundInfos() found " + (connDefns == null ? 0 : connDefns.length) + " connection definitions."));
      }

      for(int connDefnIdx = 0; connDefns != null && connDefnIdx < connDefns.length; ++connDefnIdx) {
         weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean wlConnDefnGroup = this.getConnectionDefnRef(connDefns[connDefnIdx], this.connBean, this.wlConnBean);
         if (wlConnDefnGroup != null && wlConnDefnGroup.getConnectionInstances() != null && wlConnDefnGroup.getConnectionInstances().length != 0) {
            ConnectionInstanceBean[] wlConnInstances = wlConnDefnGroup.getConnectionInstances();

            for(int wlConnInstanceIdx = 0; wlConnInstanceIdx < wlConnDefnGroup.getConnectionInstances().length; ++wlConnInstanceIdx) {
               OutboundInfoImpl outboundInfo = new OutboundInfoImpl(this, this.connBean, this.wlConnBean, connDefns[connDefnIdx], wlConnDefnGroup, wlConnInstances[wlConnInstanceIdx]);
               outboundInfos.add(outboundInfo);
               if (baseOutboundInfo != null) {
                  outboundInfo.setBaseOutboundInfo(baseOutboundInfo);
               }
            }
         }
      }

      List orphanWlsConnectionDefns = this.getOrphanWlsConnectionDefnRef();
      Iterator var15 = orphanWlsConnectionDefns.iterator();

      while(var15.hasNext()) {
         weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean wlConnDef = (weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean)var15.next();
         ConnectionInstanceBean[] var9 = wlConnDef.getConnectionInstances();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            ConnectionInstanceBean wlConnInstance = var9[var11];
            OutboundInfoImpl outboundInfo = new OutboundInfoImpl(this, this.connBean, this.wlConnBean, (ConnectionDefinitionBean)null, wlConnDef, wlConnInstance);
            outboundInfos.add(outboundInfo);
            if (baseOutboundInfo != null) {
               outboundInfo.setBaseOutboundInfo(baseOutboundInfo);
            }
         }
      }

      return outboundInfos;
   }

   public OutboundInfo getOutboundInfo(String jndi) {
      OutboundInfo retOutboundInfo = null;
      List outboundInfos = this.getOutboundInfos();
      Iterator outboundInfoIterator = outboundInfos.iterator();
      OutboundInfo tmpOutboundInfo = null;

      while(outboundInfoIterator.hasNext()) {
         tmpOutboundInfo = (OutboundInfo)outboundInfoIterator.next();
         if (tmpOutboundInfo.getJndiName().equals(jndi)) {
            retOutboundInfo = tmpOutboundInfo;
            break;
         }
      }

      return retOutboundInfo;
   }

   public List getInboundInfos() throws ElementNotFoundException {
      Debug.println((Object)this, (String)".getInboundInfos()");
      synchronized(this) {
         if (this.inboundInfos == null) {
            Debug.println((Object)this, (String)".getInboundInfos() computing/caching InboundInfos");
            MessageListenerBean[] listeners = this.getMessageListeners();
            this.inboundInfos = new ArrayList();
            Debug.println((Object)this, (String)(".getInboundInfos() found " + listeners.length + " MessageListeners"));

            for(int idx = 0; idx < listeners.length; ++idx) {
               Debug.println((Object)this, (String)(".getInboundInfos() creating InboundInfoImpl for listener: '" + this.msgListeners[idx] + "'"));
               InboundInfoImpl inInfo = new InboundInfoImpl(this, this.msgListeners[idx]);
               this.inboundInfos.add(inInfo);
            }
         }

         return this.inboundInfos;
      }
   }

   public String getRADescription() {
      String description = null;
      String[] descriptions = this.connBean.getDescriptions();
      if (descriptions.length != 0) {
         description = descriptions[0];
      }

      return description;
   }

   public String getDisplayName() {
      String displayName = null;
      String[] displayNames = this.connBean.getDisplayNames();
      if (displayNames.length != 0) {
         displayName = displayNames[0];
      }

      return displayName;
   }

   public String getSmallIcon() {
      String smallIcon = null;
      IconBean[] icons = this.connBean.getIcons();
      if (icons.length != 0) {
         smallIcon = icons[0].getSmallIcon();
      }

      return smallIcon;
   }

   public String getLargeIcon() {
      String largeIcon = null;
      IconBean[] icons = this.connBean.getIcons();
      if (icons.length != 0) {
         largeIcon = icons[0].getLargeIcon();
      }

      return largeIcon;
   }

   public String getVendorName() {
      return this.connBean.getVendorName();
   }

   public String getEisType() {
      return this.connBean != null ? this.connBean.getEisType() : null;
   }

   public String getRAVersion() {
      return this.connBean.getResourceAdapterVersion();
   }

   public String getLicenseDescription() {
      String description = null;
      if (this.connBean.getLicense() == null) {
         Debug.throwAssertionError("connBean.getLicense() == null");
      }

      String[] descriptions = this.connBean.getLicense().getDescriptions();
      if (descriptions != null && descriptions.length != 0) {
         description = descriptions[0];
      }

      return description;
   }

   public String[] getLicenseDescriptions() {
      if (this.connBean.getLicense() == null) {
         Debug.throwAssertionError("connBean.getLicense() == null");
      }

      return this.connBean.getLicense().getDescriptions();
   }

   public boolean getLicenseRequired() {
      if (this.connBean.getLicense() == null) {
         Debug.throwAssertionError("connBean.getLicense() == null");
      }

      return this.connBean.getLicense().isLicenseRequired();
   }

   public String getRAClass() {
      return this.getRABean().getResourceAdapterClass();
   }

   public List getAdminObjs() {
      return this.adminObjectsHelper.getAdminObjs();
   }

   public String getNativeLibDir() {
      return this.wlConnBean != null ? this.wlConnBean.getNativeLibdir() : this.getDefaultNativeLibDir();
   }

   public Hashtable getRAConfigProps() {
      ConfigPropertyBean[] raConfigProps = this.getRABean().getConfigProperties();
      ConfigPropertiesBean[] wlraConfigProps = new ConfigPropertiesBean[1];
      if (this.wlConnBean != null && this.wlConnBean.isPropertiesSet()) {
         wlraConfigProps[0] = this.wlConnBean.getProperties();
      }

      return DDLayerUtils.mergeConfigProperties(raConfigProps, wlraConfigProps, this.propertyNameNormalizer);
   }

   public String getLinkref() {
      String raLinkRef = null;
      if (this.isPreDiabloRA() && ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef() != null) {
         raLinkRef = ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef().getRaLinkRef();
      }

      return raLinkRef;
   }

   public String getConnectionFactoryName() {
      String connectionFactoryName = null;
      if (this.isPreDiabloRA() && ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef() != null) {
         connectionFactoryName = ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef().getConnectionFactoryName();
      }

      return connectionFactoryName;
   }

   public String getSpecVersion() {
      if (this.connBean.getVersion() == null) {
         Debug.throwAssertionError("connBean.getVersion() == null");
      }

      return this.connBean.getVersion().toString();
   }

   public String getJndiName() {
      return this.wlConnBean.getJNDIName();
   }

   public List getSecurityPermissions() {
      SecurityPermissionBean[] securityPermBeans = this.getRABean().getSecurityPermissions();
      List securityPerms = new ArrayList();
      if (securityPermBeans != null) {
         for(int idx = 0; idx < securityPermBeans.length; ++idx) {
            securityPerms.add(new SecurityPermissionInfoImpl(securityPermBeans[idx]));
         }
      }

      return securityPerms;
   }

   public SecurityIdentityInfo getSecurityIdentityInfo() {
      return this.wlConnBean.isSecuritySet() ? new SecurityIdentityInfoImpl(this.wlConnBean.getSecurity()) : null;
   }

   public URL getURL() {
      return this.url;
   }

   public boolean isEnableAccessOutsideApp() {
      return this.wlConnBean.isEnableAccessOutsideApp();
   }

   public boolean isEnableGlobalAccessToClasses() {
      return this.wlConnBean.isEnableGlobalAccessToClasses();
   }

   public WorkManagerBean getWorkManager() {
      return this.workManagerBean;
   }

   public ConnectorWorkManagerBean getConnectorWorkManager() {
      Debug.deployment("====>getConnectorWorkManager: connectorWorkManagerBean:" + this.connectorWorkManagerBean);
      return this.connectorWorkManagerBean;
   }

   public String[] getRequiredWorkContext() {
      return this.connBean.getRequiredWorkContexts();
   }

   private weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean getConnectionDefnRef(ConnectionDefinitionBean connDefn, ConnectorBean connBean, WeblogicConnectorBean wlConnBean) {
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean retWLConnDefnGroup = null;
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean[] wlConnDefnGroups = null;
      if (connDefn == null) {
         Debug.println((Object)this, (String)".getConnectionDefnRef(): argument ConnectorDefinitionBean (connDefn) is null; returning null ConnectionDefinitionBean");
         return null;
      } else if (connBean == null) {
         Debug.println((Object)this, (String)".getConnectionDefnRef(): argument ConnectorBean (connBean) == null; returning null ConnectionDefinitionBean");
         return null;
      } else if (wlConnBean == null) {
         Debug.println((Object)this, (String)".getConnectionDefnRef(): argument WeblogicConnectorBean (wlConnBean) == null; returning null ConnectionDefinitionBean");
         return null;
      } else if (!wlConnBean.isOutboundResourceAdapterSet()) {
         Debug.println((Object)this, (String)".getConnectionDefnRef(): getWeblogicOutboundRABean() is null; returning null ConnectionDefinitionBean");
         return null;
      } else {
         wlConnDefnGroups = wlConnBean.getOutboundResourceAdapter().getConnectionDefinitionGroups();
         if (this.isPreDiabloRA()) {
            if (wlConnDefnGroups == null || wlConnDefnGroups.length == 1) {
               Debug.println((Object)this, (String)(".getConnectionDefnRef() found " + (wlConnDefnGroups == null ? 0 : wlConnDefnGroups.length) + " connection factory(ies).  There should be exactly 1.  Returning the first one or null."));
               return wlConnDefnGroups == null ? null : wlConnDefnGroups[0];
            }
         } else {
            String connDefnId = connDefn.getConnectionFactoryInterface();
            Debug.println((Object)this, (String)(".getConnectionDefnRef() looking for a WL conn defn with connection factory interface = '" + connDefnId + "'"));
            if (connDefnId == null || connDefnId.trim().equals("")) {
               Debug.println((Object)this, (String)".getConnectionDefnRef() found an null or empty string as the ConnectionFactoryInteface on the connection definition.  Returning a null ConnectionDefinitionBean");
               return null;
            }

            for(int idx = 0; wlConnDefnGroups != null && idx < wlConnDefnGroups.length; ++idx) {
               String foundCFI = wlConnDefnGroups[idx].getConnectionFactoryInterface();
               if (foundCFI.equals(connDefnId)) {
                  Debug.println((Object)this, (String)(".getConnectionDefnRef() found a WL conn defn with connection factory interface = '" + connDefnId + "'"));
                  retWLConnDefnGroup = wlConnDefnGroups[idx];
                  break;
               }

               Debug.println((Object)this, (String)(".getConnectionDefnRef() looking for a WL conn defn with connection factory interface = '" + connDefnId + "' but found one with '" + foundCFI + "' instead. " + (idx + 1 < wlConnDefnGroups.length ? "Still looking." : "Done looking.")));
            }
         }

         return retWLConnDefnGroup;
      }
   }

   public List getOrphanWlsConnectionDefnRef() {
      List orphanWlConnDefnGroups = new ArrayList();
      if (this.wlConnBean != null && this.wlConnBean.isOutboundResourceAdapterSet() && this.wlConnBean.getOutboundResourceAdapter().getConnectionDefinitionGroups() != null) {
         List raConnectionFactoryInterfaces = new ArrayList();
         ConnectionDefinitionBean[] wlConnDefnGroups;
         int var5;
         int var6;
         if (this.connBean.getResourceAdapter() != null && this.connBean.getResourceAdapter().getOutboundResourceAdapter() != null && this.connBean.getResourceAdapter().getOutboundResourceAdapter().getConnectionDefinitions() != null) {
            wlConnDefnGroups = this.connBean.getResourceAdapter().getOutboundResourceAdapter().getConnectionDefinitions();
            ConnectionDefinitionBean[] var4 = wlConnDefnGroups;
            var5 = wlConnDefnGroups.length;

            for(var6 = 0; var6 < var5; ++var6) {
               ConnectionDefinitionBean def = var4[var6];
               raConnectionFactoryInterfaces.add(def.getConnectionFactoryInterface());
            }
         }

         wlConnDefnGroups = null;
         weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean[] wlConnDefnGroups = this.wlConnBean.getOutboundResourceAdapter().getConnectionDefinitionGroups();
         weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean[] var10 = wlConnDefnGroups;
         var5 = wlConnDefnGroups.length;

         for(var6 = 0; var6 < var5; ++var6) {
            weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean wlConnDefnGroup = var10[var6];
            String wlCFInterface = wlConnDefnGroup.getConnectionFactoryInterface();
            if (!raConnectionFactoryInterfaces.contains(wlCFInterface)) {
               Debug.println((Object)this, (String)(".getOrphanWlsConnectionDefnRef() found an orphan connection-definition-group <" + wlCFInterface + " >in weblogic-ra.xml which has no corresponding connection-factory-interface in ra.xml."));
               orphanWlConnDefnGroups.add(wlConnDefnGroup);
            }
         }

         return orphanWlConnDefnGroups;
      } else {
         return orphanWlConnDefnGroups;
      }
   }

   private String getDefaultNativeLibDir() {
      return DEFAULT_NATIVE_LIBDIR;
   }

   public ConnectorBean getConnectorBean() {
      return this.connBean;
   }

   private boolean isPreDiabloRA() {
      return this.wlConnBean instanceof WeblogicConnectorExtensionBean;
   }

   private ResourceAdapterBean getRABean() {
      Debug.println((Object)this, (String)".getRABean()");
      if (this.raBean == null) {
         this.raBean = this.connBean.getResourceAdapter();
      }

      Debug.println((Object)this, (String)(".getRABean() returning " + (this.raBean != null ? "non-null" : "null")));
      if (this.raBean != null) {
         Debug.println((Object)this, (String)(".getRABean().getResourceAdapterClass() = " + this.raBean.getResourceAdapterClass()));
      }

      return this.raBean;
   }

   private OutboundResourceAdapterBean getOutboundRABean() {
      if (this.outboundRABean == null) {
         this.outboundRABean = this.getRABean().getOutboundResourceAdapter();
      }

      return this.outboundRABean;
   }

   private InboundResourceAdapterBean getInboundRABean() {
      Debug.println((Object)this, (String)".getInboundRABean()");
      if (this.inboundRABean == null) {
         this.inboundRABean = this.getRABean().getInboundResourceAdapter();
      }

      Debug.println((Object)this, (String)(".getInboundRABean() returning " + (this.inboundRABean != null ? "non-null" : "null")));
      return this.inboundRABean;
   }

   private MessageListenerBean[] getMessageListeners() throws ElementNotFoundException {
      Debug.enter(this, "getMessageListeners()");
      if (this.msgListeners == null) {
         Debug.println((Object)this, (String)".getMessageListeners() computing/caching MessageListeners");
         InboundResourceAdapterBean irab = this.getInboundRABean();
         if (irab == null) {
            Debug.println((Object)this, (String)".getMessageListeners() found no InboundResourceAdapter");
            String exMsg = Debug.getExceptionNoInboundRAElement();
            throw new ElementNotFoundException(exMsg);
         }

         MessageAdapterBean mab = irab.getMessageAdapter();
         String exMsg;
         if (mab == null) {
            Debug.println((Object)this, (String)".getMessageListeners() found no MessageAdapter");
            exMsg = Debug.getExceptionNoMessageAdapterElement();
            throw new ElementNotFoundException(exMsg);
         }

         this.msgListeners = mab.getMessageListeners();
         if (this.msgListeners == null) {
            Debug.println((Object)this, (String)".getMessageListeners() found no message listeners");
            exMsg = Debug.getExceptionNoMessageListenerElement();
            throw new IllegalStateException(exMsg);
         }
      }

      Debug.println((Object)this, (String)(".getMessageListeners() returning " + this.msgListeners.length + " listeners."));
      return this.msgListeners;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String[] getRADescriptions() {
      return this.connBean.getDescriptions();
   }

   public Hashtable getConnectionGroupConfigProperties(String connectionFactoryInterface) {
      Hashtable raProperties = this.getRAConnectionDefinitionProperties(connectionFactoryInterface);
      Hashtable overrideProperties = this.getOverriddenConfigProperties(raProperties, this.getWLDefaultOutboundProperties(), this.getWLConnectionDefinitionProperties(connectionFactoryInterface));
      return overrideProperties;
   }

   public String getConnectionGroupTransactionSupport(String connectionFactoryInterface) {
      String transSupport = null;
      String raTransactionSupport = this.getOutboundRABean() != null ? this.getOutboundRABean().getTransactionSupport() : null;
      String wlraDefaultTransactionSupport = this.getWLDefaultTransactionSupport();
      String wlraGroupTransactionSupport = this.getWLGroupTransactionSupport(connectionFactoryInterface);
      if (wlraGroupTransactionSupport != null && wlraGroupTransactionSupport.length() > 0) {
         transSupport = wlraGroupTransactionSupport;
      } else if (wlraDefaultTransactionSupport != null && wlraDefaultTransactionSupport.length() > 0) {
         transSupport = wlraDefaultTransactionSupport;
      } else {
         transSupport = raTransactionSupport;
      }

      return transSupport;
   }

   public String getConnectionGroupResAuth(String connectionFactoryInterface) {
      String resAuth = null;
      String wlraDefaultResAuth = this.getWLDefaultResAuth();
      String wlraGroupResAuth = this.getWLGroupResAuth(connectionFactoryInterface);
      if (wlraGroupResAuth != null && wlraGroupResAuth.length() > 0) {
         resAuth = wlraGroupResAuth;
      } else if (wlraDefaultResAuth != null && wlraDefaultResAuth.length() > 0) {
         resAuth = wlraDefaultResAuth;
      }

      return resAuth;
   }

   public boolean isConnectionGroupReauthenticationSupport(String connectionFactoryInterface) {
      boolean raReauthenticationSupport = this.getOutboundRABean() != null ? this.getOutboundRABean().isReauthenticationSupport() : false;
      String wlraDefaultReauth = this.getWLDefaultReauthenticationSupport();
      String wlraGroupReauth = this.getWLGroupReauthenticationSupport(connectionFactoryInterface);
      boolean reauth;
      if (wlraGroupReauth != null && wlraGroupReauth.length() > 0) {
         reauth = Boolean.valueOf(wlraGroupReauth);
      } else if (wlraDefaultReauth != null && wlraDefaultReauth.length() > 0) {
         reauth = Boolean.valueOf(wlraDefaultReauth);
      } else {
         reauth = raReauthenticationSupport;
      }

      return reauth;
   }

   public AuthMechInfo[] getConnectionGroupAuthenticationMechanisms(String connectionFactoryInterface) {
      AuthMechInfo[] authMechInfos = new AuthMechInfo[0];
      AuthMechInfo[] raMechanisms = this.getRAAuthenticationMechanisms();
      AuthMechInfo[] wlraDefaultMechanisms = this.getWLDefaultAuthenticationMechanisms();
      AuthMechInfo[] wlraGroupMechanisms = this.getWLGroupAuthenticationMechanisms(connectionFactoryInterface);
      if (wlraGroupMechanisms != null && wlraGroupMechanisms.length > 0) {
         authMechInfos = wlraGroupMechanisms;
      } else if (wlraDefaultMechanisms != null && wlraDefaultMechanisms.length > 0) {
         authMechInfos = wlraDefaultMechanisms;
      } else {
         authMechInfos = raMechanisms;
      }

      return authMechInfos;
   }

   public LoggingInfo getConnectionGroupLoggingProperties(String connectionFactoryInterface) {
      ConnectionDefinitionPropertiesBean defOutRADefnProps = this.wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties();
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connDefn = this.connectionDefinitionHelper.getWLConnectionDefinitionGroup(connectionFactoryInterface);
      ConnectionDefinitionPropertiesBean defGroupDefnProps = null;
      if (connDefn != null && connDefn.isDefaultConnectionPropertiesSet()) {
         defGroupDefnProps = connDefn.getDefaultConnectionProperties();
      }

      String logFilename = defOutRADefnProps.getLogging().getLogFilename();
      boolean loggingEnabled = defOutRADefnProps.getLogging().isLoggingEnabled();
      String rotationType = defOutRADefnProps.getLogging().getRotationType();
      String rotationTime = defOutRADefnProps.getLogging().getRotationTime();
      boolean numberOfFilesLimited = defOutRADefnProps.getLogging().isNumberOfFilesLimited();
      int fileCount = defOutRADefnProps.getLogging().getFileCount();
      int fileSizeLimit = defOutRADefnProps.getLogging().getFileSizeLimit();
      int fileTimeSpan = defOutRADefnProps.getLogging().getFileTimeSpan();
      boolean rotateLogOnStartup = defOutRADefnProps.getLogging().isRotateLogOnStartup();
      String logFileRotationDir = defOutRADefnProps.getLogging().getLogFileRotationDir();
      if (defGroupDefnProps != null && defGroupDefnProps.isLoggingSet()) {
         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("LogFilename")) {
            logFilename = defGroupDefnProps.getLogging().getLogFilename();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("LoggingEnabled")) {
            loggingEnabled = defGroupDefnProps.getLogging().isLoggingEnabled();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("RotationType")) {
            rotationType = defGroupDefnProps.getLogging().getRotationType();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("RotationTime")) {
            rotationTime = defGroupDefnProps.getLogging().getRotationTime();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("NumberOfFilesLimited")) {
            numberOfFilesLimited = defGroupDefnProps.getLogging().isNumberOfFilesLimited();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("FileCount")) {
            fileCount = defGroupDefnProps.getLogging().getFileCount();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("FileSizeLimit")) {
            fileSizeLimit = defGroupDefnProps.getLogging().getFileSizeLimit();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("FileTimeSpan")) {
            fileTimeSpan = defGroupDefnProps.getLogging().getFileTimeSpan();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("RotateLogOnStartup")) {
            rotateLogOnStartup = defGroupDefnProps.getLogging().isRotateLogOnStartup();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getLogging())).isSet("LogFileRotationDir")) {
            logFileRotationDir = defGroupDefnProps.getLogging().getLogFileRotationDir();
         }
      }

      return new LoggingInfoImpl(logFilename, loggingEnabled, rotationType, rotationTime, numberOfFilesLimited, fileCount, fileSizeLimit, fileTimeSpan, rotateLogOnStartup, logFileRotationDir);
   }

   public PoolInfo getConnectionGroupPoolProperties(String connectionFactoryInterface) {
      ConnectionDefinitionPropertiesBean defOutRADefnProps = null;
      defOutRADefnProps = this.wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties();
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connDefn = this.connectionDefinitionHelper.getWLConnectionDefinitionGroup(connectionFactoryInterface);
      ConnectionDefinitionPropertiesBean defGroupDefnProps = null;
      if (connDefn != null && connDefn.isDefaultConnectionPropertiesSet()) {
         defGroupDefnProps = connDefn.getDefaultConnectionProperties();
      }

      int initialCapacity = defOutRADefnProps.getPoolParams().getInitialCapacity();
      int maxCapacity = defOutRADefnProps.getPoolParams().getMaxCapacity();
      int capacityIncrement = defOutRADefnProps.getPoolParams().getCapacityIncrement();
      boolean shrinkingEnabled = defOutRADefnProps.getPoolParams().isShrinkingEnabled();
      int shrinkFrequencySeconds = defOutRADefnProps.getPoolParams().getShrinkFrequencySeconds();
      int highestNumWaiters = defOutRADefnProps.getPoolParams().getHighestNumWaiters();
      int highestNumUnavailable = defOutRADefnProps.getPoolParams().getHighestNumUnavailable();
      int connectionCreationRetryFrequencySeconds = defOutRADefnProps.getPoolParams().getConnectionCreationRetryFrequencySeconds();
      int connectionReserveTimeoutSeconds = defOutRADefnProps.getPoolParams().getConnectionReserveTimeoutSeconds();
      int testFrequencySeconds = defOutRADefnProps.getPoolParams().getTestFrequencySeconds();
      boolean testConnectionsOnCreate = defOutRADefnProps.getPoolParams().isTestConnectionsOnCreate();
      boolean testConnectionsOnRelease = defOutRADefnProps.getPoolParams().isTestConnectionsOnRelease();
      boolean testConnectionsOnReserve = defOutRADefnProps.getPoolParams().isTestConnectionsOnReserve();
      int profileHarvestFrequencySeconds = defOutRADefnProps.getPoolParams().getProfileHarvestFrequencySeconds();
      boolean ignoreInUseConnectionsEnabled = defOutRADefnProps.getPoolParams().isIgnoreInUseConnectionsEnabled();
      boolean matchConnectionsSupported = defOutRADefnProps.getPoolParams().isMatchConnectionsSupported();
      if (defGroupDefnProps != null && defGroupDefnProps.isPoolParamsSet()) {
         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("InitialCapacity")) {
            initialCapacity = defGroupDefnProps.getPoolParams().getInitialCapacity();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("MaxCapacity")) {
            maxCapacity = defGroupDefnProps.getPoolParams().getMaxCapacity();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("CapacityIncrement")) {
            capacityIncrement = defGroupDefnProps.getPoolParams().getCapacityIncrement();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("ShrinkingEnabled")) {
            shrinkingEnabled = defGroupDefnProps.getPoolParams().isShrinkingEnabled();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("ShrinkFrequencySeconds")) {
            shrinkFrequencySeconds = defGroupDefnProps.getPoolParams().getShrinkFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("HighestNumWaiters")) {
            highestNumWaiters = defGroupDefnProps.getPoolParams().getHighestNumWaiters();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("HighestNumUnavailable")) {
            highestNumUnavailable = defGroupDefnProps.getPoolParams().getHighestNumUnavailable();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("ConnectionCreationRetryFrequencySeconds")) {
            connectionCreationRetryFrequencySeconds = defGroupDefnProps.getPoolParams().getConnectionCreationRetryFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("ConnectionReserveTimeoutSeconds")) {
            connectionReserveTimeoutSeconds = defGroupDefnProps.getPoolParams().getConnectionReserveTimeoutSeconds();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("TestFrequencySeconds")) {
            testFrequencySeconds = defGroupDefnProps.getPoolParams().getTestFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("TestConnectionsOnCreate")) {
            testConnectionsOnCreate = defGroupDefnProps.getPoolParams().isTestConnectionsOnCreate();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("TestConnectionsOnRelease")) {
            testConnectionsOnRelease = defGroupDefnProps.getPoolParams().isTestConnectionsOnRelease();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("TestConnectionsOnReserve")) {
            testConnectionsOnReserve = defGroupDefnProps.getPoolParams().isTestConnectionsOnReserve();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("ProfileHarvestFrequencySeconds")) {
            profileHarvestFrequencySeconds = defGroupDefnProps.getPoolParams().getProfileHarvestFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("IgnoreInUseConnectionsEnabled")) {
            ignoreInUseConnectionsEnabled = defGroupDefnProps.getPoolParams().isIgnoreInUseConnectionsEnabled();
         }

         if (((DescriptorBean)((DescriptorBean)defGroupDefnProps.getPoolParams())).isSet("MatchConnectionsSupported")) {
            matchConnectionsSupported = defGroupDefnProps.getPoolParams().isMatchConnectionsSupported();
         }
      }

      return new PoolInfoImpl(initialCapacity, maxCapacity, capacityIncrement, shrinkingEnabled, shrinkFrequencySeconds, highestNumWaiters, highestNumUnavailable, connectionCreationRetryFrequencySeconds, connectionReserveTimeoutSeconds, testFrequencySeconds, testConnectionsOnCreate, testConnectionsOnRelease, testConnectionsOnReserve, profileHarvestFrequencySeconds, ignoreInUseConnectionsEnabled, matchConnectionsSupported);
   }

   public Hashtable getAdminObjectGroupProperties(String adminObjectInterface) {
      return this.getAdminObjectGroupProperties(adminObjectInterface, (String)null);
   }

   public Hashtable getAdminObjectGroupProperties(String adminObjectInterface, String adminObjectClass) {
      return this.adminObjectsHelper.getAdminObjectGroupProperties(adminObjectInterface, adminObjectClass);
   }

   public AdminObjInfo getAdminObject(String jndiName) {
      return this.adminObjectsHelper.getAdminObject(jndiName);
   }

   private String getWLDefaultTransactionSupport() {
      String transSupport = null;
      if (this.wlConnBean.isOutboundResourceAdapterSet() && this.wlConnBean.getOutboundResourceAdapter().isDefaultConnectionPropertiesSet()) {
         transSupport = this.wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties().getTransactionSupport();
      }

      return transSupport;
   }

   private String getWLDefaultResAuth() {
      String resAuth = null;
      if (this.wlConnBean.isOutboundResourceAdapterSet() && this.wlConnBean.getOutboundResourceAdapter().isDefaultConnectionPropertiesSet()) {
         resAuth = this.wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties().getResAuth();
      }

      return resAuth;
   }

   private String getWLDefaultReauthenticationSupport() {
      String reauth = null;
      if (this.wlConnBean.isOutboundResourceAdapterSet() && this.wlConnBean.getOutboundResourceAdapter().isDefaultConnectionPropertiesSet()) {
         ConnectionDefinitionPropertiesBean connDefnPropsBean = this.wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties();
         if (connDefnPropsBean.isSet("ReauthenticationSupport")) {
            reauth = String.valueOf(connDefnPropsBean.isReauthenticationSupport());
         }
      }

      return reauth;
   }

   private String getWLGroupTransactionSupport(String connectionFactoryInterface) {
      String transSupport = null;
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connDefn = this.connectionDefinitionHelper.getWLConnectionDefinitionGroup(connectionFactoryInterface);
      if (connDefn != null && connDefn.isDefaultConnectionPropertiesSet()) {
         transSupport = connDefn.getDefaultConnectionProperties().getTransactionSupport();
      }

      return transSupport;
   }

   private String getWLGroupResAuth(String connectionFactoryInterface) {
      String resAuth = null;
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connDefn = this.connectionDefinitionHelper.getWLConnectionDefinitionGroup(connectionFactoryInterface);
      if (connDefn != null && connDefn.isDefaultConnectionPropertiesSet()) {
         resAuth = connDefn.getDefaultConnectionProperties().getResAuth();
      }

      return resAuth;
   }

   private String getWLGroupReauthenticationSupport(String connectionFactoryInterface) {
      String reauth = null;
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connDefn = this.connectionDefinitionHelper.getWLConnectionDefinitionGroup(connectionFactoryInterface);
      if (connDefn != null && connDefn.isDefaultConnectionPropertiesSet() && connDefn.getDefaultConnectionProperties().isSet("ReauthenticationSupport")) {
         reauth = String.valueOf(connDefn.getDefaultConnectionProperties().isReauthenticationSupport());
      }

      return reauth;
   }

   private AuthMechInfo[] getRAAuthenticationMechanisms() {
      AuthMechInfo[] authMechInfos = new AuthMechInfo[0];
      if (this.getOutboundRABean() != null) {
         AuthenticationMechanismBean[] authMechBeans = this.getOutboundRABean().getAuthenticationMechanisms();
         authMechInfos = new AuthMechInfo[authMechBeans.length];

         for(int idx = 0; idx < authMechBeans.length; ++idx) {
            authMechInfos[idx] = new AuthMechInfoImpl(authMechBeans[idx]);
         }
      }

      return authMechInfos;
   }

   private AuthMechInfo[] getWLDefaultAuthenticationMechanisms() {
      AuthMechInfo[] authMechInfos = new AuthMechInfo[0];
      if (this.wlConnBean.isOutboundResourceAdapterSet() && this.wlConnBean.getOutboundResourceAdapter().isDefaultConnectionPropertiesSet()) {
         ConnectionDefinitionPropertiesBean connDefnPropsBean = this.wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties();
         AuthenticationMechanismBean[] authMechBeans = connDefnPropsBean.getAuthenticationMechanisms();
         authMechInfos = new AuthMechInfo[authMechBeans.length];

         for(int idx = 0; idx < authMechBeans.length; ++idx) {
            authMechInfos[idx] = new AuthMechInfoImpl(authMechBeans[idx]);
         }
      }

      return authMechInfos;
   }

   private AuthMechInfo[] getWLGroupAuthenticationMechanisms(String connectionFactoryInterface) {
      AuthMechInfo[] authMechInfos = new AuthMechInfo[0];
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connDefn = this.connectionDefinitionHelper.getWLConnectionDefinitionGroup(connectionFactoryInterface);
      if (connDefn != null && connDefn.isDefaultConnectionPropertiesSet()) {
         ConnectionDefinitionPropertiesBean connDefnPropsBean = connDefn.getDefaultConnectionProperties();
         AuthenticationMechanismBean[] authMechBeans = connDefnPropsBean.getAuthenticationMechanisms();
         authMechInfos = new AuthMechInfo[authMechBeans.length];

         for(int idx = 0; idx < authMechBeans.length; ++idx) {
            authMechInfos[idx] = new AuthMechInfoImpl(authMechBeans[idx]);
         }
      }

      return authMechInfos;
   }

   private Hashtable getOverriddenConfigProperties(Hashtable raProperties, Hashtable wlraDefaultProperties, Hashtable wlraGroupProperties) {
      Hashtable overrideProperties = new Hashtable();
      Iterator iter = raProperties.values().iterator();

      while(iter.hasNext()) {
         ConfigPropertyBean propBean = (ConfigPropertyBean)iter.next();
         if (propBean != null && propBean.getConfigPropertyName() != null && propBean.getConfigPropertyName().length() > 0) {
            weblogic.j2ee.descriptor.wl.ConfigPropertyBean defaultOverrideProperty = (weblogic.j2ee.descriptor.wl.ConfigPropertyBean)wlraDefaultProperties.get(propBean.getConfigPropertyName());
            weblogic.j2ee.descriptor.wl.ConfigPropertyBean groupOverrideProperty = (weblogic.j2ee.descriptor.wl.ConfigPropertyBean)wlraGroupProperties.get(propBean.getConfigPropertyName());
            ConfigPropInfoImpl configPropInfo;
            if (groupOverrideProperty != null) {
               configPropInfo = new ConfigPropInfoImpl(propBean, groupOverrideProperty.getValue());
               overrideProperties.put(groupOverrideProperty.getName(), configPropInfo);
            } else if (defaultOverrideProperty != null) {
               configPropInfo = new ConfigPropInfoImpl(propBean, defaultOverrideProperty.getValue());
               overrideProperties.put(defaultOverrideProperty.getName(), configPropInfo);
            } else {
               configPropInfo = new ConfigPropInfoImpl(propBean, propBean.getConfigPropertyValue());
               overrideProperties.put(propBean.getConfigPropertyName(), configPropInfo);
            }
         }
      }

      return overrideProperties;
   }

   private ConnectionDefinitionBean getRAConnectionDefinition(String connectionFactoryInterface) {
      ConnectionDefinitionBean connDefn = null;
      if (this.getOutboundRABean() != null) {
         ConnectionDefinitionBean[] connDefns = this.getOutboundRABean().getConnectionDefinitions();

         for(int idx = 0; connDefns != null && idx < connDefns.length; ++idx) {
            if (connDefns[idx].getConnectionFactoryInterface().equals(connectionFactoryInterface)) {
               connDefn = connDefns[idx];
               break;
            }
         }
      }

      return connDefn;
   }

   private Hashtable getRAConnectionDefinitionProperties(String connectionFactoryInterface) {
      Hashtable props = new Hashtable();
      ConnectionDefinitionBean connDefn = null;
      connDefn = this.getRAConnectionDefinition(connectionFactoryInterface);
      if (connDefn != null) {
         ConfigPropertyBean[] configProps = connDefn.getConfigProperties();

         for(int idx = 0; idx < configProps.length; ++idx) {
            if (configProps[idx].getConfigPropertyName() != null && configProps[idx].getConfigPropertyName().length() > 0) {
               props.put(configProps[idx].getConfigPropertyName(), configProps[idx]);
            }
         }
      }

      return props;
   }

   private Hashtable getWLDefaultOutboundProperties() {
      Hashtable props = new Hashtable();
      if (this.wlConnBean.isOutboundResourceAdapterSet() && this.wlConnBean.getOutboundResourceAdapter().isDefaultConnectionPropertiesSet()) {
         ConnectionDefinitionPropertiesBean connDefnPropsBean = this.wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties();
         if (connDefnPropsBean.isPropertiesSet() && connDefnPropsBean.getProperties().getProperties() != null) {
            weblogic.j2ee.descriptor.wl.ConfigPropertyBean[] configProps = connDefnPropsBean.getProperties().getProperties();

            for(int idx = 0; idx < configProps.length; ++idx) {
               if (configProps[idx].getName() != null && configProps[idx].getName().length() > 0) {
                  props.put(configProps[idx].getName(), configProps[idx]);
               }
            }
         }
      }

      return props;
   }

   private Hashtable getWLConnectionDefinitionProperties(String connectionFactoryInterface) {
      Hashtable props = new Hashtable();
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connDefn = null;
      connDefn = this.connectionDefinitionHelper.getWLConnectionDefinitionGroup(connectionFactoryInterface);
      if (connDefn != null) {
         ConnectionDefinitionPropertiesBean connDefnPropsBean = connDefn.getDefaultConnectionProperties();
         if (connDefnPropsBean != null && connDefnPropsBean.isPropertiesSet() && connDefnPropsBean.getProperties().getProperties() != null) {
            weblogic.j2ee.descriptor.wl.ConfigPropertyBean[] configProps = connDefnPropsBean.getProperties().getProperties();

            for(int idx = 0; idx < configProps.length; ++idx) {
               if (configProps[idx].getName() != null && configProps[idx].getName().length() > 0) {
                  props.put(configProps[idx].getName(), configProps[idx]);
               }
            }
         }
      }

      return props;
   }

   public void copyBaseRA(RAInfoImpl raInfoToCopyFrom) {
      this.baseRA = raInfoToCopyFrom.baseRA;
   }

   public ConnectorAPContext getConnectorAPContext() {
      return this.apContext;
   }

   public boolean isDeployAsAWhole() {
      return this.wlConnBean.isDeployAsAWhole();
   }

   public String[] getCriticalSubComponents() {
      return this.isDeployAsAWhole() ? null : (String[])ValidationMessage.CRITICAL_SUB_COMPONENTS.toArray(new String[ValidationMessage.CRITICAL_SUB_COMPONENTS.size()]);
   }

   public Map getSubComponentsChild2ParentMap() {
      return ValidationMessage.SUB_COMPONENTS_CHILD2PARENT_MAP;
   }

   public ValidationMessage getValidationMessage() {
      return this.validationMessage;
   }

   public void setValidationMessage(ValidationMessage message) {
      this.validationMessage = message;
   }

   public AdminObjInfo buildAdminObjectInfo(AdministeredObjectBean aoBean, UniversalResourceKey key) throws ResourceException {
      String className = aoBean.getClassName();
      String interfaceName = aoBean.getInterfaceName().trim();
      AdminObjectBean adminObjBean = null;
      List beanList = this.adminObjectsHelper.getHelper().getMappingAdminObjectBeans(interfaceName, className);
      if (beanList.size() == 1) {
         adminObjBean = (AdminObjectBean)beanList.get(0);
         if (interfaceName.length() == 0) {
            interfaceName = adminObjBean.getAdminObjectInterface();
         }

         AdminObjectGroupBean adminGroupBean = this.adminObjectsHelper.getOrCreatAdminObjectGroupBean(className, interfaceName);
         AdminObjectInstanceBean adminInstanceBean = this.adminObjectsHelper.createDummyAdminObjectInstanceBean(aoBean);
         AdminObjInfo adminInfo = new AdminObjInfoImpl(this.connBean, adminObjBean, adminInstanceBean, this.wlConnBean, adminGroupBean);
         return adminInfo;
      } else {
         String exMsg;
         if (beanList.size() > 1) {
            exMsg = ConnectorLogger.getExceptionAdminObjectInterfaceAmbiguously(className, aoBean.getResourceAdapter(), key.getDefApp(), key.getDefModule(), key.getDefComp(), key.getJndi());
            throw new ResourceException(exMsg);
         } else {
            exMsg = ConnectorLogger.getExceptionNoMatchedAdminObjectDefinition(aoBean.getResourceAdapter(), className, interfaceName, key.getDefApp(), key.getDefModule(), key.getDefComp(), key.getJndi());
            throw new ResourceException(exMsg);
         }
      }
   }

   public OutboundInfo buildOutboundInfo(ConnectionFactoryResourceBean cfBean, UniversalResourceKey key) throws ResourceException {
      ConnectionDefinitionBean connDefn = this.connectionDefinitionHelper.getConnectionDefinitionBean(cfBean.getInterfaceName());
      if (connDefn == null) {
         String exMsg = ConnectorLogger.getExceptionNoMatchedConnectionDefinition(cfBean.getInterfaceName(), cfBean.getResourceAdapter(), key.getDefApp(), key.getDefModule(), key.getDefComp(), key.getJndi());
         throw new ResourceException(exMsg);
      } else {
         weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean wlConnDefnGroup = this.connectionDefinitionHelper.getOrCreateWLConnectionDefinition(cfBean.getInterfaceName());
         ConnectionInstanceBean wlConnInstance = this.connectionDefinitionHelper.createDummyConnectionInstanceBean(cfBean);
         OutboundInfo result = new OutboundInfoImpl(this, this.connBean, this.wlConnBean, connDefn, wlConnDefnGroup, wlConnInstance);
         return result;
      }
   }

   public boolean supportMessageListenerType(String messageListenerType) {
      try {
         Iterator var2 = this.getInboundInfos().iterator();

         while(var2.hasNext()) {
            InboundInfo inbound = (InboundInfo)var2.next();
            if (inbound.getMsgType().equals(messageListenerType)) {
               return true;
            }
         }
      } catch (ElementNotFoundException var4) {
      }

      return false;
   }

   public boolean isCustomizedClassloading() {
      return this.customizedClassloading;
   }
}
