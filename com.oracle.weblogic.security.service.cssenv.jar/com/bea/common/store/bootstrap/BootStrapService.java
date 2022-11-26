package com.bea.common.store.bootstrap;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.store.service.StoreService;
import java.util.List;
import weblogic.management.security.RealmMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;

public interface BootStrapService {
   String SERVICE_NAME = "com.bea.common.store.bootstrap.BootStrapService";
   String AUTHENTICATOR = "Authenticator";
   String AUTHORIZER = "Authorizer";
   String ROLEMAPPER = "RoleMapper";
   String CREDENTIALMAPPER = "CredentialMapper";
   String PKICREDENTIALMAPPER = "PKICredentialMapper";
   String IDENTITYASSERTER = "IdentityAsserter";
   String SAMLCREDENTIALMAPPER = "SAMLCredentialMapper";
   String SAMLIDENTITYASSERTER = "SAMLIdentityAssertor";
   String SAML2CREDENTIALMAPPER = "SAML2CredentialMapper";
   String SAML2IDENTITYASSERTER = "SAML2IdentityAsserter";

   /** @deprecated */
   @Deprecated
   void loadLDIFCredentialMapperTemplate(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, RealmMBean var5);

   void loadLDIFCredentialMapperTemplate(LoggerSpi var1, BootStrapPersistence var2, EntryConverter var3, String var4, RealmMBean var5);

   void loadLDIFXACMLAuthorizerTemplate(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5);

   void loadLDIFXACMLAuthorizerTemplate(LoggerSpi var1, BootStrapPersistence var2, EntryConverter var3, String var4, String var5);

   void loadLDIFXACMLRoleMapperTemplate(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5);

   void loadLDIFXACMLRoleMapperTemplate(LoggerSpi var1, BootStrapPersistence var2, EntryConverter var3, String var4, String var5);

   void loadLDIFPKICredentialMapperTemplate(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5);

   /** @deprecated */
   @Deprecated
   void loadLDIFSAML2IdentityAsserterTemplate(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5);

   void loadLDIFSAML2IdentityAsserterTemplate(LoggerSpi var1, BootStrapPersistence var2, EntryConverter var3, String var4, String var5);

   /** @deprecated */
   @Deprecated
   void loadLDIFSAML2CredentialMapperTemplate(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5);

   void loadLDIFSAML2CredentialMapperTemplate(LoggerSpi var1, BootStrapPersistence var2, EntryConverter var3, String var4, String var5);

   void importSAMLDataLDIFT(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5, String var6) throws InvalidParameterException, ErrorCollectionException;

   void importSAML2DataLDIFT(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5, String var6) throws InvalidParameterException, ErrorCollectionException;

   void importPKICredentialMapperLDIFT(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5, String var6) throws InvalidParameterException, ErrorCollectionException;

   /** @deprecated */
   @Deprecated
   void importCredentialMapperLDIFT(LoggerSpi var1, StoreService var2, EntryConverter var3, String var4, String var5, String var6) throws InvalidParameterException, ErrorCollectionException;

   void importCredentialMapperLDIFT(LoggerSpi var1, BootStrapPersistence var2, EntryConverter var3, String var4, String var5, String var6) throws InvalidParameterException, ErrorCollectionException;

   void exportSAMLDataToLDIFT(LoggerSpi var1, String var2, String var3, String var4, String var5, List var6) throws InvalidParameterException, ErrorCollectionException;

   void exportSAML2DataToLDIFT(LoggerSpi var1, String var2, String var3, String var4, String var5, List var6) throws InvalidParameterException, ErrorCollectionException;

   void exportPKICredentialMapperLDIFT(LoggerSpi var1, String var2, String var3, String var4, List var5) throws InvalidParameterException, ErrorCollectionException;

   void exportCredentialMapperLDIFT(LoggerSpi var1, String var2, String var3, String var4, List var5) throws InvalidParameterException, ErrorCollectionException;

   void updateXACMLAuthorizerPolicies(LoggerSpi var1, GlobalPolicyUpdate var2, StoreService var3, String var4, String var5);

   void updateXACMLRoleMapperPolicies(LoggerSpi var1, GlobalPolicyUpdate var2, StoreService var3, String var4, String var5);

   void updateXACMLAuthorizerPolicies(LoggerSpi var1, GlobalPolicyUpdate var2, BootStrapPersistence var3, String var4, String var5);

   void updateXACMLRoleMapperPolicies(LoggerSpi var1, GlobalPolicyUpdate var2, BootStrapPersistence var3, String var4, String var5);
}
