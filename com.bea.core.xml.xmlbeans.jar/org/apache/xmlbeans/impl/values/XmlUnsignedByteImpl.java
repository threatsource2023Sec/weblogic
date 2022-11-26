package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlUnsignedByte;

public class XmlUnsignedByteImpl extends JavaIntHolderEx implements XmlUnsignedByte {
   public XmlUnsignedByteImpl() {
      super(XmlUnsignedByte.type, false);
   }

   public XmlUnsignedByteImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
