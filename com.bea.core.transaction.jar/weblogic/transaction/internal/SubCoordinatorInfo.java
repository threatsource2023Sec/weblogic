package weblogic.transaction.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class SubCoordinatorInfo {
   private String coordinatorURL;
   private String coordinatorNonSecureURL;
   private boolean sslOnly;
   private String[] registeredXAResources;
   private String[] registeredNonXAResources;
   private static final boolean m_isDebugCoURL = Boolean.getBoolean("weblogic.transaction.internal.debugcourl");

   static boolean isDebugCoURL() {
      return m_isDebugCoURL;
   }

   SubCoordinatorInfo(Map map) {
      this.coordinatorURL = (String)map.get("CoordinatorURL");
      this.coordinatorNonSecureURL = (String)map.get("CoordinatorNonSSLURL");
      Boolean ssl = (Boolean)map.get("SSLOnly");
      if (ssl != null) {
         this.sslOnly = ssl;
      }

      this.registeredXAResources = (String[])((String[])map.get("XAResources"));
      this.registeredNonXAResources = (String[])((String[])map.get("NonXAResources"));
   }

   public String[] getRegisteredNonXAResources() {
      return this.registeredNonXAResources;
   }

   public String[] getRegisteredXAResources() {
      return this.registeredXAResources;
   }

   public String getCoordinatorURL() {
      return this.coordinatorURL;
   }

   public String getCoordinatorNonSecureURL() {
      return this.coordinatorNonSecureURL;
   }

   public boolean isSslOnly() {
      return this.sslOnly;
   }

   public String toString() {
      return "coordinatorURL=" + this.coordinatorURL + " coordinatorNonSecureURL=" + this.coordinatorNonSecureURL + ",sslOnly=" + this.sslOnly + ",registeredXAResources=" + Arrays.asList(this.registeredXAResources) + ",registeredNonXAResource=" + Arrays.asList(this.registeredNonXAResources);
   }

   public static Map createMap(ServerCoordinatorDescriptor descriptor) {
      if (isDebugCoURL()) {
         (new Throwable("SubCoordinatorInfo.createMap coURL:" + descriptor.getCoordinatorURL() + descriptor.getNonSSLCoordinatorURL())).printStackTrace();
      }

      Map m = new HashMap();
      m.put("CoordinatorURL", descriptor.getCoordinatorURL());
      m.put("CoordinatorNonSSLURL", descriptor.getNonSSLCoordinatorURL());
      m.put("SSLOnly", descriptor.isSSLOnly());
      m.put("XAResources", descriptor.getRegisteredXAResourceNames());
      m.put("NonXAResources", descriptor.getRegisteredNonXAResourceNames());
      return m;
   }
}
