package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlInteger;

public class XmlIntegerRestriction extends JavaIntegerHolderEx implements XmlInteger {
   public XmlIntegerRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
