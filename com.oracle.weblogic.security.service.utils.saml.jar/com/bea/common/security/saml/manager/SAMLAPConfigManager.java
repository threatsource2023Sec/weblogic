package com.bea.common.security.saml.manager;

import com.bea.common.security.saml.registry.SAMLAssertingPartyConfig;
import com.bea.common.security.saml.registry.SAMLAssertingPartyEntry;
import com.bea.common.security.saml.registry.SAMLAssertingPartyRegistry;
import com.bea.common.security.saml.registry.SAMLPartnerEntry;
import com.bea.common.security.saml.registry.SAMLPartnerRegistry;
import com.bea.common.security.saml.registry.SAMLV1ConfigHelper;
import com.bea.common.security.saml.utils.SAMLUtil;
import com.bea.common.security.store.data.BEASAMLAssertingParty;
import com.bea.common.security.store.data.BEASAMLAssertingPartyId;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import weblogic.management.security.ProviderMBean;
import weblogic.security.spi.ProviderInitializationException;
import weblogic.security.spi.SecurityServices;

public class SAMLAPConfigManager extends SAMLPartnerConfigManager {
   private Map redirectMap = null;
   private Map sourceSiteMap = null;
   private String defaultNameMapper = null;
   private static SecurityServices securityServices = null;

   private SAMLAPConfigManager(ProviderMBean mbean, SecurityServices services) {
      super(mbean, services);
   }

   public static SAMLAPConfigManager getManager() {
      return (SAMLAPConfigManager)getManagerInstance(0);
   }

   public static void setManager(SAMLAPConfigManager manager) {
      setManagerInstance(0, manager);
   }

   public static synchronized SAMLAPConfigManager getManager(ProviderMBean mbean, SecurityServices services) {
      SAMLAPConfigManager manager = getManager();
      if (manager != null && securityServices != null && securityServices != services) {
         manager = null;
      }

      if (manager == null) {
         securityServices = services;
         manager = new SAMLAPConfigManager(mbean, services);
         setManager(manager);
      }

      return getManager();
   }

   private void handleEntryChanged() {
      this.logDebug("handleEntryChanged", "Building maps ...");
      this.buildMaps();
      this.handleDefaultRegistryChange();
   }

   private void buildMaps() {
      this.buildPartnerMaps();
      this.buildRedirectMap();
      this.buildSourceSiteMap();
   }

   private synchronized void setDefaults(String defaultNameMapper) {
      this.defaultNameMapper = defaultNameMapper;
   }

   public synchronized void setConfig(String nameMapper) {
      this.setDefaults(nameMapper);
      this.buildMaps();
   }

   private void buildV1Maps(Properties assertions, Properties redirects) {
      this.buildV1PartnerMaps(assertions);
      this.buildV1RedirectMap(redirects);
      this.buildSourceSiteMap();
   }

   public synchronized void setV1Config(Properties assertions, Properties redirects, String nameMapper) {
      this.setDefaults(nameMapper);
      this.buildV1Maps(assertions, redirects);
   }

   public synchronized Map getRedirectMap() {
      return this.redirectMap;
   }

   public synchronized Map getSourceSiteMap() {
      return this.sourceSiteMap;
   }

   protected String getManagerName() {
      return "SAMLAPConfigManager";
   }

   protected String getDebugLoggerName() {
      return "SecuritySAMLAtn";
   }

   protected SAMLPartnerRegistry getRegistryInstance(ProviderMBean mbean, SecurityServices services) {
      return new SAMLAssertingPartyRegistry(mbean, services);
   }

   protected SAMLPartnerEntry getPartnerInstance() {
      return new SAMLAssertingPartyConfig(this.LOGGER, this.legacyEncryptor);
   }

   protected synchronized boolean applyPartnerDefaults(SAMLPartnerEntry partner) {
      SAMLAssertingPartyConfig config = (SAMLAssertingPartyConfig)partner;
      String name = config.getNameMapperClass();
      if (name == null || name.length() == 0) {
         name = this.defaultNameMapper;
      }

      config.setNameMapperClass(name);
      return true;
   }

   protected String getPartnerTargetKey(SAMLPartnerEntry partner) {
      SAMLAssertingPartyConfig ap = (SAMLAssertingPartyConfig)partner;
      if (ap.getTargetURL() != null) {
         return !ap.isWildcardTarget() && !ap.isDefaultTarget() ? makeTargetKey(ap.getProfileConfMethodName(), ap.getIssuerURI(), ap.getTargetURL()) : "wildcard";
      } else {
         return null;
      }
   }

   private static String makeTargetKey(String profile, String issuer, String target) {
      return profile + ":" + issuer + "&" + target;
   }

   protected int testPartnerWildcardMatch(String profile, String issuer, String target, SAMLPartnerEntry partner) {
      SAMLAssertingPartyConfig ap = (SAMLAssertingPartyConfig)partner;
      if (ap.getTargetURL() != null && profile.equals(ap.getProfileConfMethodName()) && issuer.equals(ap.getIssuerURI())) {
         if (ap.isDefaultTarget()) {
            return 0;
         } else {
            return target.startsWith(ap.getTargetURL()) ? ap.getTargetURL().length() : -1;
         }
      } else {
         return -1;
      }
   }

   public SAMLAssertingPartyConfig findAssertingParty(String partnerId) {
      return (SAMLAssertingPartyConfig)this.findPartner(partnerId);
   }

   public SAMLAssertingPartyConfig findAssertingPartyByRequestParams(String profile, String issuer, String target) {
      String key = makeTargetKey(profile, issuer, target);
      SAMLAssertingPartyConfig ap = (SAMLAssertingPartyConfig)this.findPartnerInTargetMap(key);
      if (ap == null) {
         ap = (SAMLAssertingPartyConfig)this.findPartnerInWildcardList(profile, issuer, target);
      }

      return ap;
   }

   public SAMLAssertingPartyConfig findAssertingPartyBySourceId(String sourceId) {
      return (SAMLAssertingPartyConfig)this.sourceSiteMap.get(sourceId);
   }

   private synchronized void buildRedirectMap() throws ProviderInitializationException {
      HashMap tmpRedirectMap = new HashMap();
      Collection partners = this.partnerMap.values();
      Iterator it = partners.iterator();

      while(true) {
         while(it.hasNext()) {
            SAMLAssertingPartyConfig partner = (SAMLAssertingPartyConfig)it.next();
            String itsURL = partner.getIntersiteTransferURL();
            String[] redirectURIs = partner.getRedirectURIs();
            if (itsURL != null && itsURL.length() > 0 && redirectURIs != null) {
               this.logDebug("buildRedirectMap", "Redirects configured for partner '" + partner.getPartnerId() + "'");
               itsURL = SAMLUtil.buildURLWithParams(itsURL, partner.getIntersiteTransferParams());
               this.logDebug("buildRedirectMap", "ITS URL with Params is '" + itsURL + "'");

               for(int i = 0; redirectURIs != null && i < redirectURIs.length; ++i) {
                  if (redirectURIs[i] != null) {
                     String tmpURL = (String)tmpRedirectMap.get(redirectURIs[i]);
                     if (tmpURL != null && !tmpURL.equals(itsURL)) {
                        this.logDebug("buildRedirectMap", "Conflicting redirects for '" + redirectURIs[i] + "'");
                     }

                     this.logDebug("buildRedirectMap", "Redirect URI " + i + " is '" + redirectURIs[i] + "'");
                     tmpRedirectMap.put(redirectURIs[i], itsURL);
                  }
               }
            } else {
               this.logDebug("buildRedirectMap", "Partner '" + partner.getPartnerId() + "' has no redirects configured");
            }
         }

         this.redirectMap = tmpRedirectMap;
         return;
      }
   }

   private synchronized void buildV1RedirectMap(Properties redirects) throws ProviderInitializationException {
      HashMap tmpRedirectMap = new HashMap();
      if (redirects != null) {
         SAMLV1ConfigHelper redirectHelper = new SAMLV1ConfigHelper(this.LOGGER, redirects, "Redirects");

         while(redirectHelper.hasMoreEntries()) {
            Map entry = redirectHelper.getNextEntry();
            if (entry != null) {
               String itsURL = (String)entry.get("beaSAMLIntersiteTransferURL") + "?";
               String redirectURI = (String)entry.get("beaSAMLRedirectURIs");
               if (itsURL != null && redirectURI != null) {
                  String tmpURL = (String)tmpRedirectMap.get(redirectURI);
                  if (tmpURL != null && !tmpURL.equals(itsURL)) {
                     this.logDebug("buildV1RedirectMap", "Conflicting redirects for '" + redirectURI + "'");
                  }

                  tmpRedirectMap.put(redirectURI, itsURL);
               }
            }
         }
      }

      this.redirectMap = tmpRedirectMap;
   }

   private synchronized void buildSourceSiteMap() {
      HashMap tmpSourceSiteMap = new HashMap();
      this.logDebug("buildSourceSiteMap", "Building map of configured source sites for artifact profile");
      Collection partners = this.partnerMap.values();
      Iterator it = partners.iterator();

      while(it.hasNext()) {
         SAMLAssertingPartyConfig partner = (SAMLAssertingPartyConfig)it.next();
         if (partner.getProfileId() == 2) {
            String srcIdHex = partner.getSourceIdHex();
            tmpSourceSiteMap.put(srcIdHex, partner);
         }
      }

      this.sourceSiteMap = tmpSourceSiteMap;
   }

   protected void handleStoreChanges(List objectIds) {
      if (this.areChangesInScope(objectIds)) {
         this.handleEntryChanged();
      }

   }

   public void firePartnerChange(SAMLPartnerEntry entry) {
      if (entry != null && entry instanceof SAMLAssertingPartyEntry && !this.isEmbeddedLDAP()) {
         this.handleEntryChanged();
      }

   }

   protected Class getStoreClass() {
      return BEASAMLAssertingParty.class;
   }

   private boolean areChangesInScope(List objectIds) {
      for(int i = 0; i < objectIds.size(); ++i) {
         if (objectIds.get(i) instanceof BEASAMLAssertingPartyId) {
            return true;
         }
      }

      return false;
   }
}
