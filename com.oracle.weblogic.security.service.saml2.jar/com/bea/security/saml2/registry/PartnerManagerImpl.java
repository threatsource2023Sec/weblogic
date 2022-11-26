package com.bea.security.saml2.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.store.data.Endpoint;
import com.bea.common.security.store.data.IdPPartnerId;
import com.bea.common.security.store.data.Partner;
import com.bea.common.security.store.data.PartnerId;
import com.bea.common.security.store.data.SPPartnerId;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.providers.registry.IdPPartner;
import com.bea.security.saml2.providers.registry.SPPartner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import kodo.jdo.KodoPersistenceManagerFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

class PartnerManagerImpl extends PartnerManager {
   private LoggerSpi log;
   private StoreService storeService;
   private String domainName;
   private String realmName;
   LegacyEncryptorSpi encryptSpi = null;
   private static final Map partnerChangeListeners = new HashMap();

   PartnerManagerImpl(LoggerSpi log, StoreService store, String realmName, String domainName, LegacyEncryptorSpi encryptSpi) {
      this.log = log;
      this.storeService = store;
      this.realmName = realmName;
      this.domainName = domainName;
      this.encryptSpi = encryptSpi;
   }

   public SPPartner findServiceProviderByIssuerURI(String uri) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;

      try {
         q = pm.newQuery(com.bea.common.security.store.data.SPPartner.class, "this.domainName == domain && this.realmName == realm && (this.issuerURI == uri || this.entityId == uri)");
         q.declareParameters("String domain, String realm, String uri");
         Collection partnerList = pm.detachCopyAll((Collection)q.execute(this.domainName, this.realmName, uri));
         Iterator i = partnerList.iterator();
         if (i.hasNext()) {
            SPPartner var6 = SAML2ObjectConverter.convertToREGSPPartner((com.bea.common.security.store.data.SPPartner)i.next(), this.encryptSpi);
            return var6;
         }
      } catch (JDOException var10) {
         if (this.debugEnabled()) {
            this.log.debug("get service provider partner with issuerURI [" + uri + "] error.");
         }

         throw new PartnerManagerException(var10);
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }

      return null;
   }

   public IdPPartner findIdentityProviderByIssuerURI(String uri) throws PartnerManagerException {
      return this.findIdentityProviderByIssuerURI(uri, (String)null);
   }

   public Collection listIdPPartners(String partnerNameWildcard, int maxToReturn) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;

      try {
         q = pm.newQuery(com.bea.common.security.store.data.IdPPartner.class, "this.domainName == domain && this.realmName == realm && this.name.matches(pattern)");
         if (maxToReturn < 0) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("maxToReturn should not be negative.");
            }

            throw new IllegalArgumentException("maxToReturn should not be negative.");
         } else {
            if (maxToReturn > 0) {
               q.getFetchPlan().setFetchSize(maxToReturn);
            }

            q.declareParameters("String domain, String realm, String pattern");
            String pattern = Utils.convertQueryPatternForJDO(partnerNameWildcard, this.storeService);
            Collection partnerList = pm.detachCopyAll((Collection)q.execute(this.domainName, this.realmName, pattern));
            ArrayList result = new ArrayList();
            Iterator i = partnerList.iterator();

            while(i.hasNext()) {
               result.add(SAML2ObjectConverter.convertToREGIdPPartner((com.bea.common.security.store.data.IdPPartner)i.next(), this.encryptSpi));
            }

            ArrayList var14 = result;
            return var14;
         }
      } catch (JDOException var12) {
         if (this.debugEnabled()) {
            this.log.debug("list identity provider partner with partnerName with wildcard [" + partnerNameWildcard + "] error.");
         }

         throw new PartnerManagerException(var12);
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }
   }

   public boolean idPPartnerExists(String partnerName) throws PartnerManagerException {
      IdPPartnerId id = new IdPPartnerId(this.domainName, this.realmName, partnerName);
      PersistenceManager pm = this.storeService.getPersistenceManager();

      boolean var5;
      try {
         id.getObject(pm);
         return true;
      } catch (JDOObjectNotFoundException var10) {
         var5 = false;
      } catch (Throwable var11) {
         if (this.debugEnabled()) {
            this.log.debug("check identity provider partner with name [" + partnerName + "] error.");
         }

         throw new PartnerManagerException(var11);
      } finally {
         pm.close();
      }

      return var5;
   }

   public IdPPartner getIdPPartner(String partnerName) throws PartnerManagerException {
      return SAML2ObjectConverter.convertToREGIdPPartner(this.getJDOIdPPartner(partnerName), this.encryptSpi);
   }

   public IdPPartner getIdPPartner(String partnerName, String targetResource, String issuerUri, String confirmationMethod) throws PartnerManagerException {
      if (partnerName != null && !partnerName.trim().equals("")) {
         return this.getIdPPartner(partnerName);
      } else if (targetResource != null && !targetResource.trim().equals("")) {
         PersistenceManager pm = this.storeService.getPersistenceManager();
         Query q = null;

         IdPPartner var16;
         try {
            q = pm.newQuery(com.bea.common.security.store.data.IdPPartner.class);
            String filter = null;
            Collection results = null;
            if (confirmationMethod != null && !confirmationMethod.equals("")) {
               filter = "this.domainName == domain && this.realmName == realm && this.confirmationMethod == confirmationMethod && this.issuerURI == uri";
               if (this.log.isDebugEnabled()) {
                  this.log.debug("Filter string to query IdPPartner:" + filter);
               }

               q.setFilter(filter);
               q.declareParameters("String domain, String realm, String confirmationMethod, String uri");
               Map paramMap = new HashMap();
               paramMap.put("domain", this.domainName);
               paramMap.put("realm", this.realmName);
               paramMap.put("confirmationMethod", confirmationMethod);
               paramMap.put("uri", issuerUri);
               results = pm.detachCopyAll((Collection)q.executeWithMap(paramMap));
            } else {
               filter = "this.domainName == domain && this.realmName == realm && this.issuerURI == uri";
               if (this.log.isDebugEnabled()) {
                  this.log.debug("Filter string to query IdPPartner:" + filter);
               }

               q.setFilter(filter);
               q.declareParameters("String domain, String realm, String uri");
               results = pm.detachCopyAll((Collection)q.execute(this.domainName, this.realmName, issuerUri));
            }

            results = this.filterResultsByAudienceURI(results, targetResource);
            if (results == null || results.size() == 0) {
               this.log.debug("can not find idp partner based on target resource " + targetResource + " and confirmation method " + confirmationMethod);
               throw new PartnerManagerException("There is no partner found for target resource " + targetResource + " and confirmation method " + confirmationMethod);
            }

            if (results.size() != 1) {
               com.bea.common.security.store.data.IdPPartner matchedPartner = (com.bea.common.security.store.data.IdPPartner)this.findBestMatch(results, targetResource);
               IdPPartner var10 = SAML2ObjectConverter.convertToREGIdPPartner(matchedPartner, this.encryptSpi);
               return var10;
            }

            this.log.debug("found idp partner with targetResource: " + targetResource);
            var16 = SAML2ObjectConverter.convertToREGIdPPartner(((com.bea.common.security.store.data.IdPPartner[])((com.bea.common.security.store.data.IdPPartner[])results.toArray(new com.bea.common.security.store.data.IdPPartner[0])))[0], this.encryptSpi);
         } catch (JDOException var14) {
            if (this.debugEnabled()) {
               this.log.debug("get idp provider partner with targetResource [" + targetResource + "] error.");
            }

            throw new PartnerManagerException(var14);
         } finally {
            if (q != null) {
               q.closeAll();
            }

            pm.close();
         }

         return var16;
      } else {
         throw new PartnerManagerException("Invalid partner name and target resource: both empty");
      }
   }

   public void addIdPPartner(IdPPartner partner) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      com.bea.common.security.store.data.IdPPartner idp = SAML2ObjectConverter.convertToJDOIdPPartner(partner, this.domainName, this.realmName, this.encryptSpi, this.log, true);
      PartnerId idpId = idp.getId();
      Transaction t = null;

      try {
         try {
            t = pm.currentTransaction();
            t.begin();
            pm.makePersistent(idp);
         } catch (Throwable var10) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("add new identity partner error. Roll back.");
            }

            throw new PartnerManagerException(var10);
         }

         t.commit();
      } finally {
         pm.close();
      }

      this.notifyPartnerChanged(new DefaultPartnerChangeEvent((PartnerId)null, idpId, (PartnerId)null));
   }

   public void updateIdPPartner(IdPPartner partner) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      IdPPartnerId idpId = new IdPPartnerId(this.domainName, this.realmName, partner.getName());
      com.bea.common.security.store.data.IdPPartner idp = null;
      Transaction t = null;

      try {
         try {
            t = pm.currentTransaction();
            t.begin();
            idp = (com.bea.common.security.store.data.IdPPartner)idpId.getObject(pm);
            List deleted = Utils.setIdPPartner(idp, partner, this.encryptSpi, this.log);
            if (deleted != null && deleted.size() != 0) {
               pm.deletePersistentAll(deleted);
            }
         } catch (JDOObjectNotFoundException var11) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("Update identity partner [" + partner.getName() + " error. the partner was not found.");
            }

            throw new PartnerManagerException(partner.toString());
         } catch (Throwable var12) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("Update identity partner [" + partner.getName() + " error. Roll back");
            }

            throw new PartnerManagerException(var12);
         }

         t.commit();
      } finally {
         pm.close();
      }

      this.notifyPartnerChanged(new DefaultPartnerChangeEvent((PartnerId)null, (PartnerId)null, idpId));
   }

   public void removeIdPPartner(String partnerName) throws PartnerManagerException {
      IdPPartnerId id = new IdPPartnerId(this.domainName, this.realmName, partnerName);
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Transaction t = null;

      try {
         try {
            t = pm.currentTransaction();
            t.begin();
            com.bea.common.security.store.data.IdPPartner idp = (com.bea.common.security.store.data.IdPPartner)id.getObject(pm);
            pm.deletePersistent(idp);
            Query q = pm.newQuery(Endpoint.class, "this.domainName == domain && this.realmName == realm && this.partnerName == name");

            try {
               q.declareParameters("String domain, String realm, String name");
               Map params = new HashMap();
               params.put("domain", this.domainName);
               params.put("realm", this.realmName);
               params.put("name", partnerName);
               q.deletePersistentAll(params);
            } finally {
               q.closeAll();
            }
         } catch (JDOObjectNotFoundException var19) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("Remove identity partner [" + partnerName + " error. the partner was not found.");
            }

            throw new PartnerManagerException(var19);
         } catch (Throwable var20) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("Remove identity partner [" + partnerName + " error. Roll back.");
            }

            throw new PartnerManagerException(var20);
         }

         t.commit();
      } finally {
         pm.close();
      }

      this.notifyPartnerChanged(new DefaultPartnerChangeEvent(id, (PartnerId)null, (PartnerId)null));
   }

   public Collection listSPPartners(String partnerNameWildcard, int maxToReturn) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;

      try {
         q = pm.newQuery(com.bea.common.security.store.data.SPPartner.class, "this.domainName == domain && this.realmName == realm && this.name.matches(pattern)");
         if (maxToReturn < 0) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("maxToReturn should not be negative.");
            }

            throw new IllegalArgumentException("maxToReturn should not be negative.");
         } else {
            if (maxToReturn > 0) {
               q.getFetchPlan().setFetchSize(maxToReturn);
            }

            q.declareParameters("String domain, String realm, String pattern");
            String pattern = Utils.convertQueryPatternForJDO(partnerNameWildcard, this.storeService);
            Collection partnerList = pm.detachCopyAll((Collection)q.execute(this.domainName, this.realmName, pattern));
            ArrayList result = new ArrayList();
            Iterator i = partnerList.iterator();

            while(i.hasNext()) {
               result.add(SAML2ObjectConverter.convertToREGSPPartner((com.bea.common.security.store.data.SPPartner)i.next(), this.encryptSpi));
            }

            ArrayList var14 = result;
            return var14;
         }
      } catch (JDOException var12) {
         if (this.debugEnabled()) {
            this.log.debug("list service provider partner with partnerName with wildcard [" + partnerNameWildcard + "] error.");
         }

         throw new PartnerManagerException(var12);
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }
   }

   public boolean spPartnerExists(String partnerName) throws PartnerManagerException {
      SPPartnerId id = new SPPartnerId(this.domainName, this.realmName, partnerName);
      PersistenceManager pm = this.storeService.getPersistenceManager();

      boolean var5;
      try {
         id.getObject(pm);
         return true;
      } catch (JDOObjectNotFoundException var10) {
         var5 = false;
      } catch (Throwable var11) {
         if (this.debugEnabled()) {
            this.log.debug("check identity provider partner with name [" + partnerName + "] error.");
         }

         throw new PartnerManagerException(var11);
      } finally {
         pm.close();
      }

      return var5;
   }

   public SPPartner getSPPartner(String partnerName) throws PartnerManagerException {
      return SAML2ObjectConverter.convertToREGSPPartner(this.getJDOSPPartner(partnerName), this.encryptSpi);
   }

   public SPPartner getSPPartner(String partnerName, String targetResource, String confirmationMethod) throws PartnerManagerException {
      if (partnerName != null && !partnerName.trim().equals("")) {
         return this.getSPPartner(partnerName);
      } else if (targetResource != null && !targetResource.trim().equals("")) {
         PersistenceManager pm = this.storeService.getPersistenceManager();
         Query q = null;

         SPPartner var9;
         try {
            q = pm.newQuery(com.bea.common.security.store.data.SPPartner.class);
            String filter = null;
            Collection results = null;
            if (confirmationMethod != null && !confirmationMethod.equals("")) {
               filter = "this.domainName == domain && this.realmName == realm && this.confirmationMethod == confirmationMethod";
               if (this.log.isDebugEnabled()) {
                  this.log.debug("Filter string to query SPPartner:" + filter);
               }

               q.setFilter(filter);
               q.declareParameters("String domain, String realm, String confirmationMethod");
               results = pm.detachCopyAll((Collection)q.execute(this.domainName, this.realmName, confirmationMethod));
            } else {
               filter = "this.domainName == domain && this.realmName == realm";
               if (this.log.isDebugEnabled()) {
                  this.log.debug("Filter string to query SPPartner:" + filter);
               }

               q.setFilter(filter);
               q.declareParameters("String domain, String realm");
               results = pm.detachCopyAll((Collection)q.execute(this.domainName, this.realmName));
            }

            results = this.filterResultsByAudienceURI(results, targetResource);
            if (results == null || results.size() == 0) {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("can not find sp partner based on target resource " + targetResource + " and confirmation method " + confirmationMethod);
               }

               throw new PartnerManagerException("No partner is found for " + targetResource + " and confirmation method " + confirmationMethod);
            }

            if (results.size() == 1) {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("found sp partner with targetResource: " + targetResource);
               }

               SPPartner var15 = SAML2ObjectConverter.convertToREGSPPartner(((com.bea.common.security.store.data.SPPartner[])((com.bea.common.security.store.data.SPPartner[])results.toArray(new com.bea.common.security.store.data.SPPartner[0])))[0], this.encryptSpi);
               return var15;
            }

            com.bea.common.security.store.data.SPPartner matchedPartner = (com.bea.common.security.store.data.SPPartner)this.findBestMatch(results, targetResource);
            var9 = SAML2ObjectConverter.convertToREGSPPartner(matchedPartner, this.encryptSpi);
         } catch (JDOException var13) {
            if (this.debugEnabled()) {
               this.log.debug("get service provider partner with targetResource [" + targetResource + "] error.");
            }

            throw new PartnerManagerException(var13);
         } finally {
            if (q != null) {
               q.closeAll();
            }

            pm.close();
         }

         return var9;
      } else {
         if (this.log.isDebugEnabled()) {
            this.log.debug("Invalid partner name and target resource: both empty");
         }

         throw new PartnerManagerException("Invalid partner name and target resource: both empty");
      }
   }

   private Collection filterResultsByAudienceURI(Collection coll, String targetResource) {
      Collection results = new ArrayList();
      String singlePlusMatchPattern = "target:+:" + targetResource;
      String singleMinusMatchPattern = "target:-:" + targetResource;
      String multipleMatchPattern = "target:*:" + targetResource;
      if (coll != null) {
         Iterator iter1 = coll.iterator();

         while(true) {
            while(true) {
               Partner partner;
               Collection audienceURIs;
               do {
                  if (!iter1.hasNext()) {
                     return results;
                  }

                  partner = (Partner)iter1.next();
                  audienceURIs = partner.getAudienceURIs();
               } while(audienceURIs == null);

               Iterator iter2 = audienceURIs.iterator();

               while(iter2.hasNext()) {
                  String audienceURI = (String)iter2.next();
                  if (audienceURI != null && (multipleMatchPattern.startsWith(audienceURI) || singlePlusMatchPattern.compareTo(audienceURI) == 0 || singleMinusMatchPattern.compareTo(audienceURI) == 0)) {
                     results.add(partner);
                     break;
                  }
               }
            }
         }
      } else {
         return results;
      }
   }

   private Partner findBestMatch(Collection results, String targetResource) throws PartnerManagerException {
      String singlePlusMatchPattern = "target:+:" + targetResource;
      String singleMinusMatchPattern = "target:-:" + targetResource;
      String multipleMatchPattern = "target:*:" + targetResource;
      if (this.log.isDebugEnabled()) {
         this.log.debug("There are multiple matches for target resource : " + targetResource);
      }

      int match = false;
      int bestMatch = 0;
      int nextBestMatch = 0;
      boolean foundFlag = false;
      boolean findNextFlag = false;
      boolean dupFlag = false;
      Partner matchedPartner = null;
      if (this.log.isDebugEnabled()) {
         this.log.debug("Try exact single match first.");
      }

      Iterator iter = results.iterator();

      while(true) {
         Partner partner;
         Collection uris;
         Iterator iter2;
         String uri;
         int match;
         while(iter.hasNext()) {
            partner = (Partner)iter.next();
            uris = partner.getAudienceURIs();
            iter2 = uris.iterator();

            while(iter2.hasNext()) {
               uri = (String)iter2.next();
               if (singlePlusMatchPattern.equals(uri) || singleMinusMatchPattern.equals(uri)) {
                  if (foundFlag) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("There are dupllicate matches for " + targetResource);
                     }

                     throw new PartnerManagerException("There are dupllicate exact matches for " + targetResource);
                  }

                  match = uri.length();
                  matchedPartner = partner;
                  foundFlag = true;
                  break;
               }
            }
         }

         if (!foundFlag) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("There is no exact single match and try wildcard match next.");
            }

            iter = results.iterator();

            while(iter.hasNext()) {
               partner = (Partner)iter.next();
               uris = partner.getAudienceURIs();
               iter2 = uris.iterator();

               while(iter2.hasNext()) {
                  uri = (String)iter2.next();
                  if (multipleMatchPattern.startsWith(uri)) {
                     if (!foundFlag) {
                        foundFlag = true;
                     }

                     match = uri.length();
                     if (!findNextFlag) {
                        if (match > bestMatch) {
                           bestMatch = match;
                        }
                     } else if (match > nextBestMatch) {
                        nextBestMatch = match;
                     }
                  }
               }

               if (!findNextFlag && bestMatch > 0) {
                  findNextFlag = true;
                  matchedPartner = partner;
               }

               if (findNextFlag && nextBestMatch == bestMatch && bestMatch != 0 && !dupFlag) {
                  dupFlag = true;
               }

               if (findNextFlag && nextBestMatch > bestMatch) {
                  bestMatch = nextBestMatch;
                  nextBestMatch = 0;
                  matchedPartner = partner;
                  if (dupFlag) {
                     dupFlag = false;
                  }
               }
            }
         }

         if (dupFlag) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("There are dupllicate wildcard matches for " + targetResource);
            }

            throw new PartnerManagerException("There are dupllicate wildcard matches for " + targetResource);
         }

         if (!foundFlag) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("There is no match found for " + targetResource);
            }

            throw new PartnerManagerException("There is no match found for " + targetResource);
         }

         return matchedPartner;
      }
   }

   public void addSPPartner(SPPartner partner) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      com.bea.common.security.store.data.SPPartner idp = SAML2ObjectConverter.convertToJDOSPPartner(partner, this.domainName, this.realmName, this.encryptSpi, this.log, true);
      Transaction t = null;

      try {
         try {
            t = pm.currentTransaction();
            t.begin();
            pm.makePersistent(idp);
         } catch (Throwable var9) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("add new service provider partner error. Roll back.");
            }

            throw new PartnerManagerException(var9);
         }

         t.commit();
      } finally {
         pm.close();
      }

   }

   public void updateSPPartner(SPPartner partner) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      SPPartnerId idpId = new SPPartnerId(this.domainName, this.realmName, partner.getName());
      com.bea.common.security.store.data.SPPartner sp = null;
      Transaction t = null;

      try {
         try {
            t = pm.currentTransaction();
            sp = (com.bea.common.security.store.data.SPPartner)idpId.getObject(pm);
            t.begin();
            List deleted = Utils.setSPPartner(sp, partner, this.encryptSpi, this.log);
            if (deleted != null && deleted.size() != 0) {
               pm.deletePersistentAll(deleted);
            }
         } catch (JDOObjectNotFoundException var11) {
            if (this.debugEnabled()) {
               this.log.debug("Update service provider partner [" + partner.getName() + " error. the partner was not found.");
            }

            if (t.isActive()) {
               t.rollback();
            }

            throw new PartnerManagerException(partner.toString());
         } catch (Throwable var12) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("Update service provider partner [" + partner.getName() + " error. Roll back");
            }

            throw new PartnerManagerException(var12);
         }

         t.commit();
      } finally {
         pm.close();
      }

   }

   public void removeSPPartner(String partnerName) throws PartnerManagerException {
      SPPartnerId id = new SPPartnerId(this.domainName, this.realmName, partnerName);
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Transaction t = null;

      try {
         try {
            t = pm.currentTransaction();
            t.begin();
            com.bea.common.security.store.data.SPPartner sp = (com.bea.common.security.store.data.SPPartner)id.getObject(pm);
            pm.deletePersistent(sp);
            Query q = pm.newQuery(Endpoint.class, "this.domainName == domain && this.realmName == realm && this.partnerName == name");

            try {
               q.declareParameters("String domain, String realm, String name");
               Map params = new HashMap();
               params.put("domain", this.domainName);
               params.put("realm", this.realmName);
               params.put("name", partnerName);
               q.deletePersistentAll(params);
            } finally {
               q.closeAll();
            }
         } catch (JDOObjectNotFoundException var19) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("Remove service provider partner [" + partnerName + " error. The partner was not found.");
            }

            throw new PartnerManagerException(var19);
         } catch (Throwable var20) {
            if (t.isActive()) {
               t.rollback();
            }

            if (this.debugEnabled()) {
               this.log.debug("Remove service provider partner [" + partnerName + " error. Roll back.");
            }

            throw new PartnerManagerException(var20);
         }

         t.commit();
      } finally {
         pm.close();
      }

      this.notifyPartnerChanged(new DefaultPartnerChangeEvent(id, (PartnerId)null, (PartnerId)null));
   }

   private com.bea.common.security.store.data.IdPPartner getJDOIdPPartner(String name) throws PartnerManagerException {
      IdPPartnerId id = new IdPPartnerId(this.domainName, this.realmName, name);
      PersistenceManager pm = this.storeService.getPersistenceManager();

      Object var5;
      try {
         com.bea.common.security.store.data.IdPPartner var4 = (com.bea.common.security.store.data.IdPPartner)id.getObject(pm, true);
         return var4;
      } catch (JDOObjectNotFoundException var10) {
         var5 = null;
      } catch (Throwable var11) {
         if (this.debugEnabled()) {
            this.log.debug("Get JDO idenrity provider partner" + name + " error.");
         }

         throw new PartnerManagerException(var11);
      } finally {
         pm.close();
      }

      return (com.bea.common.security.store.data.IdPPartner)var5;
   }

   private com.bea.common.security.store.data.SPPartner getJDOSPPartner(String name) throws PartnerManagerException {
      SPPartnerId id = new SPPartnerId(this.domainName, this.realmName, name);
      PersistenceManager pm = this.storeService.getPersistenceManager();

      Object var5;
      try {
         com.bea.common.security.store.data.SPPartner var4 = (com.bea.common.security.store.data.SPPartner)id.getObject(pm, true);
         return var4;
      } catch (JDOObjectNotFoundException var10) {
         var5 = null;
      } catch (Throwable var11) {
         if (this.debugEnabled()) {
            this.log.debug("Get JDO service provider partner" + name + " error.");
         }

         throw new PartnerManagerException(var11);
      } finally {
         pm.close();
      }

      return (com.bea.common.security.store.data.SPPartner)var5;
   }

   private boolean debugEnabled() {
      return this.log != null && this.log.isDebugEnabled();
   }

   public void addPartnerChangeListener(String id, PartnerChangeListener listener) {
      synchronized(partnerChangeListeners) {
         List list = (List)partnerChangeListeners.get(id);
         if (list == null) {
            list = new ArrayList();
            partnerChangeListeners.put(id, list);
         }

         ((List)list).add(listener);
      }
   }

   public void removePartnerChangeListener(String id, PartnerChangeListener listener) {
      synchronized(partnerChangeListeners) {
         if (this.debugEnabled()) {
            this.log.debug("invoke PartnerManagerImpl.removePartnerChangeLister()");
         }

         List list = (List)partnerChangeListeners.get(id);
         if (list != null) {
            list.remove(listener);
         }

      }
   }

   private void notifyPartnerChanged(PartnerChangeEvent event) {
      synchronized(partnerChangeListeners) {
         List list = (List)partnerChangeListeners.get(this.domainName + this.realmName);

         for(int i = 0; list != null && i < list.size(); ++i) {
            PartnerChangeListener listener = (PartnerChangeListener)list.get(i);
            if (listener != null) {
               try {
                  listener.partnerChanged(event);
               } catch (Exception var8) {
                  list.remove(listener);
                  if (this.debugEnabled()) {
                     this.log.debug("PartnerChangeListener.partnerChanged() failed " + this.domainName + this.realmName + " on listener " + listener, var8);
                  }
               }
            }
         }

      }
   }

   public Collection getPartnerMetaData(Class clazz) {
      KodoPersistenceManagerFactory pmf = (KodoPersistenceManagerFactory)this.storeService.getPersistenceManagerFactory();
      OpenJPAConfiguration config = pmf.getConfiguration();
      ClassMetaData cmd = config.getMetaDataRepositoryInstance().getMetaData(clazz, clazz.getClassLoader(), true);
      FieldMetaData[] fields = cmd.getFields();
      return Arrays.asList(fields);
   }

   public IdPPartner findIdentityProviderByIssuerURI(String uri, String type) throws PartnerManagerException {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;

      try {
         q = pm.newQuery(com.bea.common.security.store.data.IdPPartner.class, "this.domainName == domain && this.realmName == realm && (this.issuerURI == uri || this.entityId == uri)");
         q.declareParameters("String domain, String realm, String uri");
         Collection partnerList = pm.detachCopyAll((Collection)q.execute(this.domainName, this.realmName, uri));
         Iterator i = partnerList.iterator();

         while(i.hasNext()) {
            com.bea.common.security.store.data.IdPPartner idp = (com.bea.common.security.store.data.IdPPartner)i.next();
            if (type == null || idp.getPartnerType().equals(type)) {
               IdPPartner var8 = SAML2ObjectConverter.convertToREGIdPPartner(idp, this.encryptSpi);
               return var8;
            }
         }
      } catch (JDOException var12) {
         if (this.debugEnabled()) {
            this.log.debug("get identity provider partner with issuerURI [" + uri + "] error.");
         }

         throw new PartnerManagerException(var12);
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }

      return null;
   }

   private static class DefaultPartnerChangeEvent implements PartnerChangeEvent {
      final PartnerId deletedPartnerId;
      final PartnerId addedPartnerId;
      final PartnerId updatedPartnerId;

      private DefaultPartnerChangeEvent(PartnerId deletedPartnerId, PartnerId addedPartnerId, PartnerId updatedPartnerId) {
         this.deletedPartnerId = deletedPartnerId;
         this.addedPartnerId = addedPartnerId;
         this.updatedPartnerId = updatedPartnerId;
      }

      public PartnerId getDeletedPartnerId() {
         return this.deletedPartnerId;
      }

      public PartnerId getAddedPartnerId() {
         return this.addedPartnerId;
      }

      public PartnerId getUpdatedPartnerId() {
         return this.updatedPartnerId;
      }

      // $FF: synthetic method
      DefaultPartnerChangeEvent(PartnerId x0, PartnerId x1, PartnerId x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
