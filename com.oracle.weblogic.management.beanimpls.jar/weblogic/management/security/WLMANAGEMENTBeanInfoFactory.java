package weblogic.management.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.utils.codegen.ImplementationFactory;
import weblogic.utils.codegen.RoleInfoImplementationFactory;

public class WLMANAGEMENTBeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(68);
   private static final ArrayList roleInfoList;
   private static final WLMANAGEMENTBeanInfoFactory SINGLETON;

   public static final ImplementationFactory getInstance() {
      return SINGLETON;
   }

   public String getImplementationClassName(String interfaceName) {
      return (String)interfaceMap.get(interfaceName);
   }

   public String[] getInterfaces() {
      Set keySet = interfaceMap.keySet();
      return (String[])((String[])keySet.toArray(new String[keySet.size()]));
   }

   public String[] getInterfacesWithRoleInfo() {
      return (String[])((String[])roleInfoList.toArray(new String[roleInfoList.size()]));
   }

   public String getRoleInfoImplementationFactoryTimestamp() {
      try {
         InputStream is = this.getClass().getResourceAsStream("WLMANAGEMENTBeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.management.security.ApplicationVersionerMBean", "weblogic.management.security.ApplicationVersionerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.ExportMBean", "weblogic.management.security.ExportMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.IdentityDomainAwareProviderMBean", "weblogic.management.security.IdentityDomainAwareProviderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.ImportMBean", "weblogic.management.security.ImportMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.ProviderMBean", "weblogic.management.security.ProviderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.RDBMSSecurityStoreMBean", "weblogic.management.security.RDBMSSecurityStoreMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.RealmMBean", "weblogic.management.security.RealmMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.audit.AuditorMBean", "weblogic.management.security.audit.AuditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.audit.ContextHandlerMBean", "weblogic.management.security.audit.ContextHandlerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.AnyIdentityDomainAuthenticatorMBean", "weblogic.management.security.authentication.AnyIdentityDomainAuthenticatorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.AuthenticationProviderMBean", "weblogic.management.security.authentication.AuthenticationProviderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.AuthenticatorMBean", "weblogic.management.security.authentication.AuthenticatorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.GroupEditorMBean", "weblogic.management.security.authentication.GroupEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.GroupMemberListerMBean", "weblogic.management.security.authentication.GroupMemberListerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBean", "weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.GroupReaderMBean", "weblogic.management.security.authentication.GroupReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.GroupRemoverMBean", "weblogic.management.security.authentication.GroupRemoverMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.GroupUserListerMBean", "weblogic.management.security.authentication.GroupUserListerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.IdentityAsserterMBean", "weblogic.management.security.authentication.IdentityAsserterMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean", "weblogic.management.security.authentication.IdentityDomainAuthenticatorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.MemberGroupListerMBean", "weblogic.management.security.authentication.MemberGroupListerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.MultiIdentityDomainAuthenticatorMBean", "weblogic.management.security.authentication.MultiIdentityDomainAuthenticatorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.PasswordValidatorMBean", "weblogic.management.security.authentication.PasswordValidatorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.ServletAuthenticationFilterMBean", "weblogic.management.security.authentication.ServletAuthenticationFilterMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.UserAttributeEditorMBean", "weblogic.management.security.authentication.UserAttributeEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.UserAttributeReaderMBean", "weblogic.management.security.authentication.UserAttributeReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.UserEditorMBean", "weblogic.management.security.authentication.UserEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.UserLockoutManagerMBean", "weblogic.management.security.authentication.UserLockoutManagerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.UserPasswordEditorMBean", "weblogic.management.security.authentication.UserPasswordEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.UserReaderMBean", "weblogic.management.security.authentication.UserReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authentication.UserRemoverMBean", "weblogic.management.security.authentication.UserRemoverMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.AdjudicatorMBean", "weblogic.management.security.authorization.AdjudicatorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.AuthorizerMBean", "weblogic.management.security.authorization.AuthorizerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.DeployableAuthorizerMBean", "weblogic.management.security.authorization.DeployableAuthorizerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.DeployableRoleMapperMBean", "weblogic.management.security.authorization.DeployableRoleMapperMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PolicyAuxiliaryMBean", "weblogic.management.security.authorization.PolicyAuxiliaryMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PolicyConsumerMBean", "weblogic.management.security.authorization.PolicyConsumerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PolicyEditorMBean", "weblogic.management.security.authorization.PolicyEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PolicyListerMBean", "weblogic.management.security.authorization.PolicyListerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PolicyReaderMBean", "weblogic.management.security.authorization.PolicyReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PolicyStoreMBean", "weblogic.management.security.authorization.PolicyStoreMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PredicateEditorMBean", "weblogic.management.security.authorization.PredicateEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.PredicateReaderMBean", "weblogic.management.security.authorization.PredicateReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.RoleAuxiliaryMBean", "weblogic.management.security.authorization.RoleAuxiliaryMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.RoleConsumerMBean", "weblogic.management.security.authorization.RoleConsumerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.RoleEditorMBean", "weblogic.management.security.authorization.RoleEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.RoleListerMBean", "weblogic.management.security.authorization.RoleListerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.RoleMapperMBean", "weblogic.management.security.authorization.RoleMapperMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.authorization.RoleReaderMBean", "weblogic.management.security.authorization.RoleReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.CredentialCacheMBean", "weblogic.management.security.credentials.CredentialCacheMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.CredentialMapperMBean", "weblogic.management.security.credentials.CredentialMapperMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.DeployableCredentialMapperMBean", "weblogic.management.security.credentials.DeployableCredentialMapperMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.PKICredentialMapEditorMBean", "weblogic.management.security.credentials.PKICredentialMapEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.PKICredentialMapExtendedEditorMBean", "weblogic.management.security.credentials.PKICredentialMapExtendedEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.PKICredentialMapExtendedReaderMBean", "weblogic.management.security.credentials.PKICredentialMapExtendedReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.PKICredentialMapReaderMBean", "weblogic.management.security.credentials.PKICredentialMapReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.UserPasswordCredentialMapEditorMBean", "weblogic.management.security.credentials.UserPasswordCredentialMapEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedEditorMBean", "weblogic.management.security.credentials.UserPasswordCredentialMapExtendedEditorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedReaderMBean", "weblogic.management.security.credentials.UserPasswordCredentialMapExtendedReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedV2ReaderMBean", "weblogic.management.security.credentials.UserPasswordCredentialMapExtendedV2ReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.credentials.UserPasswordCredentialMapReaderMBean", "weblogic.management.security.credentials.UserPasswordCredentialMapReaderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.pk.CertPathBuilderMBean", "weblogic.management.security.pk.CertPathBuilderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.pk.CertPathProviderMBean", "weblogic.management.security.pk.CertPathProviderMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.security.pk.CertPathValidatorMBean", "weblogic.management.security.pk.CertPathValidatorMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.utils.LDAPServerMBean", "weblogic.management.utils.LDAPServerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.utils.ListerMBean", "weblogic.management.utils.ListerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.utils.NameListerMBean", "weblogic.management.utils.NameListerMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.utils.PropertiesListerMBean", "weblogic.management.utils.PropertiesListerMBeanImplBeanInfo");
      roleInfoList = new ArrayList(37);
      roleInfoList.add("weblogic.management.security.ExportMBean");
      roleInfoList.add("weblogic.management.security.ImportMBean");
      roleInfoList.add("weblogic.management.security.authentication.GroupEditorMBean");
      roleInfoList.add("weblogic.management.security.authentication.GroupMemberListerMBean");
      roleInfoList.add("weblogic.management.security.authentication.GroupReaderMBean");
      roleInfoList.add("weblogic.management.security.authentication.GroupRemoverMBean");
      roleInfoList.add("weblogic.management.security.authentication.GroupUserListerMBean");
      roleInfoList.add("weblogic.management.security.authentication.MemberGroupListerMBean");
      roleInfoList.add("weblogic.management.security.authentication.UserAttributeEditorMBean");
      roleInfoList.add("weblogic.management.security.authentication.UserAttributeReaderMBean");
      roleInfoList.add("weblogic.management.security.authentication.UserEditorMBean");
      roleInfoList.add("weblogic.management.security.authentication.UserPasswordEditorMBean");
      roleInfoList.add("weblogic.management.security.authentication.UserReaderMBean");
      roleInfoList.add("weblogic.management.security.authentication.UserRemoverMBean");
      roleInfoList.add("weblogic.management.security.authorization.PolicyAuxiliaryMBean");
      roleInfoList.add("weblogic.management.security.authorization.PolicyEditorMBean");
      roleInfoList.add("weblogic.management.security.authorization.PolicyListerMBean");
      roleInfoList.add("weblogic.management.security.authorization.PolicyReaderMBean");
      roleInfoList.add("weblogic.management.security.authorization.PolicyStoreMBean");
      roleInfoList.add("weblogic.management.security.authorization.PredicateEditorMBean");
      roleInfoList.add("weblogic.management.security.authorization.PredicateReaderMBean");
      roleInfoList.add("weblogic.management.security.authorization.RoleAuxiliaryMBean");
      roleInfoList.add("weblogic.management.security.authorization.RoleEditorMBean");
      roleInfoList.add("weblogic.management.security.authorization.RoleListerMBean");
      roleInfoList.add("weblogic.management.security.authorization.RoleReaderMBean");
      roleInfoList.add("weblogic.management.security.credentials.PKICredentialMapEditorMBean");
      roleInfoList.add("weblogic.management.security.credentials.PKICredentialMapExtendedEditorMBean");
      roleInfoList.add("weblogic.management.security.credentials.PKICredentialMapExtendedReaderMBean");
      roleInfoList.add("weblogic.management.security.credentials.PKICredentialMapReaderMBean");
      roleInfoList.add("weblogic.management.security.credentials.UserPasswordCredentialMapEditorMBean");
      roleInfoList.add("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedEditorMBean");
      roleInfoList.add("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedReaderMBean");
      roleInfoList.add("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedV2ReaderMBean");
      roleInfoList.add("weblogic.management.security.credentials.UserPasswordCredentialMapReaderMBean");
      roleInfoList.add("weblogic.management.utils.ListerMBean");
      roleInfoList.add("weblogic.management.utils.NameListerMBean");
      roleInfoList.add("weblogic.management.utils.PropertiesListerMBean");
      SINGLETON = new WLMANAGEMENTBeanInfoFactory();
   }
}
