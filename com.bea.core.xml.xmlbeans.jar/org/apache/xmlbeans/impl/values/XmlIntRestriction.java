package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlInt;

public class XmlIntRestriction extends JavaIntHolderEx implements XmlInt {
   public XmlIntRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
