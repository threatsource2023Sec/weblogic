package com.bea.adaptive.harvester.jmx;

import java.util.List;

public interface MetricMetaDataResolver {
   String getTypeForInstance(String var1);

   List getHarvestableAttributesForInstance(String var1, String var2);
}
