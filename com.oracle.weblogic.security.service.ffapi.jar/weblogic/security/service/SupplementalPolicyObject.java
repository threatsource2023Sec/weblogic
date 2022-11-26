package weblogic.security.service;

import java.io.File;
import java.io.FilePermission;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.CombiningPermissionCollection;
import weblogic.security.internal.ParsePolicies;

public class SupplementalPolicyObject {
   public static final String extraSlash = "/";
   private static Map runTimePathCache = new Hashtable();
   private static Map deployPathCache = new Hashtable();
   private static Map defaultPermissions = new Hashtable();
   public static final String EJB_COMPONENT = "EJB";
   public static final String WEB_COMPONENT = "WEB";
   public static final String CONNECTOR_COMPONENT = "CONNECTOR";
   public static final String EE_EJB_COMPONENT = "EE_EJB";
   public static final String EE_WEB_COMPONENT = "EE_WEB";
   public static final String EE_CONNECTOR_COMPONENT = "EE_CONNECTOR";
   public static final String EE_RESTRICTED_PERMISSION_SET = "EE_RESTRICTED_PERMISSION_SET";
   private static final String EE_APPLICATION_RESTRICTED_PERMISSION_URL = "file:/javaee/application/restricted/permissions";
   public static final String EE_APPLICATION_CLIENT_COMPONENT = "EE_APPLICATION_CLIENT";
   private static Map map = new Hashtable();
   private static final String appRootPrefix = "WEBLOGIC-APPLICATION-ROOT";
   private static final String fileSeparator = System.getProperty("file.separator");
   private static final String appRootString;
   private static final int appRootPrefixLength;
   private static final String JAVA_SEC_POLICY_PROP = "java.security.policy";
   private static final Permission ALL_PERMISSION;
   private static final String RESTRICTED_PERMISSIONS_EXCLUDING_ALL_PERMISSION = "RESTRICTED_PERMISSIONS_EXCLUDING_ALL_PERMISSION";
   private static final boolean IS_DD_GRANT_DISABLED;
   private static final boolean IS_PACKAGED_PERMISSIONS_DISABLED;

   public static void initAppDefaults() {
      try {
         setDefaultPermissions("EJB", "file:/weblogic/application/defaults/EJB");
         setDefaultPermissions("WEB", "file:/weblogic/application/defaults/Web");
         setDefaultPermissions("CONNECTOR", "file:/weblogic/application/defaults/Connector");
         setDefaultPermissions("EE_EJB", "file:/javaee/application/defaults/EJB");
         setDefaultPermissions("EE_WEB", "file:/javaee/application/defaults/Web");
         setDefaultPermissions("EE_CONNECTOR", "file:/javaee/application/defaults/Connector");
         setDefaultPermissions("EE_APPLICATION_CLIENT", "file:/javaee/application/defaults/Client");
         initGrantBlockPermissions("file:/javaee/application/restricted/permissions", "EE_RESTRICTED_PERMISSION_SET");
         copyRestrictedPermissionsIfNecessary();
      } catch (MalformedURLException var1) {
         System.out.println("INTERNAL ERROR: " + var1);
      }

   }

   public static void setDefaultPermissions(AuthenticatedSubject kernelID, String type, String url) throws MalformedURLException {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      setDefaultPermissions(type, url);
   }

   private static void setDefaultPermissions(String type, String url) throws MalformedURLException {
      CodeSource cs = new CodeSource(new URL(url), (Certificate[])null);
      PermissionCollection permissions = Policy.getPolicy().getPermissions(cs);
      defaultPermissions.put(type.toUpperCase(), permissions);
   }

   public static void setPoliciesFromPermissions(AuthenticatedSubject kernelID, File file, PermissionCollection permissions, String defaultType) {
      setPoliciesInternal(kernelID, getDeployTimePathKey(file), permissions, defaultType);
   }

   public static void setPoliciesFromPermissions(AuthenticatedSubject kernelID, File[] files, PermissionCollection permissions, String defaultType) {
      if (files != null && files.length > 0) {
         File[] var4 = files;
         int var5 = files.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File file = var4[var6];
            setPoliciesInternal(kernelID, getDeployTimePathKey(file), permissions, defaultType);
         }
      }

   }

   public static void setPoliciesFromPermissions(AuthenticatedSubject kernelID, String filename, PermissionCollection permissions, String defaultType) {
      setPoliciesInternal(kernelID, getDeployTimePathKey(filename), permissions, defaultType);
   }

   public static void setPoliciesFromPermissions(AuthenticatedSubject kernelID, String[] fileNames, PermissionCollection permissions, String defaultType) {
      if (fileNames != null && fileNames.length > 0) {
         String[] var4 = fileNames;
         int var5 = fileNames.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String file = var4[var6];
            setPoliciesFromPermissions(kernelID, file, permissions, defaultType);
         }
      }

   }

   public static void setPoliciesFromPermissions(AuthenticatedSubject kernelID, URL url, PermissionCollection permissions, String defaultType) {
      setPoliciesInternal(kernelID, getDeployTimePathKey(url), permissions, defaultType);
   }

   public static void setPoliciesFromGrantStatement(AuthenticatedSubject kernelID, File file, String grantStatement, String defaultType) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      setPoliciesFromGrantStatement(kernelID, getDeployTimePathKey(file), grantStatement, defaultType);
   }

   public static void setPoliciesFromGrantStatement(AuthenticatedSubject kernelID, URL url, String grantStatement, String defaultType) {
      setPoliciesFromGrantStatement(kernelID, getDeployTimePathKey(url), grantStatement, defaultType);
   }

   public static void setPoliciesFromGrantStatement(AuthenticatedSubject kernelID, String filename, String grantStatement, String defaultType) {
      setPoliciesFromGrantStatement(kernelID, new String[]{filename}, grantStatement, defaultType);
   }

   public static void setPoliciesFromGrantStatement(AuthenticatedSubject authenticatedsubject, String[] fileNames, String grantStatement, String defaultType) {
      PermissionCollection perms = null;
      if (grantStatement != null && !grantStatement.equals("")) {
         perms = ParsePolicies.parseGrantStatement(grantStatement);
      }

      if (fileNames != null && fileNames.length > 0) {
         for(int index = 0; index < fileNames.length; ++index) {
            setPoliciesInternal(authenticatedsubject, getDeployTimePathKey(fileNames[index]), perms, defaultType);
         }
      }

   }

   public static PermissionCollection getPolicies(File file) {
      return getPoliciesInternal(getRunTimePathKey(file));
   }

   public static PermissionCollection getPolicies(URL url) {
      return url != null && url.getProtocol().equals("file") ? getPoliciesInternal(getRunTimePathKey(url)) : null;
   }

   public static void removePolicies(AuthenticatedSubject kernelID, File file) {
      try {
         removePolicies(kernelID, file.toURL());
      } catch (MalformedURLException var3) {
      }

   }

   public static void removePolicies(AuthenticatedSubject kernelID, URL url) {
      removePolicies(kernelID, url.getPath());
   }

   public static synchronized void removePolicies(AuthenticatedSubject kernelID, String filePath) {
      filePath = filePath + "/";
      removePoliciesInternal(kernelID, getRunTimePathKey(filePath));
      Object deletedObj = deployPathCache.remove(filePath);
      if (deletedObj != null) {
         Iterator iterator = runTimePathCache.keySet().iterator();

         while(iterator.hasNext()) {
            String accessedFilePath = (String)iterator.next();
            if (isPathMatched(accessedFilePath, filePath)) {
               iterator.remove();
            }
         }

      }
   }

   public static void removePolicies(AuthenticatedSubject kernelID, String[] fileNames) {
      if (fileNames != null && fileNames.length > 0) {
         int length = fileNames.length;

         for(int index = 0; index < length; ++index) {
            removePolicies(kernelID, fileNames[index]);
         }
      }

   }

   public static void clearPolicies(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      map.clear();
   }

   public static boolean isAnyOfThePermissionsRestricted(PermissionCollection pc) {
      boolean restricted = false;
      if (pc != null && pc.elements().hasMoreElements()) {
         PermissionCollection restrictedPC = getDefaultPermissions("file:/javaee/application/restricted/permissions", "EE_RESTRICTED_PERMISSION_SET");
         if (restrictedPC != null && restrictedPC.elements().hasMoreElements()) {
            if (restrictedPC.implies(ALL_PERMISSION) && pc.implies(ALL_PERMISSION)) {
               restricted = true;
            } else {
               PermissionCollection restrictedPCSubset = restrictedPC;
               if (defaultPermissions.get("RESTRICTED_PERMISSIONS_EXCLUDING_ALL_PERMISSION") != null) {
                  restrictedPCSubset = (PermissionCollection)defaultPermissions.get("RESTRICTED_PERMISSIONS_EXCLUDING_ALL_PERMISSION");
               }

               Enumeration ep = pc.elements();

               while(ep.hasMoreElements()) {
                  Permission p = (Permission)ep.nextElement();
                  if (restrictedPCSubset.implies(p)) {
                     restricted = true;
                     break;
                  }
               }
            }
         }
      }

      return restricted;
   }

   private static void removePoliciesInternal(AuthenticatedSubject kernelID, String key) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      if (key != null) {
         map.remove(key);
      }
   }

   private static void setPoliciesInternal(AuthenticatedSubject kernelID, String key, PermissionCollection permissions, String defaultType) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      if (key != null) {
         PermissionCollection defaultPerms = getDefaultPermissions(key, defaultType);
         if (defaultPerms == null && permissions == null) {
            return;
         }

         if (defaultPerms == null) {
            map.put(key, permissions);
         } else if (permissions == null) {
            map.put(key, defaultPerms);
         } else {
            map.put(key, new CombiningPermissionCollection(permissions, false, defaultPerms, false));
         }
      }

   }

   private static PermissionCollection getDefaultPermissions(String key, String type) {
      PermissionCollection template = null;
      if (type != null) {
         template = (PermissionCollection)defaultPermissions.get(type.toUpperCase());
      }

      if (template == null) {
         return null;
      } else {
         PermissionCollection defaults = new Permissions();
         Enumeration e = template.elements();

         while(true) {
            while(true) {
               while(true) {
                  while(e.hasMoreElements()) {
                     Permission p = (Permission)e.nextElement();
                     String name = p.getName();
                     if (p instanceof FilePermission) {
                        File appRoot = new File(key);
                        String fullPath = appRoot.getPath();
                        FilePermission fp = (FilePermission)p;
                        if (!name.startsWith(appRootString) && !name.equals("WEBLOGIC-APPLICATION-ROOT")) {
                           if (type != null && type.startsWith("EE_") && name != null && name.trim().equals("*")) {
                              if (appRoot.isDirectory()) {
                                 fullPath = fullPath + File.separator + "-";
                              }

                              defaults.add(new FilePermission(fullPath, fp.getActions()));
                           } else {
                              defaults.add(p);
                           }
                        } else {
                           String suffix = name.substring(appRootPrefixLength);
                           if (appRoot.isDirectory()) {
                              fullPath = fullPath + suffix;
                           }

                           defaults.add(new FilePermission(fullPath, fp.getActions()));
                        }
                     } else {
                        defaults.add(p);
                     }
                  }

                  defaults.setReadOnly();
                  return defaults;
               }
            }
         }
      }
   }

   private static PermissionCollection getPoliciesInternal(String key) {
      if (key == null) {
         return null;
      } else {
         PermissionCollection permissions = (PermissionCollection)map.get(key);
         return permissions;
      }
   }

   private static String getRunTimePathKey(File file) {
      String key = null;

      try {
         key = getRunTimePathKey(file.toURL());
      } catch (MalformedURLException var3) {
      }

      return key;
   }

   private static String getRunTimePathKey(URL url) {
      String key = getRunTimePathKey(url.getPath());
      return key;
   }

   private static synchronized String getRunTimePathKey(String absolutePath) {
      absolutePath = absolutePath + "/";
      String key = null;
      key = (String)deployPathCache.get(absolutePath);
      if (key != null) {
         return key;
      } else {
         key = (String)runTimePathCache.get(absolutePath);
         if (key != null) {
            return key;
         } else {
            Iterator iterator = deployPathCache.keySet().iterator();

            while(iterator.hasNext()) {
               String filePath = (String)iterator.next();
               if (isPathMatched(absolutePath, filePath)) {
                  key = (String)deployPathCache.get(filePath);
                  break;
               }
            }

            if (key != null) {
               runTimePathCache.put(absolutePath, key);
            }

            return key;
         }
      }
   }

   private static String getDeployTimePathKey(File file) {
      String key = null;

      try {
         key = getDeployTimePathKey(file.toURL());
      } catch (MalformedURLException var3) {
      }

      return key;
   }

   private static String getDeployTimePathKey(URL url) {
      String key = getDeployTimePathKey(url.getPath());
      return key;
   }

   private static synchronized String getDeployTimePathKey(String filePath) {
      String nonNullKey = filePath;
      filePath = filePath + "/";
      String key = (String)deployPathCache.get(filePath);
      if (key == null) {
         key = nonNullKey;
      }

      if (filePath != null && key != null) {
         deployPathCache.put(filePath, key);
      }

      return key;
   }

   private static boolean isPathMatched(String parentString, String toBeMatched) {
      if (parentString.length() < toBeMatched.length()) {
         return false;
      } else {
         if (File.separator.equals("\\")) {
            if (parentString.indexOf(92) >= 0) {
               parentString = parentString.replace('\\', '/');
            }

            if (toBeMatched.indexOf(92) >= 0) {
               toBeMatched = toBeMatched.replace('\\', '/');
            }
         }

         return parentString.startsWith(toBeMatched) || parentString.regionMatches(1, toBeMatched, 0, toBeMatched.length());
      }
   }

   private static void outln(String s) {
      System.out.println(s);
   }

   private static PermissionCollection parsePermissionsFromGrant(String codeBase) {
      PermissionCollection pc = null;
      String policyFile = System.getProperty("java.security.policy");
      if (policyFile != null) {
         try {
            pc = ParsePolicies.parseGrantPermissionsFromPolicyFile(policyFile.startsWith("=") && policyFile.length() > 1 ? policyFile.substring(1) : policyFile, codeBase);
         } catch (SecurityServiceException var4) {
            throw new InvalidParameterException(var4);
         }
      }

      return pc;
   }

   private static void initGrantBlockPermissions(String grantCodeBase, String componentAsKey) {
      if (System.getSecurityManager() != null) {
         PermissionCollection pc = parsePermissionsFromGrant(grantCodeBase);
         if (pc != null) {
            defaultPermissions.put(componentAsKey, pc);
         }
      }

   }

   private static void copyRestrictedPermissionsIfNecessary() {
      PermissionCollection pc = getDefaultPermissions("file:/javaee/application/restricted/permissions", "EE_RESTRICTED_PERMISSION_SET");
      if (pc != null && pc.implies(ALL_PERMISSION)) {
         PermissionCollection subsetPC = new Permissions();
         Enumeration e = pc.elements();

         while(e.hasMoreElements()) {
            Permission p = (Permission)e.nextElement();
            if (!(p instanceof AllPermission)) {
               subsetPC.add(p);
            }
         }

         defaultPermissions.put("RESTRICTED_PERMISSIONS_EXCLUDING_ALL_PERMISSION", subsetPC);
      }

   }

   public static boolean registerSEPermissions(AuthenticatedSubject kernelID, String[] deployCodeBases, PermissionCollection permissions, String ddGrant, String deploymentDescriptor, String grantComponentType, String permissionsComponentType) throws SecurityServiceException {
      return registerSEPermissions(kernelID, deployCodeBases, permissions, ddGrant != null && ddGrant.trim().length() > 0 ? new String[]{ddGrant} : null, deploymentDescriptor, grantComponentType, permissionsComponentType);
   }

   public static boolean registerSEPermissions(AuthenticatedSubject kernelID, String[] deployCodeBases, PermissionCollection permissions, String[] multipleDDGrants, String deploymentDescriptor, String grantComponentType, String permissionsComponentType) throws SecurityServiceException {
      boolean permissionsRegistered = false;
      if (System.getSecurityManager() != null) {
         PermissionCollection permissionsFromGrants = ParsePolicies.parseMultipleGrantStatements(multipleDDGrants);
         if (!IS_PACKAGED_PERMISSIONS_DISABLED && !IS_DD_GRANT_DISABLED && permissionsFromGrants != null && permissionsFromGrants.elements().hasMoreElements() && permissions != null && permissions.elements().hasMoreElements()) {
            throw new SecurityServiceException(SecurityLogger.getConflictingPermissionsDeclarationError());
         }

         if (!IS_PACKAGED_PERMISSIONS_DISABLED) {
            PermissionCollection perms = null;
            if (permissions != null || IS_DD_GRANT_DISABLED || multipleDDGrants == null) {
               if (isAnyOfThePermissionsRestricted(permissions)) {
                  throw new SecurityServiceException(SecurityLogger.getProhibitedPermissionsError());
               }

               setPoliciesFromPermissions(kernelID, deployCodeBases, permissions, permissionsComponentType);
               permissionsRegistered = true;
            }
         } else if (permissions != null && IS_DD_GRANT_DISABLED) {
            throw new SecurityServiceException(SecurityLogger.getPackagedPermissionsDisabledError());
         }

         if (!permissionsRegistered) {
            if (IS_DD_GRANT_DISABLED && permissionsFromGrants != null && permissionsFromGrants.elements().hasMoreElements()) {
               throw new SecurityServiceException(SecurityLogger.getDeploymentDescriptorGrantDisabledError(deploymentDescriptor));
            }

            setPoliciesFromPermissions(kernelID, deployCodeBases, permissionsFromGrants, grantComponentType);
            permissionsRegistered = true;
         }
      }

      return permissionsRegistered;
   }

   static {
      appRootString = "WEBLOGIC-APPLICATION-ROOT" + fileSeparator;
      appRootPrefixLength = "WEBLOGIC-APPLICATION-ROOT".length();
      ALL_PERMISSION = new AllPermission();
      IS_DD_GRANT_DISABLED = Boolean.getBoolean("weblogic.security.dd.permissionSpecDisabled");
      IS_PACKAGED_PERMISSIONS_DISABLED = Boolean.getBoolean("weblogic.security.dd.javaEESecurityPermissionsDisabled");
   }
}
