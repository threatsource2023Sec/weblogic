package org.apache.xmlbeans.impl.xb.xmlconfig.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.xb.xmlconfig.Qnametargetenum;

public class QnametargetenumImpl extends JavaStringEnumerationHolderEx implements Qnametargetenum {
   public QnametargetenumImpl(SchemaType sType) {
      super(sType, false);
   }

   protected QnametargetenumImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
