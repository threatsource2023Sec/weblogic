package weblogic.security.jacc.simpleprovider;

import java.security.SecurityPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.jacc.RoleMapper;
import weblogic.security.jacc.RoleMapperFactory;

public class RoleMapperFactoryImpl extends RoleMapperFactory {
   private static Map rmMap = new HashMap();
   private static Map acMap = new HashMap();
   private static Map caMap = new HashMap();
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCNonPolicy");

   public RoleMapperFactoryImpl() {
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperFactoryImpl noarg constructor");
      }

   }

   public RoleMapper getRoleMapper(String appID, boolean remove) {
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperFactoryImpl.getRoleMapper appID: " + (appID == null ? "null" : appID) + " remove: " + remove);
      }

      RoleMapperImpl rm = null;
      synchronized(rmMap) {
         rm = (RoleMapperImpl)rmMap.get(appID);
         if (rm == null) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("creating a new RoleMapperImpl");
            }

            rm = new RoleMapperImpl(appID, this);
            rmMap.put(appID, rm);
         } else if (remove) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("calling delete on the role mapper");
            }

            rm.clear();
         }

         return rm;
      }
   }

   public RoleMapper getRoleMapper(String appID, String contextID, boolean remove) {
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperFactoryImpl.getRoleMapper appID: " + (appID == null ? "null" : appID) + " contextID: " + (contextID == null ? "null" : contextID) + " remove: " + remove);
      }

      RoleMapperImpl rm = null;
      synchronized(rmMap) {
         rm = (RoleMapperImpl)rmMap.get(appID);
         if (rm == null) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("creating a new RoleMapperImpl");
            }

            rm = new RoleMapperImpl(appID, this);
            rmMap.put(appID, rm);
         } else if (remove) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("calling delete on the role mapper");
            }

            rm.clear();
         }

         if (contextID != null && !contextID.equals("")) {
            caMap.put(contextID, appID);
            ArrayList acL = (ArrayList)acMap.get(appID);
            if (acL == null) {
               acL = new ArrayList(1);
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("creating a new appIDtoContextID map for appID: " + (appID == null ? "null" : appID) + " and adding contextID: " + contextID);
               }

               acL.add(contextID);
               acMap.put(appID, acL);
            } else if (!acL.contains(contextID)) {
               jaccDebugLogger.debug("adding contextID: " + contextID + " to appID: " + (appID == null ? "null" : appID));
               acL.add(contextID);
               acMap.put(appID, acL);
            }
         }

         return rm;
      }
   }

   public RoleMapper getRoleMapperForContextID(String contextID) {
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperFactoryImpl.getRoleMapperForContextID contextID: " + (contextID == null ? "null" : contextID));
      }

      RoleMapperImpl rm = null;
      synchronized(rmMap) {
         String appID = (String)caMap.get(contextID);
         if (appID == null) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("RoleMapperImpl for appID: null does not have a map from ContextID: " + (contextID == null ? "null" : contextID));
            }
         } else {
            rm = (RoleMapperImpl)rmMap.get(appID);
            if (rm == null && jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("RoleMapperImpl for appID: null does not exist");
            }
         }

         return rm;
      }
   }

   public void removeRoleMapper(String appID) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      synchronized(rmMap) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("RoleMapperFactoryImpl.getRoleMapper removing the RoleMapper for appID: " + (appID == null ? "null" : appID));
         }

         rmMap.remove(appID);
         String[] cIDs = getStrArray(((ArrayList)acMap.get(appID)).toArray());
         if (jaccDebugLogger.isDebugEnabled() && cIDs.length > 0) {
            jaccDebugLogger.debug("RoleMapperFactoryImpl.getRoleMapper removing mapping of contextIDs to appID: " + (appID == null ? "null" : appID));
         }

         for(int i = 0; i < cIDs.length; ++i) {
            caMap.remove(cIDs[i]);
         }

         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("RoleMapperFactoryImpl.getRoleMapper removing the mapping from appID: " + (appID == null ? "null" : appID) + " to contextIDs");
         }

         acMap.remove(appID);
      }
   }

   private static String[] getStrArray(Object[] reallyStrings) {
      if (reallyStrings == null) {
         return new String[0];
      } else {
         String[] retVal = new String[reallyStrings.length];

         for(int lcv = 0; lcv < reallyStrings.length; ++lcv) {
            retVal[lcv] = (String)reallyStrings[lcv];
         }

         return retVal;
      }
   }

   protected String getAppId(String contextID) {
      String appID = null;
      synchronized(caMap) {
         appID = (String)caMap.get(contextID);
      }

      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperFactoryImpl.getAppID contextID: " + (contextID == null ? "null" : contextID) + " maps to appID: " + (appID == null ? "null" : appID));
      }

      return appID;
   }
}
