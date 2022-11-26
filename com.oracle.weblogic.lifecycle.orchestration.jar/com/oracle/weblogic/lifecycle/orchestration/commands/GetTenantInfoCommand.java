package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.config.Service;
import com.oracle.weblogic.lifecycle.config.Tenant;
import com.oracle.weblogic.lifecycle.config.Tenants;
import com.oracle.weblogic.lifecycle.core.LifecycleConfigFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class GetTenantInfoCommand implements CommandInterface {
   private static final long serialVersionUID = 1L;
   private static DebugLogger debugLogger;
   private transient ServiceLocator serviceLocator;
   private transient LifecycleConfigFactory lifecycleConfigFactory;
   private transient LifecycleConfig lifecycleConfig;
   @SharedState
   public MutableString serviceUUID;
   @SharedState
   public MutableString partitionId;
   @SharedState
   public String environmentName;

   public GetTenantInfoCommand() {
      this.initialize();
   }

   public void initialize(WorkflowContext wc) {
      Objects.requireNonNull(this.environmentName);
      Objects.requireNonNull(this.partitionId);
   }

   public final boolean execute() throws Exception {
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug("GetTenantInfoCommand execute()");
      }

      if (this.serviceUUID != null) {
         this.serviceUUID.setValue(this.getServiceUUID());
      } else {
         this.serviceUUID = new MutableString(this.getServiceUUID());
      }

      return true;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      if (in != null) {
         in.defaultReadObject();
      }

      this.initialize();
   }

   private final void initialize() {
      debugLogger = DebugLogger.getDebugLogger("DebugLifecycleManager");
      this.serviceLocator = GlobalServiceLocator.getServiceLocator();
      Objects.requireNonNull(this.serviceLocator);
      this.lifecycleConfigFactory = (LifecycleConfigFactory)this.serviceLocator.getService(LifecycleConfigFactory.class, new Annotation[0]);
      Objects.requireNonNull(this.lifecycleConfigFactory);
      this.lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Objects.requireNonNull(this.lifecycleConfig);
   }

   private final String getServiceUUID() {
      String serviceId = null;
      Tenants tenants = this.lifecycleConfig.getTenants();

      assert tenants != null : "lifecycleConfig.getTenants() returns null";

      List tenantList = tenants.getTenants();
      if (tenantList != null) {
         Iterator var4 = tenantList.iterator();

         while(true) {
            while(true) {
               Tenant tenant;
               List serviceList;
               do {
                  do {
                     if (!var4.hasNext()) {
                        return serviceId;
                     }

                     tenant = (Tenant)var4.next();
                  } while(tenant == null);

                  serviceList = tenant.getServices();
               } while(serviceList == null);

               Iterator var7 = serviceList.iterator();

               while(var7.hasNext()) {
                  Service service = (Service)var7.next();
                  if (service != null && this.environmentName.equals(service.getEnvironmentRef())) {
                     serviceId = service.getId();
                     if (debugLogger != null && debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Service UUID: " + serviceId + ", Service Name: " + service.getName() + " found for Environment: " + this.environmentName);
                        debugLogger.debug("Tenant name: " + tenant.getName() + ", Tenant ID: " + tenant.getId());
                        debugLogger.debug("Partition ID: " + this.partitionId);
                     }
                     break;
                  }
               }
            }
         }
      } else {
         return serviceId;
      }
   }
}
