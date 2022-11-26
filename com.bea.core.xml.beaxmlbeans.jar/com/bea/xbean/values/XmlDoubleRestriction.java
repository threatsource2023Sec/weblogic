package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlDouble;

public class XmlDoubleRestriction extends JavaDoubleHolderEx implements XmlDouble {
   public XmlDoubleRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
