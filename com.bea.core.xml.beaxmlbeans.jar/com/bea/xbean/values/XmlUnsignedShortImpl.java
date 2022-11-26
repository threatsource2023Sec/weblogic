package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlUnsignedShort;

public class XmlUnsignedShortImpl extends JavaIntHolderEx implements XmlUnsignedShort {
   public XmlUnsignedShortImpl() {
      super(XmlUnsignedShort.type, false);
   }

   public XmlUnsignedShortImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
