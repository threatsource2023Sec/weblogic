package weblogic.management;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.store.SystemProperties;
import weblogic.utils.collections.EnumerationIterator;
import weblogic.utils.collections.PropertiesHelper;

public class SpecialPropertiesHelper extends SpecialPropertiesConstants {
   private static Set specialProperties = new HashSet();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");

   public static void configureFromSystemProperties(ServerMBean serverMBean) {
      configureFromSystemProperties(serverMBean, false, false);
   }

   public static void configureFromSystemProperties(ServerMBean serverMBean, boolean ignoreClusterProps, boolean warn) {
      Properties props = System.getProperties();
      Iterator it = new EnumerationIterator(props.propertyNames());

      while(true) {
         while(it.hasNext()) {
            String prop = (String)it.next();
            if (prop.equalsIgnoreCase("weblogic.AdministrationMBeanAuditingEnabled")) {
               ((DomainMBean)serverMBean.getParent()).setAdministrationMBeanAuditingEnabled(Boolean.getBoolean("weblogic.AdministrationMBeanAuditingEnabled"));
            } else {
               if (prop.equals("weblogic.LogFormatCompatibilityEnabled")) {
                  ((DomainMBean)serverMBean.getParent()).setLogFormatCompatibilityEnabled(Boolean.getBoolean("weblogic.LogFormatCompatibilityEnabled"));
               }

               String lcprop = prop.toLowerCase(Locale.US);
               String attr = null;
               ConfigurationMBean bean = serverMBean;
               if (!lcprop.equals("weblogic.class.path") && !specialProperties.contains(prop) && !SystemProperties.isSpecialFileStoreProperty(lcprop)) {
                  if (lcprop.startsWith("weblogic.debug.")) {
                     attr = prop.substring("weblogic.debug.".length());
                     bean = serverMBean.getServerDebug();
                  } else if (lcprop.startsWith("weblogic.ssl.")) {
                     attr = prop.substring("weblogic.ssl.".length());
                     bean = serverMBean.getSSL();
                  } else if (lcprop.startsWith("weblogic.log.")) {
                     attr = prop.substring("weblogic.log.".length());
                     bean = serverMBean.getLog();
                  } else if (lcprop.startsWith("weblogic.networkaccesspoint.")) {
                     String napName = prop.substring("weblogic.networkaccesspoint.".length());
                     int idx = napName.indexOf(".");
                     if (idx != -1) {
                        attr = napName.substring(idx + 1);
                        napName = napName.substring(0, idx);
                        bean = serverMBean.lookupNetworkAccessPoint(napName);
                     }

                     if (bean == null) {
                        ManagementLogger.logNetworkAccessPointPropertyIgnoredBecauseNotFound(prop);
                        continue;
                     }
                  } else if (lcprop.startsWith("weblogic.securemode.")) {
                     attr = prop.substring("weblogic.securemode.".length());
                     DomainMBean domain = (DomainMBean)serverMBean.getParent();
                     bean = domain.getSecurityConfiguration().getSecureMode();
                  } else if (lcprop.startsWith("weblogic.domain.")) {
                     attr = prop.substring("weblogic.domain.".length());
                     bean = (ConfigurationMBean)serverMBean.getParent();
                  } else if (lcprop.startsWith("weblogic.") && !lcprop.startsWith("weblogic.cluster.")) {
                     attr = prop.substring("weblogic.".length());
                     if (attr.equals("Cluster") && ignoreClusterProps) {
                        continue;
                     }

                     bean = serverMBean;
                  } else if (lcprop.startsWith("weblogic.cluster.") && !ignoreClusterProps) {
                     attr = prop.substring("weblogic.cluster.".length());
                     if (serverMBean.getCluster() == null) {
                        if (warn) {
                           ManagementLogger.logClusterPropertyIgnoreBecauseNoClusterConfigured(prop);
                        }
                        continue;
                     }

                     bean = serverMBean.getCluster();
                  }

                  if (attr != null && !specialProperties.contains(attr)) {
                     try {
                        Method[] m = bean.getClass().getMethods();
                        boolean found = false;
                        boolean unknownType = false;

                        int i;
                        Class[] params;
                        String propVal;
                        Object[] args;
                        for(i = 0; i < m.length; ++i) {
                           params = m[i].getParameterTypes();
                           if (m[i].getName().startsWith("set") && params.length == 1 && m[i].getName().substring(3).equals(attr)) {
                              propVal = (String)props.get(prop);
                              if (debugLogger.isDebugEnabled()) {
                                 debugLogger.debug("setting from command line [" + attr + "=" + propVal + "] on " + ((ConfigurationMBean)bean).getType() + "{" + ((ConfigurationMBean)bean).getName() + "}");
                              }

                              args = new Object[1];
                              Class type = params[0];
                              if (type == Boolean.TYPE) {
                                 args[0] = new Boolean(propVal);
                              } else if (type == Character.TYPE) {
                                 args[0] = new Character(propVal.charAt(0));
                              } else if (type == Byte.TYPE) {
                                 args[0] = new Byte(propVal);
                              } else if (type == Short.TYPE) {
                                 args[0] = new Short(propVal);
                              } else if (type == Integer.TYPE) {
                                 args[0] = new Integer(propVal);
                              } else if (type == Long.TYPE) {
                                 args[0] = new Long(propVal);
                              } else if (type == Float.TYPE) {
                                 args[0] = new Float(propVal);
                              } else if (type == Double.TYPE) {
                                 args[0] = new Double(propVal);
                              } else if (type == String.class) {
                                 args[0] = propVal;
                              } else if (type == Properties.class) {
                                 args[0] = PropertiesHelper.parse(propVal);
                              } else {
                                 unknownType = true;
                                 if (debugLogger.isDebugEnabled()) {
                                    debugLogger.debug("UNKNOWN TYPE: " + type);
                                 }
                              }

                              if (!unknownType) {
                                 found = true;
                                 m[i].invoke(bean, args);
                              }
                           }
                        }

                        for(i = 0; i < m.length && !found; ++i) {
                           params = m[i].getParameterTypes();
                           if (m[i].getName().startsWith("set") && params.length == 1 && m[i].getName().substring(3).equals(attr + "AsString")) {
                              propVal = (String)props.get(prop);
                              if (debugLogger.isDebugEnabled()) {
                                 debugLogger.debug("setting from command line via AsString [" + attr + "=" + propVal + "] on " + ((ConfigurationMBean)bean).getType() + "{" + ((ConfigurationMBean)bean).getName() + "}");
                              }

                              args = new Object[]{propVal};
                              found = true;
                              m[i].invoke(bean, args);
                              ((DescriptorImpl)((ConfigurationMBean)bean).getDescriptor()).resolveReferences();
                           }
                        }

                        if (!found && warn) {
                           ManagementLogger.logUnrecognizedProperty(prop);
                        }
                     } catch (Exception var17) {
                        ManagementLogger.logErrorSettingAttribute(attr, var17);
                     }
                  }
               }
            }
         }

         return;
      }
   }

   static {
      specialProperties.add("weblogic.management.pkpassword");
      specialProperties.add("weblogic.management.IdentityDomain");
      specialProperties.add("weblogic.management.password");
      specialProperties.add("weblogic.management.username");
      specialProperties.add("Domain");
      specialProperties.add("Name");
      specialProperties.add("Debug");
      specialProperties.add("DebugFilter");
      specialProperties.add("weblogic.management.server");
      specialProperties.add("weblogic.admin.host");
      specialProperties.add("weblogic.ProductionModeEnabled");
      specialProperties.add("weblogic.AdministrationMBeanAuditingEnabled");
      specialProperties.add("weblogic.management.GenerateDefaultConfig");
      specialProperties.add("weblogic.home");
      specialProperties.add("weblogic.management.saveDomainMillis");
      specialProperties.add("weblogic.debug.DebugScopes");
      specialProperties.add("weblogic.system.StoreBootIdentity");
      specialProperties.add("weblogic.system.BootIdentityFile");
      specialProperties.add("weblogic.system.RemoveBootIdentity");
      specialProperties.add("weblogic.system.NodeManagerBoot");
      specialProperties.add("weblogic.mbeanLegalClause.ByPass");
      specialProperties.add("weblogic.security.fullyDelegateAuthorization");
      specialProperties.add("weblogic.security.anonymousUserName");
      specialProperties.add("weblogic.debug.CertRevocCheck");
      specialProperties.add("ssl.debug");
      specialProperties.add("weblogic.security.SSL.verbose");
      specialProperties.add("weblogic.security.ssl.verbose");
      specialProperties.add("weblogic.security.SSL.ignoreHostnameVerification");
      specialProperties.add("weblogic.security.SSL.ignoreHostnameVerify");
      specialProperties.add("weblogic.security.SSL.hostnameVerifier");
      specialProperties.add("weblogic.security.SSL.trustedCAKeyStore");
      specialProperties.add("weblogic.security.SSL.protocolVersion");
      specialProperties.add("weblogic.security.SSL.enforceConstraints");
      specialProperties.add("weblogic.security.dd.javaEESecurityPermissionsDisabled");
      specialProperties.add("weblogic.security.jacc.RoleMapperFactory.provider");
      specialProperties.add("weblogic.management.anonymousAdminLookupEnabled");
      specialProperties.add("weblogic.security.identityAssertionTTL");
      specialProperties.add("weblogic.ConfigFile");
      specialProperties.add("weblogic.wtc.TraceLevel");
      specialProperties.add("weblogic.wtc.PasswordKey");
      specialProperties.add("weblogic.classloader.preprocessor");
      specialProperties.add("weblogic.oci.selectBlobChunkSize");
      specialProperties.add("weblogic.PosixSocketReaders");
      specialProperties.add("weblogic.security.hierarchyGroupMemberships");
      specialProperties.add("weblogic.application.RequireOptionalPackages");
      specialProperties.add("weblogic.management.jmx.remote.requestTimeout");
      specialProperties.add("weblogic.management.convertSecurityExtensionSchema");
      specialProperties.add("weblogic.security.TrustKeyStore");
      specialProperties.add("weblogic.security.CustomTrustKeyStoreFileName");
      specialProperties.add("weblogic.security.CustomTrustKeyStoreType");
      specialProperties.add("weblogic.security.CustomTrustKeyStorePassPhrase");
      specialProperties.add("weblogic.security.JavaStandardTrustKeyStorePassPhrase");
      specialProperties.add("weblogic.nodemanager.ServiceEnabled");
      specialProperties.add("weblogic.apache.xerces.maxentityrefs");
      specialProperties.add("weblogic.servlet.useExtendedSessionFormat");
      specialProperties.add("weblogic.security.SubjectManager");
      specialProperties.add("weblogic.security.SecurityServiceManagerDelegate");
      specialProperties.add("weblogic.internal.beantree");
      specialProperties.add("weblogic.security.providers.authentication.LDAPDelegatePoolSize");
      specialProperties.add("weblogic.jms.message.logging.destinations.all");
      specialProperties.add("weblogic.jms.message.logging.logNonDurableSubscriber");
      specialProperties.add("weblogic.jms.securityCheckInterval");
      specialProperties.add("weblogic.jms.extensions.multicast.sendDelay");
      SystemProperties.register(specialProperties);
      specialProperties.add("weblogic.messaging.kernel.persistence.InLineBodyThreshold");
      specialProperties.add("weblogic.messaging.kernel.persistence.PageInOnBoot");
      specialProperties.add("weblogic.messaging.kernel.paging.AlwaysUsePagingStore");
      specialProperties.add("weblogic.messaging.kernel.paging.BatchSize");
      specialProperties.add("weblogic.messaging.kernel.paging.PagedMessageThreshold");
      specialProperties.add("weblogic.ForceImplicitUpgradeIfNeeded");
      specialProperties.add("weblogic.diagnostics.instrumentation");
      specialProperties.add("weblogic.LogFormatCompatibilityEnabled");
      specialProperties.add("weblogic.j2ee.application.tmpDir");
      specialProperties.add("weblogic.management.allowPasswordEcho");
      specialProperties.add("webservice.client.ssl.trustedcertfile");
      specialProperties.add("weblogic.security.audit.auditLogDir");
      specialProperties.add("weblogic.security.RegisterX509CertificateFactory");
      specialProperties.add("weblogic.management.clearTextCredentialAccessEnabled");
   }
}
