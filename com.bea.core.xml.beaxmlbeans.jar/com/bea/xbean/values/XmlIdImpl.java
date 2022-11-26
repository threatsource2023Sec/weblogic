package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlID;

public class XmlIdImpl extends JavaStringHolderEx implements XmlID {
   public XmlIdImpl() {
      super(XmlID.type, false);
   }

   public XmlIdImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
