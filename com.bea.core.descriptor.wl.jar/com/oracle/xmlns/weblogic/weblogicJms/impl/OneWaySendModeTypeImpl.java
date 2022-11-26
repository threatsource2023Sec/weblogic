package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.OneWaySendModeType;

public class OneWaySendModeTypeImpl extends JavaStringEnumerationHolderEx implements OneWaySendModeType {
   private static final long serialVersionUID = 1L;

   public OneWaySendModeTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected OneWaySendModeTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
