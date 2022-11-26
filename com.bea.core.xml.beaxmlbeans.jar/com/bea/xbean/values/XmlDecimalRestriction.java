package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlDecimal;

public class XmlDecimalRestriction extends JavaDecimalHolderEx implements XmlDecimal {
   public XmlDecimalRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
