package weblogic.security.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class LdapStoreConfig {
   public static final String OID = "OID";
   public static final String ACTIVE_DIRECTORY = "ACTIVE_DIRECTORY";
   public static final String IPLANET = "IPLANET";
   public static final String EDIRECTORY = "EDIRECTORY";
   public static final String OPEN_LDAP = "OPEN_LDAP";
   public static final String OVD = "OVD";
   public static final String WLS_OVD = "WLS_OVD";
   public static final String OUD = "OUD";
   public static final String CUSTOM = "CUSTOM";
   public static final String GROUP_SEARCH_SCOPE = "group.search.scope";
   public static final String GROUP_MEMBERSHIP_SEARCHING = "group.membership.searching";
   public static final String GROUP_MEMBERSHIP_SEARCH_LEVEL = "group.membership.search.level";
   public static final String ALL_USERS_FILTER = "all.users.filter";
   public static final String USER_FROM_NAME_FILTER = "user.from.name.filter";
   public static final String ALL_GROUPS_FILTER = "all.groups.filter";
   public static final String GROUP_FROM_NAME_FILTER = "group.from.name.filter";
   public static final String WLS_OVD_LOCAL_URL = "wls.ovd.local.url";
   private final String domainName;
   private final String realmName;
   private final boolean embeddedLdap;
   private final String idStoreName;
   private final String idStoreType;
   private String host;
   private int port;
   private boolean sslEnabled;
   private String principal;
   private char[] credential;
   private String[] userObjectClass;
   private String userFilterObjectClass;
   private String userBaseDN;
   private String userNameAttribute;
   private String userDynamicGroupDNAttribute;
   private boolean hierarchicalGroupMemberships;
   private String groupBaseDN;
   private String[] groupObjectClass;
   private String staticGroupFilterObjectClass;
   private String staticGroupNameAttribute;
   private String staticMemberDNAttribute;
   private String dynamicGroupObjectClass;
   private String dynamicGroupNameAttribute;
   private String dynamicMemberURLAttribute;
   private String jaasFlag;
   private String guidAttribute;
   private String identityDomain;
   private Map configProperties;

   LdapStoreConfig(String domainName, String realmName, boolean embeddedLdap, String idStoreName, String idStoreType, String jaasFlag, String guidAttr) {
      this.domainName = domainName;
      this.realmName = realmName;
      this.embeddedLdap = embeddedLdap;
      this.idStoreName = idStoreName;
      this.idStoreType = idStoreType;
      this.jaasFlag = jaasFlag;
      this.guidAttribute = guidAttr;
      this.configProperties = Collections.emptyMap();
   }

   void setServerInfo(String host, int port, boolean useSSL, String principal, String credential) {
      this.host = host;
      this.port = port;
      this.sslEnabled = useSSL;
      this.principal = principal;
      this.credential = credential == null ? null : credential.toCharArray();
   }

   void setUserInfo(String[] userObjectClass, String userFilterObjectClass, String userBaseDN, String userNameAttribute, String userDynamicGroupDNAttribute) {
      this.userObjectClass = userObjectClass;
      this.userFilterObjectClass = userFilterObjectClass;
      this.userBaseDN = userBaseDN;
      this.userNameAttribute = userNameAttribute;
      this.userDynamicGroupDNAttribute = userDynamicGroupDNAttribute;
   }

   void setGroupInfo(boolean hierarchicalGroupMemberships, String[] groupObjectClass, String staticGroupFilterObjectClass, String groupBaseDN, String staticGroupNameAttribute, String staticMemberDNAttribute, String dynamicGroupObjectClass, String dynamicGroupNameAttribute, String dynamicMemberURLAttribute) {
      this.hierarchicalGroupMemberships = hierarchicalGroupMemberships;
      this.groupObjectClass = groupObjectClass;
      this.staticGroupFilterObjectClass = staticGroupFilterObjectClass;
      this.groupBaseDN = groupBaseDN;
      this.staticGroupNameAttribute = staticGroupNameAttribute;
      this.staticMemberDNAttribute = staticMemberDNAttribute;
      this.dynamicGroupObjectClass = dynamicGroupObjectClass;
      this.dynamicGroupNameAttribute = dynamicGroupNameAttribute;
      this.dynamicMemberURLAttribute = dynamicMemberURLAttribute;
   }

   void setIdentityDomain(String identityDomain) {
      this.identityDomain = identityDomain;
   }

   void setConfigProperties(Map configProperties) {
      if (configProperties != null) {
         this.configProperties = Collections.unmodifiableMap(configProperties);
      }

   }

   public String getIdStoreName() {
      return this.idStoreName;
   }

   public String getIdStoreType() {
      return this.idStoreType;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public boolean isSslEnabled() {
      return this.sslEnabled;
   }

   public String getPrincipal() {
      return this.principal;
   }

   public char[] getCredential() {
      return this.credential;
   }

   public String[] getUserObjectClass() {
      return this.userObjectClass;
   }

   public String getUserFilterObjectClass() {
      return this.userFilterObjectClass;
   }

   public String getUserNameAttribute() {
      return this.userNameAttribute;
   }

   public String getUserBaseDN() {
      return this.userBaseDN;
   }

   public String getUserDynamicGroupDNAttribute() {
      return this.userDynamicGroupDNAttribute;
   }

   public boolean isHierarchicalGroupMemberships() {
      return this.hierarchicalGroupMemberships;
   }

   public String getGroupBaseDN() {
      return this.groupBaseDN;
   }

   public String[] getGroupObjectClass() {
      return this.groupObjectClass;
   }

   public String getStaticGroupFilterObjectClass() {
      return this.staticGroupFilterObjectClass;
   }

   public String getStaticGroupNameAttribute() {
      return this.staticGroupNameAttribute;
   }

   public String getStaticMemberDNAttribute() {
      return this.staticMemberDNAttribute;
   }

   public String getDynamicGroupObjectClass() {
      return this.dynamicGroupObjectClass;
   }

   public String getDynamicGroupNameAttribute() {
      return this.dynamicGroupNameAttribute;
   }

   public String getDynamicMemberURLAttribute() {
      return this.dynamicMemberURLAttribute;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public String getRealmName() {
      return this.realmName;
   }

   public boolean isEmbeddedLdap() {
      return this.embeddedLdap;
   }

   public String getJAASFlag() {
      return this.jaasFlag;
   }

   public String getGUIDAttribute() {
      return this.guidAttribute;
   }

   public String getIdentityDomain() {
      return this.identityDomain;
   }

   public Map getConfigProperties() {
      return this.configProperties;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("[ domainName: ").append(this.domainName);
      buffer.append(" ]\n[ realmName: ").append(this.realmName);
      buffer.append(" ]\n[ embeddedLdap: ").append(this.embeddedLdap);
      buffer.append(" ]\n[ idStoreName: ").append(this.idStoreName);
      buffer.append(" ]\n[ idStoreType: ").append(this.idStoreType);
      buffer.append(" ]\n[ host: ").append(this.host);
      buffer.append(" ]\n[ port: ").append(this.port);
      buffer.append(" ]\n[ sslEnabled: ").append(this.sslEnabled);
      buffer.append(" ]\n[ principal: ").append(this.principal);
      buffer.append(" ]\n[ JAAS flag: ").append(this.jaasFlag);
      buffer.append(" ]\n[ GUID attr: ").append(this.guidAttribute);
      buffer.append(" ]\n[ userObjectClass: ");
      Object usrObjClz = null;
      if (this.userObjectClass != null) {
         usrObjClz = Arrays.asList(this.userObjectClass);
      }

      buffer.append(usrObjClz);
      buffer.append(" ]\n[ userFilterObjectClass: ").append(this.userFilterObjectClass);
      buffer.append(" ]\n[ userBaseDN: ").append(this.userBaseDN);
      buffer.append(" ]\n[ userNameAttribute: ").append(this.userNameAttribute);
      buffer.append(" ]\n[ userDynamicGroupDNAttribute: ").append(this.userDynamicGroupDNAttribute);
      buffer.append(" ]\n[ hierarchicalGroupMemberships: ").append(this.hierarchicalGroupMemberships);
      buffer.append(" ]\n[ groupObjectClass: ");
      Object grpObjClz = null;
      if (this.groupObjectClass != null) {
         grpObjClz = Arrays.asList(this.groupObjectClass);
      }

      buffer.append(grpObjClz);
      buffer.append(" ]\n[ staticGroupFilterObjectClass: ").append(this.staticGroupFilterObjectClass);
      buffer.append(" ]\n[ groupBaseDN: ").append(this.groupBaseDN);
      buffer.append(" ]\n[ staticGroupNameAttribute: ").append(this.staticGroupNameAttribute);
      buffer.append(" ]\n[ staticMemberDNAttribute: ").append(this.staticMemberDNAttribute);
      buffer.append(" ]\n[ dynamicGroupObjectClass: ").append(this.dynamicGroupObjectClass);
      buffer.append(" ]\n[ dynamicGroupNameAttribute: ").append(this.dynamicGroupNameAttribute);
      buffer.append(" ]\n[ dynamicMemberURLAttribute: ").append(this.dynamicMemberURLAttribute);
      buffer.append(" ]\n[ config properties: ").append(this.configProperties).append(" ]");
      return buffer.toString();
   }
}
