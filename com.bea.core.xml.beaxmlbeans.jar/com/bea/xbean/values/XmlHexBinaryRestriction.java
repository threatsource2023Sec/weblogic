package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlHexBinary;

public class XmlHexBinaryRestriction extends JavaHexBinaryHolderEx implements XmlHexBinary {
   public XmlHexBinaryRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
