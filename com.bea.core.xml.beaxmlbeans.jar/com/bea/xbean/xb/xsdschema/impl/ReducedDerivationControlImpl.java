package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.xb.xsdschema.ReducedDerivationControl;
import com.bea.xml.SchemaType;

public class ReducedDerivationControlImpl extends JavaStringEnumerationHolderEx implements ReducedDerivationControl {
   public ReducedDerivationControlImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ReducedDerivationControlImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
