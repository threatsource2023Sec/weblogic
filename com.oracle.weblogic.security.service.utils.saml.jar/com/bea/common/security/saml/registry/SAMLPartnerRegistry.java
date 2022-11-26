package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.store.data.BEASAMLPartner;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.service.StoreService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.providers.utils.BusinessObjectListerManager;
import weblogic.security.providers.utils.Utils;
import weblogic.security.spi.AuditMgmtEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditorService;
import weblogic.security.spi.SecurityServices;
import weblogic.security.utils.AuditBaseEventImpl;

public abstract class SAMLPartnerRegistry {
   private static final String PARTNER_ID_ATTR = SAMLPartnerEntry.getPartnerIdAttribute();
   private static final String PARTNER_ENABLED_ATTR = SAMLPartnerEntry.getPartnerEnabledAttribute();
   private static final String[] PARTNER_ID_ATTRS;
   private static final String OU_ATTR = "ou";
   private static final String DC_ATTR = "dc";
   private static final String OBJECT_CLASS_ATTR = "objectclass";
   protected static final String TOP_CLASS = "top";
   private static final String DOMAIN_CLASS = "domain";
   private static final String ORG_UNIT_CLASS = "organizationalUnit";
   private AuditorService auditor;
   protected LoggerSpi log = null;
   protected SecurityServices securityServices = null;
   protected StoreService storeService = null;
   protected BootStrapService bootStrapService = null;
   protected LegacyEncryptorSpi legacyEncryptor = null;
   private String domainName = null;
   private String realmName = null;
   private String domainDN = null;
   private String realmDN = null;
   private String registryDN = null;
   private String registryName = null;
   private static Map partnerChangeListeners;

   public abstract String getRegistryName();

   protected abstract String[] getEntryObjectClasses();

   protected abstract String[] getEntryAttributes();

   protected abstract String getBaseAuditEventType();

   protected abstract String makeNewPartnerId(int var1);

   protected abstract SAMLPartnerEntry makeNewEntryInstance();

   protected abstract SAMLPartnerEntry makeNewConfigInstance();

   protected abstract Class getBusinessObjectClass();

   protected abstract String getPartnerIdFromBusinessObject(Object var1);

   protected boolean isDebug() {
      return this.log == null ? false : this.log.isDebugEnabled();
   }

   protected void debug(String method, String info) {
      if (this.log != null) {
         String msg = this.registryName + "." + method + ": " + info;
         if (this.log.isDebugEnabled()) {
            this.log.debug(msg);
         }

      }
   }

   private void handleUnexpectedException(Throwable e) {
      throw new RuntimeException(e);
   }

   protected SAMLPartnerRegistry(ProviderMBean mbean, SecurityServices services) {
      String method = "constructor";
      this.auditor = services != null ? services.getAuditorService() : null;
      this.securityServices = services;
      if (this.securityServices != null) {
         this.log = ((ExtendedSecurityServices)this.securityServices).getLogger("SecuritySAMLLib");
      }

      this.realmName = mbean.getRealm().getName();
      if (this.securityServices != null) {
         this.domainName = Utils.getDomainName(this.securityServices);
         this.storeService = Utils.getStoreService(this.securityServices);
         this.bootStrapService = Utils.getBootStrapService(this.securityServices);
         this.legacyEncryptor = Utils.getLegacyEncryptorSpi(this.securityServices);
      }

      String providerName = mbean.getName();
      this.registryName = this.getRegistryName();
      this.domainDN = "dc=" + this.domainName;
      this.realmDN = "ou=" + this.realmName + "," + this.domainDN;
      this.registryDN = "ou=" + this.registryName + "," + this.realmDN;
      if (this.isDebug()) {
         this.debug(method, "succeeded.  Delegate = " + this);
      }

   }

   protected String getRegistryDN() {
      return this.registryDN;
   }

   protected String getDomainName() {
      return this.domainName;
   }

   protected String getRealmName() {
      return this.realmName;
   }

   private static void validatePartnerId(String partnerId) throws InvalidParameterException {
      if (partnerId == null || partnerId.length() < 1) {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullCertificateAlias());
      }
   }

   private String getNewPartnerId() {
      Set partnerIds = this.getAllPartnerIds();
      int nextNum = 1;

      while(true) {
         while(true) {
            String testId = this.makeNewPartnerId(nextNum);
            if (!partnerIds.contains(testId)) {
               try {
                  if (!this.partnerExists(testId)) {
                     return testId;
                  }
               } catch (InvalidParameterException var5) {
                  this.handleUnexpectedException(var5);
                  return null;
               }

               ++nextNum;
               partnerIds = this.getAllPartnerIds();
            } else {
               ++nextNum;
            }
         }
      }
   }

   private void validatePartner(SAMLPartnerEntry partner) throws InvalidParameterException {
      if (partner == null) {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullCertificateAlias());
      } else {
         partner.setEncryptionService(this.legacyEncryptor);
         partner.handleEncryption(false);
         partner.validate();
      }
   }

   public SAMLPartnerEntry getPartner(String partnerId) throws InvalidParameterException, NotFoundException {
      validatePartnerId(partnerId);
      return this.getPartnerByPartnerId(partnerId);
   }

   private SAMLPartnerEntry getPartnerByPartnerId(String partnerId) throws NotFoundException {
      List partners = null;

      try {
         partners = this.searchPartnerByWildcard(partnerId);
      } catch (Throwable var4) {
         this.debug("getPartnerByPartnerId", "Exception: " + var4.getMessage());
         throw new NotFoundException(ProvidersLogger.getSAMLCouldNotGetPartner(partnerId));
      }

      if (partners != null && partners.size() == 1) {
         return this.getPartnerFromBusinessObject(partners.get(0), false);
      } else {
         this.debug("getPartnerByPartnerId", "can not find the partner by id: " + partnerId);
         throw new NotFoundException(ProvidersLogger.getSAMLCouldNotGetPartner(partnerId));
      }
   }

   private SAMLPartnerEntry getPartnerFromBusinessObject(Object businessObject, boolean getConfigEntry) {
      if (businessObject == null) {
         return null;
      } else {
         SAMLPartnerEntry partner = null;
         if (getConfigEntry) {
            partner = this.makeNewConfigInstance();
         } else {
            partner = this.makeNewEntryInstance();
         }

         partner.setAttributesFromBusinessObject(businessObject);
         partner.setEncryptionService(this.legacyEncryptor);
         partner.handleEncryption(true);
         return partner;
      }
   }

   public Map getEnabledPartnerConfigs() {
      String method = "getEnabledPartnerConfigs";
      if (this.isDebug()) {
         this.debug(method, "");
      }

      HashMap partnerMap = new HashMap();

      try {
         String enabledPartnerFilter = "this.domainName == domain && this.realmName == realm && this.registryName == registryName && beaSAMLPartnerEnabled == 'true'";
         String declarations = "String domain, String realm, String registryName";
         Object[] parameters = new Object[]{this.getDomainName(), this.getRealmName(), this.getRegistryName()};
         Collection results = Utils.queryBusinessObjects(this.storeService, this.getBusinessObjectClass(), enabledPartnerFilter, declarations, parameters, 0);
         List partnerBusinessObjects = results != null && results.size() > 0 ? new ArrayList(results) : null;
         if (partnerBusinessObjects != null) {
            for(int i = 0; i < partnerBusinessObjects.size(); ++i) {
               Object businessObject = partnerBusinessObjects.get(i);
               if (businessObject != null) {
                  SAMLPartnerEntry partner = this.getPartnerFromBusinessObject(businessObject, true);

                  try {
                     this.debug(method, "Constructing partner '" + partner.getPartnerId() + "'");
                     partner.construct();
                     partnerMap.put(partner.getPartnerId(), partner);
                     this.debug(method, "Added partner '" + partner.getPartnerId() + "' to map");
                  } catch (InvalidParameterException var12) {
                     this.debug(method, "Exception while constructing partner '" + partner.getPartnerId() + "', partner will not be active: " + var12.toString());
                  }
               }
            }
         }
      } catch (Throwable var13) {
         this.handleUnexpectedException(var13);
      }

      if (this.isDebug()) {
         this.debug(method, "found " + partnerMap.size() + " enabled partners");
      }

      return partnerMap;
   }

   protected Set getAllPartnerIds() {
      String method = "getAllPartnerIds";
      if (this.isDebug()) {
         this.debug(method, "");
      }

      HashSet idSet = new HashSet();

      try {
         List allPartnerBusinessObjects = this.searchPartnerByWildcard("*");
         if (allPartnerBusinessObjects != null) {
            for(int i = 0; i < allPartnerBusinessObjects.size(); ++i) {
               Object businessObject = allPartnerBusinessObjects.get(i);
               if (businessObject != null) {
                  String id = this.getPartnerIdFromBusinessObject(businessObject);
                  if (id != null) {
                     idSet.add(id);
                  }
               }
            }
         }
      } catch (Throwable var7) {
         this.handleUnexpectedException(var7);
      }

      if (this.isDebug()) {
         this.debug(method, "found " + idSet.size() + " partnerIds");
      }

      return idSet;
   }

   private boolean partnerExistsInternal(String partnerId) {
      try {
         return this.getPartnerByPartnerId(partnerId) != null;
      } catch (NotFoundException var3) {
         return false;
      }
   }

   private SAMLPartnerEntry checkPartnerExists(String partnerId) throws NotFoundException {
      String method = "checkPartnerExists";

      try {
         return this.getPartnerByPartnerId(partnerId);
      } catch (NotFoundException var4) {
         if (this.isDebug()) {
            this.debug(method, "partnerId " + partnerId + " does not exist");
         }

         throw var4;
      }
   }

   public boolean partnerExists(String partnerId) throws InvalidParameterException {
      validatePartnerId(partnerId);
      String method = "partnerExists";
      if (this.isDebug()) {
         this.debug(method, "partnerId=" + partnerId);
      }

      boolean exists = false;
      exists = this.partnerExistsInternal(partnerId);
      if (this.isDebug()) {
         this.debug(method, "partnerId " + partnerId + (exists ? " exists" : " does not exist"));
      }

      return exists;
   }

   public SAMLPartnerEntry newPartnerInstance() {
      String method = "newPartnerObject";
      if (this.isDebug()) {
         this.debug(method, "");
      }

      SAMLPartnerEntry partner = this.makeNewEntryInstance();
      if (this.isDebug()) {
         this.debug(method, "made new partner object");
      }

      return partner;
   }

   protected void addPartner(SAMLPartnerEntry partner, boolean newPartnerID) throws CreateException, InvalidParameterException {
      this.validatePartner(partner);
      String partnerId = null;
      if (newPartnerID) {
         partnerId = this.getNewPartnerId();
      } else {
         partnerId = partner.getPartnerId();
      }

      if (partnerId != null && partnerId.length() != 0) {
         boolean doAudit = true;
         RuntimeException auditException = null;
         boolean var14 = false;

         try {
            var14 = true;
            this.addPartnerNoAudit(partnerId, partner);
            var14 = false;
         } catch (CreateException var15) {
            doAudit = false;
            throw var15;
         } catch (RuntimeException var16) {
            auditException = var16;
            throw var16;
         } finally {
            if (var14) {
               if (doAudit && this.auditor != null) {
                  String eventType = "addPartner";
                  String eventData = "<PartnerId = " + partnerId + ">";
                  this.auditMgmtEvent(eventType, eventData, auditException);
               }

            }
         }

         if (doAudit && this.auditor != null) {
            String eventType = "addPartner";
            String eventData = "<PartnerId = " + partnerId + ">";
            this.auditMgmtEvent(eventType, eventData, auditException);
         }

      } else {
         throw new InvalidParameterException(ProvidersLogger.getSAMLCouldNotAddPartnerWithoutId());
      }
   }

   public void addPartner(SAMLPartnerEntry partner) throws CreateException, InvalidParameterException {
      this.addPartner(partner, true);
   }

   private void addPartnerNoAudit(String partnerId, SAMLPartnerEntry partner) throws CreateException {
      try {
         this.addPartner(partnerId, partner);
      } catch (Exception var4) {
         this.handleUnexpectedException(var4);
      }

   }

   private void addPartner(String partnerId, SAMLPartnerEntry partner) throws Exception {
      String method = "addPartner";
      if (this.isDebug()) {
         this.debug(method, "partnerId=" + partnerId);
      }

      Object businessObject = partner.constructBusinessObject(this.getBusinessObjectClass(), partnerId, this.getRegistryName(), this.getRealmName(), this.getDomainName());
      Utils.persistBusinessObject(this.storeService, businessObject);
      partner.setAttribute(PARTNER_ID_ATTR, partnerId);
      if (this.isDebug()) {
         this.debug(method, "partner " + partnerId + " has been added to the registry");
      }

      this.notifyPartnerChanged(partner);
   }

   public void updatePartner(SAMLPartnerEntry partner) throws NotFoundException, InvalidParameterException {
      this.validatePartner(partner);
      String partnerId = partner.getPartnerId();
      validatePartnerId(partnerId);
      boolean doAudit = true;
      RuntimeException auditException = null;
      boolean var13 = false;

      try {
         var13 = true;
         this.updatePartnerNoAudit(partnerId, partner);
         var13 = false;
      } catch (NotFoundException var14) {
         doAudit = false;
         throw var14;
      } catch (RuntimeException var15) {
         auditException = var15;
         throw var15;
      } finally {
         if (var13) {
            if (doAudit && this.auditor != null) {
               String eventType = "updatePartner";
               String eventData = "<PartnerId = " + partnerId + ">";
               this.auditMgmtEvent(eventType, eventData, auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "updatePartner";
         String eventData = "<PartnerId = " + partnerId + ">";
         this.auditMgmtEvent(eventType, eventData, auditException);
      }

   }

   private void updatePartnerNoAudit(String partnerId, SAMLPartnerEntry partner) throws NotFoundException {
      try {
         this.updatePartner(partnerId, partner);
      } catch (NotFoundException var4) {
         throw var4;
      } catch (Throwable var5) {
         this.handleUnexpectedException(var5);
      }

   }

   private void updatePartner(String partnerId, SAMLPartnerEntry partner) throws Throwable {
      String method = "updatePartner";
      if (this.isDebug()) {
         this.debug(method, "partnerId=" + partnerId);
      }

      this.updateBusinessObject(partnerId, partner);
      this.notifyPartnerChanged(partner);
      if (this.isDebug()) {
         this.debug(method, "partner " + partnerId + " has been updated in the registry");
      }

   }

   public void removePartner(String partnerId) throws NotFoundException, InvalidParameterException {
      validatePartnerId(partnerId);
      boolean doAudit = true;
      RuntimeException auditException = null;
      boolean var12 = false;

      try {
         var12 = true;
         this.removePartnerNoAudit(partnerId);
         var12 = false;
      } catch (NotFoundException var13) {
         doAudit = false;
         throw var13;
      } catch (RuntimeException var14) {
         auditException = var14;
         throw var14;
      } finally {
         if (var12) {
            if (doAudit && this.auditor != null) {
               String eventType = "removePartner";
               String eventData = "<PartnerId = " + partnerId + ">";
               this.auditMgmtEvent(eventType, eventData, auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "removePartner";
         String eventData = "<PartnerId = " + partnerId + ">";
         this.auditMgmtEvent(eventType, eventData, auditException);
      }

   }

   private void removePartnerNoAudit(String partnerId) throws NotFoundException {
      try {
         this.removePartnerInternal(partnerId);
      } catch (NotFoundException var3) {
         throw var3;
      } catch (Throwable var4) {
         this.handleUnexpectedException(var4);
      }

   }

   private void removePartnerInternal(String partnerId) throws Throwable {
      String method = "removePartner";
      if (this.isDebug()) {
         this.debug(method, "partnerId=" + partnerId);
      }

      SAMLPartnerEntry partner = this.checkPartnerExists(partnerId);
      Class businessObjectClass = this.getBusinessObjectClass();
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.cn == partnerId";
      String declarations = "String domain, String realm, String registry, String partnerId";
      Object[] parameters = new Object[]{this.getDomainName(), this.getRealmName(), this.getRegistryName(), partnerId};
      Utils.deleteSingleBusinessObject(this.storeService, businessObjectClass, filter, declarations, parameters);
      if (this.isDebug()) {
         this.debug(method, "partner " + partnerId + " has been removed from the registry");
      }

      this.notifyPartnerChanged(partner);
   }

   protected String listPartners(BusinessObjectListerManager listerManager, String partnerIdWildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      if (partnerIdWildcard != null && partnerIdWildcard.length() >= 1) {
         if (maxToReturn < 0) {
            throw new InvalidParameterException(SecurityLogger.getMaximumToReturnCanNotBeLessThanZero());
         } else {
            List partners = null;

            try {
               partners = this.searchPartnerByWildcard(partnerIdWildcard);
            } catch (Throwable var6) {
               this.debug("listPartners", "search partner by wildcard failed: " + var6.getMessage());
            }

            List nameList = generateNameList(partners);
            return listerManager.addLister(nameList, maxToReturn);
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullCertificateAliasWildcard());
      }
   }

   private static List generateNameList(List businessObjects) {
      ArrayList nameList = new ArrayList();
      if (businessObjects != null) {
         for(int i = 0; i < businessObjects.size(); ++i) {
            BEASAMLPartner partner = (BEASAMLPartner)businessObjects.get(i);
            nameList.add(partner.getCn());
         }
      }

      return nameList;
   }

   private void auditMgmtEvent(String eventType, String eventData, Exception exception) {
      if (this.auditor != null) {
         this.auditor.providerAuditWriteEvent(new SAMLPartnerAuditMgmtEvent(this.constructEventType(eventType), eventData, exception));
      }
   }

   private String constructEventType(String eventType) {
      return this.getBaseAuditEventType() + " " + eventType;
   }

   protected List searchPartnerByWildcard(String partnerIdWildcard) throws Throwable {
      Class businessObjectClass = this.getBusinessObjectClass();
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.cn.matches(partnerIdPattern)";
      String declarations = "String domain, String realm, String registry, String partnerIdPattern";
      Object[] parameters = new Object[]{this.getDomainName(), this.getRealmName(), this.getRegistryName(), Utils.convertLDAPPatternForJDO(partnerIdWildcard, this.storeService)};
      Collection results = Utils.queryBusinessObjects(this.storeService, businessObjectClass, filter, declarations, parameters, 0);
      return results != null && results.size() > 0 ? new ArrayList(results) : null;
   }

   private void updateBusinessObject(String partnerId, SAMLPartnerEntry samlPartnerEntry) throws Throwable {
      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query q = null;

      try {
         Transaction t = pm.currentTransaction();
         t.begin();

         try {
            Class businessObjectClass = this.getBusinessObjectClass();
            String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.cn == partnerId";
            String declarations = "String domain, String realm, String registry, String partnerId";
            Object[] parameters = new Object[]{this.getDomainName(), this.getRealmName(), this.getRegistryName(), partnerId};
            q = pm.newQuery(businessObjectClass);
            q.setFilter(filter);
            q.declareParameters(declarations);
            Collection results = (Collection)q.executeWithArray(parameters);
            if (results.size() != 1) {
               this.debug("updateBusinessObject", "update businses object failed, can not determine the object to update");
               throw new Exception(ProvidersLogger.getSAMLCouldNotUpdateBusinessObject());
            }

            Object[] resultObjects = results.toArray();
            Object businessObjectToBeUpdated = samlPartnerEntry.constructBusinessObject(this.getBusinessObjectClass(), resultObjects[0], partnerId, this.getRegistryName(), this.getRealmName(), this.getDomainName());
            pm.makePersistent(businessObjectToBeUpdated);
         } catch (Throwable var16) {
            t.rollback();
            throw var16;
         }

         t.commit();
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }

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
         if (this.isDebug()) {
            this.log.debug("SAMLPartnerRegistry.removePartnerChangeListener()");
         }

         List list = (List)partnerChangeListeners.get(id);
         if (list != null) {
            list.remove(listener);
         }

      }
   }

   private void notifyPartnerChanged(SAMLPartnerEntry partner) {
      synchronized(partnerChangeListeners) {
         List list = (List)partnerChangeListeners.get(this.domainName + this.realmName);

         for(int i = 0; list != null && i < list.size(); ++i) {
            PartnerChangeListener listener = (PartnerChangeListener)list.get(i);
            if (listener != null) {
               try {
                  listener.firePartnerChange(partner);
               } catch (Exception var8) {
                  list.remove(listener);
                  if (this.isDebug()) {
                     this.log.debug("PartnerChangeListener.firePartnerChange() failed " + this.domainName + this.realmName + " on listener " + listener, var8);
                  }
               }
            }
         }

      }
   }

   static {
      PARTNER_ID_ATTRS = new String[]{PARTNER_ID_ATTR};
      partnerChangeListeners = new HashMap();
   }

   private static final class SAMLPartnerAuditMgmtEvent extends AuditBaseEventImpl implements AuditMgmtEvent {
      private String eventData;

      public SAMLPartnerAuditMgmtEvent(String eventType, String eventData, Exception exception) {
         super(exception == null ? AuditSeverity.INFORMATION : AuditSeverity.FAILURE, eventType, exception);
         this.eventData = eventData;
      }

      protected void writeAttributes(StringBuffer buf) {
         super.writeAttributes(buf);
         buf.append(this.eventData);
      }
   }
}
