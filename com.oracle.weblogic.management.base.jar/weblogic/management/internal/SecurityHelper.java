package weblogic.management.internal;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.management.ObjectName;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.ManagementLogger;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SecureModeMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.security.RealmMBean;
import weblogic.management.visibility.utils.MBeanNameUtil;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.service.AdminResource;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.MBeanResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.RoleManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.MBeanResource.ActionType;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.PartitionUtils;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.utils.Debug;

public class SecurityHelper {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static final String ADMIN_ROLENAME = "Admin";
   private static final String DEPLOYER_ROLENAME = "Deployer";
   private static final String OPERATOR_ROLENAME = "Operator";
   private static final String MONITOR_ROLENAME = "Monitor";
   private static final String[] SECURE_ROLES = new String[]{"Admin", "Deployer", "Operator", "Monitor"};
   private static final boolean ENABLE_ACL_EXCEPTION = true;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean disableACLOnMbeans = Boolean.getBoolean("weblogic.disableMBeanAuthorization");
   private static boolean debugACLs = Boolean.getBoolean("DEBUG_ACLS");
   private static PrintStream aclPrintStream = null;
   private static boolean isSecServiceInitialized;
   private static AdminResource adminMBeanResource = new AdminResource("Configuration", (String)null, (String)null);
   private static BeanInfoAccess beanInfoAccess;
   private static RoleManager roleManager;

   public static void checkForAdminRole(ContextHandler partitionContextHandler) {
      checkForRole("Admin", partitionContextHandler);
   }

   public static void checkForAdminRole(ContextHandler partitionContextHandler, String[] excludedRoles) {
      checkForRole("Admin", partitionContextHandler, excludedRoles);
   }

   public static boolean isProtectedAttribute(ObjectName name, String attributeName, PropertyDescriptor propertyDescriptor) {
      if (propertyDescriptor != null) {
         Boolean encrypted = (Boolean)propertyDescriptor.getValue("encrypted");
         if (encrypted != null && encrypted) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("SecurityHelper - attribute " + attributeName + " for object " + name + " is protected");
            }

            return true;
         }

         Boolean sensitive = (Boolean)propertyDescriptor.getValue("sensitive");
         if (sensitive != null && sensitive) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("SecurityHelper - attribute " + attributeName + " for object " + name + " is protected");
            }

            return true;
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SecurityHelper - attribute " + attributeName + " for object " + name + " is NOT protected");
      }

      return false;
   }

   public static void isAccessAllowed(ObjectName name, MBeanResource.ActionType action, String target, String methodName) throws NoAccessRuntimeException {
      isAccessAllowed(name, action, target, methodName, (BeanDescriptor)null, (MethodDescriptor)null, (PropertyDescriptor)null);
   }

   public static void isAccessAllowed(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor) throws NoAccessRuntimeException {
      isAccessAllowed(name, action, target, methodName, beanDescriptor, (MethodDescriptor)null, (PropertyDescriptor)null);
   }

   public static void isAccessAllowed(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor, PropertyDescriptor propertyDescriptor) throws NoAccessRuntimeException {
      isAccessAllowed(name, action, target, methodName, beanDescriptor, (MethodDescriptor)null, propertyDescriptor);
   }

   public static void isAccessAllowed(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor, MethodDescriptor methodDescriptor) throws NoAccessRuntimeException {
      isAccessAllowed(name, action, target, methodName, beanDescriptor, methodDescriptor, (PropertyDescriptor)null);
   }

   private static boolean isAllowed(ObjectName name, MBeanResource.ActionType action, String target, String property, PropertyDescriptor descriptor) {
      try {
         isAccessAllowed(name, action, target, property, (BeanDescriptor)null, (MethodDescriptor)null, descriptor);
         return true;
      } catch (NoAccessRuntimeException var6) {
         return false;
      }
   }

   public static boolean isAllowedAnon(ObjectName name, MBeanResource.ActionType action, String target, String property, PropertyDescriptor descriptor) {
      SecureModeMBean secMode = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getSecurityConfiguration().getSecureMode();
      boolean isSecureMode = secMode.isSecureModeEnabled() && secMode.isRestrictiveJMXPolicies();
      if (action == ActionType.READ && isSecureMode) {
         if (descriptor == null) {
            return true;
         } else {
            Boolean encrypted = (Boolean)descriptor.getValue("encrypted");
            if (encrypted != null && encrypted) {
               return false;
            } else {
               Boolean sensitive = (Boolean)descriptor.getValue("sensitive");
               return sensitive == null || !sensitive;
            }
         }
      } else {
         return isAllowed(AuthenticatedSubject.ANON, name, action, target, property, descriptor);
      }
   }

   public static boolean isAllowed(AuthenticatedSubject subject, final ObjectName name, final MBeanResource.ActionType action, final String target, final String property, final PropertyDescriptor descriptor) {
      Boolean result = (Boolean)SecurityServiceManager.runAs(KERNEL_ID, subject, new PrivilegedAction() {
         public Object run() {
            return new Boolean(SecurityHelper.isAllowed(name, action, target, property, descriptor));
         }
      });
      return result;
   }

   private static void isAccessAllowed(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor, MethodDescriptor methodDescriptor, PropertyDescriptor propertyDescriptor) throws NoAccessRuntimeException {
      if (!disableACLOnMbeans) {
         SecureModeMBean secMode = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getSecurityConfiguration().getSecureMode();
         boolean isSecureMode = secMode.isSecureModeEnabled() && secMode.isRestrictiveJMXPolicies();
         if (action != ActionType.FIND || isSecureMode) {
            AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
            if (!SecurityServiceManager.isKernelIdentity(subject)) {
               IsAccessAllowedPrivilegeAction isAccessAllowedPrivilegeAction = new IsAccessAllowedPrivilegeAction(subject, name, action, target, beanDescriptor, methodDescriptor, propertyDescriptor, isSecureMode);
               SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, isAccessAllowedPrivilegeAction);
            }

         }
      }
   }

   public static void isAccessAllowedCommo(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor) throws NoAccessRuntimeException {
      isAccessAllowedCommo(name, action, target, methodName, beanDescriptor, (MethodDescriptor)null, (PropertyDescriptor)null);
   }

   public static void isAccessAllowedCommo(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor, PropertyDescriptor propertyDescriptor) throws NoAccessRuntimeException {
      isAccessAllowedCommo(name, action, target, methodName, beanDescriptor, (MethodDescriptor)null, propertyDescriptor);
   }

   public static void isAccessAllowedCommo(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor, MethodDescriptor methodDescriptor) throws NoAccessRuntimeException {
      isAccessAllowedCommo(name, action, target, methodName, beanDescriptor, methodDescriptor, (PropertyDescriptor)null);
   }

   private static void isAccessAllowedCommo(ObjectName name, MBeanResource.ActionType action, String target, String methodName, BeanDescriptor beanDescriptor, MethodDescriptor methodDescriptor, PropertyDescriptor propertyDescriptor) throws NoAccessRuntimeException {
      if (!disableACLOnMbeans) {
         SecureModeMBean secMode = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getSecurityConfiguration().getSecureMode();
         boolean isSecureMode = secMode.isSecureModeEnabled() && secMode.isRestrictiveJMXPolicies();
         FeatureDescriptor childDescriptor = action != ActionType.EXECUTE && action != ActionType.REGISTER && action != ActionType.UNREGISTER ? propertyDescriptor : methodDescriptor;
         ContextHandler partitionContextHandler = getResourceContextHandler((ObjectName)name, new JMXContextHandler(name), beanDescriptor, (FeatureDescriptor)childDescriptor, ActionType.FIND.equals(action) ? "find" : "invoke");
         if (action == ActionType.FIND) {
            if (isSecureMode) {
               checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
            }
         } else {
            String[] methodExcludedRoles;
            if (action == ActionType.WRITE) {
               if (propertyDescriptor == null) {
                  if (isSecureMode) {
                     checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
                  }

               } else {
                  methodExcludedRoles = (String[])((String[])propertyDescriptor.getValue("rolesExcludedSet"));
                  checkForAdminRole(partitionContextHandler, methodExcludedRoles);
               }
            } else if (action == ActionType.UNREGISTER) {
               if (methodDescriptor == null) {
                  if (isSecureMode) {
                     checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
                  }

               } else {
                  methodExcludedRoles = (String[])((String[])methodDescriptor.getValue("rolesExcluded"));
                  checkForAdminRole(partitionContextHandler, methodExcludedRoles);
               }
            } else if (action == ActionType.REGISTER) {
               if (isSecureMode) {
                  checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
               }

            } else if (name == null) {
               throw new IllegalArgumentException("Object name for an MBean can not be null");
            } else {
               AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
               if (!SecurityServiceManager.isKernelIdentity(subject)) {
                  String[] methodRoles;
                  if (action == ActionType.READ) {
                     if (propertyDescriptor == null) {
                        if (isSecureMode) {
                           checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
                        }

                     } else {
                        Boolean encrypted = (Boolean)propertyDescriptor.getValue("encrypted");
                        if (encrypted != null && encrypted) {
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug("SecurityHelper - read encrypted, check for admin, attr = " + target);
                           }

                           methodRoles = (String[])((String[])propertyDescriptor.getValue("rolesExcludedGet"));
                           checkForAdminRole(partitionContextHandler, methodRoles);
                        }

                        Boolean sensitive = (Boolean)propertyDescriptor.getValue("sensitive");
                        if (sensitive != null && sensitive) {
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug("SecurityHelper - read encrypted, check for admin, attr = " + target);
                           }

                           String[] propExcludedRoles = (String[])((String[])propertyDescriptor.getValue("rolesExcludedGet"));
                           checkForAdminRole(partitionContextHandler, propExcludedRoles);
                        }

                        if (isSecureMode) {
                           checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
                        }

                     }
                  } else if (action == ActionType.EXECUTE) {
                     Boolean all;
                     String[] methodExcludedRoles;
                     if (beanDescriptor != null) {
                        methodExcludedRoles = (String[])((String[])beanDescriptor.getValue("rolesExcluded"));
                        methodRoles = (String[])((String[])beanDescriptor.getValue("rolesAllowed"));
                        if (checkForRoles(methodRoles, partitionContextHandler, methodExcludedRoles)) {
                           return;
                        }

                        all = (Boolean)beanDescriptor.getValue("rolePermitAll");
                        if (all != null && all) {
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug("SecurityHelper - rolePermitAll found for interface " + target);
                           }

                           if (isSecureMode) {
                              checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
                           }

                           return;
                        }
                     }

                     if (methodDescriptor != null) {
                        methodExcludedRoles = (String[])((String[])methodDescriptor.getValue("rolesExcluded"));
                        methodRoles = (String[])((String[])methodDescriptor.getValue("rolesAllowed"));
                        if (!checkForRoles(methodRoles, partitionContextHandler, methodExcludedRoles)) {
                           all = (Boolean)methodDescriptor.getValue("rolePermitAll");
                           if (all != null && all) {
                              if (debugLogger.isDebugEnabled()) {
                                 debugLogger.debug("SecurityHelper - rolePermitAll found for method " + target);
                              }

                              if (isSecureMode) {
                                 checkForRoles(SECURE_ROLES, partitionContextHandler, (String[])null);
                              }

                           } else {
                              checkForAdminRole(partitionContextHandler, methodExcludedRoles);
                           }
                        }
                     } else {
                        checkForAdminRole(partitionContextHandler);
                     }
                  } else {
                     Loggable l = ManagementLogger.logNoAccessAllowedSubjectLoggable(subject.toString(), name.toString(), action.toString(), target);
                     throw new NoAccessRuntimeException(l.getMessage());
                  }
               }
            }
         }
      }
   }

   public static void assertIfNotKernel(AuthenticatedSubject sub) {
      if (sub != KERNEL_ID) {
         Loggable l = ManagementLogger.logNotKernelUserLoggable(sub.toString());
         throw new AssertionError(l.getMessage());
      }
   }

   public static void assertIfNotKernel() {
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (!SecurityServiceManager.isKernelIdentity(subject)) {
         Loggable l = ManagementLogger.logNotKernelUserLoggable(subject.toString());
         throw new AssertionError(l.getMessage());
      }
   }

   private static boolean isInRole(Map roles, String roleName) {
      return roles != null && roles.get(roleName) != null;
   }

   private static boolean isInRoles(Map roles, String[] roleNames) {
      if (roles == null) {
         return false;
      } else {
         for(int i = 0; roleNames != null && i < roleNames.length; ++i) {
            if (roles.get(roleNames[i]) != null) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isInRoleOrInAdmin(Map rolesAllowedBySystem, String[] roleAllowedOnMBean) {
      String[] allRoles;
      if (roleAllowedOnMBean != null) {
         allRoles = new String[roleAllowedOnMBean.length + 1];
         allRoles[0] = "Admin";
         System.arraycopy(roleAllowedOnMBean, 0, allRoles, 1, roleAllowedOnMBean.length);
         roleAllowedOnMBean = allRoles;
      } else {
         allRoles = new String[]{"Admin"};
         roleAllowedOnMBean = allRoles;
      }

      for(int i = 0; i < roleAllowedOnMBean.length; ++i) {
         if (rolesAllowedBySystem.get(roleAllowedOnMBean[i]) != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("SecurityHelper - in roleAllowedOnMBean is true, roleAllowedOnMBean " + roleAllowedOnMBean[i]);
            }

            return true;
         }
      }

      return false;
   }

   private static RoleManager getRoleManager() {
      return roleManager != null ? roleManager : (roleManager = (RoleManager)SecurityServiceManager.getSecurityService(KERNEL_ID, SecurityServiceManager.getAdministrativeRealmName(), ServiceType.ROLE));
   }

   static Map getRoles(AuthenticatedSubject subject, ObjectName name, FeatureDescriptor parent, FeatureDescriptor child) {
      ContextHandler wrapper = getResourceContextHandler((ObjectName)name, new JMXContextHandler(name), parent, child, (String)null);
      return getRoleManager().getRoles(subject, adminMBeanResource, wrapper);
   }

   private static void checkForRole(String roleName, ContextHandler partitionContextHandler) {
      checkForRole(roleName, partitionContextHandler, (String[])null);
   }

   private static void checkForRole(final String roleName, final ContextHandler partitionContextHandler, final String[] excludedRoles) {
      AuthenticatedSubject curSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (!SecurityServiceManager.isKernelIdentity(curSubject)) {
         final AuthenticatedSubject subject = SecurityServiceManager.seal(KERNEL_ID, curSubject);
         Boolean isInRole = (Boolean)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedAction() {
            public Object run() {
               Map roles = SecurityHelper.getRoleManager().getRoles(subject, SecurityHelper.adminMBeanResource, partitionContextHandler);
               roles = SecurityHelper.removeExcludedRoles(roles, excludedRoles);
               return roles == null || roles.get("Admin") == null && roles.get(roleName) == null ? Boolean.FALSE : Boolean.TRUE;
            }
         });
         if (!isInRole) {
            Loggable l = ManagementLogger.logNoAccessForSubjectRoleLoggable(subject.toString(), roleName);
            throw new NoAccessRuntimeException(l.getMessage());
         }
      }
   }

   private static boolean checkForRoles(final String[] roleNames, final ContextHandler partitionContextHandler, final String[] excludedRoles) {
      AuthenticatedSubject curSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      final AuthenticatedSubject subject = SecurityServiceManager.seal(KERNEL_ID, curSubject);
      Boolean isInRole = (Boolean)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedAction() {
         public Object run() {
            Map roles = SecurityHelper.getRoleManager().getRoles(subject, SecurityHelper.adminMBeanResource, partitionContextHandler);
            roles = SecurityHelper.removeExcludedRoles(roles, excludedRoles);
            if (roles != null && roleNames != null) {
               for(int i = 0; i < roleNames.length; ++i) {
                  if (roles.get(roleNames[i]) != null) {
                     if (SecurityHelper.debugLogger.isDebugEnabled()) {
                        SecurityHelper.debugLogger.debug("SecurityHelper - role found " + roleNames[i]);
                     }

                     return Boolean.TRUE;
                  }
               }

               if (SecurityHelper.debugLogger.isDebugEnabled()) {
                  SecurityHelper.debugLogger.debug("SecurityHelper - role not found ");
               }

               return Boolean.FALSE;
            } else {
               return Boolean.FALSE;
            }
         }
      });
      return isInRole;
   }

   private static synchronized void dumpAclDebug(AuthenticatedSubject subject, ObjectName name, MBeanResource.ActionType action, String target, String methodName) {
      try {
         if (aclPrintStream == null) {
            String aclFileName = ManagementService.getRuntimeAccess(KERNEL_ID).getServerName() + "_debug_acls.txt";
            Debug.say("Opening ACL Log" + aclFileName);
            File aclFile = new File(aclFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(aclFile);
            aclPrintStream = new PrintStream(fileOutputStream);
         }

         aclPrintStream.println("START: INVALID MBEAN ACCESS");
         aclPrintStream.println("PRINCIPALS:" + subject.getPrincipals());
         aclPrintStream.println("RESOURCE:" + name + "|" + action + "|" + target + "|" + methodName);
         Exception ex = new Exception();
         ex.printStackTrace(aclPrintStream);
         aclPrintStream.println("END:INVALID MBEAN ACCESS");
      } catch (FileNotFoundException var8) {
         Debug.say("**** UNABLE TO OPEN DEBUG FILE *****");
      }

   }

   private static BeanInfo getBeanInfo(String type) {
      if (beanInfoAccess == null) {
         beanInfoAccess = ManagementService.getBeanInfoAccess();
      }

      String version = VersionInfo.theOne().getReleaseVersion();
      BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInterface(type, false, version);
      String rtType;
      if (beanInfo == null && type.indexOf(".") == -1) {
         rtType = "weblogic.management.configuration." + type + "MBean";
         beanInfo = beanInfoAccess.getBeanInfoForInterface(rtType, false, version);
      }

      if (beanInfo == null && type.indexOf(".") == -1) {
         rtType = "weblogic.management.runtime." + type + "MBean";
         beanInfo = beanInfoAccess.getBeanInfoForInterface(rtType, true, version);
      }

      return beanInfo;
   }

   public static BeanDescriptor getBeanDescriptor(String type) {
      BeanInfo beanInfo = getBeanInfo(type);
      return beanInfo != null ? beanInfo.getBeanDescriptor() : null;
   }

   public static PropertyDescriptor getPropertyDescriptor(String type, String attr) {
      BeanInfo beanInfo = getBeanInfo(type);
      if (beanInfo == null) {
         return null;
      } else {
         PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();

         for(int i = 0; i < props.length; ++i) {
            PropertyDescriptor prop = props[i];
            String name = prop.getName();
            if (attr.equals(name)) {
               return prop;
            }
         }

         return null;
      }
   }

   public static MethodDescriptor getMethodDescriptor(String type, String method) {
      BeanInfo beanInfo = getBeanInfo(type);
      if (beanInfo == null) {
         return null;
      } else {
         MethodDescriptor[] mthds = beanInfo.getMethodDescriptors();

         for(int i = 0; i < mthds.length; ++i) {
            MethodDescriptor mthd = mthds[i];
            String name = mthd.getName();
            if (method.equals(name)) {
               return mthd;
            }
         }

         return null;
      }
   }

   private static boolean getDecision(FeatureDescriptor parent, FeatureDescriptor child, ObjectName name, AuthenticatedSubject subject, MBeanResource.ActionType type, boolean isSecureMode) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Checking for decision for " + name.getCanonicalName() + " for action type " + type);
      }

      String typeStr = "";
      if (type == ActionType.WRITE) {
         typeStr = "Set";
      } else if (type == ActionType.READ) {
         typeStr = "Get";
      }

      ContextHandler wrapper = getResourceContextHandler((ObjectName)name, new JMXContextHandler(name), parent, child, "Get".equals(typeStr) ? "get" : "set");
      Map roles = getRoleManager().getRoles(subject, adminMBeanResource, wrapper);
      Set combinedSet = new HashSet();
      String[] methodExcludedRoles;
      String[] childRoles;
      if (parent != null) {
         methodExcludedRoles = (String[])((String[])parent.getValue("rolesExcluded" + typeStr));
         roles = removeExcludedRoles(roles, methodExcludedRoles);
         childRoles = (String[])((String[])parent.getValue("rolesAllowed"));
         if (childRoles != null) {
            combinedSet = new HashSet(Arrays.asList(childRoles));
         }
      }

      if (child != null) {
         methodExcludedRoles = (String[])((String[])child.getValue("rolesExcluded" + typeStr));
         roles = removeExcludedRoles(roles, methodExcludedRoles);
         childRoles = (String[])((String[])child.getValue("rolesAllowed" + typeStr));
         if (childRoles != null) {
            combinedSet.addAll(new HashSet(Arrays.asList(childRoles)));
         }
      }

      Boolean allOnInterface = parent != null ? (Boolean)parent.getValue("rolePermitAll") : null;
      Boolean allOnChild = child != null ? (Boolean)child.getValue("rolePermitAll" + typeStr) : null;
      if (allOnInterface != null && allOnInterface) {
         return isSecureMode ? isInRoles(roles, SECURE_ROLES) : Boolean.TRUE;
      } else if (allOnChild != null && allOnChild) {
         return isSecureMode ? isInRoles(roles, SECURE_ROLES) : Boolean.TRUE;
      } else {
         return isInRoleOrInAdmin(roles, (String[])combinedSet.toArray(new String[0])) ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   private static Map removeExcludedRoles(Map roles, String[] excludedRoles) {
      if (roles == null || roles != null && roles.isEmpty() || excludedRoles == null || excludedRoles != null && excludedRoles.length == 0) {
         return roles;
      } else {
         Map newRoles = new HashMap();

         for(int i = 0; i < excludedRoles.length; ++i) {
            if (roles.get(excludedRoles[i]) == null) {
               newRoles.put(excludedRoles[i], roles.get(excludedRoles[i]));
            }
         }

         return newRoles;
      }
   }

   public static ContextHandler getResourceContextHandler(ObjectName name, ContextHandler contextHandler, FeatureDescriptor parentDescriptor, FeatureDescriptor childDescriptor, String operation) {
      return getResourceContextHandler((MBeanPartitionFinder)(new JMXMBeanPartitionFinder(name)), contextHandler, parentDescriptor, childDescriptor, operation);
   }

   public static ContextHandler getResourceContextHandler(MBeanPartitionFinder finder, ContextHandler contextHandler, FeatureDescriptor parentDescriptor, FeatureDescriptor childDescriptor, String operation) {
      Owner parentOwner = null;
      Owner childOwner = null;
      String ownerStr;
      if (parentDescriptor != null) {
         ownerStr = (String)parentDescriptor.getValue("owner");
         if (ownerStr != null && !ownerStr.isEmpty()) {
            parentOwner = SecurityHelper.Owner.valueOf(ownerStr);
         }
      }

      if (childDescriptor != null) {
         ownerStr = (String)childDescriptor.getValue("owner");
         if (ownerStr != null && !ownerStr.isEmpty()) {
            childOwner = SecurityHelper.Owner.valueOf(ownerStr);
         }
      }

      ownerStr = null;
      Owner owner;
      if (childOwner != null) {
         owner = childOwner;
      } else {
         owner = parentOwner;
      }

      ResourceIDDContextWrapper wrapper = null;
      if (owner == SecurityHelper.Owner.Domain) {
         wrapper = new ResourceIDDContextWrapper(contextHandler, false);
         wrapper.setResourceIdentityDomain(PartitionUtils.getAdminIdentityDomain());
      } else if (owner == SecurityHelper.Owner.Partition) {
         wrapper = new ResourceIDDContextWrapper(contextHandler, false);
         if (PartitionUtils.getPartitionName() != null && !PartitionUtils.getPartitionName().isEmpty()) {
            wrapper.setResourcePartition(PartitionUtils.getPartitionName());
         } else {
            wrapper.setResourceIdentityDomain("");
         }
      } else if (owner == SecurityHelper.Owner.Context) {
         wrapper = new ResourceIDDContextWrapper(contextHandler, true);
      } else {
         String resourcePartition;
         if (owner == SecurityHelper.Owner.RealmAdministrator && PartitionUtils.getPartitionName() != null && !PartitionUtils.getPartitionName().isEmpty()) {
            resourcePartition = getRealmManagementIdentityDomain(PartitionUtils.getPartitionName(), finder.getObjectName());
            if (resourcePartition != null) {
               wrapper = new ResourceIDDContextWrapper(contextHandler, false);
               wrapper.setResourceIdentityDomain(resourcePartition);
            } else {
               wrapper = new ResourceIDDContextWrapper(contextHandler, false);
               wrapper.setResourceIdentityDomain(PartitionUtils.getAdminIdentityDomain());
            }
         } else {
            wrapper = new ResourceIDDContextWrapper(contextHandler, false);
            resourcePartition = "";

            try {
               resourcePartition = finder.getPartitionName();
            } catch (Exception var11) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(" exception occured finding the partition name for " + finder.getObjectName() + " " + var11.getMessage());
               }
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("SecurityHelper Partition name: " + resourcePartition);
            }

            if (!isRunAsPartitionResourceOwner(resourcePartition, operation, parentDescriptor, childDescriptor, wrapper)) {
               wrapper.setResourcePartition(resourcePartition);
            }
         }
      }

      return wrapper;
   }

   private static String getRealmManagementIdentityDomain(String partitionName, ObjectName objectName) {
      PartitionMBean partition = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupPartition(partitionName);
      SecurityConfigurationMBean secCfg = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getSecurityConfiguration();
      String name = objectName.getKeyProperty("Name");
      if (partition != null && name != null) {
         RealmMBean realm = partition.getRealm();
         if (realm == null) {
            realm = secCfg.getDefaultRealm();
         }

         String realmName = realm.getName();
         String realmRuntimeName;
         if (!MBeanNameUtil.isSecurityMBean(objectName)) {
            if (MBeanNameUtil.isWLSMBean(objectName)) {
               realmRuntimeName = objectName.getKeyProperty("RealmRuntime");
               if (realmRuntimeName == null) {
                  String type = objectName.getKeyProperty("Type");
                  if ("RealmRuntime".equals(type)) {
                     realmRuntimeName = name;
                  }
               }

               if (realmRuntimeName != null && realmRuntimeName.equals(realmName)) {
                  return realm.getManagementIdentityDomain();
               }
            }
         } else {
            if (name.startsWith(realmName) && realm.getManagementIdentityDomain() != null && !realm.getManagementIdentityDomain().isEmpty()) {
               if (name.equals(realmName)) {
                  return realm.getManagementIdentityDomain();
               }

               realmRuntimeName = name.substring(realmName.length());
               if (realm.lookupAuditor(realmRuntimeName) == null && realm.lookupAuthenticationProvider(realmRuntimeName) == null && realm.lookupAuthorizer(realmRuntimeName) == null && realm.lookupCredentialMapper(realmRuntimeName) == null && realm.lookupCertPathProvider(realmRuntimeName) == null && realm.lookupPasswordValidator(realmRuntimeName) == null && realm.lookupRoleMapper(realmRuntimeName) == null && (realm.getAdjudicator() == null || !realm.getAdjudicator().getName().equals(realmRuntimeName)) && (realm.getUserLockoutManager() == null || !realm.getUserLockoutManager().getName().equals(realmRuntimeName))) {
                  return null;
               }

               return realm.getManagementIdentityDomain();
            }

            return null;
         }

         return null;
      } else {
         return null;
      }
   }

   private static boolean isRunAsPartitionResourceOwner(String resourcePartition, String operation, FeatureDescriptor parentDescriptor, FeatureDescriptor childDescriptor, ResourceIDDContextWrapper wrapper) {
      SecureModeMBean secMode = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getSecurityConfiguration().getSecureMode();
      boolean isSecureMode = secMode.isSecureModeEnabled() && secMode.isRestrictiveJMXPolicies();
      if (!isSecureMode) {
         return false;
      } else if (!"DOMAIN".equals(resourcePartition)) {
         return false;
      } else if (PartitionUtils.getAdminIdentityDomain() != null && !PartitionUtils.getAdminIdentityDomain().isEmpty()) {
         if (!"get".equals(operation) && !"find".equals(operation) && !"invoke".equals(operation)) {
            return false;
         } else {
            if ("invoke".equals(operation)) {
               boolean permitAll = false;
               Boolean all;
               if (parentDescriptor != null) {
                  all = (Boolean)parentDescriptor.getValue("rolePermitAll");
                  if (all != null && all) {
                     permitAll = true;
                  }
               }

               if (childDescriptor != null) {
                  all = (Boolean)childDescriptor.getValue("rolePermitAll");
                  if (all != null && all) {
                     permitAll = true;
                  }
               }

               if (!permitAll) {
                  return false;
               }
            }

            if (PartitionUtils.getPartitionName() != null && !PartitionUtils.getPartitionName().isEmpty()) {
               wrapper.setResourcePartition(PartitionUtils.getPartitionName());
            } else {
               AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
               if (subject == null) {
                  return false;
               }

               Principal user = SubjectUtils.getUserPrincipal(subject);
               if (user == null || !(user instanceof IdentityDomainPrincipal)) {
                  return false;
               }

               String idd = ((IdentityDomainPrincipal)user).getIdentityDomain();
               if (idd == null || idd.isEmpty()) {
                  return false;
               }

               wrapper.setResourceIdentityDomain(idd);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static class PartitionContextHandler implements ContextHandler {
      private final String[] invoke_keys = new String[]{"com.bea.contextelement.jmx.ObjectName"};
      private final ObjectName objectName;
      private final String[] keys;

      public PartitionContextHandler(ObjectName objectName) {
         this.objectName = objectName;
         this.keys = this.invoke_keys;
      }

      public int size() {
         return this.keys.length;
      }

      public String[] getNames() {
         return this.keys;
      }

      public Object getValue(String name) {
         return "com.bea.contextelement.jmx.ObjectName".equals(name) ? this.objectName : null;
      }

      public ContextElement[] getValues(String[] requested) {
         ContextElement[] result = new ContextElement[requested.length];
         int found = 0;

         for(int i = 0; i < requested.length; ++i) {
            Object val = this.getValue(requested[i]);
            if (val != null) {
               result[found++] = new ContextElement(requested[i], val);
            }
         }

         if (found < requested.length) {
            ContextElement[] tooBig = result;
            result = new ContextElement[found];
            System.arraycopy(tooBig, 0, result, 0, found);
         }

         return result;
      }
   }

   public interface MBeanPartitionFinder {
      String getPartitionName() throws Exception;

      ObjectName getObjectName();
   }

   private static class JMXMBeanPartitionFinder implements MBeanPartitionFinder {
      private ObjectName oname;

      private JMXMBeanPartitionFinder(ObjectName oname) {
         this.oname = oname;
      }

      public String getPartitionName() throws Exception {
         Class contextUtilClass = Class.forName("weblogic.management.mbeanservers.JMXContextUtil");
         Method method = contextUtilClass.getMethod("getPartitionNameForMBean", ObjectName.class);
         return (String)method.invoke((Object)null, this.oname);
      }

      public ObjectName getObjectName() {
         return this.oname;
      }

      // $FF: synthetic method
      JMXMBeanPartitionFinder(ObjectName x0, Object x1) {
         this(x0);
      }
   }

   private static class IsAccessAllowedPrivilegeAction implements PrivilegedAction {
      private final AuthenticatedSubject subject;
      private final ObjectName name;
      private final MBeanResource.ActionType action;
      private final String target;
      private final String type;
      private final BeanDescriptor beanDescriptor;
      private final MethodDescriptor methodDescriptor;
      private final PropertyDescriptor propertyDescriptor;
      private final boolean isSecureMode;

      IsAccessAllowedPrivilegeAction(AuthenticatedSubject subject_, ObjectName name_, MBeanResource.ActionType action_, String target_, BeanDescriptor beanDesc, MethodDescriptor methodDesc, PropertyDescriptor propertyDesc, boolean secureMode) {
         this.subject = subject_;
         this.name = name_;
         this.action = action_;
         this.target = target_;
         this.type = this.name.getKeyProperty("Type");
         this.beanDescriptor = beanDesc;
         this.methodDescriptor = methodDesc;
         this.propertyDescriptor = propertyDesc;
         this.isSecureMode = secureMode;
      }

      public Object run() {
         Map roles = SecurityHelper.getRoles(this.subject, this.name, (FeatureDescriptor)null, (FeatureDescriptor)null);
         if (this.action == ActionType.FIND && this.isSecureMode && SecurityHelper.isInRoles(roles, SecurityHelper.SECURE_ROLES)) {
            return Boolean.TRUE;
         } else if (SecurityHelper.isInRole(roles, "Admin")) {
            return Boolean.TRUE;
         } else if (this.type == null) {
            return Boolean.TRUE;
         } else {
            Loggable l = ManagementLogger.logNoAccessAllowedSubjectLoggable(this.subject.toString(), this.type, this.action.toString(), this.target);
            if (SecurityHelper.debugACLs) {
               SecurityHelper.dumpAclDebug(this.subject, this.name, this.action, this.target, "");
            }

            BeanDescriptor mbeanDescriptor = this.beanDescriptor;
            if (mbeanDescriptor == null) {
               mbeanDescriptor = SecurityHelper.getBeanDescriptor(this.type);
            }

            PropertyDescriptor propDescriptor;
            if (this.action != ActionType.READ) {
               if (this.action == ActionType.WRITE || this.action == ActionType.EXECUTE || this.action == ActionType.REGISTER || this.action == ActionType.UNREGISTER) {
                  if (this.action == ActionType.WRITE) {
                     propDescriptor = this.propertyDescriptor;
                     if (propDescriptor == null) {
                        propDescriptor = SecurityHelper.getPropertyDescriptor(this.type, this.target);
                     }

                     if (SecurityHelper.getDecision(mbeanDescriptor, propDescriptor, this.name, this.subject, this.action, this.isSecureMode)) {
                        return Boolean.TRUE;
                     }
                  } else if (this.action == ActionType.EXECUTE) {
                     MethodDescriptor mthdDescriptor = this.methodDescriptor;
                     if (mthdDescriptor == null) {
                        mthdDescriptor = SecurityHelper.getMethodDescriptor(this.type, this.target);
                     }

                     if (SecurityHelper.getDecision(mbeanDescriptor, mthdDescriptor, this.name, this.subject, this.action, this.isSecureMode)) {
                        return Boolean.TRUE;
                     }
                  } else if (SecurityHelper.getDecision(mbeanDescriptor, mbeanDescriptor, this.name, this.subject, this.action, this.isSecureMode)) {
                     return Boolean.TRUE;
                  }
               }
            } else {
               propDescriptor = this.propertyDescriptor;
               if (propDescriptor == null) {
                  propDescriptor = SecurityHelper.getPropertyDescriptor(this.type, this.target);
               }

               if (propDescriptor == null) {
                  if (this.isSecureMode && !SecurityHelper.isInRoles(roles, SecurityHelper.SECURE_ROLES)) {
                     throw new NoAccessRuntimeException(l.getMessage());
                  }

                  return Boolean.TRUE;
               }

               Boolean encrypted = (Boolean)propDescriptor.getValue("encrypted");
               Boolean sensitive = (Boolean)propDescriptor.getValue("sensitive");
               if ((encrypted == null || !encrypted) && (sensitive == null || !sensitive)) {
                  if (this.isSecureMode && !SecurityHelper.isInRoles(roles, SecurityHelper.SECURE_ROLES)) {
                     throw new NoAccessRuntimeException(l.getMessage());
                  }

                  return Boolean.TRUE;
               }

               if (SecurityHelper.getDecision(mbeanDescriptor, propDescriptor, this.name, this.subject, this.action, this.isSecureMode)) {
                  return Boolean.TRUE;
               }
            }

            throw new NoAccessRuntimeException(l.getMessage());
         }
      }
   }

   private static enum Owner {
      Domain,
      Partition,
      Context,
      RealmAdministrator;
   }
}
