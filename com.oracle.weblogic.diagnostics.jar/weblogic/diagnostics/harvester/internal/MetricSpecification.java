package weblogic.diagnostics.harvester.internal;

import java.util.HashSet;
import weblogic.diagnostics.harvester.I18NConstants;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;

final class MetricSpecification implements I18NConstants {
   private String typeName = null;
   private boolean isEnabled = true;
   private HashSet requestedHarvestableAttributes = null;
   private HashSet requestedHarvestableInstances = null;
   private String namespace;

   MetricSpecification(String typeName, String namespace, String[] attributes, String[] instances, boolean isEnabled) {
      if (typeName == null) {
         throw new NullPointerException(TYPE_I18N);
      } else {
         this.namespace = namespace;
         this.typeName = typeName;
         this.isEnabled = isEnabled;
         String[] localArray;
         int var8;
         if (attributes != null && attributes.length > 0) {
            this.requestedHarvestableAttributes = new HashSet(attributes.length);
            localArray = WLDFHarvesterUtils.normalizeAttributeSpecs(typeName, attributes);
            String[] var7 = localArray;
            var8 = localArray.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String attr = var7[var9];
               this.requestedHarvestableAttributes.add(attr);
            }
         }

         if (instances != null && instances.length > 0) {
            this.requestedHarvestableInstances = new HashSet(instances.length);
            localArray = instances;
            int var11 = instances.length;

            for(var8 = 0; var8 < var11; ++var8) {
               String instanceName = localArray[var8];
               this.requestedHarvestableInstances.add(instanceName);
            }
         }

      }
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String[] getRequestedHarvestableAttributes() {
      String[] result = new String[0];
      if (this.requestedHarvestableAttributes != null) {
         result = (String[])this.requestedHarvestableAttributes.toArray(new String[this.requestedHarvestableAttributes.size()]);
      }

      return result;
   }

   public String[] getRequestedHarvestableInstances() {
      String[] result = new String[0];
      if (this.requestedHarvestableInstances != null) {
         result = (String[])this.requestedHarvestableInstances.toArray(new String[this.requestedHarvestableInstances.size()]);
      }

      return result;
   }

   String getTypeName() {
      return this.typeName;
   }

   boolean isEnabled() {
      return this.isEnabled;
   }

   void setEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
   }
}
