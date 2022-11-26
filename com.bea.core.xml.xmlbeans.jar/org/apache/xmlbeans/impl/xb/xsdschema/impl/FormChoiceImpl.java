package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.xb.xsdschema.FormChoice;

public class FormChoiceImpl extends JavaStringEnumerationHolderEx implements FormChoice {
   public FormChoiceImpl(SchemaType sType) {
      super(sType, false);
   }

   protected FormChoiceImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
