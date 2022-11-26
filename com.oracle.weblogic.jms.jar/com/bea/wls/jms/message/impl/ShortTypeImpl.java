package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.ShortType;
import com.bea.xbean.values.JavaIntHolderEx;
import com.bea.xml.SchemaType;

public class ShortTypeImpl extends JavaIntHolderEx implements ShortType {
   private static final long serialVersionUID = 1L;

   public ShortTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ShortTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
