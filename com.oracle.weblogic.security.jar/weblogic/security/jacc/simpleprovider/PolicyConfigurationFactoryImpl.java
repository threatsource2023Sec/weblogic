package weblogic.security.jacc.simpleprovider;

import java.security.SecurityPermission;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.security.jacc.PolicyConfiguration;
import javax.security.jacc.PolicyConfigurationFactory;
import javax.security.jacc.PolicyContextException;
import weblogic.diagnostics.debug.DebugLogger;

public class PolicyConfigurationFactoryImpl extends PolicyConfigurationFactory {
   private static Map polConfMap = new HashMap();
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCNonPolicy");

   public PolicyConfigurationFactoryImpl() {
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyConfigurationFactoryImpl noarg constructor");
      }

   }

   public PolicyConfiguration getPolicyConfiguration(String contextID, boolean remove) throws PolicyContextException {
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyConfigurationFactoryImpl.getPolicyConfiguration contextID: " + (contextID == null ? "null" : contextID) + " remove: " + remove);
      }

      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      PolicyConfiguration pc = null;
      synchronized(polConfMap) {
         pc = (PolicyConfiguration)polConfMap.get(contextID);
         if (pc == null) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("creating a new PolicyConfigurationImpl");
            }

            pc = new PolicyConfigurationImpl(contextID, this);
            polConfMap.put(contextID, pc);
         } else if (remove) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("calling delete on the policy configuration");
            }

            ((PolicyConfiguration)pc).delete();
         } else {
            ((PolicyConfigurationImpl)pc).setStateOpen();
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("set the state to open on the policy configuration");
            }
         }

         return (PolicyConfiguration)pc;
      }
   }

   protected static PolicyConfigurationImpl getPolicyConfigurationImpl(String contextID) {
      return (PolicyConfigurationImpl)polConfMap.get(contextID);
   }

   protected static Collection getPolicyConfigurationImpls() {
      return polConfMap.values();
   }

   public boolean inService(String contextID) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      PolicyConfiguration pc = (PolicyConfiguration)polConfMap.get(contextID);
      if (pc == null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("inService didn't find the pc");
         }

         return false;
      } else {
         return pc.inService();
      }
   }

   protected static void removePolicyConfiguration(String contextID) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      synchronized(polConfMap) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("removing the PolicyConfiguration for contextID: " + (contextID == null ? "null" : contextID));
         }

         polConfMap.remove(contextID);
      }
   }
}
