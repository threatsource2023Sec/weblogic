package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ForwardingPolicyType;

public class ForwardingPolicyTypeImpl extends JavaStringEnumerationHolderEx implements ForwardingPolicyType {
   private static final long serialVersionUID = 1L;

   public ForwardingPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ForwardingPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
