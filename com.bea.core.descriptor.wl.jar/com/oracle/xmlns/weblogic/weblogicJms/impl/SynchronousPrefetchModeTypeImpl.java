package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.SynchronousPrefetchModeType;

public class SynchronousPrefetchModeTypeImpl extends JavaStringEnumerationHolderEx implements SynchronousPrefetchModeType {
   private static final long serialVersionUID = 1L;

   public SynchronousPrefetchModeTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected SynchronousPrefetchModeTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
