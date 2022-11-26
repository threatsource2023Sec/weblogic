package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlUnsignedLong;

public class XmlUnsignedLongImpl extends JavaIntegerHolderEx implements XmlUnsignedLong {
   public XmlUnsignedLongImpl() {
      super(XmlUnsignedLong.type, false);
   }

   public XmlUnsignedLongImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
