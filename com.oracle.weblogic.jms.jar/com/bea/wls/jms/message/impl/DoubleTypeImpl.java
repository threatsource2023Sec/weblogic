package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.DoubleType;
import com.bea.xbean.values.JavaDoubleHolderEx;
import com.bea.xml.SchemaType;

public class DoubleTypeImpl extends JavaDoubleHolderEx implements DoubleType {
   private static final long serialVersionUID = 1L;

   public DoubleTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected DoubleTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
