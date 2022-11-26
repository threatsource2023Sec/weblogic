package weblogic.security.utils;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import weblogic.ldap.EmbeddedLDAP;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.PropertyService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.protocol.AdminServerIdentity;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.URLManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.providers.authentication.ActiveDirectoryAuthenticatorMBean;
import weblogic.security.providers.authentication.DefaultAuthenticatorMBean;
import weblogic.security.providers.authentication.IPlanetAuthenticatorMBean;
import weblogic.security.providers.authentication.LDAPAuthenticatorMBean;
import weblogic.security.providers.authentication.NovellAuthenticatorMBean;
import weblogic.security.providers.authentication.OpenLDAPAuthenticatorMBean;
import weblogic.security.providers.authentication.OracleInternetDirectoryAuthenticatorMBean;
import weblogic.security.providers.authentication.OracleUnifiedDirectoryAuthenticatorMBean;
import weblogic.security.providers.authentication.OracleVirtualDirectoryAuthenticatorMBean;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceRuntimeException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.net.InetAddressHelper;

public class IdentityStoreConfigUtil {
   private static final String ADMIN_HIERARCHY_GROUP_PROP = "weblogic.security.hierarchyGroupMemberships";
   private static final String DOMAIN_NAME_PROP = "domain.name";
   private static final String REALM_NAME_PROP = "realm.name";
   private static final String PROVIDERS_ATN_PROP = "providers.atn";
   private static final String IS_ADMIN_SERVER_PROP = "server.admin";
   private static final String DEFAULT_PARTITION_NAME = "DOMAIN";
   private static final String LOGGER_NAME = "weblogic.security.utils";
   private static final Logger LOGGER = Logger.getLogger("weblogic.security.utils");

   public static List getAllLdapStoreConfig(AuthenticatedSubject kernelId) {
      return getAllLdapStoreConfig(kernelId, (String)null);
   }

   public static List getAllLdapStoreConfig(AuthenticatedSubject kernelId, String partitionName) {
      Map secConfig = getSecurityConfiguration(kernelId, partitionName, (String)null);
      AuthenticationProviderMBean[] atnMbeans = (AuthenticationProviderMBean[])((AuthenticationProviderMBean[])secConfig.get("providers.atn"));
      List identityStoreList = new ArrayList();
      AuthenticationProviderMBean[] var5 = atnMbeans;
      int var6 = atnMbeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         AuthenticationProviderMBean atnMbean = var5[var7];
         LdapStoreConfig storeConfig = getLdapStoreConfig(atnMbean, secConfig, kernelId);
         if (storeConfig != null) {
            identityStoreList.add(storeConfig);
         }
      }

      return identityStoreList;
   }

   public static LdapStoreConfig getLdapStoreConfig(AuthenticatedSubject kernelId, String atnName) {
      return getLdapStoreConfig((AuthenticatedSubject)kernelId, (String)null, (String)atnName);
   }

   public static LdapStoreConfig getLdapStoreConfig(AuthenticatedSubject kernelId, String partitionName, String atnName) {
      Map secConfig = getSecurityConfiguration(kernelId, partitionName, atnName);
      AuthenticationProviderMBean[] atnMbeans = (AuthenticationProviderMBean[])((AuthenticationProviderMBean[])secConfig.get("providers.atn"));
      return atnMbeans.length < 1 ? null : getLdapStoreConfig(getAuthenticationProviderMBeanByPriority(atnMbeans), secConfig, kernelId);
   }

   private static AuthenticationProviderMBean getAuthenticationProviderMBeanByPriority(AuthenticationProviderMBean[] atnMbeans) {
      AuthenticationProviderMBean requiredAtnMbean = null;
      AuthenticationProviderMBean requisiteAtnMbean = null;
      AuthenticationProviderMBean sufficientAtnMbean = null;
      AuthenticationProviderMBean optionalAtnMbean = null;
      AuthenticationProviderMBean[] var5 = atnMbeans;
      int var6 = atnMbeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         AuthenticationProviderMBean atnMbean = var5[var7];
         if (atnMbean instanceof DefaultAuthenticatorMBean || atnMbean instanceof LDAPAuthenticatorMBean) {
            String priority = ((AuthenticatorMBean)atnMbean).getControlFlag();
            if ("REQUIRED".equals(priority)) {
               requiredAtnMbean = atnMbean;
               break;
            }

            if ("REQUISITE".equals(priority) && requisiteAtnMbean == null) {
               requisiteAtnMbean = atnMbean;
            } else if ("SUFFICIENT".equals(priority) && sufficientAtnMbean == null) {
               sufficientAtnMbean = atnMbean;
            } else if ("OPTIONAL".equals(priority) && optionalAtnMbean == null) {
               optionalAtnMbean = atnMbean;
            }
         }
      }

      AuthenticationProviderMBean selectMbean;
      if (requiredAtnMbean != null) {
         selectMbean = requiredAtnMbean;
      } else if (requisiteAtnMbean != null) {
         selectMbean = requisiteAtnMbean;
      } else if (sufficientAtnMbean != null) {
         selectMbean = sufficientAtnMbean;
      } else {
         selectMbean = optionalAtnMbean;
      }

      return selectMbean;
   }

   private static Map getSecurityConfiguration(AuthenticatedSubject kernelId, String partitionName, String atnName) {
      SecurityManager.checkKernelIdentity(kernelId);
      RuntimeAccess runtime = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
      if (runtime == null) {
         throw new SecurityServiceRuntimeException("The WLS ManagementService has not been initialized.");
      } else {
         DomainMBean domainMBean = runtime.getDomain();
         RealmMBean realmMbean = null;
         if (partitionName != null && partitionName.length() >= 1 && !partitionName.equals("DOMAIN")) {
            PartitionMBean partition = domainMBean.lookupPartition(partitionName);
            if (partition != null) {
               realmMbean = partition.getRealm();
            }
         } else {
            realmMbean = domainMBean.getSecurityConfiguration().getDefaultRealm();
         }

         if (realmMbean == null) {
            throw new SecurityServiceRuntimeException("No matching realm found!");
         } else {
            String domainName = domainMBean.getName();
            String realmName = realmMbean.getName();
            AuthenticationProviderMBean[] atnMbeans = null;
            if (atnName != null && atnName.length() >= 1) {
               AuthenticationProviderMBean mbean = realmMbean.lookupAuthenticationProvider(atnName);
               if (mbean != null) {
                  atnMbeans = new AuthenticationProviderMBean[]{mbean};
               }
            } else {
               atnMbeans = realmMbean.getAuthenticationProviders();
            }

            if (atnMbeans == null) {
               atnMbeans = new AuthenticationProviderMBean[0];
            }

            Boolean isAdminServer = new Boolean(runtime.isAdminServer());
            HashMap map = new HashMap();
            map.put("domain.name", domainName);
            map.put("realm.name", realmName);
            map.put("providers.atn", atnMbeans);
            map.put("server.admin", isAdminServer);
            if (LOGGER.isLoggable(Level.FINE)) {
               String msg = "\ndomainName: " + domainName + "\nisAdminServer: " + isAdminServer + "\nrealmName: " + realmName + "\natnMbeans: \n" + Arrays.asList(atnMbeans);
               LOGGER.logp(Level.FINE, "getSecurityConfiguration", "IdentityStoreConfigUtil", msg);
            }

            return map;
         }
      }
   }

   private static LdapStoreConfig getLdapStoreConfig(AuthenticationProviderMBean atnMbean, Map secConfig, AuthenticatedSubject kernelId) {
      LdapStoreConfig storeConfig = null;
      if (atnMbean instanceof DefaultAuthenticatorMBean) {
         storeConfig = getLdapStoreConfig((DefaultAuthenticatorMBean)atnMbean, secConfig, kernelId);
      } else if (atnMbean instanceof LDAPAuthenticatorMBean) {
         storeConfig = getLdapStoreConfig((LDAPAuthenticatorMBean)atnMbean, secConfig);
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.logp(Level.FINE, "getSecurityConfiguration", "getLdapStoreConfig", storeConfig == null ? "null" : storeConfig.toString());
      }

      return storeConfig;
   }

   private static LdapStoreConfig getLdapStoreConfig(LDAPAuthenticatorMBean ldapAtnMbean, Map secConfig) {
      String domainName = (String)secConfig.get("domain.name");
      String realmName = (String)secConfig.get("realm.name");
      String priority = ldapAtnMbean.getControlFlag();
      String guid = ldapAtnMbean.getGuidAttribute();
      String idStoreType = getIdentityStoreType(ldapAtnMbean);
      LdapStoreConfig storeConfig = new LdapStoreConfig(domainName, realmName, false, ldapAtnMbean.getName(), idStoreType, priority, guid);
      String userFilterObjectClass = ldapAtnMbean.getUserObjectClass();
      String userBaseDN = ldapAtnMbean.getUserBaseDN();
      String userNameAttribute = ldapAtnMbean.getUserNameAttribute();
      String userDynamicGroupDNAttribute = ldapAtnMbean.getUserDynamicGroupDNAttribute();
      storeConfig.setUserInfo((String[])null, userFilterObjectClass, userBaseDN, userNameAttribute, userDynamicGroupDNAttribute);
      boolean hierarchicalGroupMemberships = Boolean.getBoolean("weblogic.security.hierarchyGroupMemberships");
      String staticGroupObjectClass = ldapAtnMbean.getStaticGroupObjectClass();
      String groupBaseDN = ldapAtnMbean.getGroupBaseDN();
      String staticGroupNameAttribute = ldapAtnMbean.getStaticGroupNameAttribute();
      String staticMemberDNAttribute = ldapAtnMbean.getStaticMemberDNAttribute();
      String dynamicGroupObjectClass = ldapAtnMbean.getDynamicGroupObjectClass();
      String dynamicGroupNameAttribute = ldapAtnMbean.getDynamicGroupNameAttribute();
      String dynamicMemberURLAttribute = ldapAtnMbean.getDynamicMemberURLAttribute();
      storeConfig.setGroupInfo(hierarchicalGroupMemberships, (String[])null, staticGroupObjectClass, groupBaseDN, staticGroupNameAttribute, staticMemberDNAttribute, dynamicGroupObjectClass, dynamicGroupNameAttribute, dynamicMemberURLAttribute);
      String host = ldapAtnMbean.getHost();
      int port = ldapAtnMbean.getPort();
      boolean useSSL = ldapAtnMbean.isSSLEnabled();
      String principal = ldapAtnMbean.getPrincipal();
      String credential = ldapAtnMbean.getCredential();
      storeConfig.setServerInfo(host, port, useSSL, principal, credential);
      String identityDomain = ldapAtnMbean.getIdentityDomain();
      storeConfig.setIdentityDomain(identityDomain);
      Map configProperties = new HashMap();
      configProperties.put("group.search.scope", ldapAtnMbean.getGroupSearchScope());
      configProperties.put("group.membership.searching", ldapAtnMbean.getGroupMembershipSearching());
      configProperties.put("group.membership.search.level", String.valueOf(ldapAtnMbean.getMaxGroupMembershipSearchLevel()));
      configProperties.put("all.users.filter", ldapAtnMbean.getAllUsersFilter());
      configProperties.put("user.from.name.filter", ldapAtnMbean.getUserFromNameFilter());
      configProperties.put("all.groups.filter", ldapAtnMbean.getAllGroupsFilter());
      configProperties.put("group.from.name.filter", ldapAtnMbean.getGroupFromNameFilter());
      storeConfig.setConfigProperties(configProperties);
      return storeConfig;
   }

   private static LdapStoreConfig getLdapStoreConfig(DefaultAuthenticatorMBean defaultAtnMbean, Map secConfig, AuthenticatedSubject kernelId) {
      String domainName = (String)secConfig.get("domain.name");
      String realmName = (String)secConfig.get("realm.name");
      RuntimeAccess runtime = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
      if (runtime == null) {
         throw new SecurityServiceRuntimeException("The WLS ManagementService has not been initialized.");
      } else {
         EmbeddedLDAPMBean embeddedLDAPMBean = runtime.getDomain().getEmbeddedLDAP();
         if (embeddedLDAPMBean == null) {
            throw new SecurityServiceRuntimeException("Null EmneddedLDAPMBean.");
         } else {
            String priority = "REQUIRED";
            String idStoreType = "WLS_OVD";
            String idStoreName = "DefaultAuthenticator";
            if (defaultAtnMbean != null) {
               priority = defaultAtnMbean.getControlFlag();
               idStoreType = getIdentityStoreType(defaultAtnMbean);
               idStoreName = defaultAtnMbean.getName();
            }

            String guid = "orclguid";
            LdapStoreConfig storeConfig = new LdapStoreConfig(domainName, realmName, true, idStoreName, idStoreType, priority, guid);
            String[] userObjectClass = new String[]{"top", "person", "organizationalPerson", "inetOrgPerson", "wlsUser"};
            String userFilterObjectClass = "person";
            String userBaseDN = "ou=people,ou=" + realmName + ",dc=" + domainName;
            String userNameAttribute = "uid";
            String userDynamicGroupDNAttribute = "wlsMemberOf";
            storeConfig.setUserInfo(userObjectClass, userFilterObjectClass, userBaseDN, userNameAttribute, userDynamicGroupDNAttribute);
            boolean hierarchicalGroupMemberships = Boolean.getBoolean("weblogic.security.hierarchyGroupMemberships");
            String[] groupObjectClass = new String[]{"top", "groupOfURLs", "groupOfUniqueNames"};
            String staticGroupFilterObjectClass = "groupOfUniqueNames";
            String groupBaseDN = "ou=groups,ou=" + realmName + ",dc=" + domainName;
            String staticGroupNameAttribute = "cn";
            String staticMemberDNAttribute = "uniquemember";
            String dynamicGroupObjectClass = "groupofURLs";
            String dynamicGroupNameAttribute = "cn";
            String dynamicMemberURLAttribute = "memberURL";
            storeConfig.setGroupInfo(hierarchicalGroupMemberships, groupObjectClass, staticGroupFilterObjectClass, groupBaseDN, staticGroupNameAttribute, staticMemberDNAttribute, dynamicGroupObjectClass, dynamicGroupNameAttribute, dynamicMemberURLAttribute);
            boolean isAdminServer = (Boolean)secConfig.get("server.admin");
            String adminUrl = null;
            URI adminURI = null;
            String host;
            if (runtime.isAdminServerAvailable()) {
               adminUrl = EmbeddedLDAP.findLdapURL(AdminServerIdentity.getBootstrapIdentity());
            } else {
               host = ((PropertyService)LocatorUtilities.getService(PropertyService.class)).getAdminHost();
               host = InetAddressHelper.convertIfIPV6URL(host);
               adminUrl = URLManager.normalizeToLDAPProtocol(host);
            }

            if (adminUrl == null) {
               throw new SecurityServiceRuntimeException("Null Admin Embedded LDAP URL.");
            } else {
               try {
                  adminURI = new URI(adminUrl);
               } catch (URISyntaxException var43) {
                  throw new SecurityServiceRuntimeException("Invalid Admin Embedded LDAP URL." + adminUrl);
               }

               host = null;
               int port = 0;
               boolean useSSL = false;
               if (adminURI != null) {
                  useSSL = adminURI.getScheme().equalsIgnoreCase("ldaps");
                  host = InetAddressHelper.convertHostIfIPV6(adminURI.getHost());
                  port = adminURI.getPort();
               }

               String message = "admin server host: " + host + " admin server port: " + port;
               if (host != null && host.length() >= 1 && port >= 0) {
                  String localUrl = null;
                  String localHost;
                  if (!isAdminServer) {
                     localUrl = EmbeddedLDAP.findLdapURL(LocalServerIdentity.getIdentity());
                     if (localUrl == null) {
                        throw new SecurityServiceRuntimeException("Null Local Embedded LDAP URL.");
                     }

                     URI uri;
                     try {
                        uri = new URI(localUrl);
                     } catch (Exception var42) {
                        throw new SecurityServiceRuntimeException(var42);
                     }

                     localHost = InetAddressHelper.convertHostIfIPV6(uri.getHost());
                     int localPort = uri.getPort();
                     if (useSSL && !localUrl.startsWith("ldaps")) {
                        SSLMBean sslMBean = runtime.getServer().getSSL();
                        if (sslMBean != null && sslMBean.isEnabled()) {
                           int mserverSSLPort = sslMBean.getListenPort();
                           localPort = mserverSSLPort;
                           localUrl = "ldaps://" + localHost + ":" + mserverSSLPort;
                        }
                     }

                     if (!useSSL && localUrl.startsWith("ldaps")) {
                        String adminsrvName = runtime.getAdminServerName();
                        if (adminsrvName != null) {
                           DomainMBean domainMBean = runtime.getDomain();
                           ServerMBean admSrvBean = domainMBean.lookupServer(adminsrvName);
                           if (admSrvBean != null) {
                              SSLMBean sslAdminMBean = admSrvBean.getSSL();
                              if (sslAdminMBean != null && sslAdminMBean.isEnabled()) {
                                 int adminSSLport = sslAdminMBean.getListenPort();
                                 if (adminSSLport > 0) {
                                    useSSL = true;
                                    port = adminSSLport;
                                 }
                              }
                           }
                        }
                     }

                     StringBuffer buf = new StringBuffer();
                     if (embeddedLDAPMBean.isMasterFirst()) {
                        buf.append(host).append(":").append(port).append(" ").append(localHost).append(":").append(localPort);
                     } else {
                        buf.append(localHost).append(":").append(localPort).append(" ").append(host).append(":").append(port);
                     }

                     host = buf.toString();
                  }

                  String principal = "cn=Admin";
                  localHost = embeddedLDAPMBean.getCredential();
                  storeConfig.setServerInfo(host, port, useSSL, principal, localHost);
                  String identityDomain = defaultAtnMbean.getIdentityDomain();
                  storeConfig.setIdentityDomain(identityDomain);
                  Map configProperties = new HashMap();
                  configProperties.put("group.search.scope", "subtree");
                  configProperties.put("group.membership.searching", defaultAtnMbean.getGroupMembershipSearching());
                  configProperties.put("group.membership.search.level", String.valueOf(defaultAtnMbean.getMaxGroupMembershipSearchLevel()));
                  if (localUrl != null) {
                     configProperties.put("wls.ovd.local.url", localUrl);
                  }

                  configProperties.put("all.users.filter", "(objectclass=person)");
                  configProperties.put("user.from.name.filter", "(&(uid=%u)(objectclass=person))");
                  configProperties.put("all.groups.filter", "(|(objectclass=groupofUniqueNames)(objectclass=groupOfURLs))");
                  configProperties.put("group.from.name.filter", "(|(&(cn=%g)(objectclass=groupofUniqueNames))(&(cn=%g)(objectclass=groupOfURLs)))");
                  storeConfig.setConfigProperties(configProperties);
                  return storeConfig;
               } else {
                  throw new SecurityServiceRuntimeException("Invalid host or port for admin server. " + message);
               }
            }
         }
      }
   }

   private static String getIdentityStoreType(AuthenticationProviderMBean atnMbean) {
      String type;
      if (atnMbean instanceof DefaultAuthenticatorMBean) {
         type = "WLS_OVD";
      } else if (atnMbean instanceof OracleUnifiedDirectoryAuthenticatorMBean) {
         type = "OUD";
      } else if (atnMbean instanceof ActiveDirectoryAuthenticatorMBean) {
         type = "ACTIVE_DIRECTORY";
      } else if (atnMbean instanceof IPlanetAuthenticatorMBean) {
         type = "IPLANET";
      } else if (atnMbean instanceof NovellAuthenticatorMBean) {
         type = "EDIRECTORY";
      } else if (atnMbean instanceof OpenLDAPAuthenticatorMBean) {
         type = "OPEN_LDAP";
      } else if (atnMbean instanceof OracleInternetDirectoryAuthenticatorMBean) {
         type = "OID";
      } else if (atnMbean instanceof OracleVirtualDirectoryAuthenticatorMBean) {
         type = "OVD";
      } else {
         type = "CUSTOM";
      }

      return type;
   }

   private static void enableLog() {
      if (Boolean.getBoolean("debug.IdentityStoreConfigUtil")) {
         LogManager manager = LogManager.getLogManager();
         Logger logger = manager.getLogger("weblogic.security.utils");
         if (logger == null) {
            logger = LOGGER;
            manager.addLogger(logger);
         }

         logger.addHandler(getConsoleHandler());
         logger.setLevel(Level.ALL);
         logger.setUseParentHandlers(false);
      }
   }

   private static ConsoleHandler getConsoleHandler() {
      ConsoleHandler ch = new ConsoleHandler();
      ch.setFormatter(new SimpleFormatter());
      ch.setLevel(Level.ALL);
      return ch;
   }

   static {
      enableLog();
   }
}
