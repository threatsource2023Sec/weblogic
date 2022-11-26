package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlUnsignedInt;

public class XmlUnsignedIntImpl extends JavaLongHolderEx implements XmlUnsignedInt {
   public XmlUnsignedIntImpl() {
      super(XmlUnsignedInt.type, false);
   }

   public XmlUnsignedIntImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
