package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.TransactionVersion;

public class TransactionVersionImpl extends JavaStringEnumerationHolderEx implements TransactionVersion {
   private static final long serialVersionUID = 1L;

   public TransactionVersionImpl(SchemaType sType) {
      super(sType, false);
   }

   protected TransactionVersionImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
