package weblogic.management.runtime;

import java.util.Map;
import weblogic.diagnostics.harvester.HarvesterException;

public interface WLDFHarvesterRuntimeMBean extends WLDFPartitionHarvesterRuntimeMBean {
   /** @deprecated */
   @Deprecated
   String[][] getHarvestableAttributes(String var1) throws HarvesterException.HarvestableTypesNotFoundException, HarvesterException.AmbiguousTypeName, HarvesterException.TypeNotHarvestable;

   String[][] getHarvestableAttributes(String var1, String var2);

   /** @deprecated */
   @Deprecated
   String[][] getHarvestableAttributesForInstance(String var1) throws HarvesterException.HarvestableTypesNotFoundException, HarvesterException.AmbiguousTypeName, HarvesterException.TypeNotHarvestable;

   String[][] getHarvestableAttributesForInstance(String var1, String var2);

   /** @deprecated */
   @Deprecated
   String[] getCurrentlyHarvestedAttributes(String var1) throws HarvesterException.MissingConfigurationType, HarvesterException.HarvestingNotEnabled, HarvesterException.AmbiguousTypeName;

   String[] getKnownHarvestableTypes();

   String[] getKnownHarvestableTypes(String var1);

   String[] getKnownHarvestableTypes(String var1, String var2);

   /** @deprecated */
   @Deprecated
   String[] getKnownHarvestableInstances(String var1) throws HarvesterException.HarvestableTypesNotFoundException, HarvesterException.AmbiguousTypeName, HarvesterException.TypeNotHarvestable;

   String[] getKnownHarvestableInstances(String var1, String var2) throws HarvesterException.HarvestableTypesNotFoundException, HarvesterException.AmbiguousTypeName, HarvesterException.TypeNotHarvestable;

   String[] getKnownHarvestableInstances(String var1, String var2, String var3);

   /** @deprecated */
   @Deprecated
   String[] getCurrentlyHarvestedInstances(String var1) throws HarvesterException.MissingConfigurationType, HarvesterException.HarvestingNotEnabled, HarvesterException.AmbiguousTypeName;

   /** @deprecated */
   @Deprecated
   String getHarvestableType(String var1) throws HarvesterException.HarvestableInstancesNotFoundException, HarvesterException.AmbiguousInstanceName;

   String[] getConfiguredNamespaces() throws HarvesterException.HarvestingNotEnabled;

   String getDefaultNamespace() throws HarvesterException.HarvestingNotEnabled;

   /** @deprecated */
   @Deprecated
   long getSamplePeriod() throws HarvesterException.HarvestingNotEnabled;

   /** @deprecated */
   @Deprecated
   long getTotalDataSampleCount() throws HarvesterException.HarvestingNotEnabled;

   Map getAttributeInfoForAllTypes();

   Map getInstancesForAllTypes();

   /** @deprecated */
   @Deprecated
   long getCurrentDataSampleCount() throws HarvesterException.HarvestingNotEnabled;

   /** @deprecated */
   @Deprecated
   long getCurrentSnapshotStartTime() throws HarvesterException.HarvestingNotEnabled;

   /** @deprecated */
   @Deprecated
   long getCurrentSnapshotElapsedTime() throws HarvesterException.HarvestingNotEnabled;

   /** @deprecated */
   @Deprecated
   long getTotalSamplingTimeOutlierCount();

   /** @deprecated */
   @Deprecated
   boolean isCurrentSampleTimeAnOutlier();

   /** @deprecated */
   @Deprecated
   float getOutlierDetectionFactor();
}
