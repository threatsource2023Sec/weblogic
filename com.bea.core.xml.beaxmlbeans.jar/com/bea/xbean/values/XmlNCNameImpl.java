package com.bea.xbean.values;

import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.common.XMLChar;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlNCName;

public class XmlNCNameImpl extends JavaStringHolderEx implements XmlNCName {
   public XmlNCNameImpl() {
      super(XmlNCName.type, false);
   }

   public XmlNCNameImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }

   public static void validateLexical(String v, ValidationContext context) {
      if (!XMLChar.isValidNCName(v)) {
         context.invalid("NCName", new Object[]{v});
      }
   }
}
