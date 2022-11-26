package com.bea.adaptive.harvester.jmx;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface PartitionRuntimeMBeanSetManager {
   boolean isTypeInSet(String var1);
}
