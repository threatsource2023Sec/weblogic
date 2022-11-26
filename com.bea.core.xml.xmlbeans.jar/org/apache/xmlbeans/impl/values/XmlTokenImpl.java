package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlToken;

public class XmlTokenImpl extends JavaStringHolderEx implements XmlToken {
   public XmlTokenImpl() {
      super(XmlToken.type, false);
   }

   public XmlTokenImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
