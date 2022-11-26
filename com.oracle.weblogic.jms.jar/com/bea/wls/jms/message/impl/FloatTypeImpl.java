package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.FloatType;
import com.bea.xbean.values.JavaFloatHolderEx;
import com.bea.xml.SchemaType;

public class FloatTypeImpl extends JavaFloatHolderEx implements FloatType {
   private static final long serialVersionUID = 1L;

   public FloatTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected FloatTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
