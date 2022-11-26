package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlIDREF;

public class XmlIdRefImpl extends JavaStringHolderEx implements XmlIDREF {
   public XmlIdRefImpl() {
      super(XmlIDREF.type, false);
   }

   public XmlIdRefImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
