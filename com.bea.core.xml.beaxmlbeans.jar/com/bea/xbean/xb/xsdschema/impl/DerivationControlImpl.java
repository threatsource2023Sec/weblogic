package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.xb.xsdschema.DerivationControl;
import com.bea.xml.SchemaType;

public class DerivationControlImpl extends JavaStringEnumerationHolderEx implements DerivationControl {
   public DerivationControlImpl(SchemaType sType) {
      super(sType, false);
   }

   protected DerivationControlImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
