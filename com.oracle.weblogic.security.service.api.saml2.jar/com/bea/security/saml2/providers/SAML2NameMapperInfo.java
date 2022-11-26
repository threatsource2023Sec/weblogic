package com.bea.security.saml2.providers;

import java.util.Collection;

public class SAML2NameMapperInfo {
   public static final String NAME_FORMAT_UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
   public static final String NAME_FORMAT_KERBEROS = "urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos";
   public static final String NAME_FORMAT_ENTITY = "urn:oasis:names:tc:SAML:2.0:nameid-format:entity";
   public static final String NAME_FORMAT_PERSISTENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";
   public static final String NAME_FORMAT_TRANSIENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:transient";
   public static final String ATTR_NAME_FORMAT_BASIC = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";
   public static final String BEA_GROUP_ATTR_NAMESPACE = "urn:bea:security:saml2:groups";
   public static final String BEA_GROUP_ATTR_NAME = "Groups";
   public static final String BEA_GROUP_ATTR_NAMEFORMAT = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";
   private String nameQualifier = null;
   private String nameFormat = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
   private String name = null;
   private String groupAttrNamespace = "urn:bea:security:saml2:groups";
   private String groupAttrName = "Groups";
   private String groupAttrNameFormat = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";
   private Collection groups = null;

   public SAML2NameMapperInfo() {
   }

   public SAML2NameMapperInfo(String name, Collection groups) {
      this.name = name;
      this.groups = groups;
   }

   public SAML2NameMapperInfo(String nameQualifier, String name, Collection groups) {
      this.nameQualifier = nameQualifier;
      this.name = name;
      this.groups = groups;
   }

   public SAML2NameMapperInfo(String nameQualifier, String nameFormat, String name, Collection groups) {
      this.nameQualifier = nameQualifier;
      this.nameFormat = nameFormat;
      this.name = name;
      this.groups = groups;
   }

   public SAML2NameMapperInfo(String nameQualifier, String nameFormat, String name, String groupAttrName, String groupAttrNameFormat, Collection groups) {
      this.nameQualifier = nameQualifier;
      this.nameFormat = nameFormat;
      this.name = name;
      this.groupAttrName = groupAttrName;
      this.groupAttrNameFormat = groupAttrNameFormat;
      this.groups = groups;
   }

   public String getGroupAttrName() {
      return this.groupAttrName;
   }

   public void setGroupAttrName(String groupAttrName) {
      this.groupAttrName = groupAttrName;
   }

   /** @deprecated */
   @Deprecated
   public String getGroupAttrNamespace() {
      return this.groupAttrNamespace;
   }

   /** @deprecated */
   @Deprecated
   public void setGroupAttrNamespace(String groupAttrNamespace) {
      this.groupAttrNamespace = groupAttrNamespace;
   }

   public String getGroupAttrNameFormat() {
      return this.groupAttrNameFormat;
   }

   public void setGroupAttrNameFormat(String groupAttrNameFormat) {
      this.groupAttrNameFormat = groupAttrNameFormat;
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
}
