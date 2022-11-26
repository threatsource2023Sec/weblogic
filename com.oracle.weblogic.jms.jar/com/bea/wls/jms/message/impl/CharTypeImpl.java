package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.CharType;
import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;

public class CharTypeImpl extends JavaStringHolderEx implements CharType {
   private static final long serialVersionUID = 1L;

   public CharTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected CharTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
