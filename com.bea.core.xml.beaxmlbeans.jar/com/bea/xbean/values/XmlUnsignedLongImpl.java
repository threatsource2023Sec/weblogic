package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlUnsignedLong;

public class XmlUnsignedLongImpl extends JavaIntegerHolderEx implements XmlUnsignedLong {
   public XmlUnsignedLongImpl() {
      super(XmlUnsignedLong.type, false);
   }

   public XmlUnsignedLongImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
