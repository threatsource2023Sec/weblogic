package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.servicecfg.BootStrapServiceConfig;
import com.bea.common.store.bootstrap.BootStrapPersistence;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.common.store.bootstrap.GlobalPolicyUpdate;
import com.bea.common.store.service.StoreService;
import java.util.List;
import java.util.Properties;
import weblogic.management.security.RealmMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;

public class BootStrapServiceImpl implements ServiceLifecycleSpi, BootStrapService {
   private static final String ENVROOTDIR = "envrootdir";
   private static final String SECURITYROOTDIR = "securityrootdir";
   private static final String BOOTSTRAPVERSIONINGDIR = "bootstrapversioningdir";
   private static final String TMPFILEROOTDIR = "tmpfilerootdir";
   private LoggerSpi logger;
   com.bea.common.store.bootstrap.internal.BootStrapServiceImpl delegate = null;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.BootStrapService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof BootStrapServiceConfig) {
         BootStrapServiceConfig myconfig = (BootStrapServiceConfig)config;
         Properties bootStrapProperties = myconfig.getBootStrapProperties();
         String envRootDir = bootStrapProperties == null ? "." : bootStrapProperties.getProperty("envrootdir", ".");
         String securityRootDir = bootStrapProperties == null ? "." : bootStrapProperties.getProperty("securityrootdir", ".");
         String bootStrapVersioningDir = bootStrapProperties == null ? "." : bootStrapProperties.getProperty("bootstrapversioningdir", ".");
         String tmpFileRootDir = bootStrapProperties == null ? "." : bootStrapProperties.getProperty("tmpfilerootdir", ".");
         this.delegate = new com.bea.common.store.bootstrap.internal.BootStrapServiceImpl(envRootDir, securityRootDir, bootStrapVersioningDir, tmpFileRootDir);
         return Delegator.getProxy((Class)BootStrapService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "BootStrapServiceConfig"));
      }
   }

   public void shutdown() {
   }

   public void loadLDIFCredentialMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, RealmMBean realmMBean) {
      this.delegate.loadLDIFCredentialMapperTemplate(log, storeService, entryConverter, domainName, realmMBean);
   }

   public void loadLDIFXACMLAuthorizerTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFXACMLAuthorizerTemplate(log, storeService, entryConverter, domainName, realmName);
   }

   public void loadLDIFXACMLRoleMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFXACMLRoleMapperTemplate(log, storeService, entryConverter, domainName, realmName);
   }

   public void loadLDIFSAML2CredentialMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFSAML2CredentialMapperTemplate(log, storeService, entryConverter, domainName, realmName);
   }

   public void loadLDIFSAML2IdentityAsserterTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFSAML2IdentityAsserterTemplate(log, storeService, entryConverter, domainName, realmName);
   }

   public void loadLDIFPKICredentialMapperTemplate(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFPKICredentialMapperTemplate(log, storeService, entryConverter, domainName, realmName);
   }

   public void importSAMLDataLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String providerType, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.importSAMLDataLDIFT(log, storeService, entryConverter, providerType, domainName, realmName);
   }

   public void importSAML2DataLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String providerType, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.importSAML2DataLDIFT(log, storeService, entryConverter, providerType, domainName, realmName);
   }

   public void importPKICredentialMapperLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String fileName, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.importPKICredentialMapperLDIFT(log, storeService, entryConverter, fileName, domainName, realmName);
   }

   public void importCredentialMapperLDIFT(LoggerSpi log, StoreService storeService, EntryConverter entryConverter, String fileName, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.importCredentialMapperLDIFT(log, storeService, entryConverter, fileName, domainName, realmName);
   }

   public void exportSAMLDataToLDIFT(LoggerSpi log, String providerType, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.exportSAMLDataToLDIFT(log, providerType, fileName, domainName, realmName, entries);
   }

   public void exportSAML2DataToLDIFT(LoggerSpi log, String providerType, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.exportSAMLDataToLDIFT(log, providerType, fileName, domainName, realmName, entries);
   }

   public void exportPKICredentialMapperLDIFT(LoggerSpi log, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.exportPKICredentialMapperLDIFT(log, fileName, domainName, realmName, entries);
   }

   public void exportCredentialMapperLDIFT(LoggerSpi log, String fileName, String domainName, String realmName, List entries) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.exportCredentialMapperLDIFT(log, fileName, domainName, realmName, entries);
   }

   public void importCredentialMapperLDIFT(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String fileName, String domainName, String realmName) throws InvalidParameterException, ErrorCollectionException {
      this.delegate.importCredentialMapperLDIFT(log, bootStrapPersistence, entryConverter, fileName, domainName, realmName);
   }

   public void updateXACMLAuthorizerPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, StoreService storeService, String domainName, String realmName) {
      this.delegate.updateXACMLAuthorizerPolicies(log, globalPolicyUpdate, storeService, domainName, realmName);
   }

   public void updateXACMLAuthorizerPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, BootStrapPersistence bootStrapPersistence, String domainName, String realmName) {
      this.delegate.updateXACMLAuthorizerPolicies(log, globalPolicyUpdate, bootStrapPersistence, domainName, realmName);
   }

   public void updateXACMLRoleMapperPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, StoreService storeService, String domainName, String realmName) {
      this.delegate.updateXACMLRoleMapperPolicies(log, globalPolicyUpdate, storeService, domainName, realmName);
   }

   public void updateXACMLRoleMapperPolicies(LoggerSpi log, GlobalPolicyUpdate globalPolicyUpdate, BootStrapPersistence bootStrapPersistence, String domainName, String realmName) {
      this.delegate.updateXACMLRoleMapperPolicies(log, globalPolicyUpdate, bootStrapPersistence, domainName, realmName);
   }

   public void loadLDIFCredentialMapperTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, RealmMBean realmMBean) {
      this.delegate.loadLDIFCredentialMapperTemplate(log, bootStrapPersistence, entryConverter, domainName, realmMBean);
   }

   public void loadLDIFXACMLAuthorizerTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFXACMLAuthorizerTemplate(log, bootStrapPersistence, entryConverter, domainName, realmName);
   }

   public void loadLDIFXACMLRoleMapperTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFXACMLRoleMapperTemplate(log, bootStrapPersistence, entryConverter, domainName, realmName);
   }

   public void loadLDIFSAML2CredentialMapperTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFSAML2CredentialMapperTemplate(log, bootStrapPersistence, entryConverter, domainName, realmName);
   }

   public void loadLDIFSAML2IdentityAsserterTemplate(LoggerSpi log, BootStrapPersistence bootStrapPersistence, EntryConverter entryConverter, String domainName, String realmName) {
      this.delegate.loadLDIFSAML2IdentityAsserterTemplate(log, bootStrapPersistence, entryConverter, domainName, realmName);
   }
}
