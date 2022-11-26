package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.IntType;
import com.bea.xbean.values.JavaIntegerHolderEx;
import com.bea.xml.SchemaType;

public class IntTypeImpl extends JavaIntegerHolderEx implements IntType {
   private static final long serialVersionUID = 1L;

   public IntTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected IntTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
