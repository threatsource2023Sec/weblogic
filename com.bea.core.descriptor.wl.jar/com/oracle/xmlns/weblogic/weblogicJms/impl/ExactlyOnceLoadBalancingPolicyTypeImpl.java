package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ExactlyOnceLoadBalancingPolicyType;

public class ExactlyOnceLoadBalancingPolicyTypeImpl extends JavaStringEnumerationHolderEx implements ExactlyOnceLoadBalancingPolicyType {
   private static final long serialVersionUID = 1L;

   public ExactlyOnceLoadBalancingPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ExactlyOnceLoadBalancingPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
