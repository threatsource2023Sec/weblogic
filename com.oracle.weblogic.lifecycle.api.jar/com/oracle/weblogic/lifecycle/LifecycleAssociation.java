package com.oracle.weblogic.lifecycle;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LifecycleAssociation {
   LifecyclePartition getPartition1();

   LifecyclePartition getPartition2();
}
