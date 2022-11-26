package weblogic.connector.security.outbound;

import java.util.Hashtable;
import java.util.Iterator;

public final class PrincipalMap {
   private static String defaultIPName = "*";
   private Hashtable rpMap = new Hashtable();
   private boolean isEmptyMap = true;
   private boolean hasDefaultResourcePrincipal = false;
   private ResourcePrincipal defaultResourcePrincipal = null;

   public PrincipalMap(Hashtable pMap) {
      if (!pMap.isEmpty()) {
         this.isEmptyMap = false;
         Iterator keys = pMap.keySet().iterator();

         while(keys.hasNext()) {
            String ipName = (String)keys.next();
            Hashtable rpHash = (Hashtable)pMap.get(ipName);
            String rpName = (String)rpHash.get("resource-username");
            String rpPass = (String)rpHash.get("resource-password");
            ResourcePrincipal addrp = new ResourcePrincipal(rpName, rpPass);
            this.rpMap.put(ipName, addrp);
         }

         this.defaultResourcePrincipal = (ResourcePrincipal)this.rpMap.get(defaultIPName);
         if (this.defaultResourcePrincipal != null) {
            this.hasDefaultResourcePrincipal = true;
         }
      }

   }

   public ResourcePrincipal getDefaultResourcePrincipal() {
      return this.defaultResourcePrincipal;
   }

   public ResourcePrincipal getResourcePrincipal(String ipName) {
      return this.rpMap.containsKey(ipName) ? (ResourcePrincipal)this.rpMap.get(ipName) : this.defaultResourcePrincipal;
   }

   public boolean isEmptyMap() {
      return this.isEmptyMap;
   }
}
