package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlLong;

public class XmlLongRestriction extends JavaLongHolderEx implements XmlLong {
   public XmlLongRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
