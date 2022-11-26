package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicCoherence.TransportType;

public class TransportTypeImpl extends JavaStringEnumerationHolderEx implements TransportType {
   private static final long serialVersionUID = 1L;

   public TransportTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected TransportTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
