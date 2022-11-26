package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.xb.xsdschema.TypeDerivationControl;
import com.bea.xml.SchemaType;

public class TypeDerivationControlImpl extends JavaStringEnumerationHolderEx implements TypeDerivationControl {
   public TypeDerivationControlImpl(SchemaType sType) {
      super(sType, false);
   }

   protected TypeDerivationControlImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
