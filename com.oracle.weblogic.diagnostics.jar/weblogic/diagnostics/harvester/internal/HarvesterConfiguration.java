package weblogic.diagnostics.harvester.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFHarvestedTypeBean;
import weblogic.diagnostics.descriptor.WLDFHarvesterBean;
import weblogic.diagnostics.i18n.DiagnosticsHarvesterLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;

class HarvesterConfiguration implements HarvesterInternalConstants {
   private static final DebugLogger debugLogger = DebugSupport.getDebugLogger();
   private HashMap configuredTypesMap = new HashMap();
   private int enabledTypeCount = -1;
   private String configurationName;
   private long configuredSamplePeriodMillis;
   private boolean enabled;

   public String getName() {
      return this.configurationName;
   }

   HarvesterConfiguration(WLDFHarvesterBean bean) {
      this.configurationName = bean.getName();
      this.configuredSamplePeriodMillis = bean.getSamplePeriod();
      this.enabled = bean.isEnabled();
      this.processHarvestedTypeDefinitions(bean);
   }

   long getSamplePeriodMillis() {
      return this.configuredSamplePeriodMillis;
   }

   MetricSpecification[] getEnabledMetricSpecifications() {
      MetricSpecification[] specArray = null;
      if (this.configuredTypesMap != null && this.configuredTypesMap.size() > 0) {
         ArrayList enabledTypes = new ArrayList(this.configuredTypesMap.size());
         Iterator var3 = this.configuredTypesMap.values().iterator();

         while(var3.hasNext()) {
            MetricSpecification spec = (MetricSpecification)var3.next();
            if (spec.isEnabled()) {
               enabledTypes.add(spec);
            }
         }

         specArray = (MetricSpecification[])enabledTypes.toArray(new MetricSpecification[enabledTypes.size()]);
      }

      return specArray;
   }

   public int getNumEnabledTypes() {
      return this.enabledTypeCount;
   }

   private void processHarvestedTypeDefinitions(WLDFHarvesterBean harvesterBean) {
      this.enabledTypeCount = 0;
      WLDFHarvestedTypeBean[] var2 = harvesterBean.getHarvestedTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WLDFHarvestedTypeBean specMBean = var2[var4];
         String typeName = specMBean.getName();
         String namespace = specMBean.getNamespace();
         if (!HarvesterLifecycleImpl.isAdminServer() && namespace.equals("DomainRuntime")) {
            DiagnosticsHarvesterLogger.logUnservicableHarvestedTypeNamespaceError(typeName, namespace);
         } else {
            MetricSpecification spec = (MetricSpecification)this.configuredTypesMap.get(typeName);
            boolean isEnabled = specMBean.isEnabled();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Harvester configuration for type: " + typeName + " is currently " + (isEnabled ? "enabled" : "disabled"));
            }

            if (spec == null) {
               spec = new MetricSpecification(typeName, namespace, specMBean.getHarvestedAttributes(), specMBean.getHarvestedInstances(), isEnabled);
               this.configuredTypesMap.put(typeName, spec);
            }

            if (!isEnabled) {
               DiagnosticsLogger.logHarvesterTypeIsDisabled(typeName);
            } else {
               ++this.enabledTypeCount;
            }
         }
      }

   }

   public boolean isEnabled() {
      return this.enabled;
   }

   boolean isConfiguredType(String typeName) {
      MetricSpecification spec = null;
      if (this.configuredTypesMap != null) {
         synchronized(this.configuredTypesMap) {
            spec = (MetricSpecification)this.configuredTypesMap.get(typeName);
         }
      }

      return spec != null;
   }
}
