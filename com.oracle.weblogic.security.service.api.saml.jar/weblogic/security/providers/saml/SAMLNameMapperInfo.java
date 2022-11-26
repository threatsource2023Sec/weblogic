package weblogic.security.providers.saml;

import java.util.Collection;

public class SAMLNameMapperInfo {
   public static final String NAME_FORMAT_UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
   public static final String NAME_FORMAT_EMAIL_ADDR = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
   public static final String NAME_FORMAT_X500_SUBJECT = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
   public static final String NAME_FORMAT_WINDOWS_DQN = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";
   public static final String NAME_FORMAT_KERBEROS = "urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos";
   public static final String NAME_FORMAT_ENTITY = "urn:oasis:names:tc:SAML:2.0:nameid-format:entity";
   public static final String NAME_FORMAT_PERSISTENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";
   public static final String NAME_FORMAT_TRANSIENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:transient";
   public static final String ATTR_NAME_FORMAT_BASIC = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";
   public static final String BEA_GROUP_ATTR_NAMESPACE = "urn:bea:security:saml:groups";
   public static final String BEA_GROUP_ATTR_NAME = "Groups";
   public static final String BEA_GROUP_ATTR_NAMEFORMAT = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";
   private String nameQualifier = null;
   private String nameFormat = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
   private String name = null;
   private String groupAttrNamespace = "urn:bea:security:saml:groups";
   private String groupAttrName = "Groups";
   private Collection groups = null;
   private String authenticationMethod = "urn:oasis:names:tc:SAML:1.0:am:unspecified";

   public SAMLNameMapperInfo() {
   }

   public SAMLNameMapperInfo(String name, Collection groups) {
      this.name = name;
      this.groups = groups;
   }

   public SAMLNameMapperInfo(String nameQualifier, String name, Collection groups) {
      this.nameQualifier = nameQualifier;
      this.name = name;
      this.groups = groups;
   }

   public SAMLNameMapperInfo(String nameQualifier, String nameFormat, String name, Collection groups) {
      this.nameQualifier = nameQualifier;
      this.nameFormat = nameFormat;
      this.name = name;
      this.groups = groups;
   }

   public SAMLNameMapperInfo(String nameQualifier, String nameFormat, String name, String groupAttrName, String groupAttrNamespace, Collection groups) {
      this.nameQualifier = nameQualifier;
      this.nameFormat = nameFormat;
      this.name = name;
      this.groupAttrName = groupAttrName;
      this.groupAttrNamespace = groupAttrNamespace;
      this.groups = groups;
   }

   public String getGroupAttrName() {
      return this.groupAttrName;
   }

   public void setGroupAttrName(String groupAttrName) {
      this.groupAttrName = groupAttrName;
   }

   public String getGroupAttrNamespace() {
      return this.groupAttrNamespace;
   }

   public void setGroupAttrNamespace(String groupAttrNamespace) {
      this.groupAttrNamespace = groupAttrNamespace;
   }

   public Collection getGroups() {
      return this.groups;
   }

   public void setGroups(Collection groups) {
      this.groups = groups;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getNameFormat() {
      return this.nameFormat;
   }

   public void setNameFormat(String nameFormat) {
      this.nameFormat = nameFormat;
   }

   public String getNameQualifier() {
      return this.nameQualifier;
   }

   public void setNameQualifier(String nameQualifier) {
      this.nameQualifier = nameQualifier;
   }

   public void setAuthenticationMethod(String authMethod) {
      this.authenticationMethod = authMethod;
   }

   public String getAuthenticationMethod() {
      return this.authenticationMethod;
   }
}
