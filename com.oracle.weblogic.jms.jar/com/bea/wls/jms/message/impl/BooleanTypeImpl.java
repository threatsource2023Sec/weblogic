package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.BooleanType;
import com.bea.xbean.values.JavaBooleanHolderEx;
import com.bea.xml.SchemaType;

public class BooleanTypeImpl extends JavaBooleanHolderEx implements BooleanType {
   private static final long serialVersionUID = 1L;

   public BooleanTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected BooleanTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
