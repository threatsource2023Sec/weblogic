package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlDouble;

public class XmlDoubleRestriction extends JavaDoubleHolderEx implements XmlDouble {
   public XmlDoubleRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
