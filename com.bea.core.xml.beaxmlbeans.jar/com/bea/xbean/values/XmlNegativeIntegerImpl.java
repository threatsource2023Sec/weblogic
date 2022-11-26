package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlNegativeInteger;

public class XmlNegativeIntegerImpl extends JavaIntegerHolderEx implements XmlNegativeInteger {
   public XmlNegativeIntegerImpl() {
      super(XmlNegativeInteger.type, false);
   }

   public XmlNegativeIntegerImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
