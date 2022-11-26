package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ExpirationPolicyType;

public class ExpirationPolicyTypeImpl extends JavaStringEnumerationHolderEx implements ExpirationPolicyType {
   private static final long serialVersionUID = 1L;

   public ExpirationPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ExpirationPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
