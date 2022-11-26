package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBase64Binary;

public class XmlBase64BinaryRestriction extends JavaBase64HolderEx implements XmlBase64Binary {
   public XmlBase64BinaryRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
