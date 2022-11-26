package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.xb.xsdschema.TypeDerivationControl;

public class TypeDerivationControlImpl extends JavaStringEnumerationHolderEx implements TypeDerivationControl {
   public TypeDerivationControlImpl(SchemaType sType) {
      super(sType, false);
   }

   protected TypeDerivationControlImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
