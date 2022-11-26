package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNMTOKEN;
import org.apache.xmlbeans.impl.common.ValidationContext;
import org.apache.xmlbeans.impl.common.XMLChar;

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
