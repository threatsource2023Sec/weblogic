package com.bea.common.security.saml.manager;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml.registry.PartnerChangeListener;
import com.bea.common.security.saml.registry.RegistryChangeHandler;
import com.bea.common.security.saml.registry.SAMLPartnerEntry;
import com.bea.common.security.saml.registry.SAMLPartnerRegistry;
import com.bea.common.security.saml.registry.SAMLV1ConfigHelper;
import com.bea.common.store.service.RemoteCommitEvent;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.common.store.service.StoreService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.providers.utils.Utils;
import weblogic.security.spi.IdentityAssertionException;
import weblogic.security.spi.ProviderInitializationException;
import weblogic.security.spi.SecurityServices;

public abstract class SAMLPartnerConfigManager implements RemoteCommitListener, PartnerChangeListener {
   protected static final String WILDCARD_KEY = "wildcard";
   protected LoggerSpi LOGGER = null;
   protected StoreService storeService = null;
   protected static final int AP_MANAGER = 0;
   protected static final int RP_MANAGER = 1;
   private static SAMLPartnerConfigManager[] managers = new SAMLPartnerConfigManager[]{null, null};
   protected ProviderMBean mbean = null;
   protected SecurityServices services = null;
   protected LegacyEncryptorSpi legacyEncryptor = null;
   protected SAMLPartnerRegistry partnerReg = null;
   protected Map partnerMap = null;
   protected Map targetMap = null;
   protected List wildcardList = null;
   protected RegistryChangeHandler registryChangeHandler = null;

   protected abstract String getManagerName();

   protected abstract String getDebugLoggerName();

   protected abstract SAMLPartnerRegistry getRegistryInstance(ProviderMBean var1, SecurityServices var2);

   protected abstract SAMLPartnerEntry getPartnerInstance();

   protected abstract boolean applyPartnerDefaults(SAMLPartnerEntry var1);

   protected abstract String getPartnerTargetKey(SAMLPartnerEntry var1);

   protected abstract int testPartnerWildcardMatch(String var1, String var2, String var3, SAMLPartnerEntry var4);

   protected abstract void handleStoreChanges(List var1);

   protected abstract Class getStoreClass();

   protected void logDebug(String method, String msg) {
      if (this.LOGGER.isDebugEnabled()) {
         this.LOGGER.debug(this.getManagerName() + "." + method + "():" + msg);
      }

   }

   protected void handleError(String msg) throws IdentityAssertionException {
      this.logDebug("handleError", msg);
      throw new RuntimeException(this.getManagerName() + ": " + msg);
   }

   protected SAMLPartnerConfigManager(ProviderMBean mbean, SecurityServices services) {
      this.mbean = mbean;
      this.services = services;
      this.LOGGER = ((ExtendedSecurityServices)services).getLogger(this.getDebugLoggerName());
      this.storeService = Utils.getStoreService(services);
      this.legacyEncryptor = Utils.getLegacyEncryptorSpi(services);
   }

   protected static synchronized SAMLPartnerConfigManager getManagerInstance(int which) {
      return managers[which];
   }

   protected static synchronized void setManagerInstance(int which, SAMLPartnerConfigManager mgr) {
      managers[which] = mgr;
   }

   public synchronized int size() {
      return this.partnerMap != null ? this.partnerMap.size() : 0;
   }

   protected synchronized void buildPartnerMaps() throws ProviderInitializationException {
      this.initRegistry();
      this.buildPartnerMap();
      this.buildTargetMaps();
   }

   public synchronized void removePartnerChangeListener(String id) {
      if (this.partnerReg != null) {
         this.partnerReg.removePartnerChangeListener(id, this);
      }

   }

   private synchronized void initRegistry() throws ProviderInitializationException {
      if (this.partnerReg == null) {
         this.partnerReg = this.getRegistryInstance(this.mbean, this.services);
         String realmName = this.mbean.getRealm().getName();
         String domainName = Utils.getDomainName(this.services);
         this.partnerReg.addPartnerChangeListener(domainName + realmName, this);
         this.storeService.addRemoteCommitListener(this.getStoreClass(), this);
         this.logDebug("initRegistry", "Got partner registry, listening for config store changes");
      }

   }

   private synchronized void buildPartnerMap() throws ProviderInitializationException {
      Map tmpPartnerMap = this.partnerReg.getEnabledPartnerConfigs();
      Set s = tmpPartnerMap.keySet();
      Iterator it = s.iterator();

      while(it.hasNext()) {
         String partnerId = (String)it.next();
         SAMLPartnerEntry partner = (SAMLPartnerEntry)tmpPartnerMap.get(partnerId);
         if (!partner.isEnabled()) {
            this.logDebug("buildPartnerMap", "Disabled partner '" + partner.getPartnerId() + "', will not be activated");
            it.remove();
         } else if (!this.applyPartnerDefaults(partner)) {
            this.logDebug("buildPartnerMap", "Unable to apply defaults for partner '" + partner.getPartnerId() + "', partner will not be activated");
            it.remove();
         }
      }

      this.partnerMap = tmpPartnerMap;
   }

   private synchronized void buildTargetMaps() throws ProviderInitializationException {
      HashMap tmpTargetMap = new HashMap();
      LinkedList tmpWildcardList = new LinkedList();
      Collection partners = this.partnerMap.values();
      Iterator it = partners.iterator();

      while(it.hasNext()) {
         SAMLPartnerEntry partner = (SAMLPartnerEntry)it.next();
         String targetKey = this.getPartnerTargetKey(partner);
         if (targetKey != null) {
            if (targetKey.equals("wildcard")) {
               this.logDebug("buildTargetMaps", "Adding partner '" + partner.getPartnerId() + "' to wildcard list");
               tmpWildcardList.add(partner);
            } else {
               this.logDebug("buildTargetMaps", "Adding partner '" + partner.getPartnerId() + "' to target map with key '" + targetKey + "'");
               tmpTargetMap.put(targetKey, partner);
            }
         }
      }

      this.targetMap = tmpTargetMap;
      this.wildcardList = tmpWildcardList;
   }

   protected synchronized void buildV1PartnerMaps(Properties assertions) throws ProviderInitializationException {
      this.buildV1PartnerMap(assertions);
      this.buildTargetMaps();
   }

   private synchronized void buildV1PartnerMap(Properties assertions) throws ProviderInitializationException {
      HashMap tmpPartnerMap = new HashMap();
      if (assertions != null) {
         SAMLV1ConfigHelper assnHelper = new SAMLV1ConfigHelper(this.LOGGER, assertions, "Assertions");

         while(assnHelper.hasMoreEntries()) {
            Map entry = assnHelper.getNextEntry();
            if (entry != null) {
               SAMLPartnerEntry partner = this.getPartnerInstance();
               partner.setV1Config(true);
               partner.setAttributesFromMap(entry);
               partner.setEnabled(true);

               try {
                  partner.construct();
               } catch (InvalidParameterException var7) {
                  this.logDebug("buildPartnerMap", "Exception while constructing partner '" + partner.getPartnerId() + "', partner will not be active: " + var7.toString());
                  partner = null;
               }

               if (partner != null) {
                  if (this.applyPartnerDefaults(partner)) {
                     tmpPartnerMap.put(partner.getPartnerId(), partner);
                  } else {
                     this.logDebug("buildPartnerMap", "Unable to apply defaults for partner '" + partner.getPartnerId() + "', partner will not be active");
                  }
               }
            }
         }
      }

      this.partnerMap = tmpPartnerMap;
   }

   protected SAMLPartnerEntry findPartner(String partnerId) {
      return (SAMLPartnerEntry)this.partnerMap.get(partnerId);
   }

   protected SAMLPartnerEntry findPartnerInTargetMap(String targetKey) {
      this.logDebug("findPartnerInTargetMap", "Searching with key '" + targetKey + "'");
      SAMLPartnerEntry partner = (SAMLPartnerEntry)this.targetMap.get(targetKey);
      if (partner != null) {
         this.logDebug("findPartnerInTargetMap", "Found partner '" + partner.getPartnerId() + "'");
      } else {
         this.logDebug("findPartnerInTargetMap", "No partner found");
      }

      return partner;
   }

   protected SAMLPartnerEntry findPartnerInWildcardList(String profile, String issuer, String target) {
      List tmpList = this.wildcardList;
      SAMLPartnerEntry defaultMatch = null;
      SAMLPartnerEntry bestMatch = null;
      int bestMatchLen = 0;
      Iterator it = tmpList.iterator();

      while(it.hasNext()) {
         SAMLPartnerEntry partner = (SAMLPartnerEntry)it.next();
         int match = this.testPartnerWildcardMatch(profile, issuer, target, partner);
         if (match == 0) {
            defaultMatch = partner;
         } else if (match > bestMatchLen) {
            bestMatch = partner;
            bestMatchLen = match;
         }
      }

      if (bestMatch != null) {
         return bestMatch;
      } else {
         return defaultMatch;
      }
   }

   public void afterCommit(RemoteCommitEvent event) {
      Collection deletedObjectIds = event.getDeletedObjectIds();
      Collection updatedObjectIds = event.getUpdatedObjectIds();
      Collection addedObjectIds = event.getAddedObjectIds();
      ArrayList objectIds = new ArrayList();
      if (deletedObjectIds != null) {
         objectIds.addAll(deletedObjectIds);
      }

      if (updatedObjectIds != null) {
         objectIds.addAll(updatedObjectIds);
      }

      if (addedObjectIds != null) {
         objectIds.addAll(addedObjectIds);
      }

      this.handleStoreChanges(objectIds);
   }

   protected boolean isEmbeddedLDAP() {
      try {
         return this.storeService.getStoreId().startsWith("ldap");
      } catch (Exception var2) {
         return false;
      }
   }

   public void setRegistryChangeHandler(RegistryChangeHandler handler) {
      this.registryChangeHandler = handler;
   }

   protected void handleDefaultRegistryChange() {
      if (this.registryChangeHandler != null) {
         this.registryChangeHandler.handleRegistryChange();
      }

   }
}
