package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringHolderEx;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class PublicImpl extends JavaStringHolderEx implements Public {
   public PublicImpl(SchemaType sType) {
      super(sType, false);
   }

   protected PublicImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
