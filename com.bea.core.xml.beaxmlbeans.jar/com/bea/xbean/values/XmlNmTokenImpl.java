package com.bea.xbean.values;

import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.common.XMLChar;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlNMTOKEN;

public class XmlNmTokenImpl extends JavaStringHolderEx implements XmlNMTOKEN {
   public XmlNmTokenImpl() {
      super(XmlNMTOKEN.type, false);
   }

   public XmlNmTokenImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }

   public static void validateLexical(String v, ValidationContext context) {
      if (!XMLChar.isValidNmtoken(v)) {
         context.invalid("NMTOKEN", new Object[]{v});
      }
   }
}
