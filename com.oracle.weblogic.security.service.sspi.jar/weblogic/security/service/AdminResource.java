package weblogic.security.service;

import weblogic.security.spi.Resource;

public final class AdminResource extends ResourceBase {
   private static final String[] KEYS = new String[]{"category", "realm", "action"};

   public AdminResource(String category, String realm, String action) {
      this.init(new String[]{category, realm, action}, 0L);
   }

   private AdminResource(String[] values, int length) {
      this.init(values, length, 0L);
   }

   public String getType() {
      return "<adm>";
   }

   public String[] getKeys() {
      return KEYS;
   }

   protected Resource makeParent() {
      return this.length > 0 ? new AdminResource(this.values, this.length - 1) : null;
   }

   public String getResourceType() {
      return this.length > 0 ? this.values[0] : null;
   }

   public String getActionName() {
      return this.length > 2 ? this.values[2] : null;
   }

   public String getResourceName() {
      return this.length > 1 ? this.values[1] : null;
   }
}
