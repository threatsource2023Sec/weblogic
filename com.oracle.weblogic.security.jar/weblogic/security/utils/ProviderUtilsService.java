package weblogic.security.utils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.security.auth.login.AppConfigurationEntry;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.security.RealmMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.shared.LoggerWrapper;

@Contract
public interface ProviderUtilsService {
   String DEFAULT = "Default";
   String AUTHENTICATOR = "Authenticator";
   String LDIFTEXT = ".ldift";
   String LDIFTEMPLATEEXT = "Init.ldift";
   String BASELDIFTEMPLATEEXT = "Base.ldift";
   String FROMDEPLOY = "deploy";
   String FROMMBEAN = "mbean";
   String ROLEMAPPER = "RoleMapper";
   String AUTHORIZER = "Authorizer";
   String CREATE_TIMESTAMP = "createTimestamp";
   String CREATEORS_NAME = "creatorsName";
   String GUID = "orclguid";
   String[] EXCLUDED_ON_COPY_ATTRS = new String[]{"createTimestamp", "creatorsName", "orclguid"};

   void convertBaseLDIFTemplate(String var1, String var2, String var3, String var4) throws IOException;

   void applicationDeleted(LDAPConnection var1, String var2, String var3, int var4, String var5, LoggerWrapper var6) throws LDAPException;

   void cleanupAfterCollection(LDAPConnection var1, String var2, String var3, long var4, List var6, LoggerWrapper var7) throws LDAPException;

   void cleanupAfterAppDeploy(LDAPConnection var1, String var2, String var3, int var4, String var5, long var6, LoggerWrapper var8) throws LDAPException;

   void applicationCopy(LDAPConnection var1, String var2, String var3, String var4, String[] var5, String[] var6, LoggerWrapper var7) throws LDAPException;

   void loadLDIFAuthenticatorTemplate(String var1, RealmMBean var2);

   void checkImportExportDataFormat(String var1, String var2, String[] var3) throws InvalidParameterException;

   void importAuthenticatorLDIFT(String var1, Properties var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;

   void exportAuthenticatorLDIFT(String var1, Properties var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;

   void loadLDIFAuthorizerTemplate(String var1, RealmMBean var2);

   void loadLDIFRoleMapperTemplate(String var1, RealmMBean var2);

   void importRoleMapperLDIFT(String var1, Properties var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;

   void exportRoleMapperLDIFT(String var1, Properties var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;

   void exportRoleMapperLDIFT(String var1, String var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;

   AppConfigurationEntry.LoginModuleControlFlag getLoginModuleControlFlag(String var1);

   void importAuthorizerLDIFT(String var1, Properties var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;

   void exportAuthorizerLDIFT(String var1, Properties var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;

   void exportAuthorizerLDIFT(String var1, String var2, String var3, String var4) throws InvalidParameterException, ErrorCollectionException;
}
