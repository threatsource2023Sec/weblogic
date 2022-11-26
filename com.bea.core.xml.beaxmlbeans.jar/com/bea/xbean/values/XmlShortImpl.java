package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlShort;

public class XmlShortImpl extends JavaIntHolderEx implements XmlShort {
   public XmlShortImpl() {
      super(XmlShort.type, false);
   }

   public XmlShortImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
