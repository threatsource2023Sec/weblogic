package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.TransactionFlowType;

public class TransactionFlowTypeImpl extends JavaStringEnumerationHolderEx implements TransactionFlowType {
   private static final long serialVersionUID = 1L;

   public TransactionFlowTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected TransactionFlowTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
