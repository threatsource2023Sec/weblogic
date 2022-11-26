package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlShort;

public class XmlShortImpl extends JavaIntHolderEx implements XmlShort {
   public XmlShortImpl() {
      super(XmlShort.type, false);
   }

   public XmlShortImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
