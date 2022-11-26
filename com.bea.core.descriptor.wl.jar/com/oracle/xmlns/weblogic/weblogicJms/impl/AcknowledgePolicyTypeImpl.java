package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.AcknowledgePolicyType;

public class AcknowledgePolicyTypeImpl extends JavaStringEnumerationHolderEx implements AcknowledgePolicyType {
   private static final long serialVersionUID = 1L;

   public AcknowledgePolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected AcknowledgePolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
