package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ClientIdPolicyType;

public class ClientIdPolicyTypeImpl extends JavaStringEnumerationHolderEx implements ClientIdPolicyType {
   private static final long serialVersionUID = 1L;

   public ClientIdPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ClientIdPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
