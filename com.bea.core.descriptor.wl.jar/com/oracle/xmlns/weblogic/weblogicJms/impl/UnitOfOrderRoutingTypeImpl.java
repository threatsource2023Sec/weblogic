package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.UnitOfOrderRoutingType;

public class UnitOfOrderRoutingTypeImpl extends JavaStringEnumerationHolderEx implements UnitOfOrderRoutingType {
   private static final long serialVersionUID = 1L;

   public UnitOfOrderRoutingTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected UnitOfOrderRoutingTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
