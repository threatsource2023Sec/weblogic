package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.KeyTypeType;

public class KeyTypeTypeImpl extends JavaStringEnumerationHolderEx implements KeyTypeType {
   private static final long serialVersionUID = 1L;

   public KeyTypeTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected KeyTypeTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
