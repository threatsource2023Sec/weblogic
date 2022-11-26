package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryModeType;

public class DeliveryModeTypeImpl extends JavaStringEnumerationHolderEx implements DeliveryModeType {
   private static final long serialVersionUID = 1L;

   public DeliveryModeTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected DeliveryModeTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
