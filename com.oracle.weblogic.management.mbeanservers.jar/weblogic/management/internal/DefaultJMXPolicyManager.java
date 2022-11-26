package weblogic.management.internal;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.SecureModeMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ConsumptionException;
import weblogic.security.service.JMXPolicyConsumer;
import weblogic.security.service.JMXPolicyHandler;
import weblogic.security.service.JMXResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class DefaultJMXPolicyManager {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   static boolean registeredPolicies;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static String[] SECURE_ROLES = new String[]{"Deployer", "Monitor", "Operator"};
   private static String RESET_TIMESTAMP = formatTimestamp("0");

   public static synchronized void init() throws ConsumptionException {
      if (!registeredPolicies) {
         registerDefaultPolicies(false);
      }

   }

   public static synchronized void reset() throws ConsumptionException {
      registerDefaultPolicies(true);
      registerDefaultPolicies(false);
   }

   private static void registerDefaultPolicies(boolean resetPolicies) throws ConsumptionException {
      JMXPolicyConsumer policyConsumer = SecurityServiceManager.getJMXPolicyConsumer(kernelId);
      SecureModeMBean secureMode = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getSecureMode();
      boolean isSecureMode = secureMode.isSecureModeEnabled() && secureMode.isRestrictiveJMXPolicies();
      String setName = "WLSDefaultJMXResourcePolicies";
      Calendar initVersion = Calendar.getInstance();
      initVersion.clear();
      initVersion.set(2005, 7, 11, 0, 0, 0);
      String longFormat = "" + initVersion.getTime().getTime();
      String timestamp = resetPolicies ? RESET_TIMESTAMP : formatTimestamp(longFormat);
      String version = VersionInfo.theOne().getReleaseVersion();
      String fullVersion = getFullVersion();
      long startTime = 0L;
      if (debug.isDebugEnabled()) {
         debug.debug("Start registration of default JMX Resource policies.");
         startTime = System.currentTimeMillis();
      }

      boolean registerPolicy = true;
      JMXPolicyHandler handler = policyConsumer.getJMXPolicyHandler(setName, version, timestamp);
      if (handler != null) {
         if (isSecureMode) {
            setPolicy(handler, new JMXResource("get", (String)null, (String)null, (String)null), SECURE_ROLES, (String[])null);
            setPolicy(handler, new JMXResource("find", (String)null, (String)null, (String)null), SECURE_ROLES, (String[])null);
         } else {
            setUncheckedPolicy(handler, new JMXResource("get", (String)null, (String)null, (String)null));
            setUncheckedPolicy(handler, new JMXResource("find", (String)null, (String)null, (String)null));
         }

         setPolicy(handler, new JMXResource((String)null, (String)null, (String)null, (String)null), new String[0], (String[])null);
         handler.done();
      }

      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      String[] factoryNames = beanInfoAccess.getBeanInfoFactoryNames();

      for(int i = 0; factoryNames != null && i < factoryNames.length; ++i) {
         String factoryName = factoryNames[i];
         String[] interfaces = beanInfoAccess.getInterfacesWithRoleInfo(factoryName);
         if (interfaces != null && interfaces.length != 0) {
            longFormat = beanInfoAccess.getRoleInfoImplementationFactoryTimestamp(factoryName);
            timestamp = resetPolicies ? RESET_TIMESTAMP : formatTimestamp(longFormat);
            handler = policyConsumer.getJMXPolicyHandler(factoryName, version, timestamp);
            if (handler != null) {
               for(int j = 0; j < interfaces.length; ++j) {
                  BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInterface(interfaces[j], false, fullVersion);
                  if (beanInfo == null) {
                     if (debug.isDebugEnabled()) {
                        debug.debug("Beaninfo for interface is null - interface is " + interfaces[j]);
                     }
                  } else {
                     BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
                     Boolean all = (Boolean)beanDescriptor.getValue("rolePermitAll");
                     if (all != null && all) {
                        if (isSecureMode) {
                           setPolicy(handler, new JMXResource("set", (String)null, interfaces[j], (String)null), SECURE_ROLES, (String[])null);
                           setPolicy(handler, new JMXResource("invoke", (String)null, interfaces[j], (String)null), SECURE_ROLES, (String[])null);
                           setPolicy(handler, new JMXResource("create", (String)null, interfaces[j], (String)null), SECURE_ROLES, (String[])null);
                           setPolicy(handler, new JMXResource("unregister", (String)null, interfaces[j], (String)null), SECURE_ROLES, (String[])null);
                        } else {
                           setUncheckedPolicy(handler, new JMXResource("set", (String)null, interfaces[j], (String)null));
                           setUncheckedPolicy(handler, new JMXResource("invoke", (String)null, interfaces[j], (String)null));
                           setUncheckedPolicy(handler, new JMXResource("create", (String)null, interfaces[j], (String)null));
                           setUncheckedPolicy(handler, new JMXResource("unregister", (String)null, interfaces[j], (String)null));
                        }
                     }

                     String[] beanRoles = (String[])((String[])beanDescriptor.getValue("rolesAllowed"));
                     if (beanRoles != null) {
                        setPolicy(handler, new JMXResource("set", (String)null, interfaces[j], (String)null), beanRoles, (String[])null);
                        setPolicy(handler, new JMXResource("invoke", (String)null, interfaces[j], (String)null), beanRoles, (String[])null);
                        setPolicy(handler, new JMXResource("create", (String)null, interfaces[j], (String)null), beanRoles, (String[])null);
                        setPolicy(handler, new JMXResource("unregister", (String)null, interfaces[j], (String)null), beanRoles, (String[])null);
                     }

                     MethodDescriptor[] mthds = beanInfo.getMethodDescriptors();

                     for(int k = 0; mthds != null && k < mthds.length; ++k) {
                        MethodDescriptor mthd = mthds[k];
                        all = (Boolean)mthd.getValue("rolePermitAll");
                        if (all != null && all) {
                           if (isSecureMode) {
                              setPolicy(handler, new JMXResource("invoke", (String)null, interfaces[j], mthd.getName()), SECURE_ROLES, (String[])null);
                           } else {
                              setUncheckedPolicy(handler, new JMXResource("invoke", (String)null, interfaces[j], mthd.getName()));
                           }
                        }

                        String[] mthdRoles = (String[])((String[])mthd.getValue("rolesAllowed"));
                        if (mthdRoles != null) {
                           setPolicy(handler, new JMXResource("invoke", (String)null, interfaces[j], mthd.getName()), beanRoles, mthdRoles);
                        }
                     }

                     PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();

                     for(int k = 0; props != null && k < props.length; ++k) {
                        PropertyDescriptor prop = props[k];
                        String getOperation = "get";
                        String setOperation = "set";
                        Boolean encrypted = (Boolean)prop.getValue("encrypted");
                        Boolean sensitive = (Boolean)prop.getValue("sensitive");
                        if (encrypted != null && encrypted || sensitive != null && sensitive) {
                           getOperation = "getEncrypted";
                           setOperation = "setEncrypted";
                        }

                        all = (Boolean)prop.getValue("rolePermitAllGet");
                        if (all != null && all) {
                           if (isSecureMode) {
                              setPolicy(handler, new JMXResource(getOperation, (String)null, interfaces[j], prop.getName()), SECURE_ROLES, (String[])null);
                           } else {
                              setUncheckedPolicy(handler, new JMXResource(getOperation, (String)null, interfaces[j], prop.getName()));
                           }
                        }

                        String[] propRoles = (String[])((String[])prop.getValue("rolesAllowedGet"));
                        if (propRoles != null) {
                           setPolicy(handler, new JMXResource(getOperation, (String)null, interfaces[j], prop.getName()), beanRoles, propRoles);
                        }

                        all = (Boolean)prop.getValue("rolePermitAllSet");
                        if (all != null && all) {
                           if (isSecureMode) {
                              setPolicy(handler, new JMXResource(setOperation, (String)null, interfaces[j], prop.getName()), SECURE_ROLES, (String[])null);
                           } else {
                              setUncheckedPolicy(handler, new JMXResource(setOperation, (String)null, interfaces[j], prop.getName()));
                           }
                        }

                        propRoles = (String[])((String[])prop.getValue("rolesAllowedSet"));
                        if (propRoles != null) {
                           setPolicy(handler, new JMXResource(setOperation, (String)null, interfaces[j], prop.getName()), beanRoles, propRoles);
                        }
                     }
                  }
               }

               handler.done();
            }
         }
      }

      if (debug.isDebugEnabled()) {
         long endTime = System.currentTimeMillis();
         debug.debug("End registration of default JMX Resource policies. Elasped time is " + (endTime - startTime));
      }

      registeredPolicies = true;
   }

   private static void setUncheckedPolicy(JMXPolicyHandler handler, JMXResource resource) throws ConsumptionException {
      if (debug.isDebugEnabled()) {
         debug.debug("Register unchecked policy " + resource);
      }

      handler.setUncheckedPolicy(resource);
   }

   private static void setPolicy(JMXPolicyHandler handler, JMXResource resource, String[] beanRoles, String[] targetRoles) throws ConsumptionException {
      int beanLen = beanRoles == null ? 0 : beanRoles.length;
      int targetLen = targetRoles == null ? 0 : targetRoles.length;
      int len = beanLen + targetLen;
      String[] newRoles = new String[len + 1];

      int i;
      for(i = 0; i < beanLen; ++i) {
         newRoles[i] = beanRoles[i];
      }

      for(i = 0; i < targetLen; ++i) {
         newRoles[i + beanLen] = targetRoles[i];
      }

      newRoles[len] = "Admin";
      if (debug.isDebugEnabled()) {
         String rolesStr = "";

         for(int i = 0; newRoles != null && i < newRoles.length; ++i) {
            if (i > 0) {
               rolesStr = rolesStr + ",";
            }

            rolesStr = rolesStr + newRoles[i];
         }

         debug.debug("Register checked policy " + resource + " roles " + rolesStr);
      }

      handler.setPolicy(resource, newRoles);
   }

   private static String formatTimestamp(String longFormat) {
      Date date = null;

      try {
         Long lng = new Long(longFormat);
         date = new Date(lng);
      } catch (NumberFormatException var3) {
         date = new Date();
      }

      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
      return formatter.format(date);
   }

   private static String getFullVersion() {
      VersionInfo vi = VersionInfo.theOne();
      return vi.getMajor() + "." + vi.getMinor() + "." + vi.getServicePack() + "." + vi.getRollingPatch() + "." + vi.getPatchUpdate();
   }
}
