package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlLong;

public class XmlLongRestriction extends JavaLongHolderEx implements XmlLong {
   public XmlLongRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
