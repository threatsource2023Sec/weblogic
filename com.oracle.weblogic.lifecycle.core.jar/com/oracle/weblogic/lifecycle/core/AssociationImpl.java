package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleAssociation;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import org.jvnet.hk2.annotations.Service;

@Service
public class AssociationImpl implements LifecycleAssociation {
   private LifecyclePartition partition1;
   private LifecyclePartition partition2;

   public LifecyclePartition getPartition1() {
      return this.partition1;
   }

   void setPartition1(LifecyclePartition lp) {
      this.partition1 = lp;
   }

   public LifecyclePartition getPartition2() {
      return this.partition2;
   }

   void setPartition2(LifecyclePartition lp) {
      this.partition2 = lp;
   }
}
