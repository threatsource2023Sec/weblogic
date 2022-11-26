package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNonNegativeInteger;

public class XmlNonNegativeIntegerImpl extends JavaIntegerHolderEx implements XmlNonNegativeInteger {
   public XmlNonNegativeIntegerImpl() {
      super(XmlNonNegativeInteger.type, false);
   }

   public XmlNonNegativeIntegerImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
