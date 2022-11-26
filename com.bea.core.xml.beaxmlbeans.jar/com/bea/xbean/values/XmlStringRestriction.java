package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlString;

public class XmlStringRestriction extends JavaStringHolderEx implements XmlString {
   public XmlStringRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
