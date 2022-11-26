package weblogic.diagnostics.snmp.agent.monfox;

import weblogic.security.service.ResourceBase;
import weblogic.security.spi.Resource;

public final class SnmpResource extends ResourceBase {
   private static final String[] KEYS = new String[]{"category"};
   private static final SnmpResource SNMP_AUTH_RESOURCE = new SnmpResource("AUTH");
   private static final SnmpResource SNMP_PRIV_RESOURCE = new SnmpResource("PRIV");

   private SnmpResource(String category) {
      this.init(new String[]{category}, 0L);
   }

   public static SnmpResource getAuthenticationResource() {
      return SNMP_AUTH_RESOURCE;
   }

   public static SnmpResource getPrivacyResource() {
      return SNMP_PRIV_RESOURCE;
   }

   public String getType() {
      return "<snmp>";
   }

   public String[] getKeys() {
      return KEYS;
   }

   protected Resource makeParent() {
      return null;
   }
}
