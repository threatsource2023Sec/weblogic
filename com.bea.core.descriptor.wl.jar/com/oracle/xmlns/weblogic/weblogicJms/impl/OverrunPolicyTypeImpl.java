package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.OverrunPolicyType;

public class OverrunPolicyTypeImpl extends JavaStringEnumerationHolderEx implements OverrunPolicyType {
   private static final long serialVersionUID = 1L;

   public OverrunPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected OverrunPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
