package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBase64Binary;

public class XmlBase64BinaryRestriction extends JavaBase64HolderEx implements XmlBase64Binary {
   public XmlBase64BinaryRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
