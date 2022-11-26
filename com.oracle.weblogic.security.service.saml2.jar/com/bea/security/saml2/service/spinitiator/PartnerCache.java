package com.bea.security.saml2.service.spinitiator;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.store.data.IdPPartner;
import com.bea.common.security.store.data.IdPPartnerId;
import com.bea.common.security.store.data.PartnerId;
import com.bea.common.security.utils.URLMatchMap;
import com.bea.common.store.service.RemoteCommitEvent;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;
import com.bea.security.saml2.registry.PartnerChangeEvent;
import com.bea.security.saml2.registry.PartnerChangeListener;
import com.bea.security.saml2.registry.PartnerManager;
import com.bea.security.saml2.registry.PartnerManagerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class PartnerCache implements RemoteCommitListener, PartnerChangeListener {
   private final URLMatchMap redirectURIMap = new URLMatchMap();
   private PartnerManager partnerManager = null;
   private LoggerSpi log = null;
   private String domainName;
   private String realmName;

   PartnerCache(SAML2ConfigSpi config) {
      this.domainName = config.getDomainName();
      this.realmName = config.getRealmName();
      StoreService store = config.getStoreService();
      store.addRemoteCommitListener(IdPPartner.class, this);
      this.partnerManager = config.getPartnerManager();
      String id = this.domainName + this.realmName;
      this.partnerManager.addPartnerChangeListener(id, this);
      this.log = config.getLogger();
      this.buildMap();
   }

   public void afterCommit(RemoteCommitEvent event) {
      Collection deletedObjectIds = event.getDeletedObjectIds();
      Collection updatedObjectIds = event.getUpdatedObjectIds();
      Collection addedObjectIds = event.getAddedObjectIds();
      List objectIds = new ArrayList();
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

   WebSSOIdPPartner getIdPByRedirectURI(String redirectURI) {
      if (redirectURI == null) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("Redirect URI is null.");
         }

         return null;
      } else {
         String name = this.getPartnerName(redirectURI);
         if (name == null) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Can not get the partner name for the redirect URI [" + redirectURI + "].");
            }

            return null;
         } else {
            WebSSOIdPPartner partner = null;

            try {
               partner = (WebSSOIdPPartner)this.partnerManager.getIdPPartner(name);
               return partner;
            } catch (PartnerManagerException var5) {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("get IdP partner: [" + name + "] from store error.", var5);
               }

               return null;
            }
         }
      }
   }

   private void buildMap() {
      synchronized(this.redirectURIMap) {
         Collection idps;
         try {
            idps = this.partnerManager.listIdPPartners("*", 0);
         } catch (Exception var11) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("NONFATAL: Unable to update redirect URI cache from store: " + var11.toString());
            }

            return;
         }

         this.redirectURIMap.clear();
         if (idps != null) {
            Iterator var3 = idps.iterator();

            label54:
            while(true) {
               com.bea.security.saml2.providers.registry.IdPPartner idp;
               String[] redirectURIs;
               do {
                  do {
                     if (!var3.hasNext()) {
                        break label54;
                     }

                     idp = (com.bea.security.saml2.providers.registry.IdPPartner)var3.next();
                  } while(!(idp instanceof WebSSOIdPPartner));

                  redirectURIs = ((WebSSOIdPPartner)idp).getRedirectURIs();
               } while(redirectURIs == null);

               String[] var6 = redirectURIs;
               int var7 = redirectURIs.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String uri = var6[var8];
                  this.redirectURIMap.put(uri, idp.getName());
               }
            }
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug("Redirect URI cache updated.");
         }

      }
   }

   public void partnerChanged(PartnerChangeEvent event) {
      PartnerId deletedPartnerId = event.getDeletedPartnerId();
      PartnerId updatedPartnerId = event.getUpdatedPartnerId();
      PartnerId addedPartnerId = event.getAddedPartnerId();
      List partnerIds = new ArrayList();
      if (deletedPartnerId != null) {
         partnerIds.add(deletedPartnerId);
      }

      if (updatedPartnerId != null) {
         partnerIds.add(updatedPartnerId);
      }

      if (addedPartnerId != null) {
         partnerIds.add(addedPartnerId);
      }

      this.handleStoreChanges(partnerIds);
   }

   private String getPartnerName(String redirectURI) {
      synchronized(this.redirectURIMap) {
         String name = (String)this.redirectURIMap.get(redirectURI);
         return name;
      }
   }

   private void handleStoreChanges(List objectIds) {
      if (this.areChangesInScope(objectIds)) {
         this.buildMap();
      }

   }

   private boolean areChangesInScope(List objectIds) {
      for(int i = 0; i < objectIds.size(); ++i) {
         Object objectId = objectIds.get(i);
         if (objectId instanceof IdPPartnerId) {
            IdPPartnerId partnerId = (IdPPartnerId)objectId;
            String aDomainName = partnerId.getDomainName();
            String aRealmName = partnerId.getRealmName();
            if (aDomainName.equals(this.domainName) && aRealmName.equals(this.realmName)) {
               return true;
            }
         }
      }

      return false;
   }

   public void removePartnerChangeListener() {
      String id = this.domainName + this.realmName;
      this.partnerManager.removePartnerChangeListener(id, this);
   }
}
