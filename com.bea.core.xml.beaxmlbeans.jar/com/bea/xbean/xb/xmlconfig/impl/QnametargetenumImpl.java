package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.xb.xmlconfig.Qnametargetenum;
import com.bea.xml.SchemaType;

public class QnametargetenumImpl extends JavaStringEnumerationHolderEx implements Qnametargetenum {
   public QnametargetenumImpl(SchemaType sType) {
      super(sType, false);
   }

   protected QnametargetenumImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
