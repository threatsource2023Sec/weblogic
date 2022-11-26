package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlNonPositiveInteger;

public class XmlNonPositiveIntegerImpl extends JavaIntegerHolderEx implements XmlNonPositiveInteger {
   public XmlNonPositiveIntegerImpl() {
      super(XmlNonPositiveInteger.type, false);
   }

   public XmlNonPositiveIntegerImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
