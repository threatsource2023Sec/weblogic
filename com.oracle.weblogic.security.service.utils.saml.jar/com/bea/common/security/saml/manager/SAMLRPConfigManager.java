package com.bea.common.security.saml.manager;

import com.bea.common.security.saml.registry.SAMLPartnerEntry;
import com.bea.common.security.saml.registry.SAMLPartnerRegistry;
import com.bea.common.security.saml.registry.SAMLRelyingPartyConfig;
import com.bea.common.security.saml.registry.SAMLRelyingPartyEntry;
import com.bea.common.security.saml.registry.SAMLRelyingPartyRegistry;
import com.bea.common.security.store.data.BEASAMLRelyingParty;
import com.bea.common.security.store.data.BEASAMLRelyingPartyId;
import java.util.List;
import java.util.Properties;
import weblogic.management.security.ProviderMBean;
import weblogic.security.spi.SecurityServices;

public class SAMLRPConfigManager extends SAMLPartnerConfigManager {
   private String defaultNameMapper = null;
   private String defaultNameQualifier = null;
   private int defaultTimeToLive = 0;
   private int defaultTimeToLiveOffset = 0;
   private static SecurityServices securityServices = null;

   public String getDefaultNameQualifier() {
      return this.defaultNameQualifier;
   }

   private SAMLRPConfigManager(ProviderMBean mbean, SecurityServices services) {
      super(mbean, services);
   }

   public static SAMLRPConfigManager getManager() {
      return (SAMLRPConfigManager)getManagerInstance(1);
   }

   public static void setManager(SAMLRPConfigManager manager) {
      setManagerInstance(1, manager);
   }

   public static synchronized SAMLRPConfigManager getManager(ProviderMBean mbean, SecurityServices services) {
      SAMLRPConfigManager manager = getManager();
      if (manager != null && securityServices != null && securityServices != services) {
         manager = null;
      }

      if (manager == null) {
         securityServices = services;
         manager = new SAMLRPConfigManager(mbean, services);
         setManager(manager);
      }

      return getManager();
   }

   protected void handleEntryChanged() {
      this.logDebug("handleEntryChanged", "Building maps ...");
      this.buildPartnerMaps();
      this.handleDefaultRegistryChange();
   }

   private synchronized void setDefaults(String defaultNameMapper, String defaultNameQualifier, int defaultTimeToLive, int defaultTimeToLiveOffset) {
      this.defaultNameMapper = defaultNameMapper;
      this.defaultNameQualifier = defaultNameQualifier;
      this.defaultTimeToLive = defaultTimeToLive;
      this.defaultTimeToLiveOffset = defaultTimeToLiveOffset;
   }

   public synchronized void setConfig(String nameMapper, String nameQualifier, int timeToLive, int ttlOffset) {
      this.setDefaults(nameMapper, nameQualifier, timeToLive, ttlOffset);
      this.buildPartnerMaps();
   }

   public synchronized void setV1Config(Properties assertions, String nameMapper, String nameQualifier, int timeToLive, int ttlOffset) {
      this.setDefaults(nameMapper, nameQualifier, timeToLive, ttlOffset);
      this.buildV1PartnerMaps(assertions);
   }

   protected String getManagerName() {
      return "SAMLRPConfigManager";
   }

   protected String getDebugLoggerName() {
      return "SecuritySAMLCredMap";
   }

   protected SAMLPartnerRegistry getRegistryInstance(ProviderMBean mbean, SecurityServices services) {
      return new SAMLRelyingPartyRegistry(mbean, services);
   }

   protected SAMLPartnerEntry getPartnerInstance() {
      return new SAMLRelyingPartyConfig(this.LOGGER, this.legacyEncryptor);
   }

   protected synchronized boolean applyPartnerDefaults(SAMLPartnerEntry partner) {
      SAMLRelyingPartyConfig config = (SAMLRelyingPartyConfig)partner;
      String name = config.getNameMapperClass();
      if (name == null || name.length() == 0) {
         name = this.defaultNameMapper;
      }

      config.setNameMapperClass(name);
      if (config.getTimeToLive() == 0) {
         config.setTimeToLive(this.defaultTimeToLive);
      }

      if (config.getTimeToLiveOffset() == 0) {
         config.setTimeToLiveOffset(this.defaultTimeToLiveOffset);
      }

      return true;
   }

   protected String getPartnerTargetKey(SAMLPartnerEntry partner) {
      SAMLRelyingPartyConfig rp = (SAMLRelyingPartyConfig)partner;
      if (rp.getTargetURL() != null) {
         return !rp.isWildcardTarget() && !rp.isDefaultTarget() ? makeTargetKey(rp.getProfileConfMethodName(), rp.getTargetURL()) : "wildcard";
      } else {
         return null;
      }
   }

   private static String makeTargetKey(String profile, String target) {
      return profile + ":" + target;
   }

   protected int testPartnerWildcardMatch(String profile, String issuer, String target, SAMLPartnerEntry partner) {
      SAMLRelyingPartyConfig rp = (SAMLRelyingPartyConfig)partner;
      if (rp.getTargetURL() != null && profile.equals(rp.getProfileConfMethodName())) {
         if (rp.isDefaultTarget()) {
            return 0;
         } else {
            return target.startsWith(rp.getTargetURL()) ? rp.getTargetURL().length() : -1;
         }
      } else {
         return -1;
      }
   }

   public SAMLRelyingPartyConfig findRelyingParty(String partnerId) {
      return (SAMLRelyingPartyConfig)this.findPartner(partnerId);
   }

   public SAMLRelyingPartyConfig findRelyingPartyByRequestParams(String profile, String target) {
      String key = makeTargetKey(profile, target);
      SAMLRelyingPartyConfig rp = (SAMLRelyingPartyConfig)this.findPartnerInTargetMap(key);
      if (rp == null) {
         rp = (SAMLRelyingPartyConfig)this.findPartnerInWildcardList(profile, (String)null, target);
      }

      return rp;
   }

   protected void handleStoreChanges(List objectIds) {
      if (this.areChangesInScope(objectIds)) {
         this.handleEntryChanged();
      }

   }

   public void firePartnerChange(SAMLPartnerEntry entry) {
      if (entry != null && entry instanceof SAMLRelyingPartyEntry && !this.isEmbeddedLDAP()) {
         this.handleEntryChanged();
      }

   }

   protected Class getStoreClass() {
      return BEASAMLRelyingParty.class;
   }

   private boolean areChangesInScope(List objectIds) {
      for(int i = 0; i < objectIds.size(); ++i) {
         if (objectIds.get(i) instanceof BEASAMLRelyingPartyId) {
            return true;
         }
      }

      return false;
   }
}
