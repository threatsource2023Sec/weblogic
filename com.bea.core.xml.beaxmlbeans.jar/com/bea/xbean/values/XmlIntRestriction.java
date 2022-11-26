package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlInt;

public class XmlIntRestriction extends JavaIntHolderEx implements XmlInt {
   public XmlIntRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
