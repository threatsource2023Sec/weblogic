package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.SubscriptionSharingPolicyType;

public class SubscriptionSharingPolicyTypeImpl extends JavaStringEnumerationHolderEx implements SubscriptionSharingPolicyType {
   private static final long serialVersionUID = 1L;

   public SubscriptionSharingPolicyTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected SubscriptionSharingPolicyTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
