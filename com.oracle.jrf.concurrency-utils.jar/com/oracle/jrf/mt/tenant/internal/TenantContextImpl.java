package com.oracle.jrf.mt.tenant.internal;

import com.oracle.jrf.mt.tenant.PDBInfo;
import com.oracle.jrf.mt.tenant.Tenant;
import com.oracle.jrf.mt.tenant.runtime.TenantContext;
import com.oracle.weblogic.lifecycle.Environment;
import java.util.UUID;

public class TenantContextImpl implements TenantContext {
   private Tenant tenant;
   private String serviceName;
   private String serviceType;
   private UUID serviceUUID;
   private PDBInfo pdbInfo;
   private String topLevelDir;
   private Environment environment;
   private String identityDomain;
   private String twoTask;
   private static final TenantContext GLOBAL_CONTEXT = new TenantContextImpl("GLOBAL", "0");
   private static final TenantContextImpl GLOBAL_CONTEXT_UUID;

   public TenantContextImpl(String tenantName, String tenantId) {
      this.tenant = new Tenant(tenantName, tenantId);
   }

   public TenantContextImpl(String tenantName, UUID tenantUUID) {
      this.tenant = new Tenant(tenantName, tenantUUID);
   }

   public static final TenantContext getGlobalContext() {
      return GLOBAL_CONTEXT;
   }

   public static final TenantContext getGlobalContextUUID() {
      TenantContextImpl tc = GLOBAL_CONTEXT_UUID;
      tc.setServiceUUID(TenantContext.GLOBAL_UUID);
      tc.setServiceName("GLOBAL");
      return tc;
   }

   public Tenant getCurrentTenant() {
      return this.tenant;
   }

   public Tenant getTenant() {
      return this.tenant;
   }

   public String getServiceName() {
      return this.serviceName;
   }

   public String getServiceType() {
      return this.serviceType;
   }

   public UUID getServiceUUID() {
      return this.serviceUUID;
   }

   public boolean isGlobal() {
      return true;
   }

   public PDBInfo getPDB() {
      return this.pdbInfo;
   }

   public String getTopLevelDir() {
      return this.topLevelDir;
   }

   public Environment getEnvironment() {
      return this.environment;
   }

   public String getIdentityDomain() {
      return this.identityDomain;
   }

   public String getTwoTask() {
      return this.twoTask;
   }

   public void setTenant(Tenant tenant) {
      this.tenant = tenant;
   }

   public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
   }

   public void setServiceType(String serviceType) {
      this.serviceType = serviceType;
   }

   public void setServiceUUID(UUID serviceUUID) {
      this.serviceUUID = serviceUUID;
   }

   public void setPDB(PDBInfo pdbInfo) {
      this.pdbInfo = pdbInfo;
   }

   public void setTopLevelDir(String topLevelDir) {
      this.topLevelDir = topLevelDir;
   }

   public void setEnvironment(Environment environment) {
      this.environment = environment;
   }

   public void setIdentityDomain(String identityDomain) {
      this.identityDomain = identityDomain;
   }

   public void setTwoTask(String twoTask) {
      this.twoTask = twoTask;
   }

   public String toString() {
      String separator = ", ";
      String serviceName = this.getServiceName();
      UUID serviceUUID = this.getServiceUUID();
      Environment environment = this.getEnvironment();
      Tenant tenant = this.getTenant();
      String topLevelDir = this.getTopLevelDir();
      PDBInfo pdbInfo = this.getPDB();
      StringBuilder tcString = new StringBuilder();
      tcString.append("Service Name=");
      tcString.append(serviceName);
      tcString.append(", ");
      tcString.append(" Service UUID=");
      tcString.append(serviceUUID);
      tcString.append(", ");
      tcString.append(" Top Level Dir=");
      tcString.append(topLevelDir);
      tcString.append("\nTC:Tenant\n");
      if (tenant != null) {
         tcString.append(tenant.toString());
      }

      tcString.append("\nEnvironment\n");
      if (environment != null) {
         tcString.append(" Environment Name  =");
         tcString.append(environment.getName());
      }

      tcString.append("\nPDB\n");
      if (pdbInfo != null) {
         tcString.append(" PDB Id =");
         tcString.append(pdbInfo.getPDBId());
         tcString.append(", ");
         tcString.append(" PDB Name =");
         tcString.append(pdbInfo.getPDBName());
      }

      return tcString.toString();
   }

   static {
      GLOBAL_CONTEXT_UUID = new TenantContextImpl("GLOBAL", TenantContext.GLOBAL_UUID);
   }
}
