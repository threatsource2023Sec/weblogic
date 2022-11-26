package weblogic.connector.external.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.RAInfo;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConnectionInstanceBean;
import weblogic.j2ee.descriptor.wl.LoggingBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorExtensionBean;

public class OutboundInfoImpl implements OutboundInfo {
   private RAInfo raInfo = null;
   private ConnectorBean connBean = null;
   private WeblogicConnectorBean wlConnBean = null;
   private ConnectionDefinitionBean connDefn = null;
   private weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean wlConnDefnGroup = null;
   private OutboundInfo baseOutboundInfo = null;
   private ConnectionDefinitionPropertiesBean defOutRADefnProps = null;
   private ConnectionDefinitionPropertiesBean defGroupDefnProps = null;
   private ConnectionInstanceBean wlConnInstance = null;
   private ConnectionDefinitionPropertiesBean connInstanceDefnProps = null;
   private ResourceAdapterBean raBean = null;
   private OutboundResourceAdapterBean outboundRABean = null;
   String jndiName;
   List authenticationMechanisms;
   private PropertyNameNormalizer propertyNameNormalizer;

   public OutboundInfoImpl(RAInfo raInfo, ConnectorBean connBean, WeblogicConnectorBean wlConnBean, ConnectionDefinitionBean connDefn, weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean wlConnDefnGroup, ConnectionInstanceBean wlConnInstance) {
      this.raInfo = raInfo;
      this.connBean = connBean;
      this.wlConnBean = wlConnBean;
      this.connDefn = connDefn;
      this.wlConnDefnGroup = wlConnDefnGroup;
      this.wlConnInstance = wlConnInstance;
      this.propertyNameNormalizer = raInfo.getPropertyNameNormalizer();
      if (!wlConnBean.isOutboundResourceAdapterSet()) {
         Debug.throwAssertionError("wlConnBean.isOutboundResourceAdapterSet() == false.");
      }

      this.defOutRADefnProps = wlConnBean.getOutboundResourceAdapter().getDefaultConnectionProperties();
      this.defGroupDefnProps = wlConnDefnGroup.getDefaultConnectionProperties();
      this.connInstanceDefnProps = wlConnInstance.getConnectionProperties();
      this.getJndiName();
      this.getAuthenticationMechanisms();
   }

   private List convertAuthMechArrayToVector(AuthenticationMechanismBean[] authMechanismBeans) {
      List authMechanisms = new ArrayList(authMechanismBeans.length);

      for(int idx = 0; idx < authMechanismBeans.length; ++idx) {
         AuthMechInfoImpl info = new AuthMechInfoImpl(authMechanismBeans[idx]);
         authMechanisms.add(info);
      }

      return authMechanisms;
   }

   public void setBaseOutboundInfo(OutboundInfo anOutboundInfo) {
      this.baseOutboundInfo = anOutboundInfo;
   }

   public String getRADescription() {
      return (this.raInfo.getRADescription() == null || this.raInfo.getRADescription().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getRADescription() : this.raInfo.getRADescription();
   }

   public String getDisplayName() {
      return (this.raInfo.getDisplayName() == null || this.raInfo.getDisplayName().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getDisplayName() : this.raInfo.getDisplayName();
   }

   public String getVendorName() {
      return (this.raInfo.getVendorName() == null || this.raInfo.getVendorName().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getVendorName() : this.raInfo.getVendorName();
   }

   public String getEisType() {
      return (this.raInfo.getEisType() == null || this.raInfo.getEisType().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getEisType() : this.raInfo.getEisType();
   }

   public String getTransactionSupport() {
      String transSupport = null;
      if (this.connInstanceDefnProps.isSet("TransactionSupport") && this.baseOutboundInfo != null) {
         transSupport = this.baseOutboundInfo.getTransactionSupport();
      } else if (this.connInstanceDefnProps.isSet("TransactionSupport")) {
         transSupport = this.connInstanceDefnProps.getTransactionSupport();
      } else if (this.defGroupDefnProps.isSet("TransactionSupport")) {
         transSupport = this.defGroupDefnProps.getTransactionSupport();
      } else if (this.defOutRADefnProps.isSet("TransactionSupport")) {
         transSupport = this.defOutRADefnProps.getTransactionSupport();
      } else {
         transSupport = this.getOutboundRABean().getTransactionSupport();
      }

      return transSupport;
   }

   public List getAuthenticationMechanisms() {
      if (this.authenticationMechanisms != null) {
         return this.authenticationMechanisms;
      } else if ((this.connInstanceDefnProps.getAuthenticationMechanisms() == null || this.connInstanceDefnProps.getAuthenticationMechanisms().length == 0) && this.baseOutboundInfo != null) {
         return this.baseOutboundInfo.getAuthenticationMechanisms();
      } else {
         AuthenticationMechanismBean[] authMechs = this.getOutboundRABean().getAuthenticationMechanisms();
         if (this.defOutRADefnProps.getAuthenticationMechanisms() != null && this.defOutRADefnProps.getAuthenticationMechanisms().length > 0) {
            authMechs = this.defOutRADefnProps.getAuthenticationMechanisms();
         }

         if (this.defGroupDefnProps.getAuthenticationMechanisms() != null && this.defGroupDefnProps.getAuthenticationMechanisms().length > 0) {
            authMechs = this.defGroupDefnProps.getAuthenticationMechanisms();
         }

         if (this.connInstanceDefnProps.getAuthenticationMechanisms() != null && this.connInstanceDefnProps.getAuthenticationMechanisms().length > 0) {
            authMechs = this.connInstanceDefnProps.getAuthenticationMechanisms();
         }

         this.authenticationMechanisms = authMechs == null ? null : this.convertAuthMechArrayToVector(authMechs);
         return this.authenticationMechanisms;
      }
   }

   public boolean getReauthenticationSupport() {
      if (!((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps)).isSet("ReauthenticationSupport") && this.baseOutboundInfo != null) {
         return this.baseOutboundInfo.getReauthenticationSupport();
      } else {
         boolean reauthSupport = this.getOutboundRABean().isReauthenticationSupport();
         if (((DescriptorBean)((DescriptorBean)this.defOutRADefnProps)).isSet("ReauthenticationSupport")) {
            reauthSupport = this.defOutRADefnProps.isReauthenticationSupport();
         }

         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps)).isSet("ReauthenticationSupport")) {
            reauthSupport = this.defGroupDefnProps.isReauthenticationSupport();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps)).isSet("ReauthenticationSupport")) {
            reauthSupport = this.connInstanceDefnProps.isReauthenticationSupport();
         }

         return reauthSupport;
      }
   }

   public String getResAuth() {
      if (!((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps)).isSet("ResAuth") && this.baseOutboundInfo != null) {
         return this.baseOutboundInfo.getResAuth();
      } else {
         String resAuth = this.defOutRADefnProps.getResAuth();
         if (((DescriptorBean)this.defGroupDefnProps).isSet("ResAuth")) {
            resAuth = this.defGroupDefnProps.getResAuth();
         }

         if (((DescriptorBean)this.connInstanceDefnProps).isSet("ResAuth")) {
            resAuth = this.connInstanceDefnProps.getResAuth();
         }

         return resAuth;
      }
   }

   private void checkConnDefn() throws RAOutboundException {
      if (this.connDefn == null) {
         throw getOutBadCFInterfaceEx(this.getCFInterface());
      }
   }

   public static RAOutboundException getOutBadCFInterfaceEx(String CFInterface) throws RAOutboundException {
      return new RAOutboundException("the OutboundInfo with connection-factory-interface [" + CFInterface + "] does not link to a ConnectionDefinitionBean since the connnection-factory-interface of this OutboundInfo is invalid");
   }

   public String getMCFClass() throws RAOutboundException {
      this.checkConnDefn();
      return (this.connDefn.getManagedConnectionFactoryClass() == null || this.connDefn.getManagedConnectionFactoryClass().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getMCFClass() : this.connDefn.getManagedConnectionFactoryClass();
   }

   public Hashtable getMCFProps() throws RAOutboundException {
      if (!this.connInstanceDefnProps.isPropertiesSet() && this.baseOutboundInfo != null) {
         return this.baseOutboundInfo.getMCFProps();
      } else {
         this.checkConnDefn();
         ConfigPropertyBean[] raConfigPropBeans = this.connDefn.getConfigProperties();
         ConfigPropertiesBean[] wlsConfigProperties = new ConfigPropertiesBean[]{this.wlConnBean.getProperties(), !this.defOutRADefnProps.isPropertiesSet() ? null : this.defOutRADefnProps.getProperties(), !this.defGroupDefnProps.isPropertiesSet() ? null : this.defGroupDefnProps.getProperties(), !this.connInstanceDefnProps.isPropertiesSet() ? null : this.connInstanceDefnProps.getProperties()};
         return DDLayerUtils.mergeConfigProperties(raConfigPropBeans, wlsConfigProperties, this.propertyNameNormalizer);
      }
   }

   public String getCFInterface() {
      return (this.wlConnDefnGroup.getConnectionFactoryInterface() == null || this.wlConnDefnGroup.getConnectionFactoryInterface().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getCFInterface() : this.wlConnDefnGroup.getConnectionFactoryInterface();
   }

   public String getCFImpl() throws RAOutboundException {
      this.checkConnDefn();
      return (this.connDefn.getConnectionFactoryImplClass() == null || this.connDefn.getConnectionFactoryImplClass().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getCFImpl() : this.connDefn.getConnectionFactoryImplClass();
   }

   public String getConnectionInterface() throws RAOutboundException {
      this.checkConnDefn();
      return (this.connDefn.getConnectionInterface() == null || this.connDefn.getConnectionInterface().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getConnectionInterface() : this.connDefn.getConnectionInterface();
   }

   public String getConnectionImpl() throws RAOutboundException {
      this.checkConnDefn();
      return (this.connDefn.getConnectionImplClass() == null || this.connDefn.getConnectionImplClass().length() == 0) && this.baseOutboundInfo != null ? this.baseOutboundInfo.getConnectionImpl() : this.connDefn.getConnectionImplClass();
   }

   public String getConnectionFactoryName() {
      String connectionFactoryName = null;
      if (this.isPreDiabloRA() && ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef() != null) {
         connectionFactoryName = ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef().getConnectionFactoryName();
      }

      return connectionFactoryName;
   }

   public String getJndiName() {
      if (this.jndiName != null) {
         return this.jndiName;
      } else {
         if (this.wlConnInstance != null) {
            this.jndiName = this.wlConnInstance.getJNDIName();
         }

         return this.jndiName;
      }
   }

   public int getInitialCapacity() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getInitialCapacity();
      } else {
         int initCapacity = this.defOutRADefnProps.getPoolParams().getInitialCapacity();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("InitialCapacity")) {
            initCapacity = this.defGroupDefnProps.getPoolParams().getInitialCapacity();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("InitialCapacity")) {
            initCapacity = this.connInstanceDefnProps.getPoolParams().getInitialCapacity();
         }

         return initCapacity;
      }
   }

   public int getMaxCapacity() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getMaxCapacity();
      } else {
         int maxCapacity = this.defOutRADefnProps.getPoolParams().getMaxCapacity();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("MaxCapacity")) {
            maxCapacity = this.defGroupDefnProps.getPoolParams().getMaxCapacity();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("MaxCapacity")) {
            maxCapacity = this.connInstanceDefnProps.getPoolParams().getMaxCapacity();
         }

         return maxCapacity;
      }
   }

   public int getCapacityIncrement() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getCapacityIncrement();
      } else {
         int capacityIncrement = this.defOutRADefnProps.getPoolParams().getCapacityIncrement();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("CapacityIncrement")) {
            capacityIncrement = this.defGroupDefnProps.getPoolParams().getCapacityIncrement();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("CapacityIncrement")) {
            capacityIncrement = this.connInstanceDefnProps.getPoolParams().getCapacityIncrement();
         }

         return capacityIncrement;
      }
   }

   public boolean isShrinkingEnabled() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.isShrinkingEnabled();
      } else {
         boolean shrinkingEnabled = this.defOutRADefnProps.getPoolParams().isShrinkingEnabled();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("ShrinkingEnabled")) {
            shrinkingEnabled = this.defGroupDefnProps.getPoolParams().isShrinkingEnabled();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("ShrinkingEnabled")) {
            shrinkingEnabled = this.connInstanceDefnProps.getPoolParams().isShrinkingEnabled();
         }

         return shrinkingEnabled;
      }
   }

   public int getShrinkFrequencySeconds() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getShrinkFrequencySeconds();
      } else {
         int shrinkFrequencySeconds = this.defOutRADefnProps.getPoolParams().getShrinkFrequencySeconds();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("ShrinkFrequencySeconds")) {
            shrinkFrequencySeconds = this.defGroupDefnProps.getPoolParams().getShrinkFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("ShrinkFrequencySeconds")) {
            shrinkFrequencySeconds = this.connInstanceDefnProps.getPoolParams().getShrinkFrequencySeconds();
         }

         return shrinkFrequencySeconds;
      }
   }

   public int getInactiveConnectionTimeoutSeconds() {
      int inactiveConnectionTimeoutSeconds = 0;
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getInactiveConnectionTimeoutSeconds();
      } else {
         if (this.isPreDiabloRA() && ((WeblogicConnectorExtensionBean)this.wlConnBean).getProxy() != null) {
            inactiveConnectionTimeoutSeconds = ((WeblogicConnectorExtensionBean)this.wlConnBean).getProxy().getInactiveConnectionTimeoutSeconds();
         }

         return inactiveConnectionTimeoutSeconds;
      }
   }

   public boolean getConnectionProfilingEnabled() {
      boolean connectionProfilingEnabled = false;
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getConnectionProfilingEnabled();
      } else {
         if (this.isPreDiabloRA() && ((WeblogicConnectorExtensionBean)this.wlConnBean).getProxy() != null) {
            connectionProfilingEnabled = ((WeblogicConnectorExtensionBean)this.wlConnBean).getProxy().isConnectionProfilingEnabled();
         }

         return connectionProfilingEnabled;
      }
   }

   public int getHighestNumWaiters() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getHighestNumWaiters();
      } else {
         int highestNumWaiters = this.defOutRADefnProps.getPoolParams().getHighestNumWaiters();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("HighestNumWaiters")) {
            highestNumWaiters = this.defGroupDefnProps.getPoolParams().getHighestNumWaiters();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("HighestNumWaiters")) {
            highestNumWaiters = this.connInstanceDefnProps.getPoolParams().getHighestNumWaiters();
         }

         return highestNumWaiters;
      }
   }

   public int getHighestNumUnavailable() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getHighestNumUnavailable();
      } else {
         int highestNumUnavailable = this.defOutRADefnProps.getPoolParams().getHighestNumUnavailable();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("HighestNumUnavailable")) {
            highestNumUnavailable = this.defGroupDefnProps.getPoolParams().getHighestNumUnavailable();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("HighestNumUnavailable")) {
            highestNumUnavailable = this.connInstanceDefnProps.getPoolParams().getHighestNumUnavailable();
         }

         return highestNumUnavailable;
      }
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getConnectionCreationRetryFrequencySeconds();
      } else {
         int connectionCreationRetryFrequencySeconds = this.defOutRADefnProps.getPoolParams().getConnectionCreationRetryFrequencySeconds();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("ConnectionCreationRetryFrequencySeconds")) {
            connectionCreationRetryFrequencySeconds = this.defGroupDefnProps.getPoolParams().getConnectionCreationRetryFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("ConnectionCreationRetryFrequencySeconds")) {
            connectionCreationRetryFrequencySeconds = this.connInstanceDefnProps.getPoolParams().getConnectionCreationRetryFrequencySeconds();
         }

         return connectionCreationRetryFrequencySeconds;
      }
   }

   public int getConnectionReserveTimeoutSeconds() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getConnectionReserveTimeoutSeconds();
      } else {
         int connectionReserveTimeoutSeconds = this.defOutRADefnProps.getPoolParams().getConnectionReserveTimeoutSeconds();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("ConnectionReserveTimeoutSeconds")) {
            connectionReserveTimeoutSeconds = this.defGroupDefnProps.getPoolParams().getConnectionReserveTimeoutSeconds();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("ConnectionReserveTimeoutSeconds")) {
            connectionReserveTimeoutSeconds = this.connInstanceDefnProps.getPoolParams().getConnectionReserveTimeoutSeconds();
         }

         return connectionReserveTimeoutSeconds;
      }
   }

   public int getTestFrequencySeconds() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getTestFrequencySeconds();
      } else {
         int testFrequencySeconds = this.defOutRADefnProps.getPoolParams().getTestFrequencySeconds();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("TestFrequencySeconds")) {
            testFrequencySeconds = this.defGroupDefnProps.getPoolParams().getTestFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("TestFrequencySeconds")) {
            testFrequencySeconds = this.connInstanceDefnProps.getPoolParams().getTestFrequencySeconds();
         }

         return testFrequencySeconds;
      }
   }

   public boolean isTestConnectionsOnCreate() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.isTestConnectionsOnCreate();
      } else {
         boolean testConnectionsOnCreate = this.defOutRADefnProps.getPoolParams().isTestConnectionsOnCreate();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("TestConnectionsOnCreate")) {
            testConnectionsOnCreate = this.defGroupDefnProps.getPoolParams().isTestConnectionsOnCreate();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("TestConnectionsOnCreate")) {
            testConnectionsOnCreate = this.connInstanceDefnProps.getPoolParams().isTestConnectionsOnCreate();
         }

         return testConnectionsOnCreate;
      }
   }

   public boolean isTestConnectionsOnRelease() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.isTestConnectionsOnRelease();
      } else {
         boolean testConnectionsOnRelease = this.defOutRADefnProps.getPoolParams().isTestConnectionsOnRelease();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("TestConnectionsOnRelease")) {
            testConnectionsOnRelease = this.defGroupDefnProps.getPoolParams().isTestConnectionsOnRelease();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("TestConnectionsOnRelease")) {
            testConnectionsOnRelease = this.connInstanceDefnProps.getPoolParams().isTestConnectionsOnRelease();
         }

         return testConnectionsOnRelease;
      }
   }

   public boolean isTestConnectionsOnReserve() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.isTestConnectionsOnReserve();
      } else {
         boolean testConnectionsOnReserve = this.defOutRADefnProps.getPoolParams().isTestConnectionsOnReserve();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("TestConnectionsOnReserve")) {
            testConnectionsOnReserve = this.defGroupDefnProps.getPoolParams().isTestConnectionsOnReserve();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("TestConnectionsOnReserve")) {
            testConnectionsOnReserve = this.connInstanceDefnProps.getPoolParams().isTestConnectionsOnReserve();
         }

         return testConnectionsOnReserve;
      }
   }

   public Boolean getUseConnectionProxies() {
      Boolean useConnectionProxies = null;
      if (!this.isPreDiabloRA()) {
         useConnectionProxies = new Boolean(false);
      } else if (this.isLinkRefOverridingPoolParams()) {
         useConnectionProxies = this.baseOutboundInfo.getUseConnectionProxies();
      } else if (((WeblogicConnectorExtensionBean)this.wlConnBean).getProxy() != null) {
         String useConnectionProxiesString = ((WeblogicConnectorExtensionBean)this.wlConnBean).getProxy().getUseConnectionProxies();
         if (useConnectionProxiesString != null) {
            useConnectionProxies = new Boolean(useConnectionProxiesString);
         }
      }

      return useConnectionProxies;
   }

   public RAInfo getRAInfo() {
      return this.raInfo;
   }

   public String getRaLinkRef() {
      String rALinkRef = null;
      if (this.isPreDiabloRA() && ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef() != null) {
         rALinkRef = ((WeblogicConnectorExtensionBean)this.wlConnBean).getLinkRef().getRaLinkRef();
      }

      return rALinkRef;
   }

   public LoggingBean getLoggingBean() {
      return this.connInstanceDefnProps.getLogging();
   }

   public boolean isMatchConnectionsSupported() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.isMatchConnectionsSupported();
      } else {
         boolean matchConnectionsSupported = this.defOutRADefnProps.getPoolParams().isMatchConnectionsSupported();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("MatchConnectionsSupported")) {
            matchConnectionsSupported = this.defGroupDefnProps.getPoolParams().isMatchConnectionsSupported();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("MatchConnectionsSupported")) {
            matchConnectionsSupported = this.connInstanceDefnProps.getPoolParams().isMatchConnectionsSupported();
         }

         return matchConnectionsSupported;
      }
   }

   public boolean isUseFirstAvailable() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.isUseFirstAvailable();
      } else {
         boolean isUseFirstAvailable = this.defOutRADefnProps.getPoolParams().isUseFirstAvailable();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("UseFirstAvailable")) {
            isUseFirstAvailable = this.defGroupDefnProps.getPoolParams().isUseFirstAvailable();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("UseFirstAvailable")) {
            isUseFirstAvailable = this.connInstanceDefnProps.getPoolParams().isUseFirstAvailable();
         }

         return isUseFirstAvailable;
      }
   }

   private boolean isLinkRefOverridingPoolParams() {
      if (this.baseOutboundInfo == null) {
         return false;
      } else {
         return this.connInstanceDefnProps.isPoolParamsSet() && this.connInstanceDefnProps.getPoolParams().getMaxCapacity() == 0;
      }
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

   public int getProfileHarvestFrequencySeconds() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.getProfileHarvestFrequencySeconds();
      } else {
         int profileHarvestFrequencySeconds = this.defOutRADefnProps.getPoolParams().getProfileHarvestFrequencySeconds();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("ProfileHarvestFrequencySeconds")) {
            profileHarvestFrequencySeconds = this.defGroupDefnProps.getPoolParams().getProfileHarvestFrequencySeconds();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("ProfileHarvestFrequencySeconds")) {
            profileHarvestFrequencySeconds = this.connInstanceDefnProps.getPoolParams().getProfileHarvestFrequencySeconds();
         }

         return profileHarvestFrequencySeconds;
      }
   }

   public boolean isIgnoreInUseConnectionsEnabled() {
      if (this.isLinkRefOverridingPoolParams()) {
         return this.baseOutboundInfo.isIgnoreInUseConnectionsEnabled();
      } else {
         boolean ignoreInUseConnectionsEnabled = this.defOutRADefnProps.getPoolParams().isIgnoreInUseConnectionsEnabled();
         if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getPoolParams())).isSet("IgnoreInUseConnectionsEnabled")) {
            ignoreInUseConnectionsEnabled = this.defGroupDefnProps.getPoolParams().isIgnoreInUseConnectionsEnabled();
         }

         if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getPoolParams())).isSet("IgnoreInUseConnectionsEnabled")) {
            ignoreInUseConnectionsEnabled = this.connInstanceDefnProps.getPoolParams().isIgnoreInUseConnectionsEnabled();
         }

         return ignoreInUseConnectionsEnabled;
      }
   }

   public String getKey() {
      return this.getJndiName();
   }

   public String getLogFilename() {
      String logFilename = this.defOutRADefnProps.getLogging().getLogFilename();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("LogFilename")) {
         logFilename = this.defGroupDefnProps.getLogging().getLogFilename();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("LogFilename")) {
         logFilename = this.connInstanceDefnProps.getLogging().getLogFilename();
      }

      return logFilename;
   }

   public boolean isLoggingEnabled() {
      boolean isLoggingEnabled = this.defOutRADefnProps.getLogging().isLoggingEnabled();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("LoggingEnabled")) {
         isLoggingEnabled = this.defGroupDefnProps.getLogging().isLoggingEnabled();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("LoggingEnabled")) {
         isLoggingEnabled = this.connInstanceDefnProps.getLogging().isLoggingEnabled();
      }

      return isLoggingEnabled;
   }

   public String getRotationType() {
      String rotationType = this.defOutRADefnProps.getLogging().getRotationType();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("RotationType")) {
         rotationType = this.defGroupDefnProps.getLogging().getRotationType();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("RotationType")) {
         rotationType = this.connInstanceDefnProps.getLogging().getRotationType();
      }

      return rotationType;
   }

   public String getRotationTime() {
      String rotationTime = this.defOutRADefnProps.getLogging().getRotationTime();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("RotationTime")) {
         rotationTime = this.defGroupDefnProps.getLogging().getRotationTime();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("RotationTime")) {
         rotationTime = this.connInstanceDefnProps.getLogging().getRotationTime();
      }

      return rotationTime;
   }

   public boolean isNumberOfFilesLimited() {
      boolean isNumberOfFilesLimited = this.defOutRADefnProps.getLogging().isNumberOfFilesLimited();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("NumberOfFilesLimited")) {
         isNumberOfFilesLimited = this.defGroupDefnProps.getLogging().isNumberOfFilesLimited();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("NumberOfFilesLimited")) {
         isNumberOfFilesLimited = this.connInstanceDefnProps.getLogging().isNumberOfFilesLimited();
      }

      return isNumberOfFilesLimited;
   }

   public int getFileCount() {
      int fileCount = this.defOutRADefnProps.getLogging().getFileCount();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("FileCount")) {
         fileCount = this.defGroupDefnProps.getLogging().getFileCount();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("FileCount")) {
         fileCount = this.connInstanceDefnProps.getLogging().getFileCount();
      }

      return fileCount;
   }

   public int getFileSizeLimit() {
      int fileSizeLimit = this.defOutRADefnProps.getLogging().getFileSizeLimit();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("FileSizeLimit")) {
         fileSizeLimit = this.defGroupDefnProps.getLogging().getFileSizeLimit();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("FileSizeLimit")) {
         fileSizeLimit = this.connInstanceDefnProps.getLogging().getFileSizeLimit();
      }

      return fileSizeLimit;
   }

   public int getFileTimeSpan() {
      int fileTimeSpan = this.defOutRADefnProps.getLogging().getFileTimeSpan();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("FileTimeSpan")) {
         fileTimeSpan = this.defGroupDefnProps.getLogging().getFileTimeSpan();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("FileTimeSpan")) {
         fileTimeSpan = this.connInstanceDefnProps.getLogging().getFileTimeSpan();
      }

      return fileTimeSpan;
   }

   public boolean isRotateLogOnStartup() {
      boolean isRotateLogOnStartup = this.defOutRADefnProps.getLogging().isRotateLogOnStartup();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("RotateLogOnStartup")) {
         isRotateLogOnStartup = this.defGroupDefnProps.getLogging().isRotateLogOnStartup();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("RotateLogOnStartup")) {
         isRotateLogOnStartup = this.connInstanceDefnProps.getLogging().isRotateLogOnStartup();
      }

      return isRotateLogOnStartup;
   }

   public String getLogFileRotationDir() {
      String logFileRotationDir = this.defOutRADefnProps.getLogging().getLogFileRotationDir();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("LogFileRotationDir")) {
         logFileRotationDir = this.defGroupDefnProps.getLogging().getLogFileRotationDir();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("LogFileRotationDir")) {
         logFileRotationDir = this.connInstanceDefnProps.getLogging().getLogFileRotationDir();
      }

      return logFileRotationDir;
   }

   public String getDateFormatPattern() {
      String dateFormatPattern = this.defOutRADefnProps.getLogging().getDateFormatPattern();
      if (((DescriptorBean)((DescriptorBean)this.defGroupDefnProps.getLogging())).isSet("DateFormatPattern")) {
         dateFormatPattern = this.defGroupDefnProps.getLogging().getDateFormatPattern();
      }

      if (((DescriptorBean)((DescriptorBean)this.connInstanceDefnProps.getLogging())).isSet("DateFormatPattern")) {
         dateFormatPattern = this.connInstanceDefnProps.getLogging().getDateFormatPattern();
      }

      return dateFormatPattern;
   }

   public String getDescription() {
      return this.wlConnInstance.getDescription();
   }
}
