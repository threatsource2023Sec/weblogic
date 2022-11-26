package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlNonNegativeInteger;

public class XmlNonNegativeIntegerImpl extends JavaIntegerHolderEx implements XmlNonNegativeInteger {
   public XmlNonNegativeIntegerImpl() {
      super(XmlNonNegativeInteger.type, false);
   }

   public XmlNonNegativeIntegerImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
