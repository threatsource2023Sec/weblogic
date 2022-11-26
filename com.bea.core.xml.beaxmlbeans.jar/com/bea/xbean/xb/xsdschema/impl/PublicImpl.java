package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xbean.xb.xsdschema.Public;
import com.bea.xml.SchemaType;

public class PublicImpl extends JavaStringHolderEx implements Public {
   public PublicImpl(SchemaType sType) {
      super(sType, false);
   }

   protected PublicImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
