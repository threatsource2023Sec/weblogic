package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNonPositiveInteger;

public class XmlNonPositiveIntegerImpl extends JavaIntegerHolderEx implements XmlNonPositiveInteger {
   public XmlNonPositiveIntegerImpl() {
      super(XmlNonPositiveInteger.type, false);
   }

   public XmlNonPositiveIntegerImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
