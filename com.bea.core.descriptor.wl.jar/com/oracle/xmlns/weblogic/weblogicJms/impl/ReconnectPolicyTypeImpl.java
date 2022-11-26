package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ReconnectPolicyType;

public class ReconnectPolicyTypeImpl extends JavaStringEnumerationHolderEx implements ReconnectPolicyType {
   private static final long serialVersionUID = 1L;

   public ReconnectPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ReconnectPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
