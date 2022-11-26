package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.xb.xsdschema.ReducedDerivationControl;

public class ReducedDerivationControlImpl extends JavaStringEnumerationHolderEx implements ReducedDerivationControl {
   public ReducedDerivationControlImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ReducedDerivationControlImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
