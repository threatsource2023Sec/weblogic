package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.BytesType;
import com.bea.xbean.values.JavaBase64HolderEx;
import com.bea.xml.SchemaType;

public class BytesTypeImpl extends JavaBase64HolderEx implements BytesType {
   private static final long serialVersionUID = 1L;

   public BytesTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected BytesTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
