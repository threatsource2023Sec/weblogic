package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlToken;

public class XmlTokenImpl extends JavaStringHolderEx implements XmlToken {
   public XmlTokenImpl() {
      super(XmlToken.type, false);
   }

   public XmlTokenImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
