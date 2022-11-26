package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ProducerLoadBalancingPolicyType;

public class ProducerLoadBalancingPolicyTypeImpl extends JavaStringEnumerationHolderEx implements ProducerLoadBalancingPolicyType {
   private static final long serialVersionUID = 1L;

   public ProducerLoadBalancingPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ProducerLoadBalancingPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
