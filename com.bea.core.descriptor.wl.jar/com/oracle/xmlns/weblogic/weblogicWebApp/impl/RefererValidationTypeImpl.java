package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebApp.RefererValidationType;

public class RefererValidationTypeImpl extends JavaStringEnumerationHolderEx implements RefererValidationType {
   private static final long serialVersionUID = 1L;

   public RefererValidationTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected RefererValidationTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
