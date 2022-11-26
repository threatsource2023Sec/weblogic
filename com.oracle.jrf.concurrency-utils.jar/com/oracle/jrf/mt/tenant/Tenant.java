package com.oracle.jrf.mt.tenant;

import com.oracle.jrf.mt.tenant.runtime.TenantContext;
import java.util.UUID;

public class Tenant {
   private final String tenantName;
   private final String tenantId;
   private final UUID tenantUUID;

   /** @deprecated */
   @Deprecated
   public Tenant(String tenantName, String tenantId) {
      this.tenantName = tenantName;
      this.tenantId = tenantId;
      this.tenantUUID = null;
   }

   public Tenant(String tenantName, UUID tenantUUID) {
      this.tenantName = tenantName;
      this.tenantUUID = tenantUUID;
      if (tenantUUID.equals(TenantContext.GLOBAL_UUID)) {
         this.tenantId = "0";
      } else {
         this.tenantId = null;
      }

   }

   public String getTenantName() {
      return this.tenantName;
   }

   /** @deprecated */
   @Deprecated
   public String getTenantId() {
      return this.tenantId;
   }

   public UUID getTenantUUID() {
      return this.tenantUUID;
   }

   public String toString() {
      String id = "";
      if (this.tenantUUID != null) {
         id = this.tenantUUID.toString();
      } else if (this.tenantId != null) {
         id = this.tenantId;
      }

      return "Tenant Name: " + this.tenantName + ", Tenant ID: " + id;
   }
}
