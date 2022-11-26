package weblogic.security.providers.utils;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.store.service.StoreService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import weblogic.management.utils.ErrorCollectionException;

class StoreServiceBasedCertRegStore implements CertRegStore {
   private String domainName;
   private String realmName;
   private StoreService storeService = null;
   protected LoggerSpi log = null;

   private boolean isDebug() {
      return this.log == null ? false : this.log.isDebugEnabled();
   }

   private void debug(String method, String info) {
      if (this.log != null) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("StoreServiceBasedCertStore." + method + ": " + info);
         }

      }
   }

   private static void handleUnexpectedException(Throwable e) {
      throw new RuntimeException(e);
   }

   public StoreServiceBasedCertRegStore(String domainName, String realmName, StoreService storeService, LoggerSpi log) {
      this.domainName = domainName;
      this.realmName = realmName;
      this.storeService = storeService;
      this.log = log;
   }

   public void registerCertificate(WLSCertRegEntry entry) throws Exception {
      entry.setRealmName(this.realmName);
      entry.setDomainName(this.domainName);
      Utils.persistBusinessObject(this.storeService, convert2JDOEntry(entry));
   }

   public Collection registerCertificate(String group, Collection regEntries, ErrorCollectionException errorCollectionException) {
      String method = "registerCertificate";
      Collection results = new ArrayList();
      Iterator var6 = regEntries.iterator();

      while(var6.hasNext()) {
         WLSCertRegEntry entry = (WLSCertRegEntry)var6.next();

         try {
            this.registerCertificate(entry);
            results.add(entry);
         } catch (Exception var9) {
            if (this.isDebug()) {
               this.debug(method, "group " + group + ", alias " + entry.getCn() + " have not been added to the registry, exception: " + var9.getMessage());
            }

            errorCollectionException.add(var9);
         }

         if (this.isDebug()) {
            this.debug(method, "group " + group + ", alias " + entry.getCn() + " have been added to the registry");
         }
      }

      return results;
   }

   public void unregisterCertificate(String group, String alias) throws Throwable {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.cn == alias";
      String declarations = "String domain, String realm, String registry, String alias";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group, alias};
      Utils.deleteSingleBusinessObject(this.storeService, com.bea.common.security.store.data.WLSCertRegEntry.class, filter, declarations, parameters);
   }

   public void unregisterGroup(String group) throws Throwable {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry";
      String declarations = "String domain, String realm, String registry";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group};
      Utils.deleteBusinessObject(this.storeService, com.bea.common.security.store.data.WLSCertRegEntry.class, filter, declarations, parameters);
   }

   public WLSCertRegEntry getRegEntryByAlias(String group, String alias) {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.cn == alias";
      String declarations = "String domain, String realm, String registry, String alias";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group, alias};
      return this.getRegEntry(filter, declarations, parameters);
   }

   public WLSCertRegEntry getRegEntryByIssuerDN(String group, String issuerDN, String serialNumber) {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.wlsCertRegIssuerDN == issuerDN && this.wlsCertRegSerialNumber == serialNumber";
      String declarations = "String domain, String realm, String registry, String issuerDN, String serialNumber";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group, issuerDN, serialNumber};
      return this.getRegEntry(filter, declarations, parameters);
   }

   public WLSCertRegEntry getRegEntryBySubjectDN(String group, String subjectDN) {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.wlsCertRegSubjectDN == subjectDN";
      String declarations = "String domain, String realm, String registry, String subjectDN";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group, subjectDN};
      return this.getRegEntry(filter, declarations, parameters);
   }

   public WLSCertRegEntry getRegEntryBySubjectKeyId(String group, String subjectKeyId) {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.wlsCertRegSubjectKeyIdentifier == subjectKeyId";
      String declarations = "String domain, String realm, String registry, String subjectKeyId";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group, subjectKeyId};
      return this.getRegEntry(filter, declarations, parameters);
   }

   public Collection getRegEntries(String group, int maxToReturn) {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry";
      String declarations = "String domain, String realm, String registry";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group};
      return this.getRegEntries(filter, declarations, parameters, maxToReturn);
   }

   public Collection getRegEntriesByAliasPattern(String group, String aliasPattern, int maxToReturn) {
      String filter = "this.domainName == domain && this.realmName == realm && this.registryName == registry && this.cn.matches(aliasPattern)";
      String declarations = "String domain, String realm, String registry, String aliasPattern";
      Object[] parameters = new Object[]{this.domainName, this.realmName, group, Utils.convertLDAPPatternForJDO(aliasPattern, this.storeService)};
      return this.getRegEntries(filter, declarations, parameters, maxToReturn);
   }

   public Collection getRegEntriesByRegistryPattern(String groupPattern, String alias, int maxToReturn) {
      String filter = "this.domainName == domain && this.realmName == realm && this.cn==cn";
      String declarations = "String domain, String realm, String cn";
      Object[] parameters = new Object[]{this.domainName, this.realmName, alias};
      Collection entries = this.getRegEntries(filter, declarations, parameters, 0);
      return entries != null && !entries.isEmpty() ? this.filterRegEntries(entries, groupPattern, maxToReturn) : entries;
   }

   private Collection filterRegEntries(Collection entries, String groupPattern, int maxToReturn) {
      ArrayList returnList = new ArrayList();
      int count = 0;
      Iterator entryIterator = entries.iterator();

      while(entryIterator.hasNext()) {
         WLSCertRegEntry entry = (WLSCertRegEntry)entryIterator.next();
         if (match(entry.getRegistryName(), groupPattern)) {
            returnList.add(entry);
            ++count;
            if (count == 10) {
               break;
            }
         }
      }

      return returnList;
   }

   private static boolean match(String str, String pattern) {
      if (pattern == "*") {
         return true;
      } else {
         int index = pattern.indexOf(42);
         return index < 0 ? pattern.equalsIgnoreCase(str) : str.startsWith(pattern.substring(0, index));
      }
   }

   private WLSCertRegEntry getRegEntry(String filter, String declarations, Object[] parameters) {
      Collection businessObjects = this.getRegEntries(filter, declarations, parameters, 0);
      return businessObjects != null && businessObjects.size() != 0 ? (WLSCertRegEntry)businessObjects.iterator().next() : null;
   }

   private Collection getRegEntries(String filter, String declarations, Object[] parameters, int maxToReturn) {
      Collection businessObjects = null;

      try {
         businessObjects = Utils.queryBusinessObjects(this.storeService, com.bea.common.security.store.data.WLSCertRegEntry.class, filter, declarations, parameters, maxToReturn);
      } catch (Throwable var8) {
         if (this.isDebug()) {
            this.debug("getRegEntries", "Certificate Entry search exception: " + var8.getMessage());
         }

         handleUnexpectedException(var8);
      }

      if (businessObjects == null) {
         return null;
      } else {
         Collection results = new ArrayList();
         Iterator i = businessObjects.iterator();

         while(i.hasNext()) {
            results.add(convert2StoreEntry((com.bea.common.security.store.data.WLSCertRegEntry)i.next()));
         }

         return results;
      }
   }

   static WLSCertRegEntry convert2StoreEntry(com.bea.common.security.store.data.WLSCertRegEntry jdoEntry) {
      if (jdoEntry == null) {
         return null;
      } else {
         WLSCertRegEntry entry = new WLSCertRegEntry();
         entry.setCn(jdoEntry.getCn());
         entry.setDomainName(jdoEntry.getDomainName());
         entry.setRealmName(jdoEntry.getRealmName());
         entry.setRegistryName(jdoEntry.getRegistryName());
         entry.setWlsCertRegIssuerDN(jdoEntry.getWlsCertRegIssuerDN());
         entry.setWlsCertRegSerialNumber(jdoEntry.getWlsCertRegSerialNumber());
         entry.setWlsCertRegSubjectDN(jdoEntry.getWlsCertRegSubjectDN());
         entry.setWlsCertRegSubjectKeyIdentifier(jdoEntry.getWlsCertRegSubjectKeyIdentifier());
         entry.setUserCertificate(jdoEntry.getUserCertificate());
         return entry;
      }
   }

   private static com.bea.common.security.store.data.WLSCertRegEntry convert2JDOEntry(WLSCertRegEntry entry) {
      if (entry == null) {
         return null;
      } else {
         com.bea.common.security.store.data.WLSCertRegEntry jdoEntry = new com.bea.common.security.store.data.WLSCertRegEntry();
         jdoEntry.setCn(entry.getCn());
         jdoEntry.setDomainName(entry.getDomainName());
         jdoEntry.setRealmName(entry.getRealmName());
         jdoEntry.setRegistryName(entry.getRegistryName());
         jdoEntry.setWlsCertRegIssuerDN(entry.getWlsCertRegIssuerDN());
         jdoEntry.setWlsCertRegSerialNumber(entry.getWlsCertRegSerialNumber());
         jdoEntry.setWlsCertRegSubjectDN(entry.getWlsCertRegSubjectDN());
         jdoEntry.setWlsCertRegSubjectKeyIdentifier(entry.getWlsCertRegSubjectKeyIdentifier());
         jdoEntry.setUserCertificate(entry.getUserCertificate());
         return jdoEntry;
      }
   }
}
