package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.SecurityPolicyType;

public class SecurityPolicyTypeImpl extends JavaStringEnumerationHolderEx implements SecurityPolicyType {
   private static final long serialVersionUID = 1L;

   public SecurityPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected SecurityPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
