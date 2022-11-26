package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.LongType;
import com.bea.xbean.values.JavaLongHolderEx;
import com.bea.xml.SchemaType;

public class LongTypeImpl extends JavaLongHolderEx implements LongType {
   private static final long serialVersionUID = 1L;

   public LongTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected LongTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
