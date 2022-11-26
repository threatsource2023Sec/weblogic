package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TransactionVersion;

public class TransactionVersionImpl extends JavaStringEnumerationHolderEx implements TransactionVersion {
   private static final long serialVersionUID = 1L;

   public TransactionVersionImpl(SchemaType sType) {
      super(sType, false);
   }

   protected TransactionVersionImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
