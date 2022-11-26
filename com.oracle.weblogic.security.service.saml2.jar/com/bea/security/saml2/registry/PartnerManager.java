package com.bea.security.saml2.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.IdPPartner;
import com.bea.security.saml2.providers.registry.SPPartner;
import java.util.Collection;

public abstract class PartnerManager {
   public static PartnerManager newInstance(SAML2ConfigSpi config) {
      return newInstance(config.getLogger(), config.getStoreService(), config.getRealmName(), config.getDomainName(), config.getEncryptSpi());
   }

   public static PartnerManager newInstance(LoggerSpi logger, StoreService store, String realmName, String domainName, LegacyEncryptorSpi encryptSpi) {
      return new PartnerManagerImpl(logger, store, realmName, domainName, encryptSpi);
   }

   public abstract SPPartner findServiceProviderByIssuerURI(String var1) throws PartnerManagerException;

   public abstract IdPPartner findIdentityProviderByIssuerURI(String var1) throws PartnerManagerException;

   public abstract Collection listIdPPartners(String var1, int var2) throws PartnerManagerException;

   public abstract boolean idPPartnerExists(String var1) throws PartnerManagerException;

   public abstract IdPPartner getIdPPartner(String var1) throws PartnerManagerException;

   public abstract IdPPartner getIdPPartner(String var1, String var2, String var3, String var4) throws PartnerManagerException;

   public abstract void addIdPPartner(IdPPartner var1) throws PartnerManagerException;

   public abstract void updateIdPPartner(IdPPartner var1) throws PartnerManagerException;

   public abstract void removeIdPPartner(String var1) throws PartnerManagerException;

   public abstract Collection listSPPartners(String var1, int var2) throws PartnerManagerException;

   public abstract boolean spPartnerExists(String var1) throws PartnerManagerException;

   public abstract SPPartner getSPPartner(String var1) throws PartnerManagerException;

   public abstract SPPartner getSPPartner(String var1, String var2, String var3) throws PartnerManagerException;

   public abstract void addSPPartner(SPPartner var1) throws PartnerManagerException;

   public abstract void updateSPPartner(SPPartner var1) throws PartnerManagerException;

   public abstract void removeSPPartner(String var1) throws PartnerManagerException;

   public abstract void addPartnerChangeListener(String var1, PartnerChangeListener var2);

   public abstract void removePartnerChangeListener(String var1, PartnerChangeListener var2);

   public abstract Collection getPartnerMetaData(Class var1);

   public abstract IdPPartner findIdentityProviderByIssuerURI(String var1, String var2) throws PartnerManagerException;
}
