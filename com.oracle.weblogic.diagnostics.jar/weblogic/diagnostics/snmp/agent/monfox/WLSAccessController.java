package weblogic.diagnostics.snmp.agent.monfox;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;
import javax.management.ObjectName;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.ext.acm.AppAcm;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.v3.usm.ext.UsmUserSecurityExtension;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.mbeanservers.SecurityUtil;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;

public class WLSAccessController implements AppAcm.AccessController {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final WLSAccessController SINGLETON = new WLSAccessController();
   private String community = "";
   private boolean communityBasedAccessEnabled = true;
   private SnmpEngineID snmpEngineId;
   private Set validContextNames = new HashSet();
   private int accessFailureCount;

   public static WLSAccessController getInstance() {
      return SINGLETON;
   }

   private WLSAccessController() {
      if (ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer()) {
         ServerMBean[] servers = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getServers();

         for(int i = 0; i < servers.length; ++i) {
            this.validContextNames.add(servers[i].getName());
         }

         this.validContextNames.add(ManagementService.getRuntimeAccess(KERNEL_ID).getDomainName());
      }

   }

   public boolean checkAccess(SnmpAgent snmp_agent, String sec_name, int snmp_version, int sec_level, boolean is_write, String context_name) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLSAccessController: [" + sec_name + "," + snmp_version + "," + sec_level + "," + is_write + "," + context_name + "]");
      }

      if (!this.isAccessAllowed(is_write, snmp_version, sec_level, sec_name)) {
         ++this.accessFailureCount;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Access failed");
         }

         return false;
      } else {
         return true;
      }
   }

   public boolean checkAccess(SnmpAgent snmp_agent, String sec_name, int snmp_version, int sec_level, boolean is_write, String context_name, SnmpOid object_oid, String object_name, SnmpOid instance_oid) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLSAccessController: [" + sec_name + "," + snmp_version + "," + sec_level + "," + is_write + "," + context_name + "," + object_oid + "," + object_name + "," + instance_oid + ",]");
      }

      if (!this.isAccessAllowed(is_write, snmp_version, sec_level, sec_name)) {
         ++this.accessFailureCount;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Access failed");
         }

         return false;
      } else if (snmp_version <= 2 && this.communityBasedAccessEnabled) {
         return true;
      } else {
         SnmpMib mib = snmp_agent.getMib();
         SnmpMibNode node = mib.get(instance_oid);
         if (node != null && node instanceof SnmpMibLeaf) {
            SnmpMibLeaf leaf = (SnmpMibLeaf)node;
            SnmpMibTableRow row = leaf.getRow();
            if (row != null) {
               Object user_obj = row.getUserObject();
               if (user_obj instanceof MBeanInstanceTableRow) {
                  MBeanInstanceTableRow mbean_row = (MBeanInstanceTableRow)user_obj;

                  boolean success;
                  try {
                     String attributeName = mbean_row.getAttributeName(object_name);
                     if (DEBUG_LOGGER.isDebugEnabled()) {
                        DEBUG_LOGGER.debug("Checking access for attribute " + attributeName);
                     }

                     if (attributeName == null) {
                        success = false;
                     } else if (!attributeName.equals("Index") && !attributeName.equals("ObjectName")) {
                        success = this.isMBeanAccessAllowed(sec_name, mbean_row.getObjectName(), attributeName);
                     } else {
                        success = true;
                     }
                  } catch (Exception var18) {
                     if (DEBUG_LOGGER.isDebugEnabled()) {
                        DEBUG_LOGGER.debug("Exception checking MBean access", var18);
                     }

                     success = false;
                  }

                  if (!success) {
                     ++this.accessFailureCount;
                  }

                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("MBean access check = " + success);
                  }

                  return success;
               }
            }
         }

         return true;
      }
   }

   private boolean isMBeanAccessAllowed(String user_name, final ObjectName object_name, final String attribute_name) throws Exception {
      PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNEL_ID, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
      AuthenticatedSubject as = pa.impersonateIdentity(user_name, (ContextHandler)null);
      Object o = SecurityServiceManager.runAs(KERNEL_ID, as, new PrivilegedAction() {
         public Object run() {
            try {
               boolean b = SecurityUtil.isGetAccessAllowed(2, object_name, attribute_name);
               return new Boolean(b);
            } catch (Exception var2) {
               return Boolean.FALSE;
            }
         }
      });
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Returned object is " + o + " for user_name " + user_name + " object_name " + object_name + "attribute_name " + attribute_name);
      }

      if (o instanceof Boolean) {
         Boolean b = (Boolean)o;
         return b;
      } else {
         return false;
      }
   }

   private boolean isAccessAllowed(boolean is_write, int snmp_version, int sec_level, String sec_name) {
      if (is_write) {
         return false;
      } else {
         if (snmp_version == 3) {
            UsmUserSecurityExtension.UserInfo ui = WLSSecurityExtension.getInstance().getUserInfo(sec_name, this.snmpEngineId);
            if (ui == null) {
               return false;
            }

            boolean ui_auth = this.isAuthSecurityLevel(ui.getSecLevel());
            boolean client_auth = this.isAuthSecurityLevel(sec_level);
            if (ui_auth && !client_auth) {
               return false;
            }
         }

         if (snmp_version <= 2 && !this.communityBasedAccessEnabled) {
            return false;
         } else {
            String inputCommunity = sec_name;
            int index = sec_name.indexOf("@");
            if (index > 0) {
               inputCommunity = sec_name.substring(0, index);
               String contextName = index < sec_name.length() - 1 ? sec_name.substring(index + 1) : "";
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Context Name = " + contextName);
               }

               contextName = contextName == null ? "" : contextName;
               if (!this.validContextNames.contains(contextName)) {
                  String msg = "Invalid Context Name " + contextName;
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug(msg);
                  }

                  throw new IllegalArgumentException(msg);
               }
            }

            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Input community = " + inputCommunity);
            }

            return snmp_version > 2 || inputCommunity.equals(this.community);
         }
      }
   }

   private boolean isAuthSecurityLevel(int sec_level) {
      switch (sec_level) {
         case 1:
         case 3:
            return true;
         default:
            return false;
      }
   }

   String getCommunity() {
      return this.community;
   }

   void setCommunity(String community) {
      this.community = community;
   }

   boolean isCommunityBasedAccessEnabled() {
      return this.communityBasedAccessEnabled;
   }

   void setCommunityBasedAccessEnabled(boolean communityBasedAccessEnabled) {
      this.communityBasedAccessEnabled = communityBasedAccessEnabled;
   }

   void setSnmpEngineId(SnmpEngineID snmpEngineId) {
      this.snmpEngineId = snmpEngineId;
   }

   public int getFailedAuthorizationCount() {
      return this.accessFailureCount;
   }
}
