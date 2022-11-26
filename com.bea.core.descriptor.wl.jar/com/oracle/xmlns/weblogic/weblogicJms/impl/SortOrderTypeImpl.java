package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.SortOrderType;

public class SortOrderTypeImpl extends JavaStringEnumerationHolderEx implements SortOrderType {
   private static final long serialVersionUID = 1L;

   public SortOrderTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected SortOrderTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
