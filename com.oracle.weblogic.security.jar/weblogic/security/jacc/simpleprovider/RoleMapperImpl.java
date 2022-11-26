package weblogic.security.jacc.simpleprovider;

import java.security.SecurityPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.SecurityLogger;
import weblogic.security.jacc.RoleMapper;

public class RoleMapperImpl implements RoleMapper {
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCNonPolicy");
   private RoleMapperFactoryImpl rmFactoryImpl;
   private String contextId;
   private String contextIdNoNPE;
   private final Object lock = new Object();
   private Map rolesToPrincipalNames;
   private boolean policyChanged = false;

   public RoleMapperImpl(String contextID, RoleMapperFactoryImpl rmFactoryImpl) {
      this.rmFactoryImpl = rmFactoryImpl;
      this.contextId = contextID;
      this.rolesToPrincipalNames = new HashMap();
      this.contextIdNoNPE = this.contextId == null ? "null" : this.contextId;
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperImpl constructor contextId: " + this.contextIdNoNPE);
      }

   }

   public void addAppRolesToPrincipalMap(Map appRolesToPrincipalNames) throws IllegalArgumentException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (appRolesToPrincipalNames != null && !appRolesToPrincipalNames.isEmpty()) {
         try {
            synchronized(this.lock) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("RoleMapperImpl: addAppRolesToPrincipalMap contextId: " + this.contextIdNoNPE + " map has " + appRolesToPrincipalNames.size() + " roles in it.");
               }

               if (this.rolesToPrincipalNames != null && !this.rolesToPrincipalNames.isEmpty()) {
                  this.rolesToPrincipalNames = mergeMap(this.rolesToPrincipalNames, appRolesToPrincipalNames);
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("RoleMapperImpl: addAppRolesToPrincipalMap contextId: " + this.contextIdNoNPE + " map merged with existing role to pricipal names map.");
                  }
               } else {
                  this.rolesToPrincipalNames = new HashMap(appRolesToPrincipalNames);
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("RoleMapperImpl: addAppRolesToPrincipalMap contextId: " + this.contextIdNoNPE + " created new map.");
                  }
               }

               this.policyChanged = true;
            }

            Set roleNames = this.rolesToPrincipalNames.keySet();
            Iterator roleNameIterator = roleNames.iterator();

            while(true) {
               while(roleNameIterator.hasNext()) {
                  String roleName = (String)roleNameIterator.next();
                  String roleNameNoNPE = roleName == null ? "null" : roleName;
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("RoleMapperImpl: addAppRolesToPrincipalMap roleName: " + roleNameNoNPE);
                  }

                  String[] userGroupNames = (String[])((String[])this.rolesToPrincipalNames.get(roleName));
                  if (userGroupNames != null && userGroupNames.length != 0) {
                     for(int i = 0; i < userGroupNames.length; ++i) {
                        if (jaccDebugLogger.isDebugEnabled()) {
                           jaccDebugLogger.debug("RoleMapperImpl: addAppRolesToPrincipalMap contextId: " + this.contextIdNoNPE + " roleName: " + roleName + " user/group name: " + (userGroupNames[i] != null ? userGroupNames[i] : "null"));
                        }
                     }
                  } else if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("RoleMapperImpl: addAppRolesToPrincipalMap contextId: " + this.contextIdNoNPE + " roleName: " + roleName + " no user or group names");
                  }
               }

               return;
            }
         } catch (Exception var10) {
            throw new IllegalArgumentException(SecurityLogger.getBadRoleToPrincipalMap(var10, this.contextId));
         }
      } else {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("RoleMapperImpl: addAppRolesToPrincipalMap contextId: " + this.contextIdNoNPE + " map is null or empty.");
         }

      }
   }

   public Map getRolesToPrincipalNames() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      synchronized(this.lock) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("RoleMapperImpl: getRolesToPrincipalNames contextId: " + this.contextIdNoNPE + " map has " + this.rolesToPrincipalNames.size() + " roles in it.");
         }

         return new HashMap(this.rolesToPrincipalNames);
      }
   }

   void clear() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperImpl: clear contextId: " + this.contextIdNoNPE);
      }

      synchronized(this.lock) {
         this.rolesToPrincipalNames = new HashMap();
      }
   }

   private static HashMap mergeMap(Map firstMap, Map secondMap) {
      Set secondKeys = secondMap.keySet();
      Iterator sI = secondKeys.iterator();

      while(sI.hasNext()) {
         String sKey = (String)sI.next();
         if (!firstMap.containsKey(sKey)) {
            firstMap.put(sKey, secondMap.get(sKey));
         } else {
            String[] fValue = (String[])((String[])firstMap.get(sKey));
            String[] sValue = (String[])((String[])secondMap.get(sKey));
            String[] nValue = mergeArray(sValue, fValue);
            firstMap.put(sKey, nValue);
         }
      }

      return new HashMap(firstMap);
   }

   private static String[] mergeArray(String[] firstArray, String[] secondArray) {
      int first = 0;
      int second = 0;
      ArrayList newArrayList = new ArrayList(firstArray.length + secondArray.length);

      int i;
      for(i = 0; i < firstArray.length; ++i) {
         if (firstArray[i] != null) {
            ++first;
            newArrayList.add(firstArray[i]);
         }
      }

      for(i = 0; i < secondArray.length; ++i) {
         if (secondArray[i] != null) {
            ++second;
            newArrayList.add(secondArray[i]);
         }
      }

      newArrayList.trimToSize();
      Set newSet = new TreeSet(newArrayList);
      String[] newArray = (String[])((String[])newSet.toArray(new String[newSet.size()]));
      return newArray;
   }
}
