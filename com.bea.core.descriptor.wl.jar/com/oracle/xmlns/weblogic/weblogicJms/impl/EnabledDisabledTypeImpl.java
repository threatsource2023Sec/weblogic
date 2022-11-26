package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.EnabledDisabledType;

public class EnabledDisabledTypeImpl extends JavaStringEnumerationHolderEx implements EnabledDisabledType {
   private static final long serialVersionUID = 1L;

   public EnabledDisabledTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected EnabledDisabledTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
