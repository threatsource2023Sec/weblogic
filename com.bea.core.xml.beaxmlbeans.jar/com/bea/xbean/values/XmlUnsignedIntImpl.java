package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlUnsignedInt;

public class XmlUnsignedIntImpl extends JavaLongHolderEx implements XmlUnsignedInt {
   public XmlUnsignedIntImpl() {
      super(XmlUnsignedInt.type, false);
   }

   public XmlUnsignedIntImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
