package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.xb.xsdschema.FormChoice;
import com.bea.xml.SchemaType;

public class FormChoiceImpl extends JavaStringEnumerationHolderEx implements FormChoice {
   public FormChoiceImpl(SchemaType sType) {
      super(sType, false);
   }

   protected FormChoiceImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
