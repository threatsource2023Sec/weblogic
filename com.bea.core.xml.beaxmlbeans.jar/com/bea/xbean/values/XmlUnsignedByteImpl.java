package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlUnsignedByte;

public class XmlUnsignedByteImpl extends JavaIntHolderEx implements XmlUnsignedByte {
   public XmlUnsignedByteImpl() {
      super(XmlUnsignedByte.type, false);
   }

   public XmlUnsignedByteImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
