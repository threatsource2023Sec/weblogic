package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlInteger;

public class XmlIntegerRestriction extends JavaIntegerHolderEx implements XmlInteger {
   public XmlIntegerRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
