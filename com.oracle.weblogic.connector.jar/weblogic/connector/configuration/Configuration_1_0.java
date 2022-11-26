package weblogic.connector.configuration;

import com.bea.connector.monitoring1Dot0.ActivationspecDocument;
import com.bea.connector.monitoring1Dot0.AdminObjectGroupDocument;
import com.bea.connector.monitoring1Dot0.AdminObjectInstanceDocument;
import com.bea.connector.monitoring1Dot0.AdminObjectsDocument;
import com.bea.connector.monitoring1Dot0.AnonPrincipalCallerType;
import com.bea.connector.monitoring1Dot0.AnonPrincipalType;
import com.bea.connector.monitoring1Dot0.AuthenticationMechanismType;
import com.bea.connector.monitoring1Dot0.CapacityType;
import com.bea.connector.monitoring1Dot0.ConfigPropertiesType;
import com.bea.connector.monitoring1Dot0.ConfigPropertyType;
import com.bea.connector.monitoring1Dot0.ConnectionInstanceDocument;
import com.bea.connector.monitoring1Dot0.ConnectionPoolParamsType;
import com.bea.connector.monitoring1Dot0.ConnectorDocument;
import com.bea.connector.monitoring1Dot0.ConnectorWorkManagerType;
import com.bea.connector.monitoring1Dot0.ContextCaseType;
import com.bea.connector.monitoring1Dot0.ContextRequestClassType;
import com.bea.connector.monitoring1Dot0.FairShareRequestClassType;
import com.bea.connector.monitoring1Dot0.InboundCallerPrincipalMappingType;
import com.bea.connector.monitoring1Dot0.InboundDocument;
import com.bea.connector.monitoring1Dot0.InboundGroupPrincipalMappingType;
import com.bea.connector.monitoring1Dot0.LicenseDocument;
import com.bea.connector.monitoring1Dot0.LoggingType;
import com.bea.connector.monitoring1Dot0.MaxThreadsConstraintType;
import com.bea.connector.monitoring1Dot0.MessagelistenerDocument;
import com.bea.connector.monitoring1Dot0.MinThreadsConstraintType;
import com.bea.connector.monitoring1Dot0.OutboundDocument;
import com.bea.connector.monitoring1Dot0.OutboundGroupDocument;
import com.bea.connector.monitoring1Dot0.RequiredConfigPropertyDocument;
import com.bea.connector.monitoring1Dot0.ResourceAdapterSecurityType;
import com.bea.connector.monitoring1Dot0.ResponseTimeRequestClassType;
import com.bea.connector.monitoring1Dot0.SecurityPermissionType;
import com.bea.connector.monitoring1Dot0.SecurityWorkContextType;
import com.bea.connector.monitoring1Dot0.WorkManagerShutdownTriggerType;
import com.bea.connector.monitoring1Dot0.WorkManagerType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.external.ActivationSpecInfo;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.external.AuthMechInfo;
import weblogic.connector.external.ConfigPropInfo;
import weblogic.connector.external.ElementNotFoundException;
import weblogic.connector.external.InboundInfo;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.RequiredConfigPropInfo;
import weblogic.connector.external.SecurityPermissionInfo;
import weblogic.j2ee.descriptor.wl.AnonPrincipalBean;
import weblogic.j2ee.descriptor.wl.AnonPrincipalCallerBean;
import weblogic.j2ee.descriptor.wl.CapacityBean;
import weblogic.j2ee.descriptor.wl.ConnectorWorkManagerBean;
import weblogic.j2ee.descriptor.wl.ContextCaseBean;
import weblogic.j2ee.descriptor.wl.ContextRequestClassBean;
import weblogic.j2ee.descriptor.wl.FairShareRequestClassBean;
import weblogic.j2ee.descriptor.wl.InboundCallerPrincipalMappingBean;
import weblogic.j2ee.descriptor.wl.InboundGroupPrincipalMappingBean;
import weblogic.j2ee.descriptor.wl.MaxThreadsConstraintBean;
import weblogic.j2ee.descriptor.wl.MinThreadsConstraintBean;
import weblogic.j2ee.descriptor.wl.ResourceAdapterSecurityBean;
import weblogic.j2ee.descriptor.wl.ResponseTimeRequestClassBean;
import weblogic.j2ee.descriptor.wl.SecurityWorkContextBean;
import weblogic.j2ee.descriptor.wl.WorkManagerBean;
import weblogic.j2ee.descriptor.wl.WorkManagerShutdownTriggerBean;

public class Configuration_1_0 extends Configuration_BaseImpl {
   private final String SCHEMA_LOCATION = "weblogic/connector/extensions/connectorMonitoring_1_0.xsd";
   private final String SCHEMA_VERSION = "1.0";
   private MessageDigest md = null;

   public Configuration_1_0(RAInfo aRAInfo) {
      super(aRAInfo);
   }

   public String getSchema() {
      try {
         InputStream is = this.getClass().getClassLoader().getResourceAsStream("weblogic/connector/extensions/connectorMonitoring_1_0.xsd");
         InputStreamReader isr = new InputStreamReader(is);
         char[] charArray = new char[1024];
         StringBuffer tempSchema = new StringBuffer("");
         int readLength = false;

         for(int readLength = isr.read(charArray, 0, 1024); readLength != -1; readLength = isr.read(charArray, 0, 1024)) {
            tempSchema.append(charArray, 0, readLength);
         }

         return tempSchema.toString();
      } catch (IOException var6) {
         String exMsg = Debug.getExceptionMissingSchema();
         throw new MissingResourceException(exMsg, "weblogic/connector/extensions/connectorMonitoring_1_0.xsd", "weblogic/connector/extensions/connectorMonitoring_1_0.xsd");
      }
   }

   public String getConfigurationVersion() {
      return "1.0";
   }

   public String getConfiguration() {
      ConnectorDocument conDoc = ConnectorDocument.Factory.newInstance();
      ConnectorDocument.Connector connector = ConnectorDocument.Connector.Factory.newInstance();
      conDoc.setConnector(connector);
      String jndiName = this.raInfo.getJndiName();
      if (jndiName != null && jndiName.trim().length() > 0) {
         conDoc.getConnector().setJndiName(jndiName.trim());
      }

      String specVersion = this.raInfo.getSpecVersion();
      if (specVersion != null && specVersion.trim().length() > 0) {
         conDoc.getConnector().setVersion(specVersion.trim());
      }

      String[] descriptions = this.raInfo.getRADescriptions();
      if (descriptions != null) {
         for(int i = 0; i < descriptions.length; ++i) {
            conDoc.getConnector().addDescription(descriptions[i]);
         }
      }

      String vendorName = this.raInfo.getVendorName();
      if (vendorName != null && vendorName.trim().length() > 0) {
         conDoc.getConnector().setVendorName(vendorName.trim());
      }

      String eisType = this.raInfo.getEisType();
      if (eisType != null && eisType.trim().length() > 0) {
         conDoc.getConnector().setEisType(eisType.trim());
      }

      String raVersion = this.raInfo.getRAVersion();
      if (raVersion != null && raVersion.trim().length() > 0) {
         conDoc.getConnector().setResourceadapterVersion(raVersion.trim());
      }

      String raClass = this.raInfo.getRAClass();
      if (raClass != null && raClass.trim().length() > 0) {
         conDoc.getConnector().setResourceadapterClass(raClass.trim());
      }

      String linkref = this.raInfo.getLinkref();
      if (linkref != null && linkref.trim().length() > 0) {
         conDoc.getConnector().setLinkRef(linkref.trim());
      }

      conDoc.getConnector().setEnableAccessOutsideApp(this.raInfo.isEnableAccessOutsideApp());
      conDoc.getConnector().setModuleName(this.raInfo.getModuleName());
      conDoc.getConnector().setMetadataComplete(this.raInfo.getConnectorBean().isMetadataComplete());
      String[] workContexts = this.raInfo.getRequiredWorkContext();
      if (workContexts != null) {
         conDoc.getConnector().setRequiredWorkContextArray(workContexts);
      }

      conDoc.getConnector().setNativeLibdir(this.raInfo.getNativeLibDir());
      conDoc.getConnector().setEnableGlobalAccessToClasses(this.raInfo.isEnableGlobalAccessToClasses());
      Hashtable raProps = this.raInfo.getRAConfigProps();
      ConfigPropertiesType props = conDoc.getConnector().addNewProperties();
      this.setupConfigProperties(props, raProps);
      this.setupLicense(conDoc);
      this.setupWorkManager(conDoc);
      this.setupConnectorWorkManager(conDoc);
      this.setupSecurity(conDoc);
      this.setupOutbound(conDoc);
      this.setupAdminObjects(conDoc);
      this.setupInbound(conDoc);
      this.setupSecurityPermission(conDoc);
      return conDoc.toString();
   }

   private void setupOutbound(ConnectorDocument conDoc) {
      List outboundInfos = this.raInfo.getOutboundInfos();
      if (outboundInfos != null) {
         HashMap connectionFactoryToGroupMap = new HashMap();
         Iterator var4 = outboundInfos.iterator();

         while(var4.hasNext()) {
            OutboundInfo outboundInfo = (OutboundInfo)var4.next();
            OutboundGroupDocument.OutboundGroup outboundGroup = (OutboundGroupDocument.OutboundGroup)connectionFactoryToGroupMap.get(outboundInfo.getCFInterface());
            if (outboundGroup == null) {
               outboundGroup = OutboundGroupDocument.OutboundGroup.Factory.newInstance();

               try {
                  outboundGroup.setManagedconnectionfactoryClass(outboundInfo.getMCFClass());
                  outboundGroup.setConnectionFactoryInterface(outboundInfo.getCFInterface());
                  outboundGroup.setConnectionfactoryImplClass(outboundInfo.getCFImpl());
                  outboundGroup.setConnectionInterface(outboundInfo.getConnectionInterface());
                  outboundGroup.setConnectionImplClass(outboundInfo.getConnectionImpl());
               } catch (RAOutboundException var15) {
               }

               connectionFactoryToGroupMap.put(outboundInfo.getCFInterface(), outboundGroup);
            }

            ConnectionInstanceDocument.ConnectionInstance connectionInstance = outboundGroup.addNewConnectionInstance();
            String jndiName = outboundInfo.getJndiName();
            if (jndiName != null && jndiName.trim().length() > 0) {
               connectionInstance.setJndiName(jndiName.trim());
            }

            String description = outboundInfo.getDescription();
            if (description != null && description.trim().length() > 0) {
               connectionInstance.setDescription(description.trim());
            }

            connectionInstance.setTransactionSupport(outboundInfo.getTransactionSupport());
            List authMechInfos = outboundInfo.getAuthenticationMechanisms();
            if (authMechInfos != null) {
               Iterator var11 = authMechInfos.iterator();

               while(var11.hasNext()) {
                  AuthMechInfo authMechInfo = (AuthMechInfo)var11.next();
                  AuthenticationMechanismType authMech = connectionInstance.addNewAuthenticationMechanism();
                  authMech.setDescriptionArray(authMechInfo.getDescriptions());
                  authMech.setAuthenticationMechanismType(authMechInfo.getType());
                  authMech.setCredentialInterface(authMechInfo.getCredentialInterface());
               }
            }

            connectionInstance.setReauthenticationSupport(outboundInfo.getReauthenticationSupport());

            try {
               Hashtable propInfos = outboundInfo.getMCFProps();
               this.setupConfigProperties(connectionInstance.addNewProperties(), propInfos);
            } catch (RAOutboundException var14) {
            }

            connectionInstance.setResAuth(outboundInfo.getResAuth());
            ConnectionPoolParamsType poolParams = ConnectionPoolParamsType.Factory.newInstance();
            poolParams.setUseFirstAvailable(outboundInfo.isUseFirstAvailable());
            poolParams.setInitialCapacity(BigInteger.valueOf((long)outboundInfo.getInitialCapacity()));
            poolParams.setMaxCapacity(BigInteger.valueOf((long)outboundInfo.getMaxCapacity()));
            poolParams.setCapacityIncrement(BigInteger.valueOf((long)outboundInfo.getCapacityIncrement()));
            poolParams.setShrinkingEnabled(outboundInfo.isShrinkingEnabled());
            poolParams.setShrinkFrequencySeconds(BigInteger.valueOf((long)outboundInfo.getShrinkFrequencySeconds()));
            poolParams.setHighestNumWaiters(BigInteger.valueOf((long)outboundInfo.getHighestNumWaiters()));
            poolParams.setHighestNumUnavailable(BigInteger.valueOf((long)outboundInfo.getHighestNumUnavailable()));
            poolParams.setConnectionCreationRetryFrequencySeconds(BigInteger.valueOf((long)outboundInfo.getConnectionCreationRetryFrequencySeconds()));
            poolParams.setConnectionReserveTimeoutSeconds(BigInteger.valueOf((long)outboundInfo.getConnectionReserveTimeoutSeconds()));
            poolParams.setTestFrequencySeconds(BigInteger.valueOf((long)outboundInfo.getTestFrequencySeconds()));
            poolParams.setTestConnectionsOnCreate(outboundInfo.isTestConnectionsOnCreate());
            poolParams.setTestConnectionsOnRelease(outboundInfo.isTestConnectionsOnRelease());
            poolParams.setTestConnectionsOnReserve(outboundInfo.isTestConnectionsOnReserve());
            poolParams.setProfileHarvestFrequencySeconds(BigInteger.valueOf((long)outboundInfo.getProfileHarvestFrequencySeconds()));
            poolParams.setIgnoreInUseConnectionsEnabled(outboundInfo.isIgnoreInUseConnectionsEnabled());
            poolParams.setMatchConnectionsSupported(outboundInfo.isMatchConnectionsSupported());
            connectionInstance.setPoolParams(poolParams);
            LoggingType loggingParms = LoggingType.Factory.newInstance();
            loggingParms.setDateFormatPattern(outboundInfo.getDateFormatPattern());
            loggingParms.setFileCount(BigInteger.valueOf((long)outboundInfo.getFileCount()));
            loggingParms.setFileSizeLimit(BigInteger.valueOf((long)outboundInfo.getFileSizeLimit()));
            loggingParms.setFileTimeSpan(BigInteger.valueOf((long)outboundInfo.getFileTimeSpan()));
            loggingParms.setLogFileRotationDir(outboundInfo.getLogFileRotationDir());
            loggingParms.setLoggingEnabled(outboundInfo.isLoggingEnabled());
            loggingParms.setLogFilename(outboundInfo.getLogFilename());
            loggingParms.setNumberOfFilesLimited(outboundInfo.isNumberOfFilesLimited());
            loggingParms.setRotateLogOnStartup(outboundInfo.isRotateLogOnStartup());
            loggingParms.setRotationTime(outboundInfo.getRotationTime());
            loggingParms.setRotationType(outboundInfo.getRotationType());
            connectionInstance.setLogging(loggingParms);
         }

         OutboundGroupDocument.OutboundGroup[] groupArray = (OutboundGroupDocument.OutboundGroup[])connectionFactoryToGroupMap.values().toArray(new OutboundGroupDocument.OutboundGroup[0]);
         OutboundDocument.Outbound outbound = OutboundDocument.Outbound.Factory.newInstance();
         outbound.setOutboundGroupArray(groupArray);
         conDoc.getConnector().setOutbound(outbound);
      }

   }

   private void setupAdminObjects(ConnectorDocument conDoc) {
      List adminObjectList = this.raInfo.getAdminObjs();
      HashMap adminObjectToGroupMap = new HashMap();
      if (adminObjectList != null) {
         Iterator var5 = adminObjectList.iterator();

         while(var5.hasNext()) {
            AdminObjInfo adminObjectInfo = (AdminObjInfo)var5.next();
            String adminObjectInterface = adminObjectInfo.getInterface();
            String adminObjectClass = adminObjectInfo.getAdminObjClass();
            String key = adminObjectInterface + adminObjectClass;
            AdminObjectGroupDocument.AdminObjectGroup adminGroup = (AdminObjectGroupDocument.AdminObjectGroup)adminObjectToGroupMap.get(key);
            if (adminGroup == null) {
               adminGroup = AdminObjectGroupDocument.AdminObjectGroup.Factory.newInstance();
               adminGroup.setAdminObjectInterface(adminObjectInterface);
               adminGroup.setAdminObjectClass(adminObjectClass);
               adminObjectToGroupMap.put(key, adminGroup);
            }

            AdminObjectInstanceDocument.AdminObjectInstance adminInstance = adminGroup.addNewAdminObjectInstance();
            adminInstance.setJndiName(adminObjectInfo.getJndiName());
            this.setupConfigProperties(adminInstance.addNewProperties(), adminObjectInfo.getConfigProps());
         }

         AdminObjectGroupDocument.AdminObjectGroup[] groupArray = (AdminObjectGroupDocument.AdminObjectGroup[])adminObjectToGroupMap.values().toArray(new AdminObjectGroupDocument.AdminObjectGroup[0]);
         AdminObjectsDocument.AdminObjects adminObjs = AdminObjectsDocument.AdminObjects.Factory.newInstance();
         adminObjs.setAdminObjectGroupArray(groupArray);
         conDoc.getConnector().setAdminObjects(adminObjs);
      }

   }

   private void setupInbound(ConnectorDocument conDoc) {
      HashMap msgListenerTypeToMsgListenerMap = new HashMap();

      try {
         List inboundList = this.raInfo.getInboundInfos();
         InboundDocument.Inbound inbound = InboundDocument.Inbound.Factory.newInstance();
         Iterator var5 = inboundList.iterator();

         while(true) {
            MessagelistenerDocument.Messagelistener msgListener;
            ActivationSpecInfo activationSpecInfo;
            do {
               if (!var5.hasNext()) {
                  conDoc.getConnector().setInbound(inbound);
                  return;
               }

               InboundInfo inboundInfo = (InboundInfo)var5.next();
               msgListener = (MessagelistenerDocument.Messagelistener)msgListenerTypeToMsgListenerMap.get(inboundInfo.getMsgType());
               if (msgListener == null) {
                  msgListener = inbound.addNewMessagelistener();
                  msgListener.setMessagelistenerType(inboundInfo.getMsgType());
                  msgListenerTypeToMsgListenerMap.put(inboundInfo.getMsgType(), msgListener);
               }

               activationSpecInfo = inboundInfo.getActivationSpec();
            } while(activationSpecInfo == null);

            ActivationspecDocument.Activationspec activationSpec = msgListener.addNewActivationspec();
            activationSpec.setActivationspecClass(activationSpecInfo.getActivationSpecClass());
            List requiredPropertInfoList = activationSpecInfo.getRequiredProps();
            if (requiredPropertInfoList != null) {
               Iterator var11 = requiredPropertInfoList.iterator();

               while(var11.hasNext()) {
                  RequiredConfigPropInfo requiredPropInfo = (RequiredConfigPropInfo)var11.next();
                  RequiredConfigPropertyDocument.RequiredConfigProperty requiredConfigProp = activationSpec.addNewRequiredConfigProperty();
                  requiredConfigProp.setDescriptionArray(requiredPropInfo.getDescriptions());
                  requiredConfigProp.setConfigPropertyName(requiredPropInfo.getName());
               }
            }

            Map configPropInfoMap = activationSpecInfo.getConfigProps();
            this.setupConfigProperties(activationSpec.addNewProperties(), configPropInfoMap);
         }
      } catch (ElementNotFoundException var14) {
      }
   }

   private void setupWorkManager(ConnectorDocument conDoc) {
      WorkManagerBean wmBean = this.raInfo.getWorkManager();
      if (wmBean != null) {
         WorkManagerType wm = WorkManagerType.Factory.newInstance();
         wm.setName(wmBean.getName());
         ResponseTimeRequestClassBean rtrcBean = wmBean.getResponseTimeRequestClass();
         if (rtrcBean != null) {
            ResponseTimeRequestClassType rtrc = wm.addNewResponseTimeRequestClass();
            rtrc.setName(rtrcBean.getName());
            rtrc.setGoalMs(BigInteger.valueOf((long)rtrcBean.getGoalMs()));
         }

         FairShareRequestClassBean fsrcBean = wmBean.getFairShareRequestClass();
         if (fsrcBean != null) {
            FairShareRequestClassType fsrc = wm.addNewFairShareRequestClass();
            fsrc.setName(fsrcBean.getName());
            fsrc.setFairShare(BigInteger.valueOf((long)fsrcBean.getFairShare()));
         }

         ContextRequestClassBean crcBean = wmBean.getContextRequestClass();
         if (crcBean != null) {
            ContextRequestClassType crc = wm.addNewContextRequestClass();
            crc.setName(crcBean.getName());
            ContextCaseBean[] ccBeans = crcBean.getContextCases();
            if (ccBeans != null) {
               ContextCaseBean[] var9 = ccBeans;
               int var10 = ccBeans.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  ContextCaseBean ccBean = var9[var11];
                  ContextCaseType cc = crc.addNewContextCase();
                  cc.setGroupName(ccBean.getGroupName());
                  cc.setUserName(ccBean.getUserName());
                  cc.setRequestClassName(ccBean.getRequestClassName());
               }
            }
         }

         wm.setRequestClassName(wmBean.getRequestClassName());
         MinThreadsConstraintBean minTcBean = wmBean.getMinThreadsConstraint();
         if (minTcBean != null) {
            MinThreadsConstraintType minTc = wm.addNewMinThreadsConstraint();
            minTc.setName(minTcBean.getName());
            minTc.setCount(BigInteger.valueOf((long)minTcBean.getCount()));
         }

         if (wmBean.getMinThreadsConstraintName() != null) {
            wm.setMinThreadsConstraintName(wmBean.getMinThreadsConstraintName());
         }

         MaxThreadsConstraintBean maxTcBean = wmBean.getMaxThreadsConstraint();
         if (maxTcBean != null) {
            MaxThreadsConstraintType maxTc = wm.addNewMaxThreadsConstraint();
            maxTc.setName(maxTc.getName());
            maxTc.setPoolName(maxTcBean.getPoolName());
            maxTc.setCount(BigInteger.valueOf((long)maxTcBean.getCount()));
         }

         if (wmBean.getMaxThreadsConstraintName() != null) {
            wm.setMaxThreadsConstraintName(wmBean.getMaxThreadsConstraintName());
         }

         CapacityBean capBean = wmBean.getCapacity();
         if (capBean != null) {
            CapacityType cap = wm.addNewCapacity();
            cap.setName(capBean.getName());
            cap.setCount(BigInteger.valueOf((long)capBean.getCount()));
         }

         if (wmBean.getCapacityName() != null) {
            wm.setCapacityName(wmBean.getCapacityName());
         }

         WorkManagerShutdownTriggerBean wmstBean = wmBean.getWorkManagerShutdownTrigger();
         if (wmstBean != null) {
            WorkManagerShutdownTriggerType wmst = wm.addNewWorkManagerShutdownTrigger();
            wmst.setMaxStuckThreadTime(BigInteger.valueOf((long)wmstBean.getMaxStuckThreadTime()));
            wmst.setStuckThreadCount(BigInteger.valueOf((long)wmstBean.getStuckThreadCount()));
         } else {
            wm.setIgnoreStuckThreads(wmBean.getIgnoreStuckThreads());
         }

         conDoc.getConnector().setWorkManager(wm);
      }
   }

   private void setupConnectorWorkManager(ConnectorDocument conDoc) {
      ConnectorWorkManagerBean cwmBean = this.raInfo.getConnectorWorkManager();
      if (cwmBean != null) {
         ConnectorWorkManagerType cwm = conDoc.getConnector().addNewConnectorWorkManager();
         cwm.setMaxConcurrentLongRunningRequests(BigInteger.valueOf((long)cwmBean.getMaxConcurrentLongRunningRequests()));
      }
   }

   private void setupAnonPrincipal(AnonPrincipalType dp, AnonPrincipalBean dpBean) {
      boolean anony = dpBean.isUseAnonymousIdentity();
      if (anony) {
         dp.setUseAnonymousIdentity(true);
      } else {
         dp.setUseAnonymousIdentity(false);
         dp.setPrincipalName(dpBean.getPrincipalName());
      }

   }

   private void setupAnonPrincipalCaller(AnonPrincipalCallerType rap, AnonPrincipalCallerBean rapBean) {
      if (rapBean.isUseAnonymousIdentity()) {
         rap.setUseAnonymousIdentity(true);
      } else if (rapBean.isUseCallerIdentity()) {
         rap.setUseCallerIdentity(true);
      } else {
         rap.setPrincipalName(rapBean.getPrincipalName());
      }

   }

   private void setupSecurity(ConnectorDocument conDoc) {
      if (this.raInfo.getWeblogicConnectorBean().isSecuritySet()) {
         ResourceAdapterSecurityBean secBean = this.raInfo.getWeblogicConnectorBean().getSecurity();
         ResourceAdapterSecurityType sec = conDoc.getConnector().addNewSecurity();
         AnonPrincipalBean dpBean = secBean.getDefaultPrincipalName();
         if (dpBean != null) {
            this.setupAnonPrincipal(sec.addNewDefaultPrincipalName(), dpBean);
         }

         AnonPrincipalBean mpBean = secBean.getManageAsPrincipalName();
         if (mpBean != null) {
            this.setupAnonPrincipal(sec.addNewManageAsPrincipalName(), mpBean);
         }

         AnonPrincipalCallerBean rapBean = secBean.getRunAsPrincipalName();
         if (rapBean != null) {
            this.setupAnonPrincipalCaller(sec.addNewRunAsPrincipalName(), rapBean);
         }

         AnonPrincipalCallerBean rwapBean = secBean.getRunWorkAsPrincipalName();
         if (rwapBean != null) {
            this.setupAnonPrincipalCaller(sec.addNewRunWorkAsPrincipalName(), rwapBean);
         }

         if (secBean.isSecurityWorkContextSet()) {
            SecurityWorkContextBean swcBean = secBean.getSecurityWorkContext();
            SecurityWorkContextType swc = sec.addNewSecurityWorkContext();
            swc.setInboundMappingRequired(swcBean.isInboundMappingRequired());
            if (swcBean.isCallerPrincipalDefaultMappedSet()) {
               AnonPrincipalBean cpdBean = swcBean.getCallerPrincipalDefaultMapped();
               this.setupAnonPrincipal(swc.addNewCallerPrincipalDefaultMapped(), cpdBean);
            }

            InboundCallerPrincipalMappingBean[] cpmBeans = swcBean.getCallerPrincipalMappings();
            int var13;
            if (cpmBeans != null) {
               InboundCallerPrincipalMappingBean[] var11 = cpmBeans;
               int var12 = cpmBeans.length;

               for(var13 = 0; var13 < var12; ++var13) {
                  InboundCallerPrincipalMappingBean cpmBean = var11[var13];
                  InboundCallerPrincipalMappingType cpm = swc.addNewCallerPrincipalMapping();
                  cpm.setEisCallerPrincipal(cpmBean.getEisCallerPrincipal());
                  this.setupAnonPrincipal(cpm.addNewMappedCallerPrincipal(), cpmBean.getMappedCallerPrincipal());
               }
            }

            if (swcBean.getGroupPrincipalDefaultMapped() != null) {
               swc.setGroupPrincipalDefaultMapped(swcBean.getGroupPrincipalDefaultMapped());
            }

            InboundGroupPrincipalMappingBean[] gpmBeans = swcBean.getGroupPrincipalMappings();
            if (gpmBeans != null) {
               InboundGroupPrincipalMappingBean[] var19 = gpmBeans;
               var13 = gpmBeans.length;

               for(int var20 = 0; var20 < var13; ++var20) {
                  InboundGroupPrincipalMappingBean gpmBean = var19[var20];
                  InboundGroupPrincipalMappingType gpm = swc.addNewGroupPrincipalMapping();
                  gpm.setEisGroupPrincipal(gpmBean.getEisGroupPrincipal());
                  gpm.setMappedGroupPrincipal(gpmBean.getMappedGroupPrincipal());
               }
            }
         }

      }
   }

   private void setupLicense(ConnectorDocument conDoc) {
      if (this.raInfo.getConnectorBean().getLicense() != null) {
         String[] licenseDescs = this.raInfo.getLicenseDescriptions();
         boolean licenseRequired = this.raInfo.getLicenseRequired();
         LicenseDocument.License ld = conDoc.getConnector().addNewLicense();
         if (licenseDescs != null) {
            String[] var5 = licenseDescs;
            int var6 = licenseDescs.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String desc = var5[var7];
               ld.addDescription(desc);
            }
         }

         ld.setLicenseRequired(licenseRequired);
      }
   }

   private void setupConfigProperties(ConfigPropertiesType props, Map propInfos) {
      Iterator var3 = propInfos.values().iterator();

      while(var3.hasNext()) {
         ConfigPropInfo propInfo = (ConfigPropInfo)var3.next();
         ConfigPropertyType prop = props.addNewProperty();
         prop.setDescriptionArray(propInfo.getDescriptions());
         prop.setName(propInfo.getName());
         prop.setType(propInfo.getType());
         prop.setIgnore(false);
         this.setupPropertyValue(prop, propInfo.getValue(), propInfo.isConfidential());
         prop.setSupportsDynamicUpdates(propInfo.isDynamicUpdatable());
      }

   }

   private void setupPropertyValue(ConfigPropertyType prop, String value, boolean confidential) {
      if (this.md == null) {
         try {
            this.md = MessageDigest.getInstance("SHA-256");
         } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
         }
      }

      prop.setConfidential(confidential);
      if (confidential && this.md != null) {
         byte[] encoded = this.md.digest(value.getBytes());
         StringBuilder sb = new StringBuilder();
         byte[] var6 = encoded;
         int var7 = encoded.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int x = var6[var8];
            sb.append(Integer.toHexString(x & 255));
         }

         prop.setValue(sb.toString());
      } else {
         prop.setValue(value);
      }

   }

   private void setupSecurityPermission(ConnectorDocument conDoc) {
      List permissionInfoList = this.raInfo.getSecurityPermissions();
      Iterator var3 = permissionInfoList.iterator();

      while(var3.hasNext()) {
         SecurityPermissionInfo permissionInfo = (SecurityPermissionInfo)var3.next();
         SecurityPermissionType permission = conDoc.getConnector().addNewSecurityPermission();
         permission.setDescriptionArray(permissionInfo.getDescriptions());
         permission.setSecurityPermissionSpec(permissionInfo.getSpec());
      }

   }
}
