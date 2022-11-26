package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.StringType;
import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;

public class StringTypeImpl extends JavaStringHolderEx implements StringType {
   private static final long serialVersionUID = 1L;

   public StringTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected StringTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
