package weblogic.security.jacc.simpleprovider;

import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;

class GrantGenerator {
   private static GrantGenerator SINGLETON = null;
   private static final String defaultPrincipalClassName;
   private static final String PRINCIPALCLASSNAMEPROP = "weblogic.jaccprovider.principalclass";
   private static String DEFAULTPRINCIPALCLASSNAME = "weblogic.security.jacc.simpleprovider.WLSJACCPrincipalImpl";
   private static String HEADER = "//Automatically generated\n//Do not edit!\n\n";
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCNonPolicy");
   private static String principalClassName = null;

   private GrantGenerator() {
   }

   protected static String generateHeader() {
      return HEADER;
   }

   protected static String generateUncheckedGrants(String codeBase, Permissions uncheckedPermissions) {
      if (uncheckedPermissions != null && uncheckedPermissions.elements().hasMoreElements()) {
         StringBuffer sb = new StringBuffer("grant {\n\t//granted and unchecked resources - full access allowed\n");
         Enumeration e = uncheckedPermissions.elements();

         while(e.hasMoreElements()) {
            Permission p = (Permission)e.nextElement();
            sb.append(permLine(p));
         }

         sb.append("};\n\n");
         return sb.toString();
      } else {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("GrantGenerator:generateUncheckedGrants uncheckedPermissions is null or empty. Returning an empty String.");
         }

         return "";
      }
   }

   protected static String generateExcludedGrants(String codeBase, Permissions excludedPermissions) {
      if (excludedPermissions != null && excludedPermissions.elements().hasMoreElements()) {
         StringBuffer sb = new StringBuffer("grant {\n\t//excluded resources\n");
         Enumeration e = excludedPermissions.elements();

         while(e.hasMoreElements()) {
            Permission p = (Permission)e.nextElement();
            sb.append(permLine(p));
         }

         sb.append("};\n\n");
         return sb.toString();
      } else {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("GrantGenerator:generateExcludedGrants excludedPermissions is null or empty. Returning an empty String.");
         }

         return "";
      }
   }

   private static String permLine(Permission perm) {
      StringBuffer sb = new StringBuffer("\tpermission " + perm.getClass().getName());
      String permName = perm.getName();
      if (permName != null) {
         sb.append(" \"" + permName + "\"");
      }

      String permActions = perm.getActions();
      if (permActions != null) {
         sb.append(" , \"" + permActions + "\"");
      }

      sb.append(";\n");
      return sb.toString();
   }

   protected static String generateRoleGrants(String codeBase, Map appRolesToPrincipalNames, Map roleToPermissions) {
      if (codeBase != null && appRolesToPrincipalNames != null && !appRolesToPrincipalNames.isEmpty() && roleToPermissions != null && !roleToPermissions.isEmpty()) {
         HashMap userToRoleMap = (HashMap)getUserToRoleMap(appRolesToPrincipalNames);
         if (userToRoleMap.isEmpty()) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("GrantGenerator:generateRoleGrants userToRoleMap is null or empty. Returning an empty String.");
            }

            return "";
         } else {
            Set userSet = userToRoleMap.keySet();
            Iterator userNameIterator = userSet.iterator();
            StringBuffer sb = new StringBuffer();

            while(true) {
               while(userNameIterator.hasNext()) {
                  String userName = (String)userNameIterator.next();
                  if (userName == null) {
                     if (jaccDebugLogger.isDebugEnabled()) {
                        jaccDebugLogger.debug("GrantGenerator:generateRoleGrants userName in userToRoleMap is null or empty. Ignoring and continuing.");
                     }
                  } else {
                     if ("*".equals(userName)) {
                        sb.append("grant principal * * {\n");
                     } else {
                        sb.append("grant principal " + principalClassName + " \"" + userName + "\" {\n");
                     }

                     ArrayList roleNameList = (ArrayList)userToRoleMap.get(userName);
                     if (roleNameList != null && !roleNameList.isEmpty()) {
                        for(int i = 0; i < roleNameList.size(); ++i) {
                           String roleName = (String)roleNameList.get(i);
                           if (roleName == null) {
                              if (jaccDebugLogger.isDebugEnabled()) {
                                 jaccDebugLogger.debug("GrantGenerator:generateRoleGrants roleName in roleNameList(" + i + ") is null. Ignoring and continuing.");
                              }
                           } else {
                              Set permSet = (HashSet)roleToPermissions.get(roleName);
                              if (permSet == null) {
                                 if (jaccDebugLogger.isDebugEnabled()) {
                                    jaccDebugLogger.debug("GrantGenerator:generateRoleGrants permSet for roleName " + roleName + " in roleToPermissions is null. Ignoring and continuing.");
                                 }
                              } else {
                                 Iterator permIterator = permSet.iterator();

                                 while(permIterator.hasNext()) {
                                    Permission perm = (Permission)permIterator.next();
                                    if (perm == null) {
                                       if (jaccDebugLogger.isDebugEnabled()) {
                                          jaccDebugLogger.debug("GrantGenerator:generateRoleGrants perm in permSet is null. Ignoring and continuing.");
                                       }
                                    } else {
                                       sb.append("\t// Mapping this permission for Role: " + roleName + "\n");
                                       sb.append(permLine(perm) + "\n");
                                    }
                                 }
                              }
                           }
                        }

                        sb.append("};\n\n");
                     } else if (jaccDebugLogger.isDebugEnabled()) {
                        jaccDebugLogger.debug("GrantGenerator:generateRoleGrants the RoleNameList for userName " + userName + " in userToRoleMap is null or empty. Ignoring and continuing.");
                     }
                  }
               }

               return sb.toString();
            }
         }
      } else {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("GrantGenerator:generateRoleGrants appRolesToPrincipalNames and/or roleToPermissions is null or empty. Returning an empty String.");
         }

         return "";
      }
   }

   private static Map getUserToRoleMap(Map appRolesToPrincipalNames) {
      HashMap userToRoleMap = new HashMap();
      boolean isAnyAuthRoleNotMapped = false;
      Set roleSet = appRolesToPrincipalNames.keySet();
      Iterator roleIterator = roleSet.iterator();

      while(true) {
         while(roleIterator.hasNext()) {
            String roleName = (String)roleIterator.next();
            if (roleName == null) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("GrantGenerator:getUserToRoleMap roleName in appRolesToPrincipalNames is null. Ignoring and continuing.");
               }
            } else {
               String[] userGroupNames = (String[])((String[])appRolesToPrincipalNames.get(roleName));
               if (userGroupNames != null && userGroupNames.length != 0) {
                  for(int i = 0; i < userGroupNames.length; ++i) {
                     String userName = userGroupNames[i];
                     if (userName == null) {
                        if (jaccDebugLogger.isDebugEnabled()) {
                           jaccDebugLogger.debug("GrantGenerator:getUserToRoleMap userName in userGroupNames[" + i + "] is null. Ignoring and continuing.");
                        }
                     } else {
                        ArrayList roleArray = (ArrayList)userToRoleMap.get(userName);
                        if (roleArray == null) {
                           roleArray = new ArrayList(1);
                        }

                        roleArray.add(roleName);
                        if (jaccDebugLogger.isDebugEnabled()) {
                           jaccDebugLogger.debug("GrantGenerator:getUserToRoleMap for user " + userName + " added role " + roleName);
                        }

                        userToRoleMap.put(userName, roleArray);
                     }
                  }
               } else {
                  if ("**".equals(roleName)) {
                     isAnyAuthRoleNotMapped = true;
                  }

                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("GrantGenerator:getUserToRoleMap userGroupNames for roleName " + roleName + "in appRolesToPrincipalNames is null or empty. Ignoring and continuing.");
                  }
               }
            }
         }

         if (!roleSet.contains("**") || isAnyAuthRoleNotMapped) {
            ArrayList anyAuthUserRoleList = new ArrayList(1);
            anyAuthUserRoleList.add("**");
            userToRoleMap.put("*", anyAuthUserRoleList);
         }

         return userToRoleMap;
      }
   }

   static {
      defaultPrincipalClassName = DEFAULTPRINCIPALCLASSNAME;
      principalClassName = System.getProperty("weblogic.jaccprovider.principalclass", defaultPrincipalClassName);
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("GrantGenerator:PrincipalClassName: " + principalClassName);
      }

   }
}
