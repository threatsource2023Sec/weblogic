package com.bea.adaptive.harvester;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.JMException;

public interface Harvester {
   String getNamespace();

   int addWatchedValues(String var1, WatchedValues var2, HarvestCallback var3) throws IOException, JMException;

   int deleteMetrics(int var1, Collection var2);

   int deleteWatchedValues(int[] var1);

   void deleteWatchedValues(WatchedValues var1);

   int disableMetrics(int var1, Integer[] var2);

   int enableMetrics(int var1, Integer[] var2);

   void extendWatchedValues(int var1, WatchedValues var2);

   String[][] getHarvestableAttributes(String var1, String var2) throws IOException;

   List getHarvestedAttributes(int var1, String var2, String var3);

   List getHarvestedInstances(int var1, String var2, String var3);

   List getHarvestedTypes(int var1, String var2);

   List getKnownHarvestableInstances(String var1, String var2) throws IOException;

   String[][] getKnownHarvestableTypes(String var1) throws IOException;

   String getName();

   WatchedValues.Values[] getPendingMetrics(int var1);

   List getUnharvestableAttributes(int var1, String var2, String var3);

   List getDisabledAttributes(int var1, String var2, String var3);

   void harvest(Map var1);

   void oneShotHarvest(WatchedValues var1);

   int isTypeHandled(String var1);

   void deallocate();

   void deallocate(boolean var1);

   Collection validateWatchedValues(WatchedValues var1);

   String getTypeForInstance(String var1);

   void resolveMetrics(int var1, Set var2);

   void resolveAllMetrics(int[] var1);

   void setRemoveAttributesWithProblems(boolean var1);

   boolean removeAttributesWithProblems();

   void setAttributeValidationEnabled(boolean var1);

   boolean isAttributeValidationEnabled();
}
