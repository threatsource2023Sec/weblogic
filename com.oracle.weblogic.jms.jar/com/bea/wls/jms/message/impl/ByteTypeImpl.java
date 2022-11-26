package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.ByteType;
import com.bea.xbean.values.JavaIntHolderEx;
import com.bea.xml.SchemaType;

public class ByteTypeImpl extends JavaIntHolderEx implements ByteType {
   private static final long serialVersionUID = 1L;

   public ByteTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ByteTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
